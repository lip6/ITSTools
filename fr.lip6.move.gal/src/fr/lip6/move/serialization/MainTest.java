package fr.lip6.move.serialization;

import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class MainTest {
	public static void main(String [] args)
	{
		/*
		 * Tested:
		 * GAL toto {
		 * 	  int var1 = 2;
		 *    
		 *    transition t1 [True] {
		 *  	var1 = 4 ;
		 *    }
		 * }
		 */
		String outputFilename = "output.gal";
		
		GalFactory factory =  GalFactory.eINSTANCE ; 
		
		System system = GalFactory.eINSTANCE.createSystem();
		system.setName("toto");
		
		
		
		// int var1 = 4 ;
		Variable var1 = factory.createVariable() ; 
		var1.setValue(2) ;
		var1.setName("var1");
		
//		// int var2 = 78 ; 
//		Variable var2 = factory.createVariable();
//		var2.setName("var2"); 
//		var2.setValue(78);
		
		// Transition t1 [ True ] { var1 = 4; } 
		Transition t1 = factory.createTransition() ; 
		True _true = factory.createTrue();
		_true.setValue("True");
		
		t1.setGuard(_true);
		t1.setName("t1");
		
		Assignment as = factory.createAssignment();
		// Left side
		VariableRef vr = factory.createVariableRef();
		vr.setReferencedVar(var1);
		as.setLeft(vr);
		
		// Right side
		Constant cs = factory.createConstant();
		cs.setValue(4);
		as.setRight(cs);
		
		t1.getActions().add(as);
		
		// Adding to system
		system.getVariables().add(var1); 
//		system.getVariables().add(var2); 
		system.getTransitions().add(t1);
	
		
		
		// Writing to file.
//		java.lang.System.out.println(SerializationUtil.valueOf(system));
		//SerializationUtil.systemToFile(system, outputFilename);
		
		
		SerializationUtil.systemToFile(SerializationUtil.fileToGalSystem("output.gal"), 
													"output2.gal");
		
	}
}
