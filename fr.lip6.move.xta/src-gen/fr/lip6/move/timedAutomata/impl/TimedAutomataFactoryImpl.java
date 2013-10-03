/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.And;
import fr.lip6.move.timedAutomata.ArrayDecl;
import fr.lip6.move.timedAutomata.Assign;
import fr.lip6.move.timedAutomata.Assignments;
import fr.lip6.move.timedAutomata.BasicType;
import fr.lip6.move.timedAutomata.BinaryIntExpression;
import fr.lip6.move.timedAutomata.BitComplement;
import fr.lip6.move.timedAutomata.BoolType;
import fr.lip6.move.timedAutomata.BooleanExpression;
import fr.lip6.move.timedAutomata.ChanId;
import fr.lip6.move.timedAutomata.ChannelDecl;
import fr.lip6.move.timedAutomata.ChannelType;
import fr.lip6.move.timedAutomata.ClockType;
import fr.lip6.move.timedAutomata.Comparison;
import fr.lip6.move.timedAutomata.ComparisonOperators;
import fr.lip6.move.timedAutomata.Constant;
import fr.lip6.move.timedAutomata.DeclId;
import fr.lip6.move.timedAutomata.False;
import fr.lip6.move.timedAutomata.FormalDeclaration;
import fr.lip6.move.timedAutomata.Initialiser;
import fr.lip6.move.timedAutomata.Instance;
import fr.lip6.move.timedAutomata.InstantiableInSystem;
import fr.lip6.move.timedAutomata.IntExpression;
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
import fr.lip6.move.timedAutomata.Sync;
import fr.lip6.move.timedAutomata.TimedAutomataFactory;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;
import fr.lip6.move.timedAutomata.Transition;
import fr.lip6.move.timedAutomata.True;
import fr.lip6.move.timedAutomata.Type;
import fr.lip6.move.timedAutomata.TypeDecl;
import fr.lip6.move.timedAutomata.TypedefRef;
import fr.lip6.move.timedAutomata.UnaryMinus;
import fr.lip6.move.timedAutomata.VarAccess;
import fr.lip6.move.timedAutomata.VariableDecl;
import fr.lip6.move.timedAutomata.WrapBoolExpr;
import fr.lip6.move.timedAutomata.XTA;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TimedAutomataFactoryImpl extends EFactoryImpl implements TimedAutomataFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static TimedAutomataFactory init()
  {
    try
    {
      TimedAutomataFactory theTimedAutomataFactory = (TimedAutomataFactory)EPackage.Registry.INSTANCE.getEFactory(TimedAutomataPackage.eNS_URI);
      if (theTimedAutomataFactory != null)
      {
        return theTimedAutomataFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TimedAutomataFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimedAutomataFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case TimedAutomataPackage.XTA: return createXTA();
      case TimedAutomataPackage.INSTANTIABLE_IN_SYSTEM: return createInstantiableInSystem();
      case TimedAutomataPackage.INSTANCE: return createInstance();
      case TimedAutomataPackage.SYSTEM: return createSystem();
      case TimedAutomataPackage.PROC_DECL: return createProcDecl();
      case TimedAutomataPackage.FORMAL_DECLARATION: return createFormalDeclaration();
      case TimedAutomataPackage.PARAMETER: return createParameter();
      case TimedAutomataPackage.TYPE_DECL: return createTypeDecl();
      case TimedAutomataPackage.TYPE: return createType();
      case TimedAutomataPackage.BASIC_TYPE: return createBasicType();
      case TimedAutomataPackage.TYPEDEF_REF: return createTypedefRef();
      case TimedAutomataPackage.CHANNEL_DECL: return createChannelDecl();
      case TimedAutomataPackage.CHAN_ID: return createChanId();
      case TimedAutomataPackage.CHANNEL_TYPE: return createChannelType();
      case TimedAutomataPackage.INTEGER_TYPE: return createIntegerType();
      case TimedAutomataPackage.BOOL_TYPE: return createBoolType();
      case TimedAutomataPackage.RANGE_TYPE: return createRangeType();
      case TimedAutomataPackage.CLOCK_TYPE: return createClockType();
      case TimedAutomataPackage.PROC_BODY: return createProcBody();
      case TimedAutomataPackage.STATE_DECL: return createStateDecl();
      case TimedAutomataPackage.TRANSITION: return createTransition();
      case TimedAutomataPackage.SYNC: return createSync();
      case TimedAutomataPackage.SEND: return createSend();
      case TimedAutomataPackage.RECV: return createRecv();
      case TimedAutomataPackage.ASSIGNMENTS: return createAssignments();
      case TimedAutomataPackage.ASSIGN: return createAssign();
      case TimedAutomataPackage.VARIABLE_DECL: return createVariableDecl();
      case TimedAutomataPackage.DECL_ID: return createDeclId();
      case TimedAutomataPackage.INITIALISER: return createInitialiser();
      case TimedAutomataPackage.ARRAY_DECL: return createArrayDecl();
      case TimedAutomataPackage.INT_EXPRESSION: return createIntExpression();
      case TimedAutomataPackage.VAR_ACCESS: return createVarAccess();
      case TimedAutomataPackage.WRAP_BOOL_EXPR: return createWrapBoolExpr();
      case TimedAutomataPackage.CONSTANT: return createConstant();
      case TimedAutomataPackage.BOOLEAN_EXPRESSION: return createBooleanExpression();
      case TimedAutomataPackage.COMPARISON: return createComparison();
      case TimedAutomataPackage.TRUE: return createTrue();
      case TimedAutomataPackage.FALSE: return createFalse();
      case TimedAutomataPackage.BINARY_INT_EXPRESSION: return createBinaryIntExpression();
      case TimedAutomataPackage.BIT_COMPLEMENT: return createBitComplement();
      case TimedAutomataPackage.UNARY_MINUS: return createUnaryMinus();
      case TimedAutomataPackage.OR: return createOr();
      case TimedAutomataPackage.AND: return createAnd();
      case TimedAutomataPackage.NOT: return createNot();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case TimedAutomataPackage.COMPARISON_OPERATORS:
        return createComparisonOperatorsFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case TimedAutomataPackage.COMPARISON_OPERATORS:
        return convertComparisonOperatorsToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XTA createXTA()
  {
    XTAImpl xta = new XTAImpl();
    return xta;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InstantiableInSystem createInstantiableInSystem()
  {
    InstantiableInSystemImpl instantiableInSystem = new InstantiableInSystemImpl();
    return instantiableInSystem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance createInstance()
  {
    InstanceImpl instance = new InstanceImpl();
    return instance;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public fr.lip6.move.timedAutomata.System createSystem()
  {
    SystemImpl system = new SystemImpl();
    return system;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProcDecl createProcDecl()
  {
    ProcDeclImpl procDecl = new ProcDeclImpl();
    return procDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FormalDeclaration createFormalDeclaration()
  {
    FormalDeclarationImpl formalDeclaration = new FormalDeclarationImpl();
    return formalDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeDecl createTypeDecl()
  {
    TypeDeclImpl typeDecl = new TypeDeclImpl();
    return typeDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createType()
  {
    TypeImpl type = new TypeImpl();
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BasicType createBasicType()
  {
    BasicTypeImpl basicType = new BasicTypeImpl();
    return basicType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypedefRef createTypedefRef()
  {
    TypedefRefImpl typedefRef = new TypedefRefImpl();
    return typedefRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChannelDecl createChannelDecl()
  {
    ChannelDeclImpl channelDecl = new ChannelDeclImpl();
    return channelDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChanId createChanId()
  {
    ChanIdImpl chanId = new ChanIdImpl();
    return chanId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChannelType createChannelType()
  {
    ChannelTypeImpl channelType = new ChannelTypeImpl();
    return channelType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerType createIntegerType()
  {
    IntegerTypeImpl integerType = new IntegerTypeImpl();
    return integerType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoolType createBoolType()
  {
    BoolTypeImpl boolType = new BoolTypeImpl();
    return boolType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeType createRangeType()
  {
    RangeTypeImpl rangeType = new RangeTypeImpl();
    return rangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClockType createClockType()
  {
    ClockTypeImpl clockType = new ClockTypeImpl();
    return clockType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProcBody createProcBody()
  {
    ProcBodyImpl procBody = new ProcBodyImpl();
    return procBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl createStateDecl()
  {
    StateDeclImpl stateDecl = new StateDeclImpl();
    return stateDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Transition createTransition()
  {
    TransitionImpl transition = new TransitionImpl();
    return transition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Sync createSync()
  {
    SyncImpl sync = new SyncImpl();
    return sync;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Send createSend()
  {
    SendImpl send = new SendImpl();
    return send;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Recv createRecv()
  {
    RecvImpl recv = new RecvImpl();
    return recv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assignments createAssignments()
  {
    AssignmentsImpl assignments = new AssignmentsImpl();
    return assignments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assign createAssign()
  {
    AssignImpl assign = new AssignImpl();
    return assign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableDecl createVariableDecl()
  {
    VariableDeclImpl variableDecl = new VariableDeclImpl();
    return variableDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DeclId createDeclId()
  {
    DeclIdImpl declId = new DeclIdImpl();
    return declId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Initialiser createInitialiser()
  {
    InitialiserImpl initialiser = new InitialiserImpl();
    return initialiser;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ArrayDecl createArrayDecl()
  {
    ArrayDeclImpl arrayDecl = new ArrayDeclImpl();
    return arrayDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntExpression createIntExpression()
  {
    IntExpressionImpl intExpression = new IntExpressionImpl();
    return intExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VarAccess createVarAccess()
  {
    VarAccessImpl varAccess = new VarAccessImpl();
    return varAccess;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WrapBoolExpr createWrapBoolExpr()
  {
    WrapBoolExprImpl wrapBoolExpr = new WrapBoolExprImpl();
    return wrapBoolExpr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Constant createConstant()
  {
    ConstantImpl constant = new ConstantImpl();
    return constant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanExpression createBooleanExpression()
  {
    BooleanExpressionImpl booleanExpression = new BooleanExpressionImpl();
    return booleanExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Comparison createComparison()
  {
    ComparisonImpl comparison = new ComparisonImpl();
    return comparison;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public True createTrue()
  {
    TrueImpl true_ = new TrueImpl();
    return true_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public False createFalse()
  {
    FalseImpl false_ = new FalseImpl();
    return false_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BinaryIntExpression createBinaryIntExpression()
  {
    BinaryIntExpressionImpl binaryIntExpression = new BinaryIntExpressionImpl();
    return binaryIntExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BitComplement createBitComplement()
  {
    BitComplementImpl bitComplement = new BitComplementImpl();
    return bitComplement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnaryMinus createUnaryMinus()
  {
    UnaryMinusImpl unaryMinus = new UnaryMinusImpl();
    return unaryMinus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Or createOr()
  {
    OrImpl or = new OrImpl();
    return or;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public And createAnd()
  {
    AndImpl and = new AndImpl();
    return and;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Not createNot()
  {
    NotImpl not = new NotImpl();
    return not;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ComparisonOperators createComparisonOperatorsFromString(EDataType eDataType, String initialValue)
  {
    ComparisonOperators result = ComparisonOperators.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertComparisonOperatorsToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimedAutomataPackage getTimedAutomataPackage()
  {
    return (TimedAutomataPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TimedAutomataPackage getPackage()
  {
    return TimedAutomataPackage.eINSTANCE;
  }

} //TimedAutomataFactoryImpl
