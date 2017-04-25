package fr.lip6.move.serialization;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.AF;
import fr.lip6.move.gal.AG;
import fr.lip6.move.gal.AU;
import fr.lip6.move.gal.AX;
import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.EF;
import fr.lip6.move.gal.EG;
import fr.lip6.move.gal.EU;
import fr.lip6.move.gal.EX;
import fr.lip6.move.gal.Equiv;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Fixpoint;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Imply;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.LTLFuture;
import fr.lip6.move.gal.LTLGlobally;
import fr.lip6.move.gal.LTLNext;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LTLRelease;
import fr.lip6.move.gal.LTLStrongRelease;
import fr.lip6.move.gal.LTLUntil;
import fr.lip6.move.gal.LTLWeakUntil;
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

	protected IndentedPrintWriter pw;
	private boolean isCTL = false;
	private boolean isInQRef;
	private boolean isLTL;

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
		setStream(stream,0);
	}

	public void setStream(OutputStream stream, int indent) {
		pw = new IndentedPrintWriter(new PrintWriter(stream));
		for (int i =0; i < indent ; i++)
			pw.incIndent();
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
		if (lessPrio) pw.print(")"+SPACE);
		pw.print(SPACE+"||"+SPACE);
		if (lessPrio) pw.print("("+SPACE);
		doSwitch(or.getRight());
		if (lessPrio) pw.print(")"+SPACE);
		return true;
	}
	
	
	@Override
	public Boolean caseArrayInstanceDeclaration(ArrayInstanceDeclaration aid) {
		pw.indent();
		pw.print(aid.getType().getName());
		pw.print(" [");
		doSwitch(aid.getSize());
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
		case EQ : if (!isCTL) return "==" ; else return "=";
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
		if (isLTL) {
			pw.print(SPACE+"\"");
			doSwitch(comp.getLeft());
			pw.print(SPACE+ comp.getOperator().getLiteral() +SPACE);
			doSwitch(comp.getRight());
			pw.print("\""+SPACE);			
		} else {
			if (isStrict && comp.getLeft() instanceof Constant) {
				doSwitch(comp.getRight());
				pw.print(SPACE+ reverse(comp.getOperator()) +SPACE);
				doSwitch(comp.getLeft());
			} else {
				doSwitch(comp.getLeft());
				if (!isCTL || comp.getOperator()!=ComparisonOperators.EQ) {
					pw.print(SPACE+ comp.getOperator().getLiteral() +SPACE);
				} else {
					pw.print(SPACE+ "=" +SPACE);
				}
				doSwitch(comp.getRight());
			}
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
		if (isCTL) 
			pw.print("FALSE");
		else
			pw.print("false");
		return true;
	}
	@Override
	public Boolean caseTrue(True object) {
		if (isCTL) 
			pw.print("TRUE");
		else
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
	public Boolean caseBoundsProp (BoundsProp bp) {
		pw.print(SPACE+ "[bounds] : ");
		boolean first = true;
		for (TreeIterator<EObject> it = bp.getTarget().eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableReference) {
				VariableReference vref = (VariableReference) obj;
				if (first) {
					first = false;
				} else {
					pw.print('+');
				}
				doSwitch(vref);
			}
		}
		return true;
	}
	
	@Override
	public Boolean caseNeverProp(NeverProp np) {
		pw.print(SPACE+ "[never] : ");
		printPredicate(np.getPredicate());
		return true;
	}

	@Override
	public Boolean caseInvariantProp (InvariantProp np) {
		pw.print(SPACE+ "[invariant] : " );
		printPredicate(np.getPredicate());
		return true;
	}

	
	@Override
	public Boolean caseReachableProp(ReachableProp np) {
		pw.print(SPACE + "[reachable] : " );
		printPredicate(np.getPredicate());
		return true;
	}

	@Override
	public Boolean caseCTLProp(CTLProp np) {
		if (! isCTL) {
			pw.print(SPACE + "[ctl] : " );
		}
		printPredicate(np.getPredicate());
		return true;
	}

	@Override
	public Boolean caseLTLProp(LTLProp np) {
		if (! isLTL) {
			pw.print(SPACE + "[ltl] : " );
		}
		printPredicate(np.getPredicate());
		return true;
	}

	
	private void printPredicate(BooleanExpression predicate) {
		if (!isStrict) {
			pw.println();
			pw.incIndent();
			pw.indent();
		} else {
			pw.print('(');
		}
		doSwitch(predicate);
		if (!isStrict) {
			pw.println();
			pw.decIndent();
		} else {
			pw.print(')');
		}
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
		} else {
			pw.print("label ");
			pw.print("\"\" ");						
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
		if (isCTL || isLTL) {
			pw.print("#");
		}
		pw.print("property ");
		if (isStrict) {
			pw.print(prop.getName() +" ");			
		} else {
			pw.print("\"" + prop.getName()  + "\" ");			
		}
		if (isCTL || isLTL) {
			pw.println();
		}
		if (isLTL) {
			// negate the LTL sentence
			pw.print("!(");
		}
		doSwitch(prop.getBody());
		if (isLTL) {
			pw.print(")");
		}
		if (isCTL) {
			pw.println(";");
		} else {
			pw.println();
			if (!isStrict)
				pw.println(";");
		}
		return true;
	}
	
	@Override
	public Boolean caseQualifiedReference(QualifiedReference qref) {
		boolean doreset = false;
		if (!isInQRef) {
			isInQRef = true;
			doreset = true;
			if (isCTL) 
				pw.print("\"");
		}
		doSwitch(qref.getQualifier());
		if (isStrict) {
			pw.print('.');
		} else {
			pw.print(":");
		}
		doSwitch(qref.getNext());
		if (doreset) {
			isInQRef = false;
			if (isCTL) 
				pw.print("\"");
		}
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
		if (isCTL && ! isInQRef) {
			pw.print("\"");
		}
		pw.print(vref.getRef().getName());
		if (vref.getIndex() != null) {
			pw.print("["+SPACE);
			doSwitch(vref.getIndex());
			pw.print(SPACE+"]");
		}
		if (isCTL && ! isInQRef) {
			pw.print("\"");
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
	@Override
	public Boolean caseLTLFuture(LTLFuture object) {
		pw.print("F(");
		doSwitch(object.getProp());
		pw.print(")");		
		return true;	
	}
	@Override
	public Boolean caseLTLGlobally(LTLGlobally object) {
		pw.print("G(");
		doSwitch(object.getProp());
		pw.print(")");		
		return true;	
	}
	
	@Override
	public Boolean caseLTLNext(LTLNext object) {
		pw.print("X(");
		doSwitch(object.getProp());
		pw.print(")");		
		return true;
	}
	
	@Override
	public Boolean caseLTLRelease(LTLRelease object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print(")R(");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	@Override
	public Boolean caseLTLStrongRelease(LTLStrongRelease object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print(")M(");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	@Override
	public Boolean caseLTLUntil(LTLUntil object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print(")U(");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	@Override
	public Boolean caseLTLWeakUntil(LTLWeakUntil object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print(")W(");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	
	
	@Override
	public Boolean caseAF(AF o) {
		pw.print("AF(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}
	@Override
	public Boolean caseAG(AG o) {
		pw.print("AG(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}
	@Override
	public Boolean caseAX(AX o) {
		pw.print("AX(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}

	@Override
	public Boolean caseEF(EF o) {
		pw.print("EF(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}
	@Override
	public Boolean caseEG(EG o) {
		pw.print("EG(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}
	@Override
	public Boolean caseEX(EX o) {
		pw.print("EX(");
		doSwitch(o.getProp());
		pw.print(")");		
		return true;
	}
	
	@Override
	public Boolean caseEU(EU object) {
		pw.print("E((");
		doSwitch(object.getLeft());
		pw.print(")U(");
		doSwitch(object.getRight());
		pw.print("))");
		return true;
	}
	@Override
	public Boolean caseAU(AU object) {
		pw.print("A((");
		doSwitch(object.getLeft());
		pw.print(")U(");
		doSwitch(object.getRight());
		pw.print("))");
		return true;
	}
	
	@Override
	public Boolean caseImply(Imply object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print("->");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	@Override
	public Boolean caseEquiv(Equiv object) {
		pw.print("(");
		doSwitch(object.getLeft());
		pw.print("->");
		doSwitch(object.getRight());
		pw.print(")");
		return true;
	}
	
	public void setCTL(boolean b) {
		this.isCTL  = b;
	}
	
	public void setLTL(boolean b) {
		this.isLTL  = b;
	}

}
