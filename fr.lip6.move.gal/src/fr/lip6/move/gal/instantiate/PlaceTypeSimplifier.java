package fr.lip6.move.gal.instantiate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.GALParamDef;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GalInstance;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class PlaceTypeSimplifier {


	public static void collapsePlaceType (Specification spec) {

		//		Instantiator.normalizeCalls(spec);

		Set<GALTypeDeclaration> places = new HashSet<GALTypeDeclaration>();

		Map<Label,Label> labMap = new HashMap<Label, Label>();

		Label take = GF2.createLabel("take");
		Label put = GF2.createLabel("put");
		Label test = GF2.createLabel("test");



		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				if (gal.getVariables().size()==1 && gal.getArrays().isEmpty()) {


					boolean isPlaceCompatible = true;

					if (! (EcoreUtil.equals(gal.getVariables().get(0).getValue(), GF2.constant(0))
							|| EcoreUtil.equals(gal.getVariables().get(0).getValue(), GF2.constant(1))
							))
					{
						isPlaceCompatible = false;
						continue;
					}


					for (Transition tr : gal.getTransitions()) {
						if (tr.getGuard() instanceof True) {
							// could be an increment
							if (tr.getActions().size()==1) {
								Actions action = tr.getActions().get(0);
								if (isIncrement(action)) {
									labMap.put(tr.getLabel(),put);
									continue;
								}
							}
							// looks bad : true guard and something else than increment
							isPlaceCompatible = false;
							break;	
						} else if (isStrictPositiveTest(tr.getGuard())) {
							if (tr.getActions().isEmpty()) {
								labMap.put(tr.getLabel(),test);
								continue;
							} else if (tr.getActions().size() == 1 ) {
								Actions action = tr.getActions().get(0);
								if (isDecrement(action)) {
									labMap.put(tr.getLabel(),take);
									continue;
								}
							} 
						}
						isPlaceCompatible = false;
						break;
					}

					if (isPlaceCompatible) {
						places.add(gal);
					}
				}
			}
		}

		if (places.size() != 0) {
			System.err.println("Found a total of "+ places.size() + " elementary GAL types corresponding to a positive counter, with semaphore or Petri net place semantics.");

			spec.getTypes().removeAll(places);

			GALTypeDeclaration placeType = createPlaceType(put,take,test);
			spec.getTypes().add(placeType);

			for (TreeIterator<EObject> it = spec.eAllContents() ; it.hasNext() ;  ) {
				EObject obj = it.next();
				if (obj instanceof InstanceCall) {
					InstanceCall icall = (InstanceCall) obj;
					if (icall.getInstance() instanceof GalInstance) {
						GalInstance gali = (GalInstance) icall.getInstance();
						if (places.contains(gali.getType()) || gali.getType()==placeType) {

							Label tlab = labMap.get(icall.getLabel());
							if (tlab != null) {
								icall.setLabel(tlab);
							} else {
								System.err.println("this is an issue in " + ((GALTypeDeclaration)icall.getLabel().eContainer().eContainer()).getName() + ((Label) icall.getLabel()).getName());
								for (Entry<Label, Label> entry : labMap.entrySet()) {
									if (entry.getKey().getName().equals(((Label)icall.getLabel()).getName())) {
										System.err.println("Found match ?" + ((GALTypeDeclaration)entry.getKey().eContainer().eContainer()).getName());
									}
									if (entry.getKey().eContainer().eContainer() == icall.getLabel().eContainer().eContainer()) {
										System.err.println("found matching type " + entry.getKey().getName());
									}
								}
							}
						}
					}
				} else if (obj instanceof GalInstance) {
					GalInstance gal = (GalInstance) obj;
					if (places.contains(gal.getType())) {
						if (EcoreUtil.equals(gal.getType().getVariables().get(0).getValue(), GF2.constant(1))) {
							GALParamDef mdef1 = GalFactory.eINSTANCE.createGALParamDef();
							mdef1.setParam(placeType.getParams().get(0));
							mdef1.setValue(1);
							gal.getParamDefs().add(mdef1 );
						}
						gal.setType(placeType);
					}
				}
			}
		}
	}
	

	private static GALTypeDeclaration createPlaceType(Label put, Label take, Label test) {

		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName("Place");

		ConstParameter initp = GalFactory.eINSTANCE.createConstParameter();
		initp.setName("$M");
		initp.setValue(0);
		gal.getParams().add(initp);

		Variable place = GalFactory.eINSTANCE.createVariable();
		place.setName("m");
		place.setValue(GF2.createParamRef(initp));
		place.setComment("/** marking */");
		gal.getVariables().add(place);


		{
			Transition tput = GalFactory.eINSTANCE.createTransition();
			tput.setName("put");
			tput.setLabel(put);
			tput.setGuard(GF2.createComparison(GF2.createVariableRef(place), ComparisonOperators.GE, GF2.constant(0)));
			tput.getActions().add(GF2.createIncrement(place,1));
			gal.getTransitions().add(tput);
		}
		{
			Transition ttake = GalFactory.eINSTANCE.createTransition();
			ttake.setName("take");
			ttake.setLabel(take);
			ttake.setGuard(GF2.createComparison(GF2.createVariableRef(place), ComparisonOperators.GE, GF2.constant(1)));
			ttake.getActions().add(GF2.createIncrement(place,-1));
			gal.getTransitions().add(ttake);
		}
		{
			Transition ttest = GalFactory.eINSTANCE.createTransition();
			ttest.setName("test");
			ttest.setLabel(test);
			ttest.setGuard(GF2.createComparison(GF2.createVariableRef(place), ComparisonOperators.GE, GF2.constant(1)));
			gal.getTransitions().add(ttest);
		}		
		return gal ;
	}

	private static boolean isStrictPositiveTest(BooleanExpression guard) {
		if (guard instanceof Comparison) {
			Comparison cmp = (Comparison) guard;
			if (cmp.getLeft() instanceof VariableRef 
					&& cmp.getOperator().equals(ComparisonOperators.GE)
					&& EcoreUtil.equals(cmp.getRight(), GF2.constant(1))) {
				return true;
			}
		}
		return false;
	}

	private static boolean isIncrement(Actions action) {
		return isSelfOp(action, "+");
	}

	private static boolean isDecrement(Actions action) {
		return isSelfOp(action,"-");
	}

	private static boolean isSelfOp(Actions action, String op) {
		if (action instanceof Assignment) {
			Assignment ass = (Assignment) action;
			if (ass.getRight() instanceof BinaryIntExpression) {
				BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
				if (bin.getOp().equals(op)) {
					if (EcoreUtil.equals(bin.getRight(), GF2.constant(1))
							&& EcoreUtil.equals(ass.getLeft(), bin.getLeft())) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
