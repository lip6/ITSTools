package fr.lip6.move.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import fr.lip6.move.gal.Addition;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constante;
import fr.lip6.move.gal.Division;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.Modulo;
import fr.lip6.move.gal.Multiplication;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Power;
import fr.lip6.move.gal.Subtraction;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnitaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
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
			case GalPackage.ADDITION:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Addition(context, (Addition) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.AND:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0()) {
					sequence_And(context, (And) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.ASSIGNMENT:
				if(context == grammarAccess.getAssignmentRule()) {
					sequence_Assignment(context, (Assignment) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.BOOLEAN_EXPRESSION:
				if(context == grammarAccess.getComparisonRule() ||
				   context == grammarAccess.getNotAccess().getNotValAction_2()) {
					sequence_Comparison(context, (BooleanExpression) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.CONSTANTE:
				if(context == grammarAccess.getConstanteRule() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Constante(context, (Constante) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.DIVISION:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0() ||
				   context == grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Multiplication(context, (Division) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.FALSE:
				if(context == grammarAccess.getFalseRule() ||
				   context == grammarAccess.getNotAccess().getNotValAction_2() ||
				   context == grammarAccess.getPrimaryBoolRule()) {
					sequence_False(context, (False) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.MODULO:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0() ||
				   context == grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Multiplication(context, (Modulo) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.MULTIPLICATION:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0() ||
				   context == grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Multiplication(context, (Multiplication) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.NOT:
				if(context == grammarAccess.getAndRule() ||
				   context == grammarAccess.getAndAccess().getAndLeftAction_1_0() ||
				   context == grammarAccess.getNotRule() ||
				   context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0()) {
					sequence_Not(context, (Not) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.OR:
				if(context == grammarAccess.getOrRule() ||
				   context == grammarAccess.getOrAccess().getOrLeftAction_1_0()) {
					sequence_Or(context, (Or) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.POWER:
				if(context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Power(context, (Power) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.SUBTRACTION:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_Addition(context, (Subtraction) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.SYSTEM:
				if(context == grammarAccess.getSystemRule()) {
					sequence_System(context, (fr.lip6.move.gal.System) semanticObject); 
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
				if(context == grammarAccess.getNotAccess().getNotValAction_2() ||
				   context == grammarAccess.getPrimaryBoolRule() ||
				   context == grammarAccess.getTrueRule()) {
					sequence_True(context, (True) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.UNITARY_MINUS:
				if(context == grammarAccess.getAdditionRule() ||
				   context == grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0() ||
				   context == grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationRule() ||
				   context == grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0() ||
				   context == grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0() ||
				   context == grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0() ||
				   context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()) {
					sequence_UnitaryMinus(context, (UnitaryMinus) semanticObject); 
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
				if(context == grammarAccess.getPowerRule() ||
				   context == grammarAccess.getPowerAccess().getPowerLeftAction_1_0() ||
				   context == grammarAccess.getPrimaryRule() ||
				   context == grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2() ||
				   context == grammarAccess.getVariableRefRule()) {
					sequence_VariableRef(context, (VariableRef) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (left=Addition_Addition_1_0_0_0 right=Multiplication)
	 */
	protected void sequence_Addition(EObject context, Addition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ADDITION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ADDITION__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ADDITION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ADDITION__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Addition_Subtraction_1_0_1_0 right=Multiplication)
	 */
	protected void sequence_Addition(EObject context, Subtraction semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.SUBTRACTION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.SUBTRACTION__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.SUBTRACTION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.SUBTRACTION__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=And_And_1_0 right=Not)
	 */
	protected void sequence_And(EObject context, And semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((var=VariableRef expr=Addition) | (aVar+=VariableRef index=Addition expr=Addition))
	 */
	protected void sequence_Assignment(EObject context, Assignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Addition operator=CompOperators right=Addition)
	 */
	protected void sequence_Comparison(EObject context, BooleanExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__OPERATOR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__OPERATOR));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getComparisonAccess().getLeftAdditionParserRuleCall_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getComparisonAccess().getOperatorCompOperatorsParserRuleCall_1_0(), semanticObject.getOperator());
		feeder.accept(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     val=INT
	 */
	protected void sequence_Constante(EObject context, Constante semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.CONSTANTE__VAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.CONSTANTE__VAL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0(), semanticObject.getVal());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value='False'
	 */
	protected void sequence_False(EObject context, False semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Multiplication_Division_1_0_1_0 right=UnitaryMinus)
	 */
	protected void sequence_Multiplication(EObject context, Division semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.DIVISION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.DIVISION__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.DIVISION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.DIVISION__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Multiplication_Modulo_1_0_2_0 right=UnitaryMinus)
	 */
	protected void sequence_Multiplication(EObject context, Modulo semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.MODULO__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.MODULO__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.MODULO__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.MODULO__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Multiplication_Multiplication_1_0_0_0 right=UnitaryMinus)
	 */
	protected void sequence_Multiplication(EObject context, Multiplication semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.MULTIPLICATION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.MULTIPLICATION__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.MULTIPLICATION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.MULTIPLICATION__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     val=Not_Not_2
	 */
	protected void sequence_Not(EObject context, Not semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Or_Or_1_0 right=And)
	 */
	protected void sequence_Or(EObject context, Or semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Power_Power_1_0 right=Primary)
	 */
	protected void sequence_Power(EObject context, Power semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.POWER__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.POWER__LEFT));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.POWER__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.POWER__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPowerAccess().getPowerLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=QualifiedName variables+=VariableDeclaration* transitions+=Transition+)?
	 */
	protected void sequence_System(EObject context, fr.lip6.move.gal.System semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID guard=Or label=STRING? assignments+=Assignment+)
	 */
	protected void sequence_Transition(EObject context, Transition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value='True'
	 */
	protected void sequence_True(EObject context, True semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     val=UnitaryMinus_UnitaryMinus_2
	 */
	protected void sequence_UnitaryMinus(EObject context, UnitaryMinus semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.UNITARY_MINUS__VAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.UNITARY_MINUS__VAL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2(), semanticObject.getVal());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((name=QualifiedName initVal=INT) | (aname=QualifiedName index=Addition initVal=INT))
	 */
	protected void sequence_VariableDeclaration(EObject context, Variable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     var=[Variable|ID]
	 */
	protected void sequence_VariableRef(EObject context, VariableRef semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE_REF__VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE_REF__VAR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1(), semanticObject.getVar());
		feeder.finish();
	}
}
