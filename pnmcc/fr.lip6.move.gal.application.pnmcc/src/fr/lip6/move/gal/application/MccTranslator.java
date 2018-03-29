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
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;

public class MccTranslator {

	private Specification spec;
	private IOrder order;
	private String folder;
	private String examination;
	private Support simplifiedVars = new Support();
	private boolean isSafeNet = false;
	
	public MccTranslator(String pwd, String examination) {
		this.folder = pwd;
		this.examination = examination;
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
	
	
	public boolean applyOrder(Support supp) {
		if (hasStructure() && canDecompose()) {
			getLog().info("Applying decomposition ");
			getLog().fine(order.toString());
			Specification saved = EcoreUtil.copy(spec);
			try {
				supp.addAll(CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order));
				CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
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


	public boolean hasStructure() {
		return order != null;
	}


	public void flattenSpec(boolean withHierarchy) {
		if (withHierarchy) {
			if (applyOrder(simplifiedVars)) {
				return;
			}
		} 		
		simplifiedVars.addAll(GALRewriter.flatten(spec, true));
		CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
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


	public void setSpec(Specification spec) {
		this.spec = spec;
	}
}
