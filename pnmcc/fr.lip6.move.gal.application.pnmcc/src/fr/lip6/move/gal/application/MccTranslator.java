package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
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
		if (hasStructure() && canDecompose()) {
			getLog().info("Applying decomposition ");
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));
			CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));			
			Specification saved = EcoreUtil.copy(spec);

			try {
				if (useLouvain && order==null) {
					
					INextBuilder inb = INextBuilder.build(spec);
					boolean hasLarge = false;
					for ( Integer init: inb.getInitial()) {
						if (init >= 10) {
							// avoid hierarchy
							hasLarge=true;
							break;
						}
					}
						
					if (! hasLarge)
						setOrder(GraphBuilder.computeLouvain(inb,true));
				}

				getLog().fine(order.toString());
				supp.addAll(CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order));
				if (! useLouvain) 
					CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
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
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));
			CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
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
			isFlatten = true;
		}
	}

	/** Job : parse the property files into the Specification.
	 * The examination determines what happens in here. 
	 * @throws IOException */
	public void loadProperties() throws IOException {
		if (examination.equals("StateSpace")) {
			return ;
		} else {
			String propff = folder +"/" +  examination + ".xml";
			Properties props = PropertyParser.fileToProperties(propff , spec);
			spec = ToGalTransformer.toGal(props);
			if (isSafeNet) {
				rewriteVariableComparisons(spec);
			}
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
						// normalize
						ComparisonOperators op = cmp.getOperator();
						IntExpression l = cmp.getLeft();
						IntExpression r = cmp.getRight();
						switch (op) {
						case GE :
							l = cmp.getRight();
							r = cmp.getLeft();
							op = ComparisonOperators.LE;
							break;
						case GT :
							l = cmp.getRight();
							r = cmp.getLeft();
							op = ComparisonOperators.LT;
							break;
						}
						BooleanExpression res;
						// break into cases
						switch (op) {
						case EQ :
							// both 0 or both 1
							res = GF2.and(
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(0)));
							res = GF2.or( res , GF2.and(
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(1)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1))));
							break;
						case NE :
							// 01 or 10
							res = GF2.and(
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1)));
							res = GF2.or( res , GF2.and(
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(1)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(0))));
							break;
						case LT :
							// 01
							res = GF2.and(
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1)));
							break;
						case LE :
							// 0* or 11 => r is 1 or l is 0 => 0* or *1
							res = GF2.or( 
									GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
									GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1))
									);
							break;	
						default :
							throw new RuntimeException("Unexpected comparison operator in conversion "+ cmp);
						}
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
}
