package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BoolProp;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.FusionBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.PropertySimplifier;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.True;
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
	private static boolean withSeparation = false;
	
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
			simplifiedVars.addAll(GALRewriter.flatten(spec, withSeparation));
			boolean done = false; // CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			patchOrderForArrays();
			if (rewriteConstantSums()) {
				Simplifier.simplifyProperties(spec);
			}
			if ( ! spec.getProperties().isEmpty() && 
					spec.getProperties().stream()
					.map(Property::getBody)
					.filter(p -> p instanceof BoolProp).map(p -> (BoolProp) p)
					.map(BoolProp::getPredicate).allMatch(p -> p instanceof True || p instanceof False)) {
				return true;
			}
			if (done) { 						
				simplifiedVars.addAll(GALRewriter.flatten(spec, withSeparation));
			}
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
			simplifiedVars.addAll(GALRewriter.flatten(spec, withSeparation));
//			CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			patchOrderForArrays();			
			boolean done = rewriteConstantSums();	
			// done |= rewriteSums();
			if (done) {
				Simplifier.simplifyProperties(spec);
			}
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
			PropertySimplifier.pushNegation(spec);
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
	
	private static class VarInt {
		VarDecl var;
		int index;
		public VarInt(VarDecl var, int index) {
			this.var = var;
			this.index = index;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + ((var == null) ? 0 : var.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VarInt other = (VarInt) obj;
			if (index != other.index)
				return false;
			if (var == null) {
				if (other.var != null)
					return false;
			} else if (!var.equals(other.var))
				return false;
			return true;
		}
		
	}

	public boolean rewriteConstantSums() {
		boolean toret = false;
		try {
			Map<Set<VarInt>, List<IntExpression>> sumMap = collectSums();
			for (Entry<Set<VarInt>, List<IntExpression>> entry : sumMap.entrySet()) {
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
					toret = true;
				}
			}
		} catch (Exception e) {
			System.out.println("Problem detected in Rewrite constant sums.");
			e.printStackTrace();
		}
		return toret;
	}


	public boolean rewriteSums() {
		boolean toret = false;
		try {
			Map<Set<VarInt>, List<IntExpression>> sumMap = collectSums();
			for (Entry<Set<VarInt>, List<IntExpression>> entry : sumMap.entrySet()) {
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
					int v = collectChildren(head, null);				
					if (v==0) {
						EcoreUtil.replace(head, GF2.createVariableRef(sum));
					} else {
						EcoreUtil.replace(head, GF2.createBinaryIntExpression(GF2.createVariableRef(sum),"+",GF2.constant(v)));
					}
				}
				System.out.println("Successfully replaced "+ entry.getValue().size() + " occurrences of a sum of "+ entry.getKey().size() + " variables");
				toret = true;
			}
		} catch (BadSumException ve) {
			ve.printStackTrace();
		}
		return toret;
	}


	public int computeEffect(Entry<Set<VarInt>, List<IntExpression>> entry, Transition t) {
		int res = 0;
		for (Statement a : t.getActions()) {
			if (a instanceof Ite) {
				Ite ite = (Ite) a;
				if (ite.getIfFalse().size()==1 && ite.getIfFalse().get(0) instanceof Abort && ite.getIfTrue().size()==1) {
					a = ite.getIfTrue().get(0);
				}
			}
			if (a instanceof Assignment) {
				Assignment ass = (Assignment) a;
				VarDecl v = (VarDecl) ((VariableReference) ass.getLeft()).getRef();
				int ind = -1;
				if (((VariableReference) ass.getLeft()).getIndex() != null) {
					ind = ((Constant) ((VariableReference) ass.getLeft()).getIndex()).getValue();
				}
				if (entry.getKey().contains(new VarInt(v,ind))) {
					if (ass.getType()==AssignType.INCR) {
						res += ((Constant) ass.getRight()).getValue();
					} else if (ass.getType()==AssignType.DECR) {
						res -= ((Constant) ass.getRight()).getValue();
					}
				}
			} else {
				return 1;
			}
		}
		return res;
	}


	public Variable createSumOfVariable(Entry<Set<VarInt>, List<IntExpression>> entry) {
		StringBuilder sb = new StringBuilder();
		for (VarInt v: entry.getKey()) {
			sb.append(v.var.getName()).append("_");
			if (v.index != -1) {
				sb.append(v.index).append("_");
			}
			if (sb.length() >= 64) {
				break;
			}
		}
		Variable sum = GalFactory.eINSTANCE.createVariable();
		sum.setName(sb.toString());

		int summed = entry.getKey().stream().mapToInt(v -> v.index < 0 ?
				((Constant)((Variable)v.var).getValue()).getValue()
				: ((Constant) ((ArrayPrefix)v.var).getValues().get(v.index)).getValue()
				).sum(); 
		
		sum.setValue(GF2.constant(summed));
		return sum;
	}


	public Map<Set<VarInt>, List<IntExpression>> collectSums() {
		Map<Set<VarInt>,List<IntExpression> > sumMap = new HashMap<>();
		for (Property p : spec.getProperties()) {
			for (TreeIterator<EObject> it=p.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof BinaryIntExpression) {
					BinaryIntExpression bin = (BinaryIntExpression) obj;
					if (bin.getOp().equals("+")) {
						Set<VarInt> vars = new HashSet<>();
						try {
							int added = collectChildren(bin, vars);
							sumMap.computeIfAbsent(vars, v->new ArrayList<>()).add(bin);							
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
	private int collectChildren(IntExpression expr, Set<VarInt> vars) throws BadSumException {
		int a = 0;
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			if (bin.getOp().equals("+")) {
				a = collectChildren(bin.getLeft(), vars);
				a += collectChildren(bin.getRight(), vars);
			} else {
				throw new BadSumException();
			}
		} else if (expr instanceof VariableReference) {
			VariableReference vr = (VariableReference) expr;
			int ind=-1;
			if (vr.getIndex()!= null) {
				if (vr.getIndex() instanceof Constant) {
					Constant cte = (Constant) vr.getIndex();
					ind = cte.getValue();
				} else {
					throw new BadSumException();
				}
			}
			if (vars != null)
				vars.add( new VarInt((VarDecl) vr.getRef(),ind));
		} else if (expr instanceof Constant) {
			a = ((Constant) expr).getValue();
		} else {
			throw new BadSumException();
		}
		return a;
	}
}
