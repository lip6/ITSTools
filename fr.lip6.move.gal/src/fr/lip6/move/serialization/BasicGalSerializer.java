package fr.lip6.move.serialization;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Fixpoint;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.ParamDef;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Predicate;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.TemplateTypeDeclaration;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.WrapBoolExpr;
import fr.lip6.move.gal.util.GalSwitch;

public class BasicGalSerializer extends GalSwitch<Boolean>{

	private final boolean isStrict;
	private final String SPACE ;
	
	public BasicGalSerializer() {
		this(false);
	}
	
	public BasicGalSerializer(boolean strictForITS) {
		if (strictForITS) {
			isStrict = true;
			SPACE = "";
		} else {
			isStrict = false;
			SPACE = " ";
		}
	}

	private IndentedPrintWriter pw;

	public void serialize (EObject modelElement, OutputStream stream) {
		setStream(stream);

		doSwitch(modelElement);		
		close();
	}

	public void close() {
		pw.flush();
		pw.close();
		pw = null;
	}

	public void setStream(OutputStream stream) {
		pw = new IndentedPrintWriter(new PrintWriter(stream));
	}

	
	@Override
	public Boolean caseSpecification(Specification spec) {
		pw.println();
		for (ConstParameter cp : spec.getParams()) {
			caseConstParameter(cp);
			pw.print(" ;");
			pw.println();
		}
		for (TypedefDeclaration dd : spec.getTypedefs()) {
			caseTypedefDeclaration(dd);
		}
		for (TypeDeclaration td : spec.getTypes()) {
			doSwitch(td);
		}
		
		if (spec.getMain() != null) {
			pw.println("main "+ spec.getMain().getName() +" ;");
		}
		for (Property prop : spec.getProperties()) {
			caseProperty(prop);
		}
		return true;
	}
	
	@Override
	public Boolean caseConstParameter(ConstParameter cp) {
		pw.print(cp.getName()+"="+cp.getValue());
		return true;
	}
	
	
	@Override
	public Boolean caseTypedefDeclaration(TypedefDeclaration dd) {
		if (dd.getComment() != null) {
			pw.println(dd.getComment());
		}
		pw.indent();		
		pw.print("typedef " + dd.getName() + "= ");
		doSwitch(dd.getMin());
		pw.print("..");
		doSwitch(dd.getMax());
		pw.print(" ;");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseAbort(Abort object) {
		caseStatement(object);
		pw.println("abort ;");
		return true;
	}
	
	@Override
	public Boolean caseAnd(And and) {
		boolean lessPrio = isStrict || and.getLeft() instanceof Or;
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(and.getLeft());
		if (lessPrio) pw.print(SPACE+")");
		pw.print(SPACE+"&&"+SPACE);
		lessPrio = isStrict || and.getRight() instanceof Or;
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(and.getRight());
		if (lessPrio) pw.print(SPACE+")");
		return true;
	}

	@Override
	public Boolean caseNot(Not not) {
		if (not.getValue() instanceof Not) {
			doSwitch(((Not) not.getValue()).getValue());
			return true;
		}
		boolean lessPrio = isStrict || ! ( not.getValue() instanceof Comparison);
		pw.print("!");
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(not.getValue());
		if (lessPrio) pw.print(")"+SPACE);
		return true;
	}


	@Override
	public Boolean caseOr(Or or) {
		boolean lessPrio = isStrict;
		
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(or.getLeft());
		pw.print(SPACE+"||"+SPACE);
		doSwitch(or.getRight());
		if (lessPrio) pw.print(")"+SPACE);
		return true;
	}
	
	
	@Override
	public Boolean caseArrayInstanceDeclaration(ArrayInstanceDeclaration aid) {
		pw.indent();
		pw.print(aid.getType().getName());
		pw.print(" [");
		pw.print(aid.getSize());
		pw.print("] ");
		pw.print(aid.getName());
		if (! aid.getParamDefs().isEmpty()) {
			pw.print("(");
			printList(aid.getParamDefs());
			pw.print(")");
		}
		pw.print(";");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseAssignment(Assignment ass) {
		caseStatement(ass);
		pw.indent();
		doSwitch(ass.getLeft());
		pw.print(" "+ ass.getType().getLiteral() + " ");
		doSwitch(ass.getRight());
		pw.println(";");
		return true;
	}
	
	@Override
	public Boolean caseStatement(Statement ass) {
		if (ass.getComment() != null) {
			pw.println(ass.getComment());
		}
		return true;
	}
	
	private <T extends EObject> void printList(EList<T> eList) {
		boolean isfirst = true;		
		for (T elem : eList) {
			if (isfirst) {
				isfirst = false;
			} else {
				pw.print(", ");
			}
			doSwitch(elem);
		}
	}

	@Override
	public Boolean caseVarDecl(VarDecl ap) {
		if (ap.getComment() != null) {
			pw.println(ap.getComment());
		}
		if (ap.isHotbit()) {
			pw.print("hotbit (");
			pw.print(ap.getHottype().getName());
			pw.print(")");
			pw.println();
		}
		return true;
	}
	
	@Override
	public Boolean caseArrayPrefix(ArrayPrefix ap) {
		caseVarDecl(ap);
		pw.indent();
		pw.print("array [");
		doSwitch(ap.getSize());
		pw.print("]");
		pw.print(ap.getName());
		pw.print(" = (");
		printList(ap.getValues());
		pw.print(" );");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseBinaryIntExpression(BinaryIntExpression bin) {
		pw.print("("+SPACE);
		doSwitch(bin.getLeft());
		pw.print(SPACE + bin.getOp() + SPACE);
		doSwitch(bin.getRight());
		pw.print(SPACE+")");
		return true;
	}
	
	@Override
	public Boolean caseBitComplement(BitComplement bc) {
		pw.print("~");
		doSwitch(bc.getValue());
		return true;
	}
	private String reverse(ComparisonOperators op) {
		switch (op) {
		case EQ : return "==";
		case GE : return "<=";
		case GT : return "<";
		case LE : return ">=";
		case LT : return ">";
		case NE : return "!=";
		default : return "unknown operator";
		}
	}

	@Override
	public Boolean caseComparison(Comparison comp) {
		if (isStrict && comp.getLeft() instanceof Constant) {
			doSwitch(comp.getRight());
			pw.print(SPACE+ reverse(comp.getOperator()) +SPACE);
			doSwitch(comp.getLeft());
		} else {
			doSwitch(comp.getLeft());
			pw.print(SPACE+ comp.getOperator().getLiteral() +SPACE);
			doSwitch(comp.getRight());
		}

		return true;
	}

	@Override
	public Boolean caseConstant(Constant cte) {
		pw.print(cte.getValue());
		return true;
	}
	
	@Override
	public Boolean caseFalse(False object) {
		pw.print("false");
		return true;
	}
	@Override
	public Boolean caseTrue(True object) {
		pw.print("true");
		return true;
	}
	
	@Override
	public Boolean caseFor(For forloop) {
		caseStatement(forloop);
		pw.indent();
		pw.print("for (");
		pw.print(forloop.getParam().getName());
		pw.print(" : ");
		pw.print(forloop.getParam().getType().getName());
		pw.print(") {");
		pw.incIndent();
		pw.println();
		for (Statement act : forloop.getActions()) {
			doSwitch(act);
		}
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	@Override
	public Boolean caseFixpoint(Fixpoint fix) {
		caseStatement(fix);
		pw.println("fixpoint {");
		pw.incIndent();
		for (Statement act : fix.getActions()) {
			doSwitch(act);
		}
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	@Override
	public Boolean caseInstanceCall(InstanceCall call) {
		caseStatement(call);
		pw.indent();
		doSwitch(call.getInstance());
		pw.print('.');
		pw.print("\"" + call.getLabel().getName()  + "\"") ;
		if (! call.getParams().isEmpty()) {
			pw.print("( ");
			printList(call.getParams());
			pw.print(" )");
		}
		pw.print(" ;");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseGALTypeDeclaration(GALTypeDeclaration gal) {
		pw.print("gal ");
		pw.print(gal.getName());

		if (! gal.getParams().isEmpty()) {
			pw.print("(");
			printList(gal.getParams());
			pw.print(")");
		}
		pw.print("{");
		pw.println();
		pw.incIndent();
		
		for (TypedefDeclaration td : gal.getTypedefs()) {
			caseTypedefDeclaration(td);
		}
		
		for (Variable var : gal.getVariables()) {
			caseVariable(var);
		}
		
		for (ArrayPrefix ap : gal.getArrays()) {
			caseArrayPrefix(ap);
		}
		
		for (Transition tr : gal.getTransitions()) {
			caseTransition(tr);
		}
		
		for (Predicate pred : gal.getPredicates()) {
			casePredicate(pred);
		}
		
		if (gal.getTransient() != null) {
			pw.indent();
			pw.print("TRANSIENT = ");
			doSwitch(gal.getTransient());
			pw.print(" ;");
			pw.println();
		}
		
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	@Override
	public Boolean caseVariable(Variable var) {
		caseVarDecl(var);
		pw.indent();
		pw.print("int ");
		pw.print(var.getName());
		pw.print(" = ");
		doSwitch(var.getValue());
		pw.print(";");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseIte(Ite ite) {
		caseStatement(ite);
		pw.indent();
		pw.print("if (");
		doSwitch(ite.getCond());
		pw.print(") {");
		pw.println();

		pw.incIndent();
		for (Statement act : ite.getIfTrue()) {
			doSwitch(act);
		}
		pw.decIndent();		
		if (! ite.getIfFalse().isEmpty()) {
			pw.println("} else {");
			pw.incIndent();
			for (Statement act : ite.getIfFalse()) {
				doSwitch(act);
			}
			pw.decIndent();		
		} 
		pw.println("}");
		return true;
	}

	
	@Override
	public Boolean caseInstanceDeclaration(InstanceDeclaration aid) {
		pw.indent();
		pw.print(aid.getType().getName());
		pw.print(" ");
		pw.print(aid.getName());
		if (! aid.getParamDefs().isEmpty()) {
			pw.print("(");
			printList(aid.getParamDefs());
			pw.print(")");
		}
		pw.print(";");
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseTransition(Transition tr) {
		if (tr.getComment() != null) {
			pw.println(tr.getComment());
		}
		pw.indent();		
		pw.print("transition ");
		pw.print(tr.getName());
		if (! tr.getParams().isEmpty()) {
			pw.print("(");
			printList(tr.getParams());
			pw.print(")");
		}
		pw.print(" [ ");
		doSwitch(tr.getGuard());
		pw.print(" ]" );
		
		pw.print(" ");
		if (tr.getLabel() != null) {
			pw.print("label ");
			caseLabel(tr.getLabel());
		}
		pw.print("{");
		pw.println();
		pw.incIndent();
		
		for (Statement act : tr.getActions()) {
			doSwitch(act);
		}		
		
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	@Override
	public Boolean caseParameter(Parameter param) {
		pw.print(param.getType().getName());
		pw.print(" ");
		pw.print(param.getName());
		return true;
	}

	
	@Override
	public Boolean caseLabel(Label lab) {
		pw.print("\"" + lab.getName()  + "\" ");			
		if (! lab.getParams().isEmpty()) {
			pw.print("(");
			printList(lab.getParams());
			pw.print(")");
		}
		return true;
	}
	
	@Override
	public Boolean caseNeverProp(NeverProp np) {
		pw.print(SPACE+ "[never] : " );
		if (!isStrict) {
			pw.println();
			pw.incIndent();
			pw.indent();
		}
		doSwitch(np.getPredicate());
		if (!isStrict) {
			pw.println();
			pw.decIndent();
		}
		return true;
	}

	@Override
	public Boolean caseInvariantProp (InvariantProp np) {
		pw.print(SPACE+ "[invariant] : " );
		if (!isStrict) {
			pw.println();
			pw.incIndent();
			pw.indent();
		}
		doSwitch(np.getPredicate());
		if (!isStrict) {
			pw.println();
			pw.decIndent();
		}
		return true;
	}

	
	@Override
	public Boolean caseReachableProp(ReachableProp np) {
		pw.print(SPACE + "[reachable] :" );
		if (!isStrict) {
			pw.println();
			pw.incIndent();
			pw.indent();
		}
		doSwitch(np.getPredicate());
		if (!isStrict) {
			pw.println();
			pw.decIndent();
		}
		return true;
	}
	
	@Override
	public Boolean casePredicate(Predicate pred) {
		if (pred.getComment() != null) {
			pw.println(pred.getComment());
		}
		pw.indent();		
		pw.print("predicate ");
		pw.print(pred.getName());
		pw.print(" = ");
		doSwitch(pred.getValue());
		pw.print(";");
		pw.println();
		
		return true;
	}
	
	
	@Override
	public Boolean caseSelfCall(SelfCall call) {
		caseStatement(call);
		pw.indent();
		pw.print("self");
		pw.print('.');
		pw.print("\"" + call.getLabel().getName()  + "\""); 
		if (! call.getParams().isEmpty()) {
			pw.print("( ");
			printList(call.getParams());
			pw.print(" )");
		}
		pw.print(';');
		pw.println();
		return true;
	}
	
	@Override
	public Boolean caseParamDef(ParamDef pdef) {
		pw.print(pdef.getParam().getName());
		pw.print("=");
		pw.print(pdef.getValue());
		return true;
	}
	
	@Override
	public Boolean caseParamRef(ParamRef pref) {
		pw.print(pref.getRefParam().getName());
		return true;
	}
	
	@Override
	public Boolean caseSynchronization(Synchronization tr) {
//		if (tr.getComment() != null) {
//			pw.println(tr.getComment());
//		}
		pw.indent();		
		pw.print("synchronization ");
		pw.print(tr.getName());
		if (! tr.getParams().isEmpty()) {
			pw.print("(");
			printList(tr.getParams());
			pw.print(")");
		}
		pw.print(" ");
		if (tr.getLabel() != null) {
			pw.print("label ");
			pw.print("\"" + tr.getLabel().getName()  + "\" ");			
		}
		pw.print("{");
		pw.println();
		pw.incIndent();
		
		for (Statement act : tr.getActions()) {
			doSwitch(act);
		}		
		
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	
	@Override
	public Boolean caseCompositeTypeDeclaration(CompositeTypeDeclaration ctd) {
		pw.print("composite ");
		pw.print(ctd.getName());

		if (! ctd.getTemplateParams().isEmpty()) {
			pw.print("<");
			printList(ctd.getTemplateParams());
			pw.print("> ");
		}
		
		
		if (! ctd.getParams().isEmpty()) {
			pw.print(" (");
			printList(ctd.getParams());
			pw.print(")");
		}
		pw.print(" {");
		pw.println();
		pw.incIndent();
		
		for (TypedefDeclaration td : ctd.getTypedefs()) {
			caseTypedefDeclaration(td);
		}
		
		for (InstanceDecl var : ctd.getInstances()) {
			doSwitch(var);
		}
		
		for (Synchronization tr : ctd.getSynchronizations()) {
			caseSynchronization(tr);
		}		
		pw.decIndent();
		pw.println("}");
		return true;
	}
	
	@Override
	public Boolean caseProperty(Property prop) {
		pw.print("property ");
		if (isStrict) {
			pw.print(prop.getName() +" ");			
		} else {
			pw.print("\"" + prop.getName()  + "\" ");			
		}
		doSwitch(prop.getBody());
		pw.println(";");
		return true;
	}
	
	@Override
	public Boolean caseQualifiedReference(QualifiedReference qref) {
		doSwitch(qref.getQualifier());
		if (isStrict) {
			pw.print('.');
		} else {
			pw.print(":");
		}
		doSwitch(qref.getNext());
		return true;
	}
	
	@Override
	public Boolean caseTemplateTypeDeclaration(TemplateTypeDeclaration ttd) {
		pw.print(ttd.getName());
		pw.print(" extends ");
		printList(ttd.getInterfaces());
		return true;
	}
	
	@Override
	public Boolean caseUnaryMinus(UnaryMinus minus) {
		pw.print("-");
		boolean lessPrio =  isStrict || minus.getValue() instanceof BinaryIntExpression;
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(minus.getValue());
		if (lessPrio) pw.print(")"+SPACE);
		return true;
	}
	
	@Override
	public Boolean caseVariableReference(VariableReference vref) {
		pw.print(vref.getRef().getName());
		if (vref.getIndex() != null) {
			pw.print("["+SPACE);
			doSwitch(vref.getIndex());
			pw.print(SPACE+"]");
		}		
		return true;
	}
	
	@Override
	public Boolean caseWrapBoolExpr(WrapBoolExpr wrap) {
		pw.print("(");
		doSwitch(wrap.getValue());
		pw.print(")");
		return true;
	}
}
