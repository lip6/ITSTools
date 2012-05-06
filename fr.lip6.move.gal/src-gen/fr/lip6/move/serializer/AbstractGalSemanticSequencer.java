package fr.lip6.move.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.InitValues;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Peek;
import fr.lip6.move.gal.Pop;
import fr.lip6.move.gal.Push;
import fr.lip6.move.gal.Transient;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.WrapBoolExpr;
import fr.lip6.move.services.GalGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("restriction")
public class AbstractGalSemanticSequencer extends AbstractSemanticSequencer {

	@Inject
	protected GalGrammarAccess grammarAccess;
	
	@Inject
	protected ISemanticSequencerDiagnosticProvider diagnosticProvider;
	
	@Inject
	protected ITransientValueService transientValues;
	
	@Inject
	@GenericSequencer
	protected Provider<ISemanticSequencer> genericSequencerProvider;
	
	protected ISemanticSequencer genericSequencer;
	
	
	@Override
	public void init(ISemanticSequencer sequencer, ISemanticSequenceAcceptor sequenceAcceptor, Acceptor errorAcceptor) {
		super.init(sequencer, sequenceAcceptor, errorAcceptor);
		this.genericSequencer = genericSequencerProvider.get();
		this.genericSequencer.init(sequencer, sequenceAcceptor, errorAcceptor);
	}
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == GalPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case GalPackage.AND:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_And(context, (And) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.ARRAY_PREFIX:
				if(context == grammarAccess.getArrayDeclarationRule()) {
					sequence_ArrayDeclaration(context, (ArrayPrefix) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.ARRAY_VAR_ACCESS:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getArrayVarAccessRule() ||
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
					sequence_ArrayVarAccess(context, (ArrayVarAccess) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.ASSIGNMENT:
				if(context == grammarAccess.getActionsRule() ||
				   context == grammarAccess.getAssignmentRule()) {
					sequence_Assignment(context, (Assignment) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.BINARY_INT_EXPRESSION:
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
					sequence_Multiplication(context, (BinaryIntExpression) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.BIT_COMPLEMENT:
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
			case GalPackage.COMPARISON:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getComparisonRule() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Comparison(context, (Comparison) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.CONSTANT:
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
			case GalPackage.FALSE:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getFalseRule() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_False(context, (False) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.INIT_VALUES:
				if(context == grammarAccess.getInitValuesRule()) {
					sequence_InitValues(context, (InitValues) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.LIST:
				if(context == grammarAccess.getListDeclarationRule()) {
					sequence_ListDeclaration(context, (List) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.NOT:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Not(context, (Not) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.OR:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_Or(context, (Or) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.PEEK:
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
				   context == grammarAccess.getPeekRule() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule()) {
					sequence_Peek(context, (Peek) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.POP:
				if(context == grammarAccess.getActionsRule() ||
				   context == grammarAccess.getPopRule()) {
					sequence_Pop(context, (Pop) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.PUSH:
				if(context == grammarAccess.getActionsRule() ||
				   context == grammarAccess.getPushRule()) {
					sequence_Push(context, (Push) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.SYSTEM:
				if(context == grammarAccess.getSystemRule()) {
					sequence_System(context, (fr.lip6.move.gal.System) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.TRANSIENT:
				if(context == grammarAccess.getTransientRule()) {
					sequence_Transient(context, (Transient) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.TRANSITION:
				if(context == grammarAccess.getTransitionRule()) {
					sequence_Transition(context, (Transition) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.TRUE:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getNotAccess().getNotValueAction_0_2() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryBoolRule() ||
				   context == grammarAccess.getTrueRule()) {
					sequence_True(context, (True) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.UNARY_MINUS:
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
			case GalPackage.VARIABLE:
				if(context == grammarAccess.getVariableDeclarationRule()) {
					sequence_VariableDeclaration(context, (Variable) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.VARIABLE_REF:
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
				   context == grammarAccess.getVarAccessRule() ||
				   context == grammarAccess.getVariableRefRule()) {
					sequence_VariableRef(context, (VariableRef) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.WRAP_BOOL_EXPR:
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
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (left=And_And_1_0 right=Not)
	 */
	protected void sequence_And(EObject context, And semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.AND__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.AND__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.AND__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.AND__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAndAccess().getAndLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (size=INT name=QualifiedName values=InitValues?)
	 */
	protected void sequence_ArrayDeclaration(EObject context, ArrayPrefix semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (prefix=[ArrayPrefix|QualifiedName] index=BitOr)
	 */
	protected void sequence_ArrayVarAccess(EObject context, ArrayVarAccess semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ARRAY_VAR_ACCESS__PREFIX) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ARRAY_VAR_ACCESS__PREFIX));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ARRAY_VAR_ACCESS__INDEX) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ARRAY_VAR_ACCESS__INDEX));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixQualifiedNameParserRuleCall_0_0_1(), semanticObject.getPrefix());
		feeder.accept(grammarAccess.getArrayVarAccessAccess().getIndexBitOrParserRuleCall_2_0(), semanticObject.getIndex());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=VarAccess right=BitOr)
	 */
	protected void sequence_Assignment(EObject context, Assignment semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ASSIGNMENT__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ASSIGNMENT__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ASSIGNMENT__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ASSIGNMENT__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignmentAccess().getLeftVarAccessParserRuleCall_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAssignmentAccess().getRightBitOrParserRuleCall_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=BitComplement_BitComplement_0_2
	 */
	protected void sequence_BitComplement(EObject context, BitComplement semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.BIT_COMPLEMENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.BIT_COMPLEMENT__VALUE));
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
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.UNARY_MINUS__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.UNARY_MINUS__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=BitOr operator=ComparisonOperators right=BitOr)
	 */
	protected void sequence_Comparison(EObject context, Comparison semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.COMPARISON__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.COMPARISON__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.COMPARISON__OPERATOR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.COMPARISON__OPERATOR));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.COMPARISON__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.COMPARISON__RIGHT));
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
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.CONSTANT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.CONSTANT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getConstantAccess().getValueINTTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value='False'
	 */
	protected void sequence_False(EObject context, False semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.FALSE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.FALSE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFalseAccess().getValueFalseKeyword_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (values+=Integer values+=Integer*)
	 */
	protected void sequence_InitValues(EObject context, InitValues semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=QualifiedName values=InitValues?)
	 */
	protected void sequence_ListDeclaration(EObject context, List semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
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
	protected void sequence_Multiplication(EObject context, BinaryIntExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Not_Not_0_2
	 */
	protected void sequence_Not(EObject context, Not semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.NOT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.NOT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNotAccess().getNotValueAction_0_2(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Or_Or_1_0 right=And)
	 */
	protected void sequence_Or(EObject context, Or semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.OR__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.OR__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.OR__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.OR__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getOrAccess().getOrLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     list=[List|QualifiedName]
	 */
	protected void sequence_Peek(EObject context, Peek semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.PEEK__LIST) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.PEEK__LIST));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPeekAccess().getListListQualifiedNameParserRuleCall_0_0_1(), semanticObject.getList());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     list=[List|QualifiedName]
	 */
	protected void sequence_Pop(EObject context, Pop semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.POP__LIST) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.POP__LIST));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPopAccess().getListListQualifiedNameParserRuleCall_0_0_1(), semanticObject.getList());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (list=[List|QualifiedName] value=BitOr)
	 */
	protected void sequence_Push(EObject context, Push semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.PUSH__LIST) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.PUSH__LIST));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.PUSH__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.PUSH__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPushAccess().getListListQualifiedNameParserRuleCall_0_0_1(), semanticObject.getList());
		feeder.accept(grammarAccess.getPushAccess().getValueBitOrParserRuleCall_3_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=QualifiedName 
	 *         variables+=VariableDeclaration* 
	 *         arrays+=ArrayDeclaration* 
	 *         lists+=ListDeclaration* 
	 *         transitions+=Transition* 
	 *         transient=Transient?
	 *     )
	 */
	protected void sequence_System(EObject context, fr.lip6.move.gal.System semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Or
	 */
	protected void sequence_Transient(EObject context, Transient semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.TRANSIENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.TRANSIENT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTransientAccess().getValueOrParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=QualifiedName guard=Or label=QualifiedName? actions+=Actions*)
	 */
	protected void sequence_Transition(EObject context, Transition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value='True'
	 */
	protected void sequence_True(EObject context, True semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.TRUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.TRUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTrueAccess().getValueTrueKeyword_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=QualifiedName value=Integer)
	 */
	protected void sequence_VariableDeclaration(EObject context, Variable semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE__NAME));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_1_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getVariableDeclarationAccess().getValueIntegerParserRuleCall_3_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     referencedVar=[Variable|QualifiedName]
	 */
	protected void sequence_VariableRef(EObject context, VariableRef semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE_REF__REFERENCED_VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE_REF__REFERENCED_VAR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVariableRefAccess().getReferencedVarVariableQualifiedNameParserRuleCall_0_1(), semanticObject.getReferencedVar());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=Or
	 */
	protected void sequence_WrapBoolExpr(EObject context, WrapBoolExpr semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.WRAP_BOOL_EXPR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.WRAP_BOOL_EXPR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getWrapBoolExprAccess().getValueOrParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
}
