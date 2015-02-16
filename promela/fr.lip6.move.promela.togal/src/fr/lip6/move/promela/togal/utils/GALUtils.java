package fr.lip6.move.promela.togal.utils;

import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public class GALUtils {


	public static BooleanExpression makeGALBool(int i) {
		if (i != 0)
			return GalFactory.eINSTANCE.createTrue();
		else
			return GalFactory.eINSTANCE.createFalse();

	}

	public static ArrayPrefix makeArray(String name, int size,
			IntExpression value) {

		ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
		ap.setName(name);
		ap.setSize(size);

		for (int i = 0; i < size; i++) {
			ap.getValues().add(EcoreUtil.copy(value));
		}

		return ap;
	}


	public static VariableReference makeArrayAccess(ArrayPrefix a, IntExpression i) {
		return GF2.createArrayVarAccess(a, i);
	}

	

	public static Statement makeAssignInc(VariableReference va) {
		return GF2.createIncrement(va, 1);
	}

	public static Statement makeAssignDec(VariableReference va) {
		return GF2.createAssignment(va, makePlus(GF2.constant(-1), EcoreUtil.copy(va)));
	}

	public static VariableReference makeRef(Variable v) {
		return GF2.createVariableRef(v);
	}

	public static BinaryIntExpression makePlus(IntExpression left,
			IntExpression right) {
		BinaryIntExpression res = GalFactory.eINSTANCE
				.createBinaryIntExpression();
		res.setOp("+"); // TODO: real enum....
		res.setLeft(left);
		res.setRight(right);
		return res;
	}


	public static BooleanExpression combineAnd(List<BooleanExpression> l) {

		if (l.isEmpty())
			throw illegal("List is empty..");

		Iterator<BooleanExpression> lit = l.iterator();

		BooleanExpression tmp = lit.next();

		while (lit.hasNext()) {
			tmp = GF2.and(tmp, lit.next());
		}
		return tmp;
	}

	public static BooleanExpression combineOr(List<BooleanExpression> l) {

		if (l.isEmpty())
			throw illegal("List is empty..");
		Iterator<BooleanExpression> lit = l.iterator();
		BooleanExpression tmp = lit.next();
		while (lit.hasNext()) {
			tmp = GF2.or(tmp, lit.next());
		}
		return tmp;
	}

	public static BooleanExpression combineNone(List<BooleanExpression> conds) {
		BooleanExpression res = combineOr(conds);
		// MAYBE: improve avec négation attachée (bénéficier court circuit

		return GF2.not(res);

		// CHECK
	}

	public static BooleanExpression combineNone(boolean b) {
		return b ? GalFactory.eINSTANCE.createTrue() : GalFactory.eINSTANCE
				.createFalse();

	}

}
