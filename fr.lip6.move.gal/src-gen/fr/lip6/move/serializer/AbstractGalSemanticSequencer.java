package fr.lip6.move.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import fr.lip6.move.gal.Addition;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constante;
import fr.lip6.move.gal.Division;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.Modulo;
import fr.lip6.move.gal.Multiplication;
import fr.lip6.move.gal.Power;
import fr.lip6.move.gal.Subtraction;
import fr.lip6.move.gal.Transition;
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
			case GalPackage.ASSIGNMENT:
				if(context == grammarAccess.getAssignmentRule()) {
					sequence_Assignment(context, (Assignment) semanticObject); 
					return; 
				}
				else break;
			case GalPackage.BOOLEAN_EXPRESSION:
				if(context == grammarAccess.getNotRule()) {
					sequence_Not(context, (BooleanExpression) semanticObject); 
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
	 *     (var=VariableRef expr=Addition)
	 */
	protected void sequence_Assignment(EObject context, Assignment semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ASSIGNMENT__VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ASSIGNMENT__VAR));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.ASSIGNMENT__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.ASSIGNMENT__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0(), semanticObject.getVar());
		feeder.accept(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0(), semanticObject.getExpr());
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
	 *     empty=ID
	 */
	protected void sequence_Not(EObject context, BooleanExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__EMPTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.BOOLEAN_EXPRESSION__EMPTY));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNotAccess().getEmptyIDTerminalRuleCall_0(), semanticObject.getEmpty());
		feeder.finish();
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
	 *     (name=ID variables+=VariableDeclaration* transitions+=Transition+)?
	 */
	protected void sequence_System(EObject context, fr.lip6.move.gal.System semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID label=STRING? assignments+=Assignment+)
	 */
	protected void sequence_Transition(EObject context, Transition semanticObject) {
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
	 *     (name=ID initVal=INT)
	 */
	protected void sequence_VariableDeclaration(EObject context, Variable semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE__NAME));
			if(transientValues.isValueTransient(semanticObject, GalPackage.Literals.VARIABLE__INIT_VAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, GalPackage.Literals.VARIABLE__INIT_VAL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVariableDeclarationAccess().getNameIDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_2_0(), semanticObject.getInitVal());
		feeder.finish();
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
