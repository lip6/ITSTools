package fr.lip6.move.gal.application.mcc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.FusionBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.louvain.GraphBuilder;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToStructuralTransformer;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;
import fr.lip6.move.gal.struct2gal.SpecBuilder;
import fr.lip6.move.gal.struct2gal.StructuralReductionBuilder;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.hlpn.SparseHLPetriNet;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.serialization.SerializationUtil;

public class MccTranslator {

	private static final int DEBUG = 0;
	private static boolean withSeparation = false;
	private boolean doITS = false;
	private boolean doLTSMin = false;
	private String folder;
	private SparseHLPetriNet hlpn;
	private boolean isFlatten = false;
	private boolean isHier = false;
	private int missingTokens = 0;
	private IOrder order;
	private Support simplifiedVars = new Support();
	private Specification spec;

	private SparsePetriNet spn;

	private TGBA tgba = null;

	private boolean useLouvain;

	public MccTranslator(String pwd, boolean useLouvain) {
		this.folder = pwd;
		this.useLouvain = useLouvain;
	}

	public boolean applyOrder(Support supp) {
		if (hasStructure()) {
			getLog().info("Applying decomposition ");
			simplifiedVars.addAll(GALRewriter.flatten(spec, withSeparation));
			boolean done = false; // CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
			patchOrderForArrays();
			if (SumRewriter.rewriteConstantSums(spec)) {
				Simplifier.simplifyProperties(spec);
			}
//			if ( ! spec.getProperties().isEmpty() &&
//					spec.getProperties().stream()
//					.map(Property::getBody)
//					.filter(p -> p instanceof BoolProp).map(p -> (BoolProp) p)
//					.map(BoolProp::getPredicate).allMatch(p -> p instanceof True || p instanceof False)) {
//				return true;
//			}
			if (done) {
				simplifiedVars.addAll(GALRewriter.flatten(spec, withSeparation));
			}
			Specification saved = EcoreUtil.copy(spec);

			try {
				if (useLouvain && order == null) {

					INextBuilder inb = INextBuilder.build(spec);
					List<BitSet> constraints = new ArrayList<>();
					for (Property p : spec.getProperties()) {
						for (TreeIterator<EObject> it = p.eAllContents(); it.hasNext();) {
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
					try {
						IOrder graph = GraphBuilder.computeLouvain(inb, true, constraints);
						setOrder(graph);
					} catch (OutOfMemoryError e) {
						System.out.println("Louvain graph construction failed due to OOM.");
					}

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
				supp.addAll(CompositeBuilder.getInstance()
						.decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order));
				if (!useLouvain) {
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
				spec = saved;
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Check if the specification can be decomposed, i.e. if no properties contain 
	 * addition or comparison of variables in different subcomponents.  
	 * @return true if the specification can be decomposed and SDD can handle the operations
	 */
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
		for (TreeIterator<EObject> it = prop.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof BinaryIntExpression) {
				return true;
			} else if (obj instanceof Comparison) {
				Comparison cmp = (Comparison) obj;
				if (!(cmp.getLeft() instanceof Constant || cmp.getRight() instanceof Constant)) {
					return true;
				}
			}
		}
		return false;
	}

	public MccTranslator copy() {
		MccTranslator copy = new MccTranslator(folder, useLouvain);
		copy.order = this.order;
		copy.simplifiedVars = new Support(simplifiedVars);
		copy.spec = EcoreUtil.copy(spec);
		copy.useLouvain = useLouvain;
		if (spn != null) {
			copy.spn = new SparsePetriNet(spn);
		}
		copy.hlpn = this.hlpn;
		copy.missingTokens = this.missingTokens;
		copy.doITS = doITS;
		copy.doLTSMin = doLTSMin;
		return copy;
	}

	public int countMissingTokens() {
		return this.missingTokens;
	}

	public void createSPN() {
		createSPN(true, true);
	}

	public void createSPN(boolean redPlace, boolean redTrans) {
		if (hlpn != null) {
			hlpn.simplifyLogic();
			if (!redPlace || !redTrans) {
				spn = hlpn.unfold(ReductionType.STATESPACE);
			} else {
				spn = hlpn.unfold(ReductionType.REACHABILITY);
			}
			if (DEBUG >= 1) {
				FlowPrinter.drawNet(new StructuralReduction(spn), "Unfolded");
			}
		}
		if (DEBUG >= 1) {
			System.out.println("initial properties :" + spn.getProperties());
		}

		simplifySPN(redPlace, redTrans);
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
			boolean done = SumRewriter.rewriteConstantSums(spec);
			// done |= rewriteSums();
			if (done) {
				Simplifier.simplifyProperties(spec);
			}
			isFlatten = true;
		}
	}

	public String getFolder() {
		return folder;
	}

	public SparseHLPetriNet getHLPN() {
		return hlpn;
	}

	private PropertyType getPropertyType(String examination) {
		if ("ReachabilityFireability".equals(examination) || "ReachabilityCardinality".equals(examination)) {
			return PropertyType.INVARIANT;
		} else if (examination.contains("Deadlock") || "GlobalProperties".equals(examination)) {
			return PropertyType.DEADLOCK;
		} else if ("UpperBounds".equals(examination)) {
			return PropertyType.BOUNDS;
		} else if (examination.startsWith("CTL")) {
			return PropertyType.CTL;
		} else if (examination.startsWith("LTL")) {
			return PropertyType.LTL;
		} else {
			return PropertyType.UNKNOWN;
		}
	}

	public Specification getSpec() {
		return spec;
	}

	public SparsePetriNet getSPN() {
		return spn;
	}

	public TGBA getTgba() {
		return tgba;
	}

	public boolean hasStructure() {
		return order != null || useLouvain;
	}

	public boolean isDoITS() {
		return doITS;
	}

	public boolean isDoLTSMin() {
		return doLTSMin;
	}

	public void loadGAL(String gal) throws IOException {
		File ff = new File(folder + "/" + gal);
		if (ff != null && ff.exists()) {
			getLog().info("Parsing GAL file : " + ff.getAbsolutePath());

			spec = SerializationUtil.fileToGalSystem(ff.getCanonicalPath());
			order = null;
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(spec.getTypes().size() - 1));
			}
		} else {
			throw new IOException("Cannot open file " + ff.getAbsolutePath());
		}
	}
	/**
	 * Job : parse the property files into the Specification. The examination
	 * determines what happens in here.
	 * 
	 * @throws IOException
	 */
	public void loadProperties(String examination) throws IOException {
		if (examination.equals("StateSpace")) {
			return;
		} else {
			long time = System.currentTimeMillis();
			String propff = folder + "/" + examination + ".xml";
			int parsed = 0;
			if (hlpn != null) {
				parsed = fr.lip6.move.gal.mcc.properties.PropertyParser.fileToProperties(propff, hlpn,
						getPropertyType(examination));
				hlpn.simplifyLogic();
			} else {
				parsed = fr.lip6.move.gal.mcc.properties.PropertyParser.fileToProperties(propff, spn,
						getPropertyType(examination));
			}
			System.out.println("Parsed " + parsed + " properties from file " + propff + " in "
					+ (System.currentTimeMillis() - time) + " ms.");
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
					for (int i = 0; i < varOrder.getVars().size(); i++) {
						if (varOrder.getVars().get(i).contains("[")) {
							varOrder.getVars().set(i, varOrder.getVars().get(i).replace('[', '_').replaceAll("]", ""));
						}
					}
					return null;
				}
			});
		}
	}

	public void rebuildSpecification(DoneProperties doneProps) {
		Specification reduced = SpecBuilder.rebuildSpecification(getSPN());
		if (tgba != null) {
			for (AtomicProp atom : tgba.getAPs()) {
				reduced.getProperties()
						.add(StructuralToGal.toGal(atom, ((GALTypeDeclaration) reduced.getMain()).getVariables()));
			}
		}
		for (fr.lip6.move.gal.structural.Property prop : spn.getProperties()) {
			if (!doneProps.containsKey(prop.getName())) {
				reduced.getProperties()
						.add(StructuralToGal.toGal(prop, ((GALTypeDeclaration) reduced.getMain()).getVariables()));
			}
		}
		Instantiator.normalizeProperties(reduced);
		GALRewriter.flatten(reduced, withSeparation);
		setSpec(reduced);
	}

	public void rebuildSPN() {
		INextBuilder nb = INextBuilder.build(getSpec());
		IDeterministicNextBuilder dnb = IDeterministicNextBuilder.build(nb);
		StructuralReduction sr = StructuralReductionBuilder.createStructuralReduction(dnb);
		boolean isskel = spn.isSkeleton();
		spn = new SparsePetriNet();
		spn.setSkeleton(isskel);
		spn.readFrom(sr);
		GalToStructural gts = new GalToStructural(dnb);
		for (Property p : getSpec().getProperties()) {
			spn.getProperties().add(gts.convert(p));
		}
	}

	/**
	 * removes any properties with addition in them, CTL parser can't deal with them
	 * currently.
	 */
	public void removeAdditionProperties() {
		spec.getProperties().removeIf(prop -> {
			for (TreeIterator<EObject> it = prop.eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				if (obj instanceof BinaryIntExpression) {
					return true;
				}
			}
			return false;
		});
	}

	public void setHLPN(SparseHLPetriNet hlpn) {
		this.hlpn = hlpn;
	}

	public void setITS(boolean doITS) {
		this.doITS = doITS;
	}

	public void setLouvain(boolean b) {
		this.useLouvain = b;
	}

	public void setLTSMin(boolean doLTSmin) {
		this.doLTSMin = doLTSmin;
	}

	public void setMissingTokens(int val) {
		this.missingTokens = val;
	}

	public void setOrder(IOrder order) {
		this.order = order;
	}

	public void setSpec(Specification spec) {
		this.spec = spec;
		this.isFlatten = false;
		if (spec != null) {
			this.isHier = spec.getTypes().size() > 1;
		}
	}

	public void setSpn(SparsePetriNet spn, boolean reduce) {
		this.spn = spn;
		simplifySPN(reduce, reduce);
	}

	public void setTgba(TGBA tgba) {
		this.tgba = tgba;
	}

	public void simplifySPN(boolean redPlace, boolean redTrans) {
		spn.simplifyLogic();
		spn.testInInitial();
		spn.toPredicates();

		if (redPlace) {
			spn.removeConstantPlaces();
		}
		if (redTrans) {
			spn.removeRedundantTransitions(false);
		}
		if (redPlace) {
			spn.removeConstantPlaces();
		}
		spn.simplifyLogic();
		if (spn.isSafe()) {
			spn.assumeOneSafe();
		}
		if (DEBUG >= 1) {
			System.out.println("after syntactic reduction properties :" + spn.getProperties());
		}
	}

	/**
	 * Sets the spec and order attributes, spec is set to result of PNML tranlsation
	 * and order is set to null if no nupn/computed order is available.
	 * 
	 * @param folder     input folder absolute path, containing a model.pnml file
	 * @param reversible set to true to add P >= 0 constraints in guards of
	 *                   transitions adding to P, ensuring predecessor relation is
	 *                   inverse to succ.
	 * @throws IOException if file can't be found
	 */
	public void transformPNML() throws IOException {
		File ff = new File(folder + "/" + "model.pnml");
		if (ff != null && ff.exists()) {
			getLog().info("Parsing pnml file : " + ff.getAbsolutePath());

//			PnmlToGalTransformer trans = new PnmlToGalTransformer();
//			spec = trans.transform(ff.toURI());
//			order = trans.getOrder();
//			isSafeNet = trans.foundNupn();
//			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
//			if (spec.getMain() == null) {
//				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
//			}

			PnmlToStructuralTransformer transPN = new PnmlToStructuralTransformer();
			spn = transPN.transformPT(ff.toURI());
			if (spn == null) {
				hlpn = transPN.transformHLPN(ff.toURI());
			}
			order = transPN.getOrder();

		} else {
			throw new IOException("Cannot open file " + ff.getAbsolutePath());
		}
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");

	}
}
