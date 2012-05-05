package fr.lip6.move.generator

import fr.lip6.move.gal.ArrayVarAccess
import fr.lip6.move.gal.BinaryIntExpression
import fr.lip6.move.gal.BitComplement
import fr.lip6.move.gal.BooleanExpression
import fr.lip6.move.gal.Constant
import fr.lip6.move.gal.False
import fr.lip6.move.gal.IntExpression
import fr.lip6.move.gal.True
import fr.lip6.move.gal.UnaryMinus
import fr.lip6.move.gal.VariableRef
import fr.lip6.move.gal.WrapBoolExpr
import fr.lip6.move.gal.Not

import static fr.lip6.move.generator.GalGeneratorUtils.*
import fr.lip6.move.gal.And
import fr.lip6.move.gal.Or
import fr.lip6.move.gal.Comparison
import fr.lip6.move.gal.Peek


class GalGeneratorUtils { 

	def static String parseIntExpression(IntExpression ie, String stateName){
		
		switch ie {
			Constant			: ""+ie.value
			
			VariableRef 		: 
				stateName+".getVariable(\""
				+ie.referencedVar.name
				+"\")"
			
			ArrayVarAccess		:
				stateName+".getValueInArray(\""+
				ie.prefix.name+"\","+
				parseIntExpression(ie.index,stateName)+")"
			
			Peek				: 
				stateName+".peekInList(\""+ie.list.name+"\")"
			
			UnaryMinus			: 
				"-("+parseIntExpression(ie.value,stateName)+")"
			
			BitComplement		:
				"~("+parseIntExpression(ie.value,stateName)+")"
			
			BinaryIntExpression 
			case  
			"**".equals(ie.op)	:
				"(int)java.lang.Math.pow("+
				"(double)"+parseIntExpression(ie.left,stateName)+
				","+
				"(double)"+parseIntExpression(ie.right,stateName)+
				")"
				
			BinaryIntExpression :
				"("
				+parseIntExpression(ie.left,stateName)
				+ie.op
				+parseIntExpression(ie.right,stateName)
				+")"
			
			WrapBoolExpr		: "("+parseBoolExpression(ie.value, stateName)+")"
			
			default				: throw new RuntimeException("unknown operation")
		}
	}
	
	def static String parseBoolExpression(BooleanExpression be, String stateName) {
		switch be{
			True		: "true"
			False		: "false"
			
			Not			: 
				"!("+parseBoolExpression(be.value,stateName)+")"
				
			And			: 
				"("
				+parseBoolExpression(be.left,stateName)
				+"&&"
				+parseBoolExpression(be.right,stateName)
				+")"
				
			Or			: 
				"("
				+parseBoolExpression(be.left,stateName)
				+"||"
				+parseBoolExpression(be.right,stateName)
				+")"
				
			Comparison	:
				"("
				+parseIntExpression(be.left,stateName)
				+be.operator
				+parseIntExpression(be.right,stateName)
				+")"
					
			default: throw new RuntimeException("unknown operation")
		}
	}
	
}
