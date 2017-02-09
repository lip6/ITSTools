package fr.lip6.move.gal.semantics;

import java.io.ByteArrayOutputStream;

import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.serialization.BasicGalSerializer;

public class ExpressionPrinter {

	
	public static String print(EObject ass, String stateName, VariableIndexer index) {
		BasicGalSerializer bgs = new BasicGalSerializer() {

			@Override
			public Boolean caseVariableReference(VariableReference vref) {
				String vname = vref.getRef().getName();
				if (vref.getIndex() != null) {
					pw.print(stateName+"["+index.getIndex(vname)) ;
					pw.print("+");
					doSwitch(vref.getIndex());
					pw.print("]");
				} else {
					pw.print(stateName+"["+index.getIndex(vname)+ "]");
				}
				return true;
			}

		};
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bgs.setStream(baos,2);
		bgs.doSwitch(ass);
		bgs.close();
		return baos.toString();
	}
}
