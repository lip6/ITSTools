package serialization;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.serialization.SerializationUtil;


/**
 * Tests for serialization
 * @author steph
 */
public class SerializationTests {
	
	/**
	 * Returns a sample Gal system
	 * @return
	 */
	public System sampleGalSystem(String systemName)
	{
		/*
		 * Sample:
		 * GAL toto {
		 * 	  int var1 = 2;
		 *    
		 *    transition t1 [True] {
		 *  	var1 = 4 ;
		 *    }
		 * }
		 */
		GalFactory factory =  GalFactory.eINSTANCE ; 
		
		System system = GalFactory.eINSTANCE.createSystem();
		system.setName(systemName); 
		
		
		
		// int var1 = 4 ;
		Variable var1 = factory.createVariable() ; 
		var1.setValue(2) ;
		var1.setName("var1");
		
		
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
		return system ; 
		
		
	}
	
	@Test 
	public void testQualifiedName()
	{
		System system = sampleGalSystem("test.3.test") ; 
		
		try {
			SerializationUtil.systemToFile(system, "output_qualifiedName.gal") ;
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		System system2 = SerializationUtil.fileToGalSystem("output_qualifiedName.gal");
		
		assertEquals(system.getName(), system2.getName());
		
	}
	
	//@Test
	public void testSerialization1()
	{
		/*
		 * Sample Gal tested:
		 * GAL toto {
		 * 	  int var1 = 2;
		 *    
		 *    transition t1 [True] {
		 *  	var1 = 4 ;
		 *    }
		 * }
		 */
		String outputFilename = "output.gal";
		System system = sampleGalSystem("test.3.tata")   ; 
		
		
		// Writing to file.
		try {
			SerializationUtil.systemToFile(system, outputFilename);
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		// Getting from file
		System system2 = SerializationUtil.fileToGalSystem(outputFilename);
		
		// Systems names equality
		assertEquals(system2.getName(),system.getName());
		// var1 == 2 ? (as expected)
		assertEquals(2, system2.getVariables().get(0).getValue());
		
		
//		SerializationUtil.systemToFile(SerializationUtil.fileToGalSystem("output.gal"), 
//													"output2.gal");
		
	}
}
