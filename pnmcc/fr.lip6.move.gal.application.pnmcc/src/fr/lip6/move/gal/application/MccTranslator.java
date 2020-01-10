package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.FusionBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.PropertySimplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.louvain.GraphBuilder;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;
import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.serialization.SerializationUtil;

public class MccTranslator {

	private Specification spec;
	private IOrder order;
	private String folder;
	private String examination;
	private Support simplifiedVars = new Support();
	private boolean isSafeNet = false;
	private boolean useLouvain;
	private boolean isFlatten = false;
	private boolean isHier = false;
	
	public MccTranslator(String pwd, String examination, boolean useLouvain) {
		this.folder = pwd;
		this.examination = examination;
		this.useLouvain = useLouvain;
	}


	public Specification getSpec() {
		return spec;
	}
	/**
	 * Sets the spec and order attributes, spec is set to result of PNML tranlsation and order is set to null if no nupn/computed order is available.
	 * @param folder input folder absolute path, containing a model.pnml file
	 * @param reversible set to true to add P >= 0 constraints in guards of transitions adding to P, ensuring predecessor relation is inverse to succ. 
	 * @throws IOException if file can't be found
	 */
	public void transformPNML() throws IOException {
		File ff = new File(folder+ "/"+ "model.pnml");
		if (ff != null && ff.exists()) {
			getLog().info("Parsing pnml file : " + ff.getAbsolutePath());

			PnmlToGalTransformer trans = new PnmlToGalTransformer();
			spec = trans.transform(ff.toURI());
			order = trans.getOrder();
			isSafeNet = trans.foundNupn();
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
			}
		} else {
			throw new IOException("Cannot open file "+ff.getAbsolutePath());
		}
	}
	
	public void loadGAL(String gal) throws IOException {
		File ff = new File(folder+ "/"+ gal);
		if (ff != null && ff.exists()) {
			getLog().info("Parsing GAL file : " + ff.getAbsolutePath());

			spec = SerializationUtil.fileToGalSystem(ff.getCanonicalPath());
			order = null;
			isSafeNet = false;
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
			}
		} else {
			throw new IOException("Cannot open file "+ff.getAbsolutePath());
		}
	}
	
	
	public boolean applyOrder(Support supp) {
		if (hasStructure()) {
			getLog().info("Applying decomposition ");
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));
			CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			patchOrderForArrays();
			rewriteConstantSums();							
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));			
			Specification saved = EcoreUtil.copy(spec);

			try {
				if (useLouvain && order==null) {
					
					INextBuilder inb = INextBuilder.build(spec);
					List<BitSet> constraints = new ArrayList<>();
					for (Property p : spec.getProperties()) {
						for (TreeIterator<EObject> it=p.eAllContents() ; it.hasNext() ;) {
							EObject obj = it.next();
							if (obj instanceof Comparison) {
								Comparison cmp = (Comparison) obj;
								BitSet tmp = new BitSet();
								NextSupportAnalyzer.computeQualifiedSupport(cmp, tmp, inb);
								if (tmp.cardinality() > 1) {
									constraints.add(tmp);
								}
							}
						}
					}
					setOrder(GraphBuilder.computeLouvain(inb,true,constraints));

//					boolean hasLarge = false;
//					for ( Integer init: inb.getInitial()) {
//						if (init >= 10) {
//							// avoid hierarchy
//							hasLarge=true;
//							break;
//						}
//					}
//					if (! hasLarge)
				}

				getLog().fine(order.toString());
				supp.addAll(CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order));
				if (! useLouvain) {
					CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
					patchOrderForArrays();
				}
				if (!canDecompose()) {
					FusionBuilder.toSingleGAL(spec);
				}
				isHier = true;
				isFlatten = true;
			} catch (Exception e) {
				getLog().warning("Could not apply decomposition. Using flat GAL structure.");
				e.printStackTrace();
				spec = saved ;
				return false ;
			}
			return true;
		}
		return false;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
		
	}

	public boolean isSafeNet() {
		return isSafeNet;
	}
	
	public boolean hasStructure() {
		return order != null || useLouvain;
	}


	public void flattenSpec(boolean withHierarchy) {
		if (withHierarchy && !isHier) {
			if (applyOrder(simplifiedVars)) {
				return;
			}
		}
		if (!isFlatten) {
			rewriteConstantSums();							
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));
			CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			patchOrderForArrays();			
			isFlatten = true;
		}
	}


	private void patchOrderForArrays() {
		if (order != null) {
			order.accept(new IOrderVisitor<Void>() {

				@Override
				public Void visitComposite(CompositeGalOrder o) {
					for (IOrder sub : o.getChildren()) {
						sub.accept(this);
					}
					return null;
				}

				@Override
				public Void visitVars(VarOrder varOrder) {
					for (int i = 0 ; i < varOrder.getVars().size() ; i++) {
						if (varOrder.getVars().get(i).contains("[")) {
							varOrder.getVars().set(i, varOrder.getVars().get(i).replace('[', '_').replaceAll("]", ""));
						}
					}
					return null;
				}
			});
		}
	}

	/** Job : parse the property files into the Specification.
	 * The examination determines what happens in here. 
	 * @throws IOException */
	public void loadProperties() throws IOException {
		if (examination.equals("StateSpace")) {
			return ;
		} else {
			long time = System.currentTimeMillis();
			String propff = folder +"/" +  examination + ".xml";
			Properties props = PropertyParser.fileToProperties(propff , spec);
			spec = ToGalTransformer.toGal(props);
			if (isSafeNet) {
				rewriteVariableComparisons(spec);
			}
			System.out.println("Properties parsed from file "+propff+" in "+ (System.currentTimeMillis() - time) + " ms.");
		}
	}

	private void rewriteVariableComparisons(Specification spec) {
		Map<BooleanExpression, BooleanExpression> todo = new HashMap<BooleanExpression, BooleanExpression>();
		for (Property prop : spec.getProperties()) {
			for (TreeIterator<EObject> it = prop.getBody().eAllContents(); it.hasNext() ;) {
				EObject obj = it.next();
				if (obj instanceof IntExpression) {
					it.prune();
				} else if (obj instanceof Comparison) {
					Comparison cmp = (Comparison) obj;
					if (cmp.getLeft() instanceof Reference && cmp.getRight() instanceof Reference) {
						BooleanExpression res = PropertySimplifier.assumeOneBounded(cmp);
						todo.put(cmp,res);
					}
					it.prune();
				}
			}
		}
		for (Entry<BooleanExpression, BooleanExpression> ent : todo.entrySet()) {
			EcoreUtil.replace(ent.getKey(), ent.getValue());
		}
	}




	private boolean canDecompose() {
		boolean canDecompose = true;
		for (Property prop : spec.getProperties()) {
			if (containsAdditionOrComparison(prop)) {
				canDecompose = false;
				break;
			}
		}
		return canDecompose;
	}
	
	private boolean containsAdditionOrComparison(Property prop) {
		for (TreeIterator<EObject> it = prop.eAllContents() ; it.hasNext() ;  ) {
			EObject obj = it.next();
			if (obj instanceof BinaryIntExpression) {
				return true; 
			} else if (obj instanceof Comparison) {
				Comparison cmp = (Comparison) obj;
				if (! (cmp.getLeft() instanceof Constant || cmp.getRight() instanceof Constant)) {
					return true;
				}
			}
		}
		return false;
	}


	public int countMissingTokens() {
		int addedTokens = 0;
		if ("StateSpace".equals(examination)) {
			for (ISupportVariable var : simplifiedVars) {
				IntExpression ie = var.getInitialValue();
				if (ie instanceof Constant) {
					Constant cte = (Constant) ie;
					addedTokens += cte.getValue();
				} else {
					System.err.println("Expected initially simplified variable to have constant value.");
				}
			}
		}
		return addedTokens;
	}




	public String getFolder() {
		return folder;
	}

	/** removes any properties with addition in them, CTL parser can't deal with them currently. */
	public void removeAdditionProperties() {
		spec.getProperties().removeIf(
				prop ->
				{
					for (TreeIterator<EObject> it = prop.eAllContents() ; it.hasNext() ;  ) {
						EObject obj = it.next();
						if (obj instanceof BinaryIntExpression) {
							return true;
						}
					}
					return false;
				}
				);
	}


	
	public void setOrder(IOrder order) {
		this.order = order;
	}
	
	public void setSpec(Specification spec) {
		this.spec = spec;
		this.isFlatten = false;
		this.isHier = spec.getTypes().size() > 1;
	}


	public MccTranslator copy() {		
		MccTranslator copy = new MccTranslator(folder, examination, useLouvain);
		copy.order = this.order;
		copy.isSafeNet = this.isSafeNet;
		copy.simplifiedVars = new Support(simplifiedVars);
		copy.spec = EcoreUtil.copy(spec);
		copy.useLouvain = useLouvain;
		return copy ;
	}


	public void setLouvain(boolean b) {
		this.useLouvain = b;
	}

	public void rewriteConstantSums() {
		try {
		Map<List<Variable>, List<IntExpression>> sumMap = collectSums();
		for (Entry<List<Variable>, List<IntExpression>> entry : sumMap.entrySet()) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			boolean isConst = true;
			for (Transition t: gal.getTransitions()) {
				int res = computeEffect(entry, t);
				if (res != 0) {
					isConst = false;
					break;
				}
			}
			if (isConst) {
				Variable sum = createSumOfVariable(entry);
				for (IntExpression head : entry.getValue()) {
					EcoreUtil.replace(head, EcoreUtil.copy(sum.getValue()));
				}		
				System.out.println("Successfully replaced "+ entry.getValue().size() + " occurrences of a constant sum of "+ entry.getKey().size() + " variables");
			}
		}
		} catch (Exception e) {
			System.out.println("Problem detected in Rewrite constant sums.");
			e.printStackTrace();
		}
	}
	
	
	public void rewriteSums() {
		Map<List<Variable>, List<IntExpression>> sumMap = collectSums();
		for (Entry<List<Variable>, List<IntExpression>> entry : sumMap.entrySet()) {
			Variable sum = createSumOfVariable(entry);
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();			
			for (Transition t: gal.getTransitions()) {
				int res = computeEffect(entry, t);
				if (res != 0) {
					t.getActions().add(GF2.createIncrement(sum, res));
					if (res < 0) {
						t.setGuard(GF2.and(GF2.createComparison(GF2.createVariableRef(sum), ComparisonOperators.GE, GF2.constant(-res)), t.getGuard()));
					}
				}
			}
			gal.getVariables().add(sum);
			for (IntExpression head : entry.getValue()) {
				EcoreUtil.replace(head, GF2.createVariableRef(sum));
			}
			System.out.println("Successfully replaced "+ entry.getValue().size() + " occurrences of a sum of "+ entry.getKey().size() + " variables");
		}
	}


	public int computeEffect(Entry<List<Variable>, List<IntExpression>> entry, Transition t) {
		int res = 0;
		for (Statement a : t.getActions()) {
			if (a instanceof Assignment) {
				Assignment ass = (Assignment) a;
				Variable v = (Variable) ((VariableReference) ass.getLeft()).getRef();
				if (entry.getKey().contains(v)) {
					if (ass.getType()==AssignType.INCR) {
						res += ((Constant) ass.getRight()).getValue();
					} else if (ass.getType()==AssignType.DECR) {
						res -= ((Constant) ass.getRight()).getValue();
					}
				}
			}
		}
		return res;
	}


	public Variable createSumOfVariable(Entry<List<Variable>, List<IntExpression>> entry) {
		StringBuilder sb = new StringBuilder();
		for (Variable v: entry.getKey()) {
			sb.append(v.getName()).append("_");
		}
		Variable sum = GalFactory.eINSTANCE.createVariable();
		sum.setName(sb.toString());
		int summed = entry.getKey().stream().map(v -> ((Constant)v.getValue()).getValue()).reduce(0, (x,y)->x+y);
		sum.setValue(GF2.constant(summed));
		return sum;
	}


	public Map<List<Variable>, List<IntExpression>> collectSums() {
		Map<List<Variable>,List<IntExpression> > sumMap = new HashMap<>();
		for (Property p : spec.getProperties()) {
			for (TreeIterator<EObject> it=p.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof BinaryIntExpression) {
					BinaryIntExpression bin = (BinaryIntExpression) obj;
					if (bin.getOp().equals("+")) {
						List<Variable> vars = new ArrayList<Variable>();
						try {
							collectChildren(bin, vars);
							vars.sort((a,b) -> a.getName().compareTo(b.getName()));
							sumMap.compute(vars, (k,v) -> {
								if (v==null) {
									v = new ArrayList<>();
								}
								v.add(bin);
								return v;
							});							
						} catch (BadSumException e) {
							e.printStackTrace();
						}
						it.prune();
					}					
				}
			}
		}
		return sumMap;
	}

	private class BadSumException extends Exception {}
	private void collectChildren(IntExpression expr, List<Variable> vars) throws BadSumException {
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			if (bin.getOp().equals("+")) {
				collectChildren(bin.getLeft(), vars);
				collectChildren(bin.getRight(), vars);
			} else {
				throw new BadSumException();
			}
		} else if (expr instanceof VariableReference) {
			VariableReference vr = (VariableReference) expr;
			if (vr.getIndex()!= null) {
				throw new BadSumException();
			}
			vars.add((Variable) vr.getRef());
		} else {
			throw new BadSumException();
		}
	}
}
