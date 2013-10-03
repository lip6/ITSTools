/**
 */
package fr.lip6.move.timedAutomata;

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
 * @see fr.lip6.move.timedAutomata.TimedAutomataFactory
 * @model kind="package"
 * @generated
 */
public interface TimedAutomataPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "timedAutomata";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.lip6.fr/move/TimedAutomata";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "timedAutomata";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TimedAutomataPackage eINSTANCE = fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl.init();

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.XTAImpl <em>XTA</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.XTAImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getXTA()
   * @generated
   */
  int XTA = 0;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__VARIABLES = 0;

  /**
   * The feature id for the '<em><b>Channels</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__CHANNELS = 1;

  /**
   * The feature id for the '<em><b>Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__TYPES = 2;

  /**
   * The feature id for the '<em><b>Templates</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__TEMPLATES = 3;

  /**
   * The feature id for the '<em><b>Instances</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__INSTANCES = 4;

  /**
   * The feature id for the '<em><b>System</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA__SYSTEM = 5;

  /**
   * The number of structural features of the '<em>XTA</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int XTA_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.InstantiableInSystemImpl <em>Instantiable In System</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.InstantiableInSystemImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInstantiableInSystem()
   * @generated
   */
  int INSTANTIABLE_IN_SYSTEM = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANTIABLE_IN_SYSTEM__NAME = 0;

  /**
   * The number of structural features of the '<em>Instantiable In System</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.InstanceImpl <em>Instance</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.InstanceImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInstance()
   * @generated
   */
  int INSTANCE = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE__NAME = INSTANTIABLE_IN_SYSTEM__NAME;

  /**
   * The feature id for the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE__TYPE = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Args</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE__ARGS = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Instance</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_FEATURE_COUNT = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.SystemImpl <em>System</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.SystemImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSystem()
   * @generated
   */
  int SYSTEM = 3;

  /**
   * The feature id for the '<em><b>Instances</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM__INSTANCES = 0;

  /**
   * The number of structural features of the '<em>System</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYSTEM_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ProcDeclImpl <em>Proc Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ProcDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getProcDecl()
   * @generated
   */
  int PROC_DECL = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_DECL__NAME = INSTANTIABLE_IN_SYSTEM__NAME;

  /**
   * The feature id for the '<em><b>Params</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_DECL__PARAMS = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_DECL__BODY = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Proc Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_DECL_FEATURE_COUNT = INSTANTIABLE_IN_SYSTEM_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.FormalDeclarationImpl <em>Formal Declaration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.FormalDeclarationImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getFormalDeclaration()
   * @generated
   */
  int FORMAL_DECLARATION = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_DECLARATION__NAME = 0;

  /**
   * The number of structural features of the '<em>Formal Declaration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FORMAL_DECLARATION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ParameterImpl <em>Parameter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ParameterImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getParameter()
   * @generated
   */
  int PARAMETER = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__NAME = FORMAL_DECLARATION__NAME;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER__TYPE = FORMAL_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Parameter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_FEATURE_COUNT = FORMAL_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.TypeDeclImpl <em>Type Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.TypeDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTypeDecl()
   * @generated
   */
  int TYPE_DECL = 7;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_DECL__TYPE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_DECL__NAME = 1;

  /**
   * The number of structural features of the '<em>Type Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.TypeImpl <em>Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.TypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getType()
   * @generated
   */
  int TYPE = 8;

  /**
   * The number of structural features of the '<em>Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.BasicTypeImpl <em>Basic Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.BasicTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBasicType()
   * @generated
   */
  int BASIC_TYPE = 9;

  /**
   * The number of structural features of the '<em>Basic Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASIC_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.TypedefRefImpl <em>Typedef Ref</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.TypedefRefImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTypedefRef()
   * @generated
   */
  int TYPEDEF_REF = 10;

  /**
   * The feature id for the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPEDEF_REF__REF = TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Typedef Ref</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPEDEF_REF_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ChannelDeclImpl <em>Channel Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ChannelDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChannelDecl()
   * @generated
   */
  int CHANNEL_DECL = 11;

  /**
   * The number of structural features of the '<em>Channel Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHANNEL_DECL_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ChanIdImpl <em>Chan Id</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ChanIdImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChanId()
   * @generated
   */
  int CHAN_ID = 12;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAN_ID__NAME = 0;

  /**
   * The number of structural features of the '<em>Chan Id</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHAN_ID_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ChannelTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChannelType()
   * @generated
   */
  int CHANNEL_TYPE = 13;

  /**
   * The feature id for the '<em><b>Chans</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHANNEL_TYPE__CHANS = CHANNEL_DECL_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Urgent</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHANNEL_TYPE__URGENT = CHANNEL_DECL_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Broadcast</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHANNEL_TYPE__BROADCAST = CHANNEL_DECL_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Channel Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CHANNEL_TYPE_FEATURE_COUNT = CHANNEL_DECL_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.IntegerTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getIntegerType()
   * @generated
   */
  int INTEGER_TYPE = 14;

  /**
   * The feature id for the '<em><b>Const</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TYPE__CONST = BASIC_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Integer Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TYPE_FEATURE_COUNT = BASIC_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.BoolTypeImpl <em>Bool Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.BoolTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBoolType()
   * @generated
   */
  int BOOL_TYPE = 15;

  /**
   * The feature id for the '<em><b>Const</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOL_TYPE__CONST = BASIC_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Bool Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOL_TYPE_FEATURE_COUNT = BASIC_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.RangeTypeImpl <em>Range Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.RangeTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getRangeType()
   * @generated
   */
  int RANGE_TYPE = 16;

  /**
   * The feature id for the '<em><b>Const</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_TYPE__CONST = BASIC_TYPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Min</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_TYPE__MIN = BASIC_TYPE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_TYPE__MAX = BASIC_TYPE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Range Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_TYPE_FEATURE_COUNT = BASIC_TYPE_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ClockTypeImpl <em>Clock Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ClockTypeImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getClockType()
   * @generated
   */
  int CLOCK_TYPE = 17;

  /**
   * The number of structural features of the '<em>Clock Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_TYPE_FEATURE_COUNT = BASIC_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl <em>Proc Body</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ProcBodyImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getProcBody()
   * @generated
   */
  int PROC_BODY = 18;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__VARIABLES = 0;

  /**
   * The feature id for the '<em><b>Types</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__TYPES = 1;

  /**
   * The feature id for the '<em><b>States</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__STATES = 2;

  /**
   * The feature id for the '<em><b>Commit States</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__COMMIT_STATES = 3;

  /**
   * The feature id for the '<em><b>Urgent States</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__URGENT_STATES = 4;

  /**
   * The feature id for the '<em><b>Init State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__INIT_STATE = 5;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY__TRANSITIONS = 6;

  /**
   * The number of structural features of the '<em>Proc Body</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROC_BODY_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.StateDeclImpl <em>State Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.StateDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getStateDecl()
   * @generated
   */
  int STATE_DECL = 19;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_DECL__NAME = 0;

  /**
   * The feature id for the '<em><b>Invariant</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_DECL__INVARIANT = 1;

  /**
   * The number of structural features of the '<em>State Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.TransitionImpl <em>Transition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.TransitionImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTransition()
   * @generated
   */
  int TRANSITION = 20;

  /**
   * The feature id for the '<em><b>Src</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__SRC = 0;

  /**
   * The feature id for the '<em><b>Dest</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__DEST = 1;

  /**
   * The feature id for the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__GUARD = 2;

  /**
   * The feature id for the '<em><b>Sync</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__SYNC = 3;

  /**
   * The feature id for the '<em><b>Assign</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION__ASSIGN = 4;

  /**
   * The number of structural features of the '<em>Transition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSITION_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.SyncImpl <em>Sync</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.SyncImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSync()
   * @generated
   */
  int SYNC = 21;

  /**
   * The feature id for the '<em><b>Channel</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNC__CHANNEL = 0;

  /**
   * The number of structural features of the '<em>Sync</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYNC_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.SendImpl <em>Send</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.SendImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSend()
   * @generated
   */
  int SEND = 22;

  /**
   * The feature id for the '<em><b>Channel</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEND__CHANNEL = SYNC__CHANNEL;

  /**
   * The number of structural features of the '<em>Send</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEND_FEATURE_COUNT = SYNC_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.RecvImpl <em>Recv</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.RecvImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getRecv()
   * @generated
   */
  int RECV = 23;

  /**
   * The feature id for the '<em><b>Channel</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RECV__CHANNEL = SYNC__CHANNEL;

  /**
   * The number of structural features of the '<em>Recv</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RECV_FEATURE_COUNT = SYNC_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.AssignmentsImpl <em>Assignments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.AssignmentsImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAssignments()
   * @generated
   */
  int ASSIGNMENTS = 24;

  /**
   * The feature id for the '<em><b>Assigns</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENTS__ASSIGNS = 0;

  /**
   * The number of structural features of the '<em>Assignments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.AssignImpl <em>Assign</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.AssignImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAssign()
   * @generated
   */
  int ASSIGN = 25;

  /**
   * The feature id for the '<em><b>Lhs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGN__LHS = 0;

  /**
   * The feature id for the '<em><b>Rhs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGN__RHS = 1;

  /**
   * The number of structural features of the '<em>Assign</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGN_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.VariableDeclImpl <em>Variable Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.VariableDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getVariableDecl()
   * @generated
   */
  int VARIABLE_DECL = 26;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECL__TYPE = 0;

  /**
   * The feature id for the '<em><b>Declid</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECL__DECLID = 1;

  /**
   * The number of structural features of the '<em>Variable Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_DECL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.DeclIdImpl <em>Decl Id</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.DeclIdImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getDeclId()
   * @generated
   */
  int DECL_ID = 27;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECL_ID__NAME = FORMAL_DECLARATION__NAME;

  /**
   * The feature id for the '<em><b>Arrays</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECL_ID__ARRAYS = FORMAL_DECLARATION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Init</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECL_ID__INIT = FORMAL_DECLARATION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Decl Id</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DECL_ID_FEATURE_COUNT = FORMAL_DECLARATION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.InitialiserImpl <em>Initialiser</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.InitialiserImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInitialiser()
   * @generated
   */
  int INITIALISER = 28;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INITIALISER__EXPR = 0;

  /**
   * The number of structural features of the '<em>Initialiser</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INITIALISER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ArrayDeclImpl <em>Array Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ArrayDeclImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getArrayDecl()
   * @generated
   */
  int ARRAY_DECL = 29;

  /**
   * The feature id for the '<em><b>Size</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_DECL__SIZE = 0;

  /**
   * The number of structural features of the '<em>Array Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_DECL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.IntExpressionImpl <em>Int Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.IntExpressionImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getIntExpression()
   * @generated
   */
  int INT_EXPRESSION = 30;

  /**
   * The number of structural features of the '<em>Int Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INT_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.VarAccessImpl <em>Var Access</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.VarAccessImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getVarAccess()
   * @generated
   */
  int VAR_ACCESS = 31;

  /**
   * The feature id for the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VAR_ACCESS__REF = INT_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Var Access</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VAR_ACCESS_FEATURE_COUNT = INT_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.WrapBoolExprImpl <em>Wrap Bool Expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.WrapBoolExprImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getWrapBoolExpr()
   * @generated
   */
  int WRAP_BOOL_EXPR = 32;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ConstantImpl <em>Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ConstantImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getConstant()
   * @generated
   */
  int CONSTANT = 33;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.BooleanExpressionImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBooleanExpression()
   * @generated
   */
  int BOOLEAN_EXPRESSION = 34;

  /**
   * The number of structural features of the '<em>Boolean Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.ComparisonImpl <em>Comparison</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.ComparisonImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getComparison()
   * @generated
   */
  int COMPARISON = 35;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.TrueImpl <em>True</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.TrueImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTrue()
   * @generated
   */
  int TRUE = 36;

  /**
   * The number of structural features of the '<em>True</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRUE_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.FalseImpl <em>False</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.FalseImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getFalse()
   * @generated
   */
  int FALSE = 37;

  /**
   * The number of structural features of the '<em>False</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FALSE_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.BinaryIntExpressionImpl <em>Binary Int Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.BinaryIntExpressionImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBinaryIntExpression()
   * @generated
   */
  int BINARY_INT_EXPRESSION = 38;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.BitComplementImpl <em>Bit Complement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.BitComplementImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBitComplement()
   * @generated
   */
  int BIT_COMPLEMENT = 39;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.UnaryMinusImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getUnaryMinus()
   * @generated
   */
  int UNARY_MINUS = 40;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.OrImpl <em>Or</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.OrImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getOr()
   * @generated
   */
  int OR = 41;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.AndImpl <em>And</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.AndImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAnd()
   * @generated
   */
  int AND = 42;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.impl.NotImpl <em>Not</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.impl.NotImpl
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getNot()
   * @generated
   */
  int NOT = 43;

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
   * The meta object id for the '{@link fr.lip6.move.timedAutomata.ComparisonOperators <em>Comparison Operators</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see fr.lip6.move.timedAutomata.ComparisonOperators
   * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getComparisonOperators()
   * @generated
   */
  int COMPARISON_OPERATORS = 44;


  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.XTA <em>XTA</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>XTA</em>'.
   * @see fr.lip6.move.timedAutomata.XTA
   * @generated
   */
  EClass getXTA();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.XTA#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getVariables()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_Variables();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.XTA#getChannels <em>Channels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Channels</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getChannels()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_Channels();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.XTA#getTypes <em>Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Types</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getTypes()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_Types();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.XTA#getTemplates <em>Templates</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Templates</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getTemplates()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_Templates();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.XTA#getInstances <em>Instances</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Instances</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getInstances()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_Instances();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.XTA#getSystem <em>System</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>System</em>'.
   * @see fr.lip6.move.timedAutomata.XTA#getSystem()
   * @see #getXTA()
   * @generated
   */
  EReference getXTA_System();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.InstantiableInSystem <em>Instantiable In System</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Instantiable In System</em>'.
   * @see fr.lip6.move.timedAutomata.InstantiableInSystem
   * @generated
   */
  EClass getInstantiableInSystem();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.InstantiableInSystem#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.timedAutomata.InstantiableInSystem#getName()
   * @see #getInstantiableInSystem()
   * @generated
   */
  EAttribute getInstantiableInSystem_Name();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Instance <em>Instance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Instance</em>'.
   * @see fr.lip6.move.timedAutomata.Instance
   * @generated
   */
  EClass getInstance();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.Instance#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Type</em>'.
   * @see fr.lip6.move.timedAutomata.Instance#getType()
   * @see #getInstance()
   * @generated
   */
  EReference getInstance_Type();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.Instance#getArgs <em>Args</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Args</em>'.
   * @see fr.lip6.move.timedAutomata.Instance#getArgs()
   * @see #getInstance()
   * @generated
   */
  EReference getInstance_Args();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.System <em>System</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>System</em>'.
   * @see fr.lip6.move.timedAutomata.System
   * @generated
   */
  EClass getSystem();

  /**
   * Returns the meta object for the reference list '{@link fr.lip6.move.timedAutomata.System#getInstances <em>Instances</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Instances</em>'.
   * @see fr.lip6.move.timedAutomata.System#getInstances()
   * @see #getSystem()
   * @generated
   */
  EReference getSystem_Instances();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ProcDecl <em>Proc Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Proc Decl</em>'.
   * @see fr.lip6.move.timedAutomata.ProcDecl
   * @generated
   */
  EClass getProcDecl();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ProcDecl#getParams <em>Params</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Params</em>'.
   * @see fr.lip6.move.timedAutomata.ProcDecl#getParams()
   * @see #getProcDecl()
   * @generated
   */
  EReference getProcDecl_Params();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.ProcDecl#getBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body</em>'.
   * @see fr.lip6.move.timedAutomata.ProcDecl#getBody()
   * @see #getProcDecl()
   * @generated
   */
  EReference getProcDecl_Body();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.FormalDeclaration <em>Formal Declaration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Formal Declaration</em>'.
   * @see fr.lip6.move.timedAutomata.FormalDeclaration
   * @generated
   */
  EClass getFormalDeclaration();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.FormalDeclaration#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.timedAutomata.FormalDeclaration#getName()
   * @see #getFormalDeclaration()
   * @generated
   */
  EAttribute getFormalDeclaration_Name();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter</em>'.
   * @see fr.lip6.move.timedAutomata.Parameter
   * @generated
   */
  EClass getParameter();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Parameter#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see fr.lip6.move.timedAutomata.Parameter#getType()
   * @see #getParameter()
   * @generated
   */
  EReference getParameter_Type();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.TypeDecl <em>Type Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Decl</em>'.
   * @see fr.lip6.move.timedAutomata.TypeDecl
   * @generated
   */
  EClass getTypeDecl();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.TypeDecl#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see fr.lip6.move.timedAutomata.TypeDecl#getType()
   * @see #getTypeDecl()
   * @generated
   */
  EReference getTypeDecl_Type();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.TypeDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.timedAutomata.TypeDecl#getName()
   * @see #getTypeDecl()
   * @generated
   */
  EAttribute getTypeDecl_Name();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type</em>'.
   * @see fr.lip6.move.timedAutomata.Type
   * @generated
   */
  EClass getType();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.BasicType <em>Basic Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Basic Type</em>'.
   * @see fr.lip6.move.timedAutomata.BasicType
   * @generated
   */
  EClass getBasicType();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.TypedefRef <em>Typedef Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Typedef Ref</em>'.
   * @see fr.lip6.move.timedAutomata.TypedefRef
   * @generated
   */
  EClass getTypedefRef();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.TypedefRef#getRef <em>Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Ref</em>'.
   * @see fr.lip6.move.timedAutomata.TypedefRef#getRef()
   * @see #getTypedefRef()
   * @generated
   */
  EReference getTypedefRef_Ref();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ChannelDecl <em>Channel Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Channel Decl</em>'.
   * @see fr.lip6.move.timedAutomata.ChannelDecl
   * @generated
   */
  EClass getChannelDecl();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ChanId <em>Chan Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Chan Id</em>'.
   * @see fr.lip6.move.timedAutomata.ChanId
   * @generated
   */
  EClass getChanId();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.ChanId#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.timedAutomata.ChanId#getName()
   * @see #getChanId()
   * @generated
   */
  EAttribute getChanId_Name();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ChannelType <em>Channel Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Channel Type</em>'.
   * @see fr.lip6.move.timedAutomata.ChannelType
   * @generated
   */
  EClass getChannelType();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ChannelType#getChans <em>Chans</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Chans</em>'.
   * @see fr.lip6.move.timedAutomata.ChannelType#getChans()
   * @see #getChannelType()
   * @generated
   */
  EReference getChannelType_Chans();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.ChannelType#isUrgent <em>Urgent</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Urgent</em>'.
   * @see fr.lip6.move.timedAutomata.ChannelType#isUrgent()
   * @see #getChannelType()
   * @generated
   */
  EAttribute getChannelType_Urgent();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.ChannelType#isBroadcast <em>Broadcast</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Broadcast</em>'.
   * @see fr.lip6.move.timedAutomata.ChannelType#isBroadcast()
   * @see #getChannelType()
   * @generated
   */
  EAttribute getChannelType_Broadcast();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.IntegerType <em>Integer Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Type</em>'.
   * @see fr.lip6.move.timedAutomata.IntegerType
   * @generated
   */
  EClass getIntegerType();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.IntegerType#isConst <em>Const</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Const</em>'.
   * @see fr.lip6.move.timedAutomata.IntegerType#isConst()
   * @see #getIntegerType()
   * @generated
   */
  EAttribute getIntegerType_Const();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.BoolType <em>Bool Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Bool Type</em>'.
   * @see fr.lip6.move.timedAutomata.BoolType
   * @generated
   */
  EClass getBoolType();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.BoolType#isConst <em>Const</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Const</em>'.
   * @see fr.lip6.move.timedAutomata.BoolType#isConst()
   * @see #getBoolType()
   * @generated
   */
  EAttribute getBoolType_Const();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Type</em>'.
   * @see fr.lip6.move.timedAutomata.RangeType
   * @generated
   */
  EClass getRangeType();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.RangeType#isConst <em>Const</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Const</em>'.
   * @see fr.lip6.move.timedAutomata.RangeType#isConst()
   * @see #getRangeType()
   * @generated
   */
  EAttribute getRangeType_Const();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.RangeType#getMin <em>Min</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Min</em>'.
   * @see fr.lip6.move.timedAutomata.RangeType#getMin()
   * @see #getRangeType()
   * @generated
   */
  EAttribute getRangeType_Min();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.RangeType#getMax <em>Max</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Max</em>'.
   * @see fr.lip6.move.timedAutomata.RangeType#getMax()
   * @see #getRangeType()
   * @generated
   */
  EAttribute getRangeType_Max();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ClockType <em>Clock Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Clock Type</em>'.
   * @see fr.lip6.move.timedAutomata.ClockType
   * @generated
   */
  EClass getClockType();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ProcBody <em>Proc Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Proc Body</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody
   * @generated
   */
  EClass getProcBody();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getVariables()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_Variables();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getTypes <em>Types</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Types</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getTypes()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_Types();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getStates <em>States</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>States</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getStates()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_States();

  /**
   * Returns the meta object for the reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getCommitStates <em>Commit States</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Commit States</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getCommitStates()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_CommitStates();

  /**
   * Returns the meta object for the reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getUrgentStates <em>Urgent States</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Urgent States</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getUrgentStates()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_UrgentStates();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.ProcBody#getInitState <em>Init State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Init State</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getInitState()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_InitState();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.ProcBody#getTransitions <em>Transitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transitions</em>'.
   * @see fr.lip6.move.timedAutomata.ProcBody#getTransitions()
   * @see #getProcBody()
   * @generated
   */
  EReference getProcBody_Transitions();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.StateDecl <em>State Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>State Decl</em>'.
   * @see fr.lip6.move.timedAutomata.StateDecl
   * @generated
   */
  EClass getStateDecl();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.StateDecl#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see fr.lip6.move.timedAutomata.StateDecl#getName()
   * @see #getStateDecl()
   * @generated
   */
  EAttribute getStateDecl_Name();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.StateDecl#getInvariant <em>Invariant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Invariant</em>'.
   * @see fr.lip6.move.timedAutomata.StateDecl#getInvariant()
   * @see #getStateDecl()
   * @generated
   */
  EReference getStateDecl_Invariant();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Transition <em>Transition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transition</em>'.
   * @see fr.lip6.move.timedAutomata.Transition
   * @generated
   */
  EClass getTransition();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.Transition#getSrc <em>Src</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Src</em>'.
   * @see fr.lip6.move.timedAutomata.Transition#getSrc()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Src();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.Transition#getDest <em>Dest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Dest</em>'.
   * @see fr.lip6.move.timedAutomata.Transition#getDest()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Dest();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Transition#getGuard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Guard</em>'.
   * @see fr.lip6.move.timedAutomata.Transition#getGuard()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Guard();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Transition#getSync <em>Sync</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Sync</em>'.
   * @see fr.lip6.move.timedAutomata.Transition#getSync()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Sync();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Transition#getAssign <em>Assign</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Assign</em>'.
   * @see fr.lip6.move.timedAutomata.Transition#getAssign()
   * @see #getTransition()
   * @generated
   */
  EReference getTransition_Assign();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Sync <em>Sync</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Sync</em>'.
   * @see fr.lip6.move.timedAutomata.Sync
   * @generated
   */
  EClass getSync();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.Sync#getChannel <em>Channel</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Channel</em>'.
   * @see fr.lip6.move.timedAutomata.Sync#getChannel()
   * @see #getSync()
   * @generated
   */
  EReference getSync_Channel();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Send <em>Send</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Send</em>'.
   * @see fr.lip6.move.timedAutomata.Send
   * @generated
   */
  EClass getSend();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Recv <em>Recv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Recv</em>'.
   * @see fr.lip6.move.timedAutomata.Recv
   * @generated
   */
  EClass getRecv();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Assignments <em>Assignments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignments</em>'.
   * @see fr.lip6.move.timedAutomata.Assignments
   * @generated
   */
  EClass getAssignments();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.Assignments#getAssigns <em>Assigns</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Assigns</em>'.
   * @see fr.lip6.move.timedAutomata.Assignments#getAssigns()
   * @see #getAssignments()
   * @generated
   */
  EReference getAssignments_Assigns();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Assign <em>Assign</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assign</em>'.
   * @see fr.lip6.move.timedAutomata.Assign
   * @generated
   */
  EClass getAssign();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Assign#getLhs <em>Lhs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lhs</em>'.
   * @see fr.lip6.move.timedAutomata.Assign#getLhs()
   * @see #getAssign()
   * @generated
   */
  EReference getAssign_Lhs();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Assign#getRhs <em>Rhs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Rhs</em>'.
   * @see fr.lip6.move.timedAutomata.Assign#getRhs()
   * @see #getAssign()
   * @generated
   */
  EReference getAssign_Rhs();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.VariableDecl <em>Variable Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable Decl</em>'.
   * @see fr.lip6.move.timedAutomata.VariableDecl
   * @generated
   */
  EClass getVariableDecl();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.VariableDecl#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see fr.lip6.move.timedAutomata.VariableDecl#getType()
   * @see #getVariableDecl()
   * @generated
   */
  EReference getVariableDecl_Type();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.VariableDecl#getDeclid <em>Declid</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Declid</em>'.
   * @see fr.lip6.move.timedAutomata.VariableDecl#getDeclid()
   * @see #getVariableDecl()
   * @generated
   */
  EReference getVariableDecl_Declid();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.DeclId <em>Decl Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Decl Id</em>'.
   * @see fr.lip6.move.timedAutomata.DeclId
   * @generated
   */
  EClass getDeclId();

  /**
   * Returns the meta object for the containment reference list '{@link fr.lip6.move.timedAutomata.DeclId#getArrays <em>Arrays</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arrays</em>'.
   * @see fr.lip6.move.timedAutomata.DeclId#getArrays()
   * @see #getDeclId()
   * @generated
   */
  EReference getDeclId_Arrays();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.DeclId#getInit <em>Init</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Init</em>'.
   * @see fr.lip6.move.timedAutomata.DeclId#getInit()
   * @see #getDeclId()
   * @generated
   */
  EReference getDeclId_Init();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Initialiser <em>Initialiser</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Initialiser</em>'.
   * @see fr.lip6.move.timedAutomata.Initialiser
   * @generated
   */
  EClass getInitialiser();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Initialiser#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see fr.lip6.move.timedAutomata.Initialiser#getExpr()
   * @see #getInitialiser()
   * @generated
   */
  EReference getInitialiser_Expr();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.ArrayDecl <em>Array Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Array Decl</em>'.
   * @see fr.lip6.move.timedAutomata.ArrayDecl
   * @generated
   */
  EClass getArrayDecl();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.ArrayDecl#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Size</em>'.
   * @see fr.lip6.move.timedAutomata.ArrayDecl#getSize()
   * @see #getArrayDecl()
   * @generated
   */
  EAttribute getArrayDecl_Size();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.IntExpression <em>Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Int Expression</em>'.
   * @see fr.lip6.move.timedAutomata.IntExpression
   * @generated
   */
  EClass getIntExpression();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.VarAccess <em>Var Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Var Access</em>'.
   * @see fr.lip6.move.timedAutomata.VarAccess
   * @generated
   */
  EClass getVarAccess();

  /**
   * Returns the meta object for the reference '{@link fr.lip6.move.timedAutomata.VarAccess#getRef <em>Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Ref</em>'.
   * @see fr.lip6.move.timedAutomata.VarAccess#getRef()
   * @see #getVarAccess()
   * @generated
   */
  EReference getVarAccess_Ref();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.WrapBoolExpr <em>Wrap Bool Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Wrap Bool Expr</em>'.
   * @see fr.lip6.move.timedAutomata.WrapBoolExpr
   * @generated
   */
  EClass getWrapBoolExpr();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.WrapBoolExpr#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.timedAutomata.WrapBoolExpr#getValue()
   * @see #getWrapBoolExpr()
   * @generated
   */
  EReference getWrapBoolExpr_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Constant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constant</em>'.
   * @see fr.lip6.move.timedAutomata.Constant
   * @generated
   */
  EClass getConstant();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.Constant#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see fr.lip6.move.timedAutomata.Constant#getValue()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.BooleanExpression <em>Boolean Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Expression</em>'.
   * @see fr.lip6.move.timedAutomata.BooleanExpression
   * @generated
   */
  EClass getBooleanExpression();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Comparison <em>Comparison</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Comparison</em>'.
   * @see fr.lip6.move.timedAutomata.Comparison
   * @generated
   */
  EClass getComparison();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Comparison#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.timedAutomata.Comparison#getLeft()
   * @see #getComparison()
   * @generated
   */
  EReference getComparison_Left();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.Comparison#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see fr.lip6.move.timedAutomata.Comparison#getOperator()
   * @see #getComparison()
   * @generated
   */
  EAttribute getComparison_Operator();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Comparison#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.timedAutomata.Comparison#getRight()
   * @see #getComparison()
   * @generated
   */
  EReference getComparison_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.True <em>True</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>True</em>'.
   * @see fr.lip6.move.timedAutomata.True
   * @generated
   */
  EClass getTrue();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.False <em>False</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>False</em>'.
   * @see fr.lip6.move.timedAutomata.False
   * @generated
   */
  EClass getFalse();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.BinaryIntExpression <em>Binary Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Binary Int Expression</em>'.
   * @see fr.lip6.move.timedAutomata.BinaryIntExpression
   * @generated
   */
  EClass getBinaryIntExpression();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.timedAutomata.BinaryIntExpression#getLeft()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EReference getBinaryIntExpression_Left();

  /**
   * Returns the meta object for the attribute '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getOp <em>Op</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Op</em>'.
   * @see fr.lip6.move.timedAutomata.BinaryIntExpression#getOp()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EAttribute getBinaryIntExpression_Op();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.timedAutomata.BinaryIntExpression#getRight()
   * @see #getBinaryIntExpression()
   * @generated
   */
  EReference getBinaryIntExpression_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.BitComplement <em>Bit Complement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Bit Complement</em>'.
   * @see fr.lip6.move.timedAutomata.BitComplement
   * @generated
   */
  EClass getBitComplement();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.BitComplement#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.timedAutomata.BitComplement#getValue()
   * @see #getBitComplement()
   * @generated
   */
  EReference getBitComplement_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.UnaryMinus <em>Unary Minus</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unary Minus</em>'.
   * @see fr.lip6.move.timedAutomata.UnaryMinus
   * @generated
   */
  EClass getUnaryMinus();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.UnaryMinus#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.timedAutomata.UnaryMinus#getValue()
   * @see #getUnaryMinus()
   * @generated
   */
  EReference getUnaryMinus_Value();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Or <em>Or</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Or</em>'.
   * @see fr.lip6.move.timedAutomata.Or
   * @generated
   */
  EClass getOr();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Or#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.timedAutomata.Or#getLeft()
   * @see #getOr()
   * @generated
   */
  EReference getOr_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Or#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.timedAutomata.Or#getRight()
   * @see #getOr()
   * @generated
   */
  EReference getOr_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.And <em>And</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>And</em>'.
   * @see fr.lip6.move.timedAutomata.And
   * @generated
   */
  EClass getAnd();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.And#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see fr.lip6.move.timedAutomata.And#getLeft()
   * @see #getAnd()
   * @generated
   */
  EReference getAnd_Left();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.And#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see fr.lip6.move.timedAutomata.And#getRight()
   * @see #getAnd()
   * @generated
   */
  EReference getAnd_Right();

  /**
   * Returns the meta object for class '{@link fr.lip6.move.timedAutomata.Not <em>Not</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Not</em>'.
   * @see fr.lip6.move.timedAutomata.Not
   * @generated
   */
  EClass getNot();

  /**
   * Returns the meta object for the containment reference '{@link fr.lip6.move.timedAutomata.Not#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see fr.lip6.move.timedAutomata.Not#getValue()
   * @see #getNot()
   * @generated
   */
  EReference getNot_Value();

  /**
   * Returns the meta object for enum '{@link fr.lip6.move.timedAutomata.ComparisonOperators <em>Comparison Operators</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Comparison Operators</em>'.
   * @see fr.lip6.move.timedAutomata.ComparisonOperators
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
  TimedAutomataFactory getTimedAutomataFactory();

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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.XTAImpl <em>XTA</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.XTAImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getXTA()
     * @generated
     */
    EClass XTA = eINSTANCE.getXTA();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__VARIABLES = eINSTANCE.getXTA_Variables();

    /**
     * The meta object literal for the '<em><b>Channels</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__CHANNELS = eINSTANCE.getXTA_Channels();

    /**
     * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__TYPES = eINSTANCE.getXTA_Types();

    /**
     * The meta object literal for the '<em><b>Templates</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__TEMPLATES = eINSTANCE.getXTA_Templates();

    /**
     * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__INSTANCES = eINSTANCE.getXTA_Instances();

    /**
     * The meta object literal for the '<em><b>System</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference XTA__SYSTEM = eINSTANCE.getXTA_System();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.InstantiableInSystemImpl <em>Instantiable In System</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.InstantiableInSystemImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInstantiableInSystem()
     * @generated
     */
    EClass INSTANTIABLE_IN_SYSTEM = eINSTANCE.getInstantiableInSystem();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INSTANTIABLE_IN_SYSTEM__NAME = eINSTANCE.getInstantiableInSystem_Name();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.InstanceImpl <em>Instance</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.InstanceImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInstance()
     * @generated
     */
    EClass INSTANCE = eINSTANCE.getInstance();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INSTANCE__TYPE = eINSTANCE.getInstance_Type();

    /**
     * The meta object literal for the '<em><b>Args</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INSTANCE__ARGS = eINSTANCE.getInstance_Args();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.SystemImpl <em>System</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.SystemImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSystem()
     * @generated
     */
    EClass SYSTEM = eINSTANCE.getSystem();

    /**
     * The meta object literal for the '<em><b>Instances</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYSTEM__INSTANCES = eINSTANCE.getSystem_Instances();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ProcDeclImpl <em>Proc Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ProcDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getProcDecl()
     * @generated
     */
    EClass PROC_DECL = eINSTANCE.getProcDecl();

    /**
     * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_DECL__PARAMS = eINSTANCE.getProcDecl_Params();

    /**
     * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_DECL__BODY = eINSTANCE.getProcDecl_Body();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.FormalDeclarationImpl <em>Formal Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.FormalDeclarationImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getFormalDeclaration()
     * @generated
     */
    EClass FORMAL_DECLARATION = eINSTANCE.getFormalDeclaration();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FORMAL_DECLARATION__NAME = eINSTANCE.getFormalDeclaration_Name();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ParameterImpl <em>Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ParameterImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getParameter()
     * @generated
     */
    EClass PARAMETER = eINSTANCE.getParameter();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARAMETER__TYPE = eINSTANCE.getParameter_Type();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.TypeDeclImpl <em>Type Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.TypeDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTypeDecl()
     * @generated
     */
    EClass TYPE_DECL = eINSTANCE.getTypeDecl();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_DECL__TYPE = eINSTANCE.getTypeDecl_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_DECL__NAME = eINSTANCE.getTypeDecl_Name();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.TypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.TypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getType()
     * @generated
     */
    EClass TYPE = eINSTANCE.getType();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.BasicTypeImpl <em>Basic Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.BasicTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBasicType()
     * @generated
     */
    EClass BASIC_TYPE = eINSTANCE.getBasicType();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.TypedefRefImpl <em>Typedef Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.TypedefRefImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTypedefRef()
     * @generated
     */
    EClass TYPEDEF_REF = eINSTANCE.getTypedefRef();

    /**
     * The meta object literal for the '<em><b>Ref</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPEDEF_REF__REF = eINSTANCE.getTypedefRef_Ref();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ChannelDeclImpl <em>Channel Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ChannelDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChannelDecl()
     * @generated
     */
    EClass CHANNEL_DECL = eINSTANCE.getChannelDecl();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ChanIdImpl <em>Chan Id</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ChanIdImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChanId()
     * @generated
     */
    EClass CHAN_ID = eINSTANCE.getChanId();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHAN_ID__NAME = eINSTANCE.getChanId_Name();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ChannelTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getChannelType()
     * @generated
     */
    EClass CHANNEL_TYPE = eINSTANCE.getChannelType();

    /**
     * The meta object literal for the '<em><b>Chans</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CHANNEL_TYPE__CHANS = eINSTANCE.getChannelType_Chans();

    /**
     * The meta object literal for the '<em><b>Urgent</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHANNEL_TYPE__URGENT = eINSTANCE.getChannelType_Urgent();

    /**
     * The meta object literal for the '<em><b>Broadcast</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CHANNEL_TYPE__BROADCAST = eINSTANCE.getChannelType_Broadcast();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.IntegerTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getIntegerType()
     * @generated
     */
    EClass INTEGER_TYPE = eINSTANCE.getIntegerType();

    /**
     * The meta object literal for the '<em><b>Const</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_TYPE__CONST = eINSTANCE.getIntegerType_Const();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.BoolTypeImpl <em>Bool Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.BoolTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBoolType()
     * @generated
     */
    EClass BOOL_TYPE = eINSTANCE.getBoolType();

    /**
     * The meta object literal for the '<em><b>Const</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BOOL_TYPE__CONST = eINSTANCE.getBoolType_Const();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.RangeTypeImpl <em>Range Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.RangeTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getRangeType()
     * @generated
     */
    EClass RANGE_TYPE = eINSTANCE.getRangeType();

    /**
     * The meta object literal for the '<em><b>Const</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RANGE_TYPE__CONST = eINSTANCE.getRangeType_Const();

    /**
     * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RANGE_TYPE__MIN = eINSTANCE.getRangeType_Min();

    /**
     * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RANGE_TYPE__MAX = eINSTANCE.getRangeType_Max();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ClockTypeImpl <em>Clock Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ClockTypeImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getClockType()
     * @generated
     */
    EClass CLOCK_TYPE = eINSTANCE.getClockType();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl <em>Proc Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ProcBodyImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getProcBody()
     * @generated
     */
    EClass PROC_BODY = eINSTANCE.getProcBody();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__VARIABLES = eINSTANCE.getProcBody_Variables();

    /**
     * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__TYPES = eINSTANCE.getProcBody_Types();

    /**
     * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__STATES = eINSTANCE.getProcBody_States();

    /**
     * The meta object literal for the '<em><b>Commit States</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__COMMIT_STATES = eINSTANCE.getProcBody_CommitStates();

    /**
     * The meta object literal for the '<em><b>Urgent States</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__URGENT_STATES = eINSTANCE.getProcBody_UrgentStates();

    /**
     * The meta object literal for the '<em><b>Init State</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__INIT_STATE = eINSTANCE.getProcBody_InitState();

    /**
     * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROC_BODY__TRANSITIONS = eINSTANCE.getProcBody_Transitions();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.StateDeclImpl <em>State Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.StateDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getStateDecl()
     * @generated
     */
    EClass STATE_DECL = eINSTANCE.getStateDecl();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATE_DECL__NAME = eINSTANCE.getStateDecl_Name();

    /**
     * The meta object literal for the '<em><b>Invariant</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_DECL__INVARIANT = eINSTANCE.getStateDecl_Invariant();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.TransitionImpl <em>Transition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.TransitionImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTransition()
     * @generated
     */
    EClass TRANSITION = eINSTANCE.getTransition();

    /**
     * The meta object literal for the '<em><b>Src</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__SRC = eINSTANCE.getTransition_Src();

    /**
     * The meta object literal for the '<em><b>Dest</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__DEST = eINSTANCE.getTransition_Dest();

    /**
     * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__GUARD = eINSTANCE.getTransition_Guard();

    /**
     * The meta object literal for the '<em><b>Sync</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__SYNC = eINSTANCE.getTransition_Sync();

    /**
     * The meta object literal for the '<em><b>Assign</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANSITION__ASSIGN = eINSTANCE.getTransition_Assign();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.SyncImpl <em>Sync</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.SyncImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSync()
     * @generated
     */
    EClass SYNC = eINSTANCE.getSync();

    /**
     * The meta object literal for the '<em><b>Channel</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SYNC__CHANNEL = eINSTANCE.getSync_Channel();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.SendImpl <em>Send</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.SendImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getSend()
     * @generated
     */
    EClass SEND = eINSTANCE.getSend();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.RecvImpl <em>Recv</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.RecvImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getRecv()
     * @generated
     */
    EClass RECV = eINSTANCE.getRecv();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.AssignmentsImpl <em>Assignments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.AssignmentsImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAssignments()
     * @generated
     */
    EClass ASSIGNMENTS = eINSTANCE.getAssignments();

    /**
     * The meta object literal for the '<em><b>Assigns</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENTS__ASSIGNS = eINSTANCE.getAssignments_Assigns();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.AssignImpl <em>Assign</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.AssignImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAssign()
     * @generated
     */
    EClass ASSIGN = eINSTANCE.getAssign();

    /**
     * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGN__LHS = eINSTANCE.getAssign_Lhs();

    /**
     * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGN__RHS = eINSTANCE.getAssign_Rhs();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.VariableDeclImpl <em>Variable Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.VariableDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getVariableDecl()
     * @generated
     */
    EClass VARIABLE_DECL = eINSTANCE.getVariableDecl();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_DECL__TYPE = eINSTANCE.getVariableDecl_Type();

    /**
     * The meta object literal for the '<em><b>Declid</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_DECL__DECLID = eINSTANCE.getVariableDecl_Declid();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.DeclIdImpl <em>Decl Id</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.DeclIdImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getDeclId()
     * @generated
     */
    EClass DECL_ID = eINSTANCE.getDeclId();

    /**
     * The meta object literal for the '<em><b>Arrays</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DECL_ID__ARRAYS = eINSTANCE.getDeclId_Arrays();

    /**
     * The meta object literal for the '<em><b>Init</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DECL_ID__INIT = eINSTANCE.getDeclId_Init();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.InitialiserImpl <em>Initialiser</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.InitialiserImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getInitialiser()
     * @generated
     */
    EClass INITIALISER = eINSTANCE.getInitialiser();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INITIALISER__EXPR = eINSTANCE.getInitialiser_Expr();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ArrayDeclImpl <em>Array Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ArrayDeclImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getArrayDecl()
     * @generated
     */
    EClass ARRAY_DECL = eINSTANCE.getArrayDecl();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARRAY_DECL__SIZE = eINSTANCE.getArrayDecl_Size();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.IntExpressionImpl <em>Int Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.IntExpressionImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getIntExpression()
     * @generated
     */
    EClass INT_EXPRESSION = eINSTANCE.getIntExpression();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.VarAccessImpl <em>Var Access</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.VarAccessImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getVarAccess()
     * @generated
     */
    EClass VAR_ACCESS = eINSTANCE.getVarAccess();

    /**
     * The meta object literal for the '<em><b>Ref</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VAR_ACCESS__REF = eINSTANCE.getVarAccess_Ref();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.WrapBoolExprImpl <em>Wrap Bool Expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.WrapBoolExprImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getWrapBoolExpr()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ConstantImpl <em>Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ConstantImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getConstant()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.BooleanExpressionImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBooleanExpression()
     * @generated
     */
    EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.ComparisonImpl <em>Comparison</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.ComparisonImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getComparison()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.TrueImpl <em>True</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.TrueImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getTrue()
     * @generated
     */
    EClass TRUE = eINSTANCE.getTrue();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.FalseImpl <em>False</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.FalseImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getFalse()
     * @generated
     */
    EClass FALSE = eINSTANCE.getFalse();

    /**
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.BinaryIntExpressionImpl <em>Binary Int Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.BinaryIntExpressionImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBinaryIntExpression()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.BitComplementImpl <em>Bit Complement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.BitComplementImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getBitComplement()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.UnaryMinusImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getUnaryMinus()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.OrImpl <em>Or</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.OrImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getOr()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.AndImpl <em>And</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.AndImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getAnd()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.impl.NotImpl <em>Not</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.impl.NotImpl
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getNot()
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
     * The meta object literal for the '{@link fr.lip6.move.timedAutomata.ComparisonOperators <em>Comparison Operators</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see fr.lip6.move.timedAutomata.ComparisonOperators
     * @see fr.lip6.move.timedAutomata.impl.TimedAutomataPackageImpl#getComparisonOperators()
     * @generated
     */
    EEnum COMPARISON_OPERATORS = eINSTANCE.getComparisonOperators();

  }

} //TimedAutomataPackage
