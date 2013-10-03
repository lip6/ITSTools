/**
 */
package fr.lip6.move.timedAutomata.util;

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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage
 * @generated
 */
public class TimedAutomataAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TimedAutomataPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimedAutomataAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = TimedAutomataPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TimedAutomataSwitch<Adapter> modelSwitch =
    new TimedAutomataSwitch<Adapter>()
    {
      @Override
      public Adapter caseXTA(XTA object)
      {
        return createXTAAdapter();
      }
      @Override
      public Adapter caseInstantiableInSystem(InstantiableInSystem object)
      {
        return createInstantiableInSystemAdapter();
      }
      @Override
      public Adapter caseInstance(Instance object)
      {
        return createInstanceAdapter();
      }
      @Override
      public Adapter caseSystem(fr.lip6.move.timedAutomata.System object)
      {
        return createSystemAdapter();
      }
      @Override
      public Adapter caseProcDecl(ProcDecl object)
      {
        return createProcDeclAdapter();
      }
      @Override
      public Adapter caseFormalDeclaration(FormalDeclaration object)
      {
        return createFormalDeclarationAdapter();
      }
      @Override
      public Adapter caseParameter(Parameter object)
      {
        return createParameterAdapter();
      }
      @Override
      public Adapter caseTypeDecl(TypeDecl object)
      {
        return createTypeDeclAdapter();
      }
      @Override
      public Adapter caseType(Type object)
      {
        return createTypeAdapter();
      }
      @Override
      public Adapter caseBasicType(BasicType object)
      {
        return createBasicTypeAdapter();
      }
      @Override
      public Adapter caseTypedefRef(TypedefRef object)
      {
        return createTypedefRefAdapter();
      }
      @Override
      public Adapter caseChannelDecl(ChannelDecl object)
      {
        return createChannelDeclAdapter();
      }
      @Override
      public Adapter caseChanId(ChanId object)
      {
        return createChanIdAdapter();
      }
      @Override
      public Adapter caseChannelType(ChannelType object)
      {
        return createChannelTypeAdapter();
      }
      @Override
      public Adapter caseIntegerType(IntegerType object)
      {
        return createIntegerTypeAdapter();
      }
      @Override
      public Adapter caseBoolType(BoolType object)
      {
        return createBoolTypeAdapter();
      }
      @Override
      public Adapter caseRangeType(RangeType object)
      {
        return createRangeTypeAdapter();
      }
      @Override
      public Adapter caseClockType(ClockType object)
      {
        return createClockTypeAdapter();
      }
      @Override
      public Adapter caseProcBody(ProcBody object)
      {
        return createProcBodyAdapter();
      }
      @Override
      public Adapter caseStateDecl(StateDecl object)
      {
        return createStateDeclAdapter();
      }
      @Override
      public Adapter caseTransition(Transition object)
      {
        return createTransitionAdapter();
      }
      @Override
      public Adapter caseSync(Sync object)
      {
        return createSyncAdapter();
      }
      @Override
      public Adapter caseSend(Send object)
      {
        return createSendAdapter();
      }
      @Override
      public Adapter caseRecv(Recv object)
      {
        return createRecvAdapter();
      }
      @Override
      public Adapter caseAssignments(Assignments object)
      {
        return createAssignmentsAdapter();
      }
      @Override
      public Adapter caseAssign(Assign object)
      {
        return createAssignAdapter();
      }
      @Override
      public Adapter caseVariableDecl(VariableDecl object)
      {
        return createVariableDeclAdapter();
      }
      @Override
      public Adapter caseDeclId(DeclId object)
      {
        return createDeclIdAdapter();
      }
      @Override
      public Adapter caseInitialiser(Initialiser object)
      {
        return createInitialiserAdapter();
      }
      @Override
      public Adapter caseArrayDecl(ArrayDecl object)
      {
        return createArrayDeclAdapter();
      }
      @Override
      public Adapter caseIntExpression(IntExpression object)
      {
        return createIntExpressionAdapter();
      }
      @Override
      public Adapter caseVarAccess(VarAccess object)
      {
        return createVarAccessAdapter();
      }
      @Override
      public Adapter caseWrapBoolExpr(WrapBoolExpr object)
      {
        return createWrapBoolExprAdapter();
      }
      @Override
      public Adapter caseConstant(Constant object)
      {
        return createConstantAdapter();
      }
      @Override
      public Adapter caseBooleanExpression(BooleanExpression object)
      {
        return createBooleanExpressionAdapter();
      }
      @Override
      public Adapter caseComparison(Comparison object)
      {
        return createComparisonAdapter();
      }
      @Override
      public Adapter caseTrue(True object)
      {
        return createTrueAdapter();
      }
      @Override
      public Adapter caseFalse(False object)
      {
        return createFalseAdapter();
      }
      @Override
      public Adapter caseBinaryIntExpression(BinaryIntExpression object)
      {
        return createBinaryIntExpressionAdapter();
      }
      @Override
      public Adapter caseBitComplement(BitComplement object)
      {
        return createBitComplementAdapter();
      }
      @Override
      public Adapter caseUnaryMinus(UnaryMinus object)
      {
        return createUnaryMinusAdapter();
      }
      @Override
      public Adapter caseOr(Or object)
      {
        return createOrAdapter();
      }
      @Override
      public Adapter caseAnd(And object)
      {
        return createAndAdapter();
      }
      @Override
      public Adapter caseNot(Not object)
      {
        return createNotAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.XTA <em>XTA</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.XTA
   * @generated
   */
  public Adapter createXTAAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.InstantiableInSystem <em>Instantiable In System</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.InstantiableInSystem
   * @generated
   */
  public Adapter createInstantiableInSystemAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Instance <em>Instance</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Instance
   * @generated
   */
  public Adapter createInstanceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.System <em>System</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.System
   * @generated
   */
  public Adapter createSystemAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ProcDecl <em>Proc Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ProcDecl
   * @generated
   */
  public Adapter createProcDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.FormalDeclaration <em>Formal Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.FormalDeclaration
   * @generated
   */
  public Adapter createFormalDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Parameter
   * @generated
   */
  public Adapter createParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.TypeDecl <em>Type Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.TypeDecl
   * @generated
   */
  public Adapter createTypeDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Type
   * @generated
   */
  public Adapter createTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.BasicType <em>Basic Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.BasicType
   * @generated
   */
  public Adapter createBasicTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.TypedefRef <em>Typedef Ref</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.TypedefRef
   * @generated
   */
  public Adapter createTypedefRefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ChannelDecl <em>Channel Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ChannelDecl
   * @generated
   */
  public Adapter createChannelDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ChanId <em>Chan Id</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ChanId
   * @generated
   */
  public Adapter createChanIdAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ChannelType <em>Channel Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ChannelType
   * @generated
   */
  public Adapter createChannelTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.IntegerType <em>Integer Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.IntegerType
   * @generated
   */
  public Adapter createIntegerTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.BoolType <em>Bool Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.BoolType
   * @generated
   */
  public Adapter createBoolTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.RangeType
   * @generated
   */
  public Adapter createRangeTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ClockType <em>Clock Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ClockType
   * @generated
   */
  public Adapter createClockTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ProcBody <em>Proc Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ProcBody
   * @generated
   */
  public Adapter createProcBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.StateDecl <em>State Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.StateDecl
   * @generated
   */
  public Adapter createStateDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Transition <em>Transition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Transition
   * @generated
   */
  public Adapter createTransitionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Sync <em>Sync</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Sync
   * @generated
   */
  public Adapter createSyncAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Send <em>Send</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Send
   * @generated
   */
  public Adapter createSendAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Recv <em>Recv</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Recv
   * @generated
   */
  public Adapter createRecvAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Assignments <em>Assignments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Assignments
   * @generated
   */
  public Adapter createAssignmentsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Assign <em>Assign</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Assign
   * @generated
   */
  public Adapter createAssignAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.VariableDecl <em>Variable Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.VariableDecl
   * @generated
   */
  public Adapter createVariableDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.DeclId <em>Decl Id</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.DeclId
   * @generated
   */
  public Adapter createDeclIdAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Initialiser <em>Initialiser</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Initialiser
   * @generated
   */
  public Adapter createInitialiserAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.ArrayDecl <em>Array Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.ArrayDecl
   * @generated
   */
  public Adapter createArrayDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.IntExpression <em>Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.IntExpression
   * @generated
   */
  public Adapter createIntExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.VarAccess <em>Var Access</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.VarAccess
   * @generated
   */
  public Adapter createVarAccessAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.WrapBoolExpr <em>Wrap Bool Expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.WrapBoolExpr
   * @generated
   */
  public Adapter createWrapBoolExprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Constant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Constant
   * @generated
   */
  public Adapter createConstantAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.BooleanExpression <em>Boolean Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.BooleanExpression
   * @generated
   */
  public Adapter createBooleanExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Comparison <em>Comparison</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Comparison
   * @generated
   */
  public Adapter createComparisonAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.True <em>True</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.True
   * @generated
   */
  public Adapter createTrueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.False <em>False</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.False
   * @generated
   */
  public Adapter createFalseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.BinaryIntExpression <em>Binary Int Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.BinaryIntExpression
   * @generated
   */
  public Adapter createBinaryIntExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.BitComplement <em>Bit Complement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.BitComplement
   * @generated
   */
  public Adapter createBitComplementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.UnaryMinus <em>Unary Minus</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.UnaryMinus
   * @generated
   */
  public Adapter createUnaryMinusAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Or <em>Or</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Or
   * @generated
   */
  public Adapter createOrAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.And <em>And</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.And
   * @generated
   */
  public Adapter createAndAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.lip6.move.timedAutomata.Not <em>Not</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.lip6.move.timedAutomata.Not
   * @generated
   */
  public Adapter createNotAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //TimedAutomataAdapterFactory
