package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public class BoundsBuilder {


	private static Map<String,Label> labels;

	public static void boundVariable (Specification spec, int k) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;

				labels = new HashMap<String, Label>();

				for (Variable var : gal.getVariables()) {
					if (var.getValue() instanceof Constant) {
						Constant cte = (Constant) var.getValue();
						if (cte.getValue() >= k) {
							cte.setValue(k);
						}
					}
				}
				for (ArrayPrefix ap : gal.getArrays()) {
					for (IntExpression val : ap.getValues()) {
						if (val instanceof Constant) {
							Constant cte = (Constant) val;
							if (cte.getValue() >= k) {
								cte.setValue(k);
							}
						}
					}
				}
				List<Transition> toadd = new ArrayList<Transition>();
				for (Transition tr : gal.getTransitions()) {
					// collect all x+=n or x-=n statements
					List<Assignment> totreat = new ArrayList<Assignment>();
					for (TreeIterator<EObject> it = tr.eAllContents() ; it.hasNext() ; ) {
						EObject st = it.next();
						if (st instanceof Assignment) {
							Assignment ass = (Assignment) st;
							if ( ass.getType() != AssignType.ASSIGN && ass.getRight() instanceof Constant) {
								totreat.add(ass);
							}
							it.prune();
						}
					}
					for (Assignment ass : totreat) {
						Constant cte = (Constant) ass.getRight();
						int n = cte.getValue();
						if (ass.getType()== AssignType.INCR) {
							// x+=n ; 
							// ->
							// if (x < k) x+=n;
							BooleanExpression cond = GF2.createComparison(EcoreUtil.copy(ass.getLeft()), ComparisonOperators.LT, GF2.constant(k));
							Ite ite = GalFactory.eINSTANCE.createIte();
							ite.setCond(cond);
							ite.getIfTrue().add(EcoreUtil.copy(ass));
							EcoreUtil.replace(ass, ite);
						} else if (ass.getType()== AssignType.DECR) {	

							// x-=n
							// ->
							// self."decX_$n"
							// tr maxdecXby ($n : n..n , $i : 0..$n) [x >= k] lab "decX_$n" { x-=$i; }
							// tr stddecXby ($n :n..n) [x < k] lab "decX_$n" { x-= $n }
							//
							String decname = ass.getLeft().getRef().getName()+n;
							Label lab = labels.get(decname );
							if (lab == null) {
								List<Transition> tdecs = buildDecrementVariants(ass.getLeft(), n, k);
								toadd.addAll(tdecs);
								lab = tdecs.get(0).getLabel();
								labels.put(decname, lab);
							}
							Statement call = GF2.createSelfCall(lab);
							EcoreUtil.replace(ass, call);
						}
					}
				}
				gal.getTransitions().addAll(toadd);
			}
			
		}
		// ensure no gc leak
		labels = null;
	}



	private static List<Transition> buildDecrementVariants (VariableReference left, int n, int k) {
		// tr maxDecXby ($n : n..n, $i : 0..n) [x >= k] label "decX_$n" { x-=$i; }
		Transition tdec = GF2.createTransition("maxDec"+left.getRef().getName()+"by");

		// build typedefs
		// tdn = n..n
		TypedefDeclaration tdn = GF2.createTypeDef("tdn",n,n);
		// tdi = 0..n
		TypedefDeclaration tdi = GF2.createTypeDef("tdi",0,n);

		// $pn : tdn
		Parameter pn = GF2.createParameter ("$pn",tdn);
		tdec.getParams().add(EcoreUtil.copy(pn));
		// $pi : tdi
		Parameter pi = GF2.createParameter ("$pi",tdi);
		tdec.getParams().add(pi);

		// label "decX_$n"
		Label lab = GF2.createLabel("dec"+left.getRef().getName()+"by$pn");
		tdec.setLabel(EcoreUtil.copy(lab));

		// [x >= k]
		BooleanExpression guardXgeqK = GF2.createComparison(EcoreUtil.copy(left), ComparisonOperators.GE, GF2.constant(k));
		tdec.setGuard(guardXgeqK);
		// { x-=$i; }
		Statement decbyi = GF2.createTypedAssignment(EcoreUtil.copy(left), AssignType.DECR, GF2.createParamRef(pi));
		tdec.getActions().add(decbyi);

		// we can instantiate
		List<Transition> tdecs = Instantiator.instantiateParameters(tdec);

		// tr stdDecXby ($n : n)  [x < k] lab "decX_$n" { x-= $n ; }
		Transition tstddec = GF2.createTransition("stdDec"+left.getRef().getName()+"by");

		// $pn : tdn
		tstddec.getParams().add(pn);
		// label "decX_$n"
		tstddec.setLabel(EcoreUtil.copy(lab));

		// [x < k]
		BooleanExpression guardXltK = GF2.createComparison(EcoreUtil.copy(left), ComparisonOperators.LT, GF2.constant(k));
		tstddec.setGuard(guardXltK);
		// { x-=$n; }
		Statement decbyn = GF2.createTypedAssignment(EcoreUtil.copy(left), AssignType.DECR, GF2.createParamRef(pn));
		tstddec.getActions().add(decbyn);

		tdecs.addAll(Instantiator.instantiateParameters(tstddec));
		return tdecs;
	}
}