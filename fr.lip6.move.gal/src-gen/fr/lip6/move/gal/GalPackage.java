/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
   * The feature id for the '<em><b>Arrays</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__ARRAYS = 2;

  /**
   * The feature id for the '<em><b>Lists</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__LISTS = 3;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__TRANSITIONS = 4;

  /**
   * The feature id for the '<em><b>Transient</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__TRANSIENT = 5;

  /**
   * The number of structural features of the '<em>System</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.TransientImpl <em>Transient</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.TransientImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getTransient()
   * @generated
   */
  int TRANSIENT = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSIENT__VALUE = 0;

  /**
   * The number of structural features of the '<em>Transient</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSIENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.VariableImpl <em>Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.VariableImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariable()
   * @generated
   */
  int VARIABLE = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__VALUE = 1;

  /**
   * The number of structural features of the '<em>Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ArrayPrefixImpl <em>Array Prefix</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ArrayPrefixImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getArrayPrefix()
   * @generated
   */
  int ARRAY_PREFIX = 3;

  /**
   * The feature id for the '<em><b>Size</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_PREFIX__SIZE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_PREFIX__NAME = 1;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_PREFIX__VALUES = 2;

  /**
   * The number of structural features of the '<em>Array Prefix</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_PREFIX_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ListImpl <em>List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ListImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getList()
   * @generated
   */
  int LIST = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST__NAME = 0;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST__VALUES = 1;

  /**
   * The number of structural features of the '<em>List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.InitValuesImpl <em>Init Values</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.InitValuesImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getInitValues()
   * @generated
   */
  int INIT_VALUES = 5;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INIT_VALUES__VALUES = 0;

  /**
   * The number of structural features of the '<em>Init Values</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INIT_VALUES_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.TransitionImpl <em>Transition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.TransitionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getTransition()
   * @generated
   */
  int TRANSITION = 6;

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
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__ACTIONS = 3;

  /**
   * The number of structural features of the '<em>Transition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ActionsImpl <em>Actions</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ActionsImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getActions()
   * @generated
   */
  int ACTIONS = 7;

  /**
   * The number of structural features of the '<em>Actions</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIONS_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.PushImpl <em>Push</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.PushImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getPush()
   * @generated
   */
  int PUSH = 8;

  /**
   * The feature id for the '<em><b>List</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PUSH__LIST = ACTIONS_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PUSH__VALUE = ACTIONS_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Push</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PUSH_FEATURE_COUNT = ACTIONS_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.PopImpl <em>Pop</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.PopImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getPop()
   * @generated
   */
  int POP = 9;

  /**
   * The feature id for the '<em><b>List</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POP__LIST = ACTIONS_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Pop</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int POP_FEATURE_COUNT = ACTIONS_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.AssignmentImpl <em>Assignment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.AssignmentImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getAssignment()
   * @generated
   */
  int ASSIGNMENT = 10;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__LEFT = ACTIONS_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__RIGHT = ACTIONS_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Assignment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_FEATURE_COUNT = ACTIONS_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.IntExpressionImpl <em>Int Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.IntExpressionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getIntExpression()
   * @generated
   */
  int INT_EXPRESSION = 14;

  /**
   * The number of structural features of the '<em>Int Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INT_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.VarAccessImpl <em>Var Access</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.VarAccessImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getVarAccess()
   * @generated
   */
  int VAR_ACCESS = 11;

  /**
   * The number of structural features of the '<em>Var Access</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VAR_ACCESS_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.VariableRefImpl <em>Variable Ref</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.VariableRefImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getVariableRef()
   * @generated
   */
  int VARIABLE_REF = 12;

  /**
   * The feature id for the '<em><b>Referenced Var</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REF__REFERENCED_VAR = VAR_ACCESS_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Variable Ref</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REF_FEATURE_COUNT = VAR_ACCESS_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ArrayVarAccessImpl <em>Array Var Access</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ArrayVarAccessImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getArrayVarAccess()
   * @generated
   */
  int ARRAY_VAR_ACCESS = 13;

  /**
   * The feature id for the '<em><b>Prefix</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_VAR_ACCESS__PREFIX = VAR_ACCESS_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_VAR_ACCESS__INDEX = VAR_ACCESS_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Array Var Access</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_VAR_ACCESS_FEATURE_COUNT = VAR_ACCESS_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.WrapBoolExprImpl <em>Wrap Bool Expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.WrapBoolExprImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getWrapBoolExpr()
   * @generated
   */
  int WRAP_BOOL_EXPR = 15;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WRAP_BOOL_EXPR__VALUE = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Wrap Bool Expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WRAP_BOOL_EXPR_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ConstantImpl <em>Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ConstantImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getConstant()
   * @generated
   */
  int CONSTANT = 16;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__VALUE = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Constant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.PeekImpl <em>Peek</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.PeekImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getPeek()
   * @generated
   */
  int PEEK = 17;

  /**
   * The feature id for the '<em><b>List</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PEEK__LIST = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Peek</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PEEK_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.BooleanExpressionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getBooleanExpression()
   * @generated
   */
  int BOOLEAN_EXPRESSION = 18;

  /**
   * The number of structural features of the '<em>Boolean Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.ComparisonImpl <em>Comparison</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.ComparisonImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getComparison()
   * @generated
   */
  int COMPARISON = 19;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARISON__LEFT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARISON__OPERATOR = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARISON__RIGHT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Comparison</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARISON_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.TrueImpl <em>True</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.TrueImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getTrue()
   * @generated
   */
  int TRUE = 20;

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
  int FALSE = 21;

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
   * The meta object id for the '{@link fr.lip6.move.gal.impl.BinaryIntExpressionImpl <em>Binary Int Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.BinaryIntExpressionImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getBinaryIntExpression()
   * @generated
   */
  int BINARY_INT_EXPRESSION = 22;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_INT_EXPRESSION__LEFT = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_INT_EXPRESSION__OP = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_INT_EXPRESSION__RIGHT = INT_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Binary Int Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BINARY_INT_EXPRESSION_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.BitComplementImpl <em>Bit Complement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.BitComplementImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getBitComplement()
   * @generated
   */
  int BIT_COMPLEMENT = 23;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIT_COMPLEMENT__VALUE = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Bit Complement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIT_COMPLEMENT_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.UnaryMinusImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getUnaryMinus()
   * @generated
   */
  int UNARY_MINUS = 24;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_MINUS__VALUE = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Unary Minus</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNARY_MINUS_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.OrImpl <em>Or</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.OrImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getOr()
   * @generated
   */
  int OR = 25;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR__LEFT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR__RIGHT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Or</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.AndImpl <em>And</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.AndImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getAnd()
   * @generated
   */
  int AND = 26;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND__LEFT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND__RIGHT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>And</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.impl.NotImpl <em>Not</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.impl.NotImpl
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getNot()
   * @generated
   */
  int NOT = 27;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT__VALUE = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Not</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NOT_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.gal.ComparisonOperators <em>Comparison Operators</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.gal.ComparisonOperators
   * @see fr.lip6.move.gal.impl.GalPackageImpl#getComparisonOperators()
   * @generated
   */
  int COMPARISON_OPERATORS = 28;


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
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.System#getArrays <em>Arrays</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arrays</em>'.
   * @see fr.lip6.move.gal.System#getArrays()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Arrays();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.System#getLists <em>Lists</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Lists</em>'.
   * @see fr.lip6.move.gal.System#getLists()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Lists();

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
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.System#getTransient <em>Transient</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Transient</em>'.
   * @see fr.lip6.move.gal.System#getTransient()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Transient();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Transient <em>Transient</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transient</em>'.
   * @see fr.lip6.move.gal.Transient
   * @generated
   */
  EClass getTransient();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Transient#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.Transient#getValue()
   * @see #getTransient()
   * @generated
   */
  EReference getTransient_Value();

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
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Variable#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.lip6.move.gal.Variable#getValue()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.ArrayPrefix <em>Array Prefix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Array Prefix</em>'.
   * @see fr.lip6.move.gal.ArrayPrefix
   * @generated
   */
  EClass getArrayPrefix();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.ArrayPrefix#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Size</em>'.
   * @see fr.lip6.move.gal.ArrayPrefix#getSize()
   * @see #getArrayPrefix()
   * @generated
   */
  EAttribute getArrayPrefix_Size();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.ArrayPrefix#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.gal.ArrayPrefix#getName()
   * @see #getArrayPrefix()
   * @generated
   */
  EAttribute getArrayPrefix_Name();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.ArrayPrefix#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Values</em>'.
   * @see fr.lip6.move.gal.ArrayPrefix#getValues()
   * @see #getArrayPrefix()
   * @generated
   */
  EReference getArrayPrefix_Values();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.List <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>List</em>'.
   * @see fr.lip6.move.gal.List
   * @generated
   */
  EClass getList();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.List#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.gal.List#getName()
   * @see #getList()
   * @generated
   */
  EAttribute getList_Name();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.List#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Values</em>'.
   * @see fr.lip6.move.gal.List#getValues()
   * @see #getList()
   * @generated
   */
  EReference getList_Values();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.InitValues <em>Init Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Init Values</em>'.
   * @see fr.lip6.move.gal.InitValues
   * @generated
   */
  EClass getInitValues();

  /**
   * Returns the meta object for the attribute list '{@link fr.lip6.move.gal.InitValues#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see fr.lip6.move.gal.InitValues#getValues()
   * @see #getInitValues()
   * @generated
   */
  EAttribute getInitValues_Values();

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
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.gal.Transition#getActions <em>Actions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Actions</em>'.
   * @see fr.lip6.move.gal.Transition#getActions()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Actions();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Actions <em>Actions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Actions</em>'.
   * @see fr.lip6.move.gal.Actions
   * @generated
   */
  EClass getActions();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Push <em>Push</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Push</em>'.
   * @see fr.lip6.move.gal.Push
   * @generated
   */
  EClass getPush();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.Push#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>List</em>'.
   * @see fr.lip6.move.gal.Push#getList()
   * @see #getPush()
   * @generated
   */
  EReference getPush_List();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Push#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.Push#getValue()
   * @see #getPush()
   * @generated
   */
  EReference getPush_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Pop <em>Pop</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pop</em>'.
   * @see fr.lip6.move.gal.Pop
   * @generated
   */
  EClass getPop();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.Pop#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>List</em>'.
   * @see fr.lip6.move.gal.Pop#getList()
   * @see #getPop()
   * @generated
   */
  EReference getPop_List();

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
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Assignment#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Assignment#getLeft()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Assignment#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Assignment#getRight()
   * @see #getAssignment()
   * @generated
   */
  EReference getAssignment_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.VarAccess <em>Var Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Var Access</em>'.
   * @see fr.lip6.move.gal.VarAccess
   * @generated
   */
  EClass getVarAccess();

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
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.VariableRef#getReferencedVar <em>Referenced Var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Referenced Var</em>'.
   * @see fr.lip6.move.gal.VariableRef#getReferencedVar()
   * @see #getVariableRef()
   * @generated
   */
  EReference getVariableRef_ReferencedVar();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.ArrayVarAccess <em>Array Var Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Array Var Access</em>'.
   * @see fr.lip6.move.gal.ArrayVarAccess
   * @generated
   */
  EClass getArrayVarAccess();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.ArrayVarAccess#getPrefix <em>Prefix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Prefix</em>'.
   * @see fr.lip6.move.gal.ArrayVarAccess#getPrefix()
   * @see #getArrayVarAccess()
   * @generated
   */
  EReference getArrayVarAccess_Prefix();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.ArrayVarAccess#getIndex <em>Index</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Index</em>'.
   * @see fr.lip6.move.gal.ArrayVarAccess#getIndex()
   * @see #getArrayVarAccess()
   * @generated
   */
  EReference getArrayVarAccess_Index();

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
   * Returns the meta object for class '{@link fr.lip6.move.gal.WrapBoolExpr <em>Wrap Bool Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Wrap Bool Expr</em>'.
   * @see fr.lip6.move.gal.WrapBoolExpr
   * @generated
   */
  EClass getWrapBoolExpr();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.WrapBoolExpr#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.WrapBoolExpr#getValue()
   * @see #getWrapBoolExpr()
   * @generated
   */
  EReference getWrapBoolExpr_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Constant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constant</em>'.
   * @see fr.lip6.move.gal.Constant
   * @generated
   */
  EClass getConstant();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Constant#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.lip6.move.gal.Constant#getValue()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.Peek <em>Peek</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Peek</em>'.
   * @see fr.lip6.move.gal.Peek
   * @generated
   */
  EClass getPeek();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.gal.Peek#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>List</em>'.
   * @see fr.lip6.move.gal.Peek#getList()
   * @see #getPeek()
   * @generated
   */
  EReference getPeek_List();

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
   * Returns the meta object for class '{@link fr.lip6.move.gal.Comparison <em>Comparison</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Comparison</em>'.
   * @see fr.lip6.move.gal.Comparison
   * @generated
   */
  EClass getComparison();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Comparison#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Comparison#getLeft()
   * @see #getComparison()
   * @generated
   */
  EReference getComparison_Left();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.Comparison#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see fr.lip6.move.gal.Comparison#getOperator()
   * @see #getComparison()
   * @generated
   */
  EAttribute getComparison_Operator();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Comparison#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Comparison#getRight()
   * @see #getComparison()
   * @generated
   */
  EReference getComparison_Right();

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
   * Returns the meta object for class '{@link fr.lip6.move.gal.BinaryIntExpression <em>Binary Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Binary Int Expression</em>'.
   * @see fr.lip6.move.gal.BinaryIntExpression
   * @generated
   */
  EClass getBinaryIntExpression();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.BinaryIntExpression#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.BinaryIntExpression#getLeft()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EReference getBinaryIntExpression_Left();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.gal.BinaryIntExpression#getOp <em>Op</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Op</em>'.
   * @see fr.lip6.move.gal.BinaryIntExpression#getOp()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EAttribute getBinaryIntExpression_Op();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.BinaryIntExpression#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.BinaryIntExpression#getRight()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EReference getBinaryIntExpression_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.BitComplement <em>Bit Complement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Bit Complement</em>'.
   * @see fr.lip6.move.gal.BitComplement
   * @generated
   */
  EClass getBitComplement();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.BitComplement#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.BitComplement#getValue()
   * @see #getBitComplement()
   * @generated
   */
  EReference getBitComplement_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.gal.UnaryMinus <em>Unary Minus</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unary Minus</em>'.
   * @see fr.lip6.move.gal.UnaryMinus
   * @generated
   */
  EClass getUnaryMinus();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.UnaryMinus#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.UnaryMinus#getValue()
   * @see #getUnaryMinus()
   * @generated
   */
  EReference getUnaryMinus_Value();

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
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Or#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.Or#getLeft()
   * @see #getOr()
   * @generated
   */
  EReference getOr_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Or#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.Or#getRight()
   * @see #getOr()
   * @generated
   */
  EReference getOr_Right();

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
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.And#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.gal.And#getLeft()
   * @see #getAnd()
   * @generated
   */
  EReference getAnd_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.And#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.gal.And#getRight()
   * @see #getAnd()
   * @generated
   */
  EReference getAnd_Right();

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
   * Returns the meta object for the containment reference '{@link fr.lip6.move.gal.Not#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.gal.Not#getValue()
   * @see #getNot()
   * @generated
   */
  EReference getNot_Value();

  /**
   * Returns the meta object for enum '{@link fr.lip6.move.gal.ComparisonOperators <em>Comparison Operators</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Comparison Operators</em>'.
   * @see fr.lip6.move.gal.ComparisonOperators
   * @generated
   */
  EEnum getComparisonOperators();

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
     * The meta object literal for the '<em><b>Arrays</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__ARRAYS = eINSTANCE.getSystem_Arrays();

    /**
     * The meta object literal for the '<em><b>Lists</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__LISTS = eINSTANCE.getSystem_Lists();

    /**
     * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__TRANSITIONS = eINSTANCE.getSystem_Transitions();

    /**
     * The meta object literal for the '<em><b>Transient</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__TRANSIENT = eINSTANCE.getSystem_Transient();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.TransientImpl <em>Transient</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.TransientImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getTransient()
     * @generated
     */
    EClass TRANSIENT = eINSTANCE.getTransient();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSIENT__VALUE = eINSTANCE.getTransient_Value();

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
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__VALUE = eINSTANCE.getVariable_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ArrayPrefixImpl <em>Array Prefix</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ArrayPrefixImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getArrayPrefix()
     * @generated
     */
    EClass ARRAY_PREFIX = eINSTANCE.getArrayPrefix();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARRAY_PREFIX__SIZE = eINSTANCE.getArrayPrefix_Size();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARRAY_PREFIX__NAME = eINSTANCE.getArrayPrefix_Name();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARRAY_PREFIX__VALUES = eINSTANCE.getArrayPrefix_Values();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ListImpl <em>List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ListImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getList()
     * @generated
     */
    EClass LIST = eINSTANCE.getList();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LIST__NAME = eINSTANCE.getList_Name();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LIST__VALUES = eINSTANCE.getList_Values();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.InitValuesImpl <em>Init Values</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.InitValuesImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getInitValues()
     * @generated
     */
    EClass INIT_VALUES = eINSTANCE.getInitValues();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INIT_VALUES__VALUES = eINSTANCE.getInitValues_Values();

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
     * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__ACTIONS = eINSTANCE.getTransition_Actions();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ActionsImpl <em>Actions</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ActionsImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getActions()
     * @generated
     */
    EClass ACTIONS = eINSTANCE.getActions();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.PushImpl <em>Push</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.PushImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getPush()
     * @generated
     */
    EClass PUSH = eINSTANCE.getPush();

    /**
     * The meta object literal for the '<em><b>List</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PUSH__LIST = eINSTANCE.getPush_List();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PUSH__VALUE = eINSTANCE.getPush_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.PopImpl <em>Pop</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.PopImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getPop()
     * @generated
     */
    EClass POP = eINSTANCE.getPop();

    /**
     * The meta object literal for the '<em><b>List</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference POP__LIST = eINSTANCE.getPop_List();

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
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__LEFT = eINSTANCE.getAssignment_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__RIGHT = eINSTANCE.getAssignment_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.VarAccessImpl <em>Var Access</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.VarAccessImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getVarAccess()
     * @generated
     */
    EClass VAR_ACCESS = eINSTANCE.getVarAccess();

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
     * The meta object literal for the '<em><b>Referenced Var</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_REF__REFERENCED_VAR = eINSTANCE.getVariableRef_ReferencedVar();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ArrayVarAccessImpl <em>Array Var Access</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ArrayVarAccessImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getArrayVarAccess()
     * @generated
     */
    EClass ARRAY_VAR_ACCESS = eINSTANCE.getArrayVarAccess();

    /**
     * The meta object literal for the '<em><b>Prefix</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARRAY_VAR_ACCESS__PREFIX = eINSTANCE.getArrayVarAccess_Prefix();

    /**
     * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARRAY_VAR_ACCESS__INDEX = eINSTANCE.getArrayVarAccess_Index();

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
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.WrapBoolExprImpl <em>Wrap Bool Expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.WrapBoolExprImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getWrapBoolExpr()
     * @generated
     */
    EClass WRAP_BOOL_EXPR = eINSTANCE.getWrapBoolExpr();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WRAP_BOOL_EXPR__VALUE = eINSTANCE.getWrapBoolExpr_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ConstantImpl <em>Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ConstantImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getConstant()
     * @generated
     */
    EClass CONSTANT = eINSTANCE.getConstant();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__VALUE = eINSTANCE.getConstant_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.PeekImpl <em>Peek</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.PeekImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getPeek()
     * @generated
     */
    EClass PEEK = eINSTANCE.getPeek();

    /**
     * The meta object literal for the '<em><b>List</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PEEK__LIST = eINSTANCE.getPeek_List();

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
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.ComparisonImpl <em>Comparison</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.ComparisonImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getComparison()
     * @generated
     */
    EClass COMPARISON = eINSTANCE.getComparison();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPARISON__LEFT = eINSTANCE.getComparison_Left();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPARISON__OPERATOR = eINSTANCE.getComparison_Operator();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPARISON__RIGHT = eINSTANCE.getComparison_Right();

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
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.BinaryIntExpressionImpl <em>Binary Int Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.BinaryIntExpressionImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getBinaryIntExpression()
     * @generated
     */
    EClass BINARY_INT_EXPRESSION = eINSTANCE.getBinaryIntExpression();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BINARY_INT_EXPRESSION__LEFT = eINSTANCE.getBinaryIntExpression_Left();

    /**
     * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BINARY_INT_EXPRESSION__OP = eINSTANCE.getBinaryIntExpression_Op();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BINARY_INT_EXPRESSION__RIGHT = eINSTANCE.getBinaryIntExpression_Right();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.BitComplementImpl <em>Bit Complement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.BitComplementImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getBitComplement()
     * @generated
     */
    EClass BIT_COMPLEMENT = eINSTANCE.getBitComplement();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BIT_COMPLEMENT__VALUE = eINSTANCE.getBitComplement_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.impl.UnaryMinusImpl
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getUnaryMinus()
     * @generated
     */
    EClass UNARY_MINUS = eINSTANCE.getUnaryMinus();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNARY_MINUS__VALUE = eINSTANCE.getUnaryMinus_Value();

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
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OR__LEFT = eINSTANCE.getOr_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OR__RIGHT = eINSTANCE.getOr_Right();

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
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AND__LEFT = eINSTANCE.getAnd_Left();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AND__RIGHT = eINSTANCE.getAnd_Right();

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
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NOT__VALUE = eINSTANCE.getNot_Value();

    /**
     * The meta object literal for the '{@link fr.lip6.move.gal.ComparisonOperators <em>Comparison Operators</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.gal.ComparisonOperators
     * @see fr.lip6.move.gal.impl.GalPackageImpl#getComparisonOperators()
     * @generated
     */
    EEnum COMPARISON_OPERATORS = eINSTANCE.getComparisonOperators();

  }

} //GalPackage
