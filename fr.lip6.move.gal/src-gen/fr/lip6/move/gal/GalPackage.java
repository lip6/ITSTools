/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.lip6.move.gal.GalFactory
 * @model kind="package"
 * @generated
 */
public interface GalPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "gal";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.lip6.fr/move/Gal";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "gal";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GalPackage eINSTANCE = fr.lip6.move.gal.impl.GalPackageImpl.init();

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.SystemImpl <em>System</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.SystemImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getSystem()
   * @generated
   */
  int SYSTEM = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__NAME = 0;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__VARIABLES = 1;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__TRANSITIONS = 2;

  /**
   * The number of structural features of the '<em>System</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.VariableImpl <em>Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.VariableImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariable()
   * @generated
   */
  int VARIABLE = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__NAME = 0;

  /**
   * The feature id for the '<em><b>Init Val</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__INIT_VAL = 1;

  /**
   * The feature id for the '<em><b>Aname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__ANAME = 2;

  /**
   * The feature id for the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__INDEX = 3;

  /**
   * The number of structural features of the '<em>Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.TransitionImpl <em>Transition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.TransitionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getTransition()
   * @generated
   */
  int TRANSITION = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__NAME = 0;

  /**
   * The feature id for the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__GUARD = 1;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__LABEL = 2;

  /**
   * The feature id for the '<em><b>Assignments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__ASSIGNMENTS = 3;

  /**
   * The number of structural features of the '<em>Transition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.AssignmentImpl <em>Assignment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.AssignmentImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getAssignment()
   * @generated
   */
  int ASSIGNMENT = 3;

  /**
   * The feature id for the '<em><b>Var</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__VAR = 0;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__EXPR = 1;

  /**
   * The feature id for the '<em><b>AVar</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__AVAR = 2;

  /**
   * The feature id for the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__INDEX = 3;

  /**
   * The number of structural features of the '<em>Assignment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.IntExpressionImpl <em>Int Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.IntExpressionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getIntExpression()
   * @generated
   */
  int INT_EXPRESSION = 4;

  /**
   * The number of structural features of the '<em>Int Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INT_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ConstanteImpl <em>Constante</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ConstanteImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getConstante()
   * @generated
   */
  int CONSTANTE = 5;

  /**
   * The feature id for the '<em><b>Val</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANTE__VAL = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Constante</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANTE_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.VariableRefImpl <em>Variable Ref</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.VariableRefImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariableRef()
   * @generated
   */
  int VARIABLE_REF = 6;

  /**
   * The feature id for the '<em><b>Var</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REF__VAR = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Variable Ref</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REF_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.BooleanExpressionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getBooleanExpression()
   * @generated
   */
  int BOOLEAN_EXPRESSION = 7;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION__LEFT = 0;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION__OPERATOR = 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION__RIGHT = 2;

  /**
   * The number of structural features of the '<em>Boolean Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.TrueImpl <em>True</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.TrueImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getTrue()
   * @generated
   */
  int TRUE = 8;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE__LEFT = BOOLEAN_EXPRESSION__LEFT;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE__OPERATOR = BOOLEAN_EXPRESSION__OPERATOR;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE__RIGHT = BOOLEAN_EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE__VALUE = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>True</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.FalseImpl <em>False</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.FalseImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getFalse()
   * @generated
   */
  int FALSE = 9;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE__LEFT = BOOLEAN_EXPRESSION__LEFT;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE__OPERATOR = BOOLEAN_EXPRESSION__OPERATOR;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE__RIGHT = BOOLEAN_EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE__VALUE = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>False</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.AdditionImpl <em>Addition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.AdditionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getAddition()
   * @generated
   */
  int ADDITION = 10;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Addition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.SubtractionImpl <em>Subtraction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.SubtractionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getSubtraction()
   * @generated
   */
  int SUBTRACTION = 11;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTRACTION__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTRACTION__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Subtraction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUBTRACTION_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.MultiplicationImpl <em>Multiplication</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.MultiplicationImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getMultiplication()
   * @generated
   */
  int MULTIPLICATION = 12;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Multiplication</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.DivisionImpl <em>Division</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.DivisionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getDivision()
   * @generated
   */
  int DIVISION = 13;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIVISION__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIVISION__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Division</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIVISION_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ModuloImpl <em>Modulo</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ModuloImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getModulo()
   * @generated
   */
  int MODULO = 14;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULO__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULO__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Modulo</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODULO_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.UnitaryMinusImpl <em>Unitary Minus</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.UnitaryMinusImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getUnitaryMinus()
   * @generated
   */
  int UNITARY_MINUS = 15;

  /**
   * The feature id for the '<em><b>Val</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNITARY_MINUS__VAL = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Unitary Minus</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNITARY_MINUS_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.PowerImpl <em>Power</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.PowerImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getPower()
   * @generated
   */
  int POWER = 16;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POWER__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POWER__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Power</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POWER_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.OrImpl <em>Or</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.OrImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getOr()
   * @generated
   */
  int OR = 17;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR__LEFT = BOOLEAN_EXPRESSION__LEFT;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR__OPERATOR = BOOLEAN_EXPRESSION__OPERATOR;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR__RIGHT = BOOLEAN_EXPRESSION__RIGHT;

  /**
   * The number of structural features of the '<em>Or</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.AndImpl <em>And</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.AndImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getAnd()
   * @generated
   */
  int AND = 18;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND__LEFT = BOOLEAN_EXPRESSION__LEFT;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND__OPERATOR = BOOLEAN_EXPRESSION__OPERATOR;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND__RIGHT = BOOLEAN_EXPRESSION__RIGHT;

  /**
   * The number of structural features of the '<em>And</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.NotImpl <em>Not</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.NotImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getNot()
   * @generated
   */
  int NOT = 19;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT__LEFT = BOOLEAN_EXPRESSION__LEFT;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT__OPERATOR = BOOLEAN_EXPRESSION__OPERATOR;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT__RIGHT = BOOLEAN_EXPRESSION__RIGHT;

  /**
   * The feature id for the '<em><b>Val</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT__VAL = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Not</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;


  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.System <em>System</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>System</em>'.
   * @see fr.lip6.move.gal.System
   * @generated
   */
  EClass getSystem();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.System#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.gal.System#getName()
   * @see #getSystem()
   * @generated
   */
  EAttribute getSystem_Name();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.System#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see fr.lip6.move.gal.System#getVariables()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Variables();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.System#getTransitions <em>Transitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transitions</em>'.
   * @see fr.lip6.move.gal.System#getTransitions()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Transitions();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Variable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable</em>'.
   * @see fr.lip6.move.gal.Variable
   * @generated
   */
  EClass getVariable();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Variable#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.gal.Variable#getName()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_Name();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Variable#getInitVal <em>Init Val</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Init Val</em>'.
   * @see fr.lip6.move.gal.Variable#getInitVal()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_InitVal();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Variable#getAname <em>Aname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Aname</em>'.
   * @see fr.lip6.move.gal.Variable#getAname()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_Aname();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Variable#getIndex <em>Index</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Index</em>'.
   * @see fr.lip6.move.gal.Variable#getIndex()
   * @see #getVariable()
   * @generated
   */
  EReference getVariable_Index();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Transition <em>Transition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transition</em>'.
   * @see fr.lip6.move.gal.Transition
   * @generated
   */
  EClass getTransition();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Transition#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.gal.Transition#getName()
   * @see #getTransition()
   * @generated
   */
  EAttribute getTransition_Name();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Transition#getGuard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Guard</em>'.
   * @see fr.lip6.move.gal.Transition#getGuard()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Guard();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Transition#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see fr.lip6.move.gal.Transition#getLabel()
   * @see #getTransition()
   * @generated
   */
  EAttribute getTransition_Label();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.Transition#getAssignments <em>Assignments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Assignments</em>'.
   * @see fr.lip6.move.gal.Transition#getAssignments()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Assignments();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Assignment <em>Assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignment</em>'.
   * @see fr.lip6.move.gal.Assignment
   * @generated
   */
  EClass getAssignment();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Assignment#getVar <em>Var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Var</em>'.
   * @see fr.lip6.move.gal.Assignment#getVar()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Var();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Assignment#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see fr.lip6.move.gal.Assignment#getExpr()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Expr();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.Assignment#getAVar <em>AVar</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>AVar</em>'.
   * @see fr.lip6.move.gal.Assignment#getAVar()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_AVar();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Assignment#getIndex <em>Index</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Index</em>'.
   * @see fr.lip6.move.gal.Assignment#getIndex()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Index();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.IntExpression <em>Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Int Expression</em>'.
   * @see fr.lip6.move.gal.IntExpression
   * @generated
   */
  EClass getIntExpression();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Constante <em>Constante</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constante</em>'.
   * @see fr.lip6.move.gal.Constante
   * @generated
   */
  EClass getConstante();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Constante#getVal <em>Val</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Val</em>'.
   * @see fr.lip6.move.gal.Constante#getVal()
   * @see #getConstante()
   * @generated
   */
  EAttribute getConstante_Val();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.VariableRef <em>Variable Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable Ref</em>'.
   * @see fr.lip6.move.gal.VariableRef
   * @generated
   */
  EClass getVariableRef();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.VariableRef#getVar <em>Var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Var</em>'.
   * @see fr.lip6.move.gal.VariableRef#getVar()
   * @see #getVariableRef()
   * @generated
   */
  EReference getVariableRef_Var();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.BooleanExpression <em>Boolean Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Expression</em>'.
   * @see fr.lip6.move.gal.BooleanExpression
   * @generated
   */
  EClass getBooleanExpression();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.BooleanExpression#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.BooleanExpression#getLeft()
   * @see #getBooleanExpression()
   * @generated
   */
  EReference getBooleanExpression_Left();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.BooleanExpression#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see fr.lip6.move.gal.BooleanExpression#getOperator()
   * @see #getBooleanExpression()
   * @generated
   */
  EAttribute getBooleanExpression_Operator();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.BooleanExpression#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.BooleanExpression#getRight()
   * @see #getBooleanExpression()
   * @generated
   */
  EReference getBooleanExpression_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.True <em>True</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>True</em>'.
   * @see fr.lip6.move.gal.True
   * @generated
   */
  EClass getTrue();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.True#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.lip6.move.gal.True#getValue()
   * @see #getTrue()
   * @generated
   */
  EAttribute getTrue_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.False <em>False</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>False</em>'.
   * @see fr.lip6.move.gal.False
   * @generated
   */
  EClass getFalse();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.False#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.lip6.move.gal.False#getValue()
   * @see #getFalse()
   * @generated
   */
  EAttribute getFalse_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Addition <em>Addition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Addition</em>'.
   * @see fr.lip6.move.gal.Addition
   * @generated
   */
  EClass getAddition();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Addition#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Addition#getLeft()
   * @see #getAddition()
   * @generated
   */
  EReference getAddition_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Addition#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Addition#getRight()
   * @see #getAddition()
   * @generated
   */
  EReference getAddition_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Subtraction <em>Subtraction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Subtraction</em>'.
   * @see fr.lip6.move.gal.Subtraction
   * @generated
   */
  EClass getSubtraction();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Subtraction#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Subtraction#getLeft()
   * @see #getSubtraction()
   * @generated
   */
  EReference getSubtraction_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Subtraction#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Subtraction#getRight()
   * @see #getSubtraction()
   * @generated
   */
  EReference getSubtraction_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Multiplication <em>Multiplication</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Multiplication</em>'.
   * @see fr.lip6.move.gal.Multiplication
   * @generated
   */
  EClass getMultiplication();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Multiplication#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Multiplication#getLeft()
   * @see #getMultiplication()
   * @generated
   */
  EReference getMultiplication_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Multiplication#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Multiplication#getRight()
   * @see #getMultiplication()
   * @generated
   */
  EReference getMultiplication_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Division <em>Division</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Division</em>'.
   * @see fr.lip6.move.gal.Division
   * @generated
   */
  EClass getDivision();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Division#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Division#getLeft()
   * @see #getDivision()
   * @generated
   */
  EReference getDivision_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Division#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Division#getRight()
   * @see #getDivision()
   * @generated
   */
  EReference getDivision_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Modulo <em>Modulo</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Modulo</em>'.
   * @see fr.lip6.move.gal.Modulo
   * @generated
   */
  EClass getModulo();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Modulo#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Modulo#getLeft()
   * @see #getModulo()
   * @generated
   */
  EReference getModulo_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Modulo#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Modulo#getRight()
   * @see #getModulo()
   * @generated
   */
  EReference getModulo_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.UnitaryMinus <em>Unitary Minus</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unitary Minus</em>'.
   * @see fr.lip6.move.gal.UnitaryMinus
   * @generated
   */
  EClass getUnitaryMinus();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.UnitaryMinus#getVal <em>Val</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Val</em>'.
   * @see fr.lip6.move.gal.UnitaryMinus#getVal()
   * @see #getUnitaryMinus()
   * @generated
   */
  EReference getUnitaryMinus_Val();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Power <em>Power</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Power</em>'.
   * @see fr.lip6.move.gal.Power
   * @generated
   */
  EClass getPower();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Power#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Power#getLeft()
   * @see #getPower()
   * @generated
   */
  EReference getPower_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Power#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Power#getRight()
   * @see #getPower()
   * @generated
   */
  EReference getPower_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Or <em>Or</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Or</em>'.
   * @see fr.lip6.move.gal.Or
   * @generated
   */
  EClass getOr();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.And <em>And</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>And</em>'.
   * @see fr.lip6.move.gal.And
   * @generated
   */
  EClass getAnd();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Not <em>Not</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Not</em>'.
   * @see fr.lip6.move.gal.Not
   * @generated
   */
  EClass getNot();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Not#getVal <em>Val</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Val</em>'.
   * @see fr.lip6.move.gal.Not#getVal()
   * @see #getNot()
   * @generated
   */
  EReference getNot_Val();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  GalFactory getGalFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.SystemImpl <em>System</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.SystemImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getSystem()
     * @generated
     */
    EClass SYSTEM = eINSTANCE.getSystem();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SYSTEM__NAME = eINSTANCE.getSystem_Name();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__VARIABLES = eINSTANCE.getSystem_Variables();

    /**
     * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__TRANSITIONS = eINSTANCE.getSystem_Transitions();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.VariableImpl <em>Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.VariableImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariable()
     * @generated
     */
    EClass VARIABLE = eINSTANCE.getVariable();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

    /**
     * The meta object literal for the '<em><b>Init Val</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__INIT_VAL = eINSTANCE.getVariable_InitVal();

    /**
     * The meta object literal for the '<em><b>Aname</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__ANAME = eINSTANCE.getVariable_Aname();

    /**
     * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE__INDEX = eINSTANCE.getVariable_Index();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.TransitionImpl <em>Transition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.TransitionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getTransition()
     * @generated
     */
    EClass TRANSITION = eINSTANCE.getTransition();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRANSITION__NAME = eINSTANCE.getTransition_Name();

    /**
     * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__GUARD = eINSTANCE.getTransition_Guard();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRANSITION__LABEL = eINSTANCE.getTransition_Label();

    /**
     * The meta object literal for the '<em><b>Assignments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__ASSIGNMENTS = eINSTANCE.getTransition_Assignments();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.AssignmentImpl <em>Assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.AssignmentImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getAssignment()
     * @generated
     */
    EClass ASSIGNMENT = eINSTANCE.getAssignment();

    /**
     * The meta object literal for the '<em><b>Var</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__VAR = eINSTANCE.getAssignment_Var();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__EXPR = eINSTANCE.getAssignment_Expr();

    /**
     * The meta object literal for the '<em><b>AVar</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__AVAR = eINSTANCE.getAssignment_AVar();

    /**
     * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__INDEX = eINSTANCE.getAssignment_Index();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.IntExpressionImpl <em>Int Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.IntExpressionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getIntExpression()
     * @generated
     */
    EClass INT_EXPRESSION = eINSTANCE.getIntExpression();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ConstanteImpl <em>Constante</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ConstanteImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getConstante()
     * @generated
     */
    EClass CONSTANTE = eINSTANCE.getConstante();

    /**
     * The meta object literal for the '<em><b>Val</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANTE__VAL = eINSTANCE.getConstante_Val();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.VariableRefImpl <em>Variable Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.VariableRefImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariableRef()
     * @generated
     */
    EClass VARIABLE_REF = eINSTANCE.getVariableRef();

    /**
     * The meta object literal for the '<em><b>Var</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_REF__VAR = eINSTANCE.getVariableRef_Var();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.BooleanExpressionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getBooleanExpression()
     * @generated
     */
    EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BOOLEAN_EXPRESSION__LEFT = eINSTANCE.getBooleanExpression_Left();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BOOLEAN_EXPRESSION__OPERATOR = eINSTANCE.getBooleanExpression_Operator();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BOOLEAN_EXPRESSION__RIGHT = eINSTANCE.getBooleanExpression_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.TrueImpl <em>True</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.TrueImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getTrue()
     * @generated
     */
    EClass TRUE = eINSTANCE.getTrue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRUE__VALUE = eINSTANCE.getTrue_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.FalseImpl <em>False</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.FalseImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getFalse()
     * @generated
     */
    EClass FALSE = eINSTANCE.getFalse();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FALSE__VALUE = eINSTANCE.getFalse_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.AdditionImpl <em>Addition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.AdditionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getAddition()
     * @generated
     */
    EClass ADDITION = eINSTANCE.getAddition();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ADDITION__LEFT = eINSTANCE.getAddition_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ADDITION__RIGHT = eINSTANCE.getAddition_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.SubtractionImpl <em>Subtraction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.SubtractionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getSubtraction()
     * @generated
     */
    EClass SUBTRACTION = eINSTANCE.getSubtraction();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTRACTION__LEFT = eINSTANCE.getSubtraction_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUBTRACTION__RIGHT = eINSTANCE.getSubtraction_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.MultiplicationImpl <em>Multiplication</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.MultiplicationImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getMultiplication()
     * @generated
     */
    EClass MULTIPLICATION = eINSTANCE.getMultiplication();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MULTIPLICATION__LEFT = eINSTANCE.getMultiplication_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MULTIPLICATION__RIGHT = eINSTANCE.getMultiplication_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.DivisionImpl <em>Division</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.DivisionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getDivision()
     * @generated
     */
    EClass DIVISION = eINSTANCE.getDivision();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DIVISION__LEFT = eINSTANCE.getDivision_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DIVISION__RIGHT = eINSTANCE.getDivision_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ModuloImpl <em>Modulo</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ModuloImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getModulo()
     * @generated
     */
    EClass MODULO = eINSTANCE.getModulo();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODULO__LEFT = eINSTANCE.getModulo_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODULO__RIGHT = eINSTANCE.getModulo_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.UnitaryMinusImpl <em>Unitary Minus</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.UnitaryMinusImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getUnitaryMinus()
     * @generated
     */
    EClass UNITARY_MINUS = eINSTANCE.getUnitaryMinus();

    /**
     * The meta object literal for the '<em><b>Val</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNITARY_MINUS__VAL = eINSTANCE.getUnitaryMinus_Val();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.PowerImpl <em>Power</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.PowerImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getPower()
     * @generated
     */
    EClass POWER = eINSTANCE.getPower();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference POWER__LEFT = eINSTANCE.getPower_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference POWER__RIGHT = eINSTANCE.getPower_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.OrImpl <em>Or</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.OrImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getOr()
     * @generated
     */
    EClass OR = eINSTANCE.getOr();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.AndImpl <em>And</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.AndImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getAnd()
     * @generated
     */
    EClass AND = eINSTANCE.getAnd();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.NotImpl <em>Not</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.NotImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getNot()
     * @generated
     */
    EClass NOT = eINSTANCE.getNot();

    /**
     * The meta object literal for the '<em><b>Val</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NOT__VAL = eINSTANCE.getNot_Val();

  }

} //GalPackage
