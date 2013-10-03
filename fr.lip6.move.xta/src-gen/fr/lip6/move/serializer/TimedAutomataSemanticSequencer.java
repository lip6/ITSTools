package fr.lip6.move.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import fr.lip6.move.services.TimedAutomataGrammarAccess;
import fr.lip6.move.timedAutomata.And;
import fr.lip6.move.timedAutomata.ArrayDecl;
import fr.lip6.move.timedAutomata.Assign;
import fr.lip6.move.timedAutomata.Assignments;
import fr.lip6.move.timedAutomata.BinaryIntExpression;
import fr.lip6.move.timedAutomata.BitComplement;
import fr.lip6.move.timedAutomata.BoolType;
import fr.lip6.move.timedAutomata.ChanId;
import fr.lip6.move.timedAutomata.ChannelType;
import fr.lip6.move.timedAutomata.ClockType;
import fr.lip6.move.timedAutomata.Comparison;
import fr.lip6.move.timedAutomata.Constant;
import fr.lip6.move.timedAutomata.DeclId;
import fr.lip6.move.timedAutomata.False;
import fr.lip6.move.timedAutomata.Initialiser;
import fr.lip6.move.timedAutomata.Instance;
import fr.lip6.move.timedAutomata.IntegerType;
import fr.lip6.move.timedAutomata.Not;
import fr.lip6.move.timedAutomata.Or;
import fr.lip6.move.timedAutomata.Parameter;
import fr.lip6.move.timedAutomata.ProcBody;
import fr.lip6.move.timedAutomata.ProcDecl;
import fr.lip6.move.timedAutomata.RangeType;
import fr.lip6.move.timedAutomata.Recv;
import fr.lip6.move.timedAutomata.Send;
import fr.lip6.move.timedAutomata.StateDecl;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;
import fr.lip6.move.timedAutomata.Transition;
import fr.lip6.move.timedAutomata.True;
import fr.lip6.move.timedAutomata.TypeDecl;
import fr.lip6.move.timedAutomata.TypedefRef;
import fr.lip6.move.timedAutomata.UnaryMinus;
import fr.lip6.move.timedAutomata.VarAccess;
import fr.lip6.move.timedAutomata.VariableDecl;
import fr.lip6.move.timedAutomata.WrapBoolExpr;
import fr.lip6.move.timedAutomata.XTA;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class TimedAutomataSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private TimedAutomataGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == TimedAutomataPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case TimedAutomataPackage.AND:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_And(context, (And) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.ARRAY_DECL:
				if(context == grammarAccess.getArrayDeclRule()) {
					sequence_ArrayDecl(context, (ArrayDecl) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.ASSIGN:
				if(context == grammarAccess.getAssignRule()) {
					sequence_Assign(context, (Assign) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.ASSIGNMENTS:
				if(context == grammarAccess.getAssignmentsRule()) {
					sequence_Assignments(context, (Assignments) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.BINARY_INT_EXPRESSION:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule()) {
					sequence_Addition_BitAnd_BitOr_BitShift_BitXor_Multiplication_Power(context, (BinaryIntExpression) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.BIT_COMPLEMENT:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule()) {
					sequence_BitComplement(context, (BitComplement) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.BOOL_TYPE:
				if(context == grammarAccess.getBasicTypeRule() ||
				   context == grammarAccess.getBoolTypeRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_BoolType(context, (BoolType) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.CHAN_ID:
				if(context == grammarAccess.getChanIdRule()) {
					sequence_ChanId(context, (ChanId) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.CHANNEL_TYPE:
				if(context == grammarAccess.getChannelDeclRule()) {
					sequence_ChannelDecl_ChannelType(context, (ChannelType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getChannelTypeRule()) {
					sequence_ChannelType(context, (ChannelType) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.CLOCK_TYPE:
				if(context == grammarAccess.getBasicTypeRule() ||
				   context == grammarAccess.getClockTypeRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_ClockType(context, (ClockType) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.COMPARISON:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getComparisonRule() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Comparison(context, (Comparison) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.CONSTANT:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getConstRefRule() ||
				   context == grammarAccess.getConstantRule() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule()) {
					sequence_Constant(context, (Constant) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.DECL_ID:
				if(context == grammarAccess.getDeclIdRule() ||
				   context == grammarAccess.getFormalDeclarationRule()) {
					sequence_DeclId(context, (DeclId) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.FALSE:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getFalseRule() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_False(context, (False) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.INITIALISER:
				if(context == grammarAccess.getInitialiserRule()) {
					sequence_Initialiser(context, (Initialiser) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.INSTANCE:
				if(context == grammarAccess.getInstanceRule() ||
				   context == grammarAccess.getInstantiableInSystemRule()) {
					sequence_Instance(context, (Instance) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.INTEGER_TYPE:
				if(context == grammarAccess.getBasicTypeRule() ||
				   context == grammarAccess.getIntegerTypeRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_IntegerType(context, (IntegerType) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.NOT:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Not(context, (Not) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.OR:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Or(context, (Or) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.PARAMETER:
				if(context == grammarAccess.getFormalDeclarationRule() ||
				   context == grammarAccess.getParameterRule()) {
					sequence_Parameter(context, (Parameter) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.PROC_BODY:
				if(context == grammarAccess.getProcBodyRule()) {
					sequence_ProcBody(context, (ProcBody) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.PROC_DECL:
				if(context == grammarAccess.getInstantiableInSystemRule() ||
				   context == grammarAccess.getProcDeclRule()) {
					sequence_ProcDecl(context, (ProcDecl) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.RANGE_TYPE:
				if(context == grammarAccess.getBasicTypeRule() ||
				   context == grammarAccess.getRangeTypeRule() ||
				   context == grammarAccess.getTypeRule()) {
					sequence_RangeType(context, (RangeType) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.RECV:
				if(context == grammarAccess.getRecvRule() ||
				   context == grammarAccess.getSyncRule()) {
					sequence_Recv(context, (Recv) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.SEND:
				if(context == grammarAccess.getSendRule() ||
				   context == grammarAccess.getSyncRule()) {
					sequence_Send(context, (Send) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.STATE_DECL:
				if(context == grammarAccess.getStateDeclRule()) {
					sequence_StateDecl(context, (StateDecl) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.SYSTEM:
				if(context == grammarAccess.getSystemRule()) {
					sequence_System(context, (fr.lip6.move.timedAutomata.System) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.TRANSITION:
				if(context == grammarAccess.getTransitionRule()) {
					sequence_Transition(context, (Transition) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.TRUE:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule() ||
				   context == grammarAccess.getTrueRule()) {
					sequence_True(context, (True) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.TYPE_DECL:
				if(context == grammarAccess.getTypeDeclRule()) {
					sequence_TypeDecl(context, (TypeDecl) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.TYPEDEF_REF:
				if(context == grammarAccess.getTypeRule() ||
				   context == grammarAccess.getTypedefRefRule()) {
					sequence_TypedefRef(context, (TypedefRef) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.UNARY_MINUS:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule()) {
					sequence_BitComplement(context, (UnaryMinus) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.VAR_ACCESS:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getVarAccessRule()) {
					sequence_VarAccess(context, (VarAccess) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.VARIABLE_DECL:
				if(context == grammarAccess.getVariableDeclRule()) {
					sequence_VariableDecl(context, (VariableDecl) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.WRAP_BOOL_EXPR:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitAndRule() ||
				   context == grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitComplementRule() ||
				   context == grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2() ||
				   context == grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2() ||
				   context == grammarAccess.getBitOrRule() ||
				   context == grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitShiftRule() ||
				   context == grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getBitXorRule() ||
				   context == grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getWrapBoolExprRule()) {
					sequence_WrapBoolExpr(context, (WrapBoolExpr) semanticObject); 
					return; 
				}
				else break;
			case TimedAutomataPackage.XTA:
				if(context == grammarAccess.getXTARule()) {
					sequence_XTA(context, (XTA) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         (left=Multiplication_BinaryIntExpression_1_0 (op='/' | op='*' | op='%') right=BitComplement) | 
	 *         (left=Power_BinaryIntExpression_1_0 op='**' right=Primary) | 
	 *         (left=Addition_BinaryIntExpression_1_0 (op='+' | op='-') right=Multiplication) | 
	 *         (left=BitShift_BinaryIntExpression_1_0 (op='<<' | op='>>') right=Addition) | 
	 *         (left=BitAnd_BinaryIntExpression_1_0 op='&' right=BitShift) | 
	 *         (left=BitXor_BinaryIntExpression_1_0 op='^' right=BitAnd) | 
	 *         (left=BitOr_BinaryIntExpression_1_0 op='|' right=BitXor)
	 *     )
	 */
	protected void sequence_Addition_BitAnd_BitOr_BitShift_BitXor_Multiplication_Power(EObject context, BinaryIntExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=And_And_1_0 right=Not)
	 */
	protected void sequence_And(EObject context, And semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.AND__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.AND__LEFT));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.AND__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.AND__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAndAccess().getAndLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     size=INT
	 */
	protected void sequence_ArrayDecl(EObject context, ArrayDecl semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.ARRAY_DECL__SIZE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.ARRAY_DECL__SIZE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getArrayDeclAccess().getSizeINTTerminalRuleCall_1_0(), semanticObject.getSize());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (lhs=VarAccess rhs=BitOr)
	 */
	protected void sequence_Assign(EObject context, Assign semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.ASSIGN__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.ASSIGN__LHS));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.ASSIGN__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.ASSIGN__RHS));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignAccess().getLhsVarAccessParserRuleCall_0_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getAssignAccess().getRhsBitOrParserRuleCall_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (assigns+=Assign assigns+=Assign*)
	 */
	protected void sequence_Assignments(EObject context, Assignments semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=BitComplement_BitComplement_0_2
	 */
	protected void sequence_BitComplement(EObject context, BitComplement semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.BIT_COMPLEMENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.BIT_COMPLEMENT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=BitComplement_UnaryMinus_1_2
	 */
	protected void sequence_BitComplement(EObject context, UnaryMinus semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.UNARY_MINUS__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.UNARY_MINUS__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (const?='const'?)
	 */
	protected void sequence_BoolType(EObject context, BoolType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_ChanId(EObject context, ChanId semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.CHAN_ID__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.CHAN_ID__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getChanIdAccess().getNameIDTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (urgent?='urgent'? broadcast?='broadcast'? chans+=ChanId chans+=ChanId*)
	 */
	protected void sequence_ChannelDecl_ChannelType(EObject context, ChannelType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (urgent?='urgent'? broadcast?='broadcast'?)
	 */
	protected void sequence_ChannelType(EObject context, ChannelType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {ClockType}
	 */
	protected void sequence_ClockType(EObject context, ClockType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=BitOr operator=ComparisonOperators right=BitOr)
	 */
	protected void sequence_Comparison(EObject context, Comparison semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.COMPARISON__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.COMPARISON__LEFT));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.COMPARISON__OPERATOR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.COMPARISON__OPERATOR));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.COMPARISON__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.COMPARISON__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getComparisonAccess().getLeftBitOrParserRuleCall_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getComparisonAccess().getOperatorComparisonOperatorsEnumRuleCall_1_0(), semanticObject.getOperator());
		feeder.accept(grammarAccess.getComparisonAccess().getRightBitOrParserRuleCall_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=INT
	 */
	protected void sequence_Constant(EObject context, Constant semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.CONSTANT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.CONSTANT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getConstantAccess().getValueINTTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID arrays+=ArrayDecl* init=Initialiser?)
	 */
	protected void sequence_DeclId(EObject context, DeclId semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {False}
	 */
	protected void sequence_False(EObject context, False semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=BitOr
	 */
	protected void sequence_Initialiser(EObject context, Initialiser semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.INITIALISER__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.INITIALISER__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getInitialiserAccess().getExprBitOrParserRuleCall_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID type=[ProcDecl|ID] (args+=BitOr args+=BitOr*)?)
	 */
	protected void sequence_Instance(EObject context, Instance semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (const?='const'?)
	 */
	protected void sequence_IntegerType(EObject context, IntegerType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=PrimaryBool
	 */
	protected void sequence_Not(EObject context, Not semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.NOT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.NOT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNotAccess().getValuePrimaryBoolParserRuleCall_0_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Or_Or_1_0 right=And)
	 */
	protected void sequence_Or(EObject context, Or semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.OR__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.OR__LEFT));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.OR__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.OR__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getOrAccess().getOrLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (type=Type name=ID)
	 */
	protected void sequence_Parameter(EObject context, Parameter semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.FORMAL_DECLARATION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.FORMAL_DECLARATION__NAME));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.PARAMETER__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.PARAMETER__TYPE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getParameterAccess().getTypeTypeParserRuleCall_0_0(), semanticObject.getType());
		feeder.accept(grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (variables+=VariableDecl | types+=TypeDecl)* 
	 *         states+=StateDecl 
	 *         states+=StateDecl* 
	 *         (commitStates+=[StateDecl|ID] commitStates+=[StateDecl|ID]*)? 
	 *         (urgentStates+=[StateDecl|ID] urgentStates+=[StateDecl|ID]*)? 
	 *         initState=[StateDecl|ID] 
	 *         transitions+=Transition 
	 *         transitions+=Transition*
	 *     )
	 */
	protected void sequence_ProcBody(EObject context, ProcBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (params+=Parameter params+=Parameter*)? body=ProcBody)
	 */
	protected void sequence_ProcDecl(EObject context, ProcDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (const?='const'? min=INT max=INT)
	 */
	protected void sequence_RangeType(EObject context, RangeType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     channel=[ChanId|ID]
	 */
	protected void sequence_Recv(EObject context, Recv semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.SYNC__CHANNEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.SYNC__CHANNEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRecvAccess().getChannelChanIdIDTerminalRuleCall_1_0_1(), semanticObject.getChannel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     channel=[ChanId|ID]
	 */
	protected void sequence_Send(EObject context, Send semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.SYNC__CHANNEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.SYNC__CHANNEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSendAccess().getChannelChanIdIDTerminalRuleCall_1_0_1(), semanticObject.getChannel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID invariant=Or?)
	 */
	protected void sequence_StateDecl(EObject context, StateDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (instances+=[InstantiableInSystem|ID] instances+=[InstantiableInSystem|ID]*)
	 */
	protected void sequence_System(EObject context, fr.lip6.move.timedAutomata.System semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (src=[StateDecl|ID] dest=[StateDecl|ID] guard=Or? sync=Sync? assign=Assignments?)
	 */
	protected void sequence_Transition(EObject context, Transition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {True}
	 */
	protected void sequence_True(EObject context, True semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (type=Type name=ID)
	 */
	protected void sequence_TypeDecl(EObject context, TypeDecl semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.TYPE_DECL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.TYPE_DECL__TYPE));
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.TYPE_DECL__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.TYPE_DECL__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTypeDeclAccess().getTypeTypeParserRuleCall_1_0(), semanticObject.getType());
		feeder.accept(grammarAccess.getTypeDeclAccess().getNameIDTerminalRuleCall_2_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ref=[TypeDecl|ID]
	 */
	protected void sequence_TypedefRef(EObject context, TypedefRef semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.TYPEDEF_REF__REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.TYPEDEF_REF__REF));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTypedefRefAccess().getRefTypeDeclIDTerminalRuleCall_0_1(), semanticObject.getRef());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ref=[FormalDeclaration|ID]
	 */
	protected void sequence_VarAccess(EObject context, VarAccess semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.VAR_ACCESS__REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.VAR_ACCESS__REF));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVarAccessAccess().getRefFormalDeclarationIDTerminalRuleCall_0_1(), semanticObject.getRef());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (type=BasicType declid+=DeclId declid+=DeclId*)
	 */
	protected void sequence_VariableDecl(EObject context, VariableDecl semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Or
	 */
	protected void sequence_WrapBoolExpr(EObject context, WrapBoolExpr semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, TimedAutomataPackage.Literals.WRAP_BOOL_EXPR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TimedAutomataPackage.Literals.WRAP_BOOL_EXPR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getWrapBoolExprAccess().getValueOrParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((variables+=VariableDecl | channels+=ChannelDecl | types+=TypeDecl | templates+=ProcDecl)* instances+=Instance* system=System)
	 */
	protected void sequence_XTA(EObject context, XTA semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
