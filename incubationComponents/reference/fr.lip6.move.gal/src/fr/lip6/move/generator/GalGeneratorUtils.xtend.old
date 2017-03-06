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

	def static String parseIntExpression(IntExpression ie, String destination){
		
		switch ie {
			Constant			: ""+ie.value
			
			VariableRef 		: 
				destination+".getVariable(\""
				+ie.referencedVar.name
				+"\")"
			
			ArrayVarAccess		:
				destination+".getValueInArray(\""+
				ie.prefix.name+"\","+
				parseIntExpression(ie.index,destination)+")"
			
			Peek				: 
				destination+".peekInList(\""+ie.list.name+"\")"
			
			UnaryMinus			: 
				"(-("+parseIntExpression(ie.value,destination)+"))"
			
			BitComplement		:
				"(~("+parseIntExpression(ie.value,destination)+"))"
			
			BinaryIntExpression 
			case  
			"**".equals(ie.op)	:
				"(int)java.lang.Math.pow("+
				"(double)"+parseIntExpression(ie.left,destination)+
				","+
				"(double)"+parseIntExpression(ie.right,destination)+
				")"
				
			BinaryIntExpression :
				"("
				+parseIntExpression(ie.left,destination)
				+ie.op
				+parseIntExpression(ie.right,destination)
				+")"
			
			WrapBoolExpr		: "(new environment.WrapBool("+parseBoolExpression(ie.value, destination)+")).evaluate()"
			
			default				: throw new RuntimeException("unknown operation")
		}
	}
	
	def static String parseBoolExpression(BooleanExpression be, String destination){
		switch be{
			True		: "true"
			False		: "false"
			
			Not			: 
				"(!("+parseBoolExpression(be.value,destination)+"))"
				
			And			: 
				"("
				+parseBoolExpression(be.left,destination)
				+"&&"
				+parseBoolExpression(be.right,destination)
				+")"
				
			Or			: 
				"("
				+parseBoolExpression(be.left,destination)
				+"||"
				+parseBoolExpression(be.right,destination)
				+")"
				
			Comparison	:
				"("
				+parseIntExpression(be.left,destination)
				+be.operator
				+parseIntExpression(be.right,destination)
				+")"
					
			default: throw new RuntimeException("unknown operation")
		}
	}
	
}
