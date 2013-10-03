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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage
 * @generated
 */
public class TimedAutomataSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TimedAutomataPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimedAutomataSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = TimedAutomataPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case TimedAutomataPackage.XTA:
      {
        XTA xta = (XTA)theEObject;
        T result = caseXTA(xta);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.INSTANTIABLE_IN_SYSTEM:
      {
        InstantiableInSystem instantiableInSystem = (InstantiableInSystem)theEObject;
        T result = caseInstantiableInSystem(instantiableInSystem);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.INSTANCE:
      {
        Instance instance = (Instance)theEObject;
        T result = caseInstance(instance);
        if (result == null) result = caseInstantiableInSystem(instance);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.SYSTEM:
      {
        fr.lip6.move.timedAutomata.System system = (fr.lip6.move.timedAutomata.System)theEObject;
        T result = caseSystem(system);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.PROC_DECL:
      {
        ProcDecl procDecl = (ProcDecl)theEObject;
        T result = caseProcDecl(procDecl);
        if (result == null) result = caseInstantiableInSystem(procDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.FORMAL_DECLARATION:
      {
        FormalDeclaration formalDeclaration = (FormalDeclaration)theEObject;
        T result = caseFormalDeclaration(formalDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.PARAMETER:
      {
        Parameter parameter = (Parameter)theEObject;
        T result = caseParameter(parameter);
        if (result == null) result = caseFormalDeclaration(parameter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.TYPE_DECL:
      {
        TypeDecl typeDecl = (TypeDecl)theEObject;
        T result = caseTypeDecl(typeDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.TYPE:
      {
        Type type = (Type)theEObject;
        T result = caseType(type);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.BASIC_TYPE:
      {
        BasicType basicType = (BasicType)theEObject;
        T result = caseBasicType(basicType);
        if (result == null) result = caseType(basicType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.TYPEDEF_REF:
      {
        TypedefRef typedefRef = (TypedefRef)theEObject;
        T result = caseTypedefRef(typedefRef);
        if (result == null) result = caseType(typedefRef);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.CHANNEL_DECL:
      {
        ChannelDecl channelDecl = (ChannelDecl)theEObject;
        T result = caseChannelDecl(channelDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.CHAN_ID:
      {
        ChanId chanId = (ChanId)theEObject;
        T result = caseChanId(chanId);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.CHANNEL_TYPE:
      {
        ChannelType channelType = (ChannelType)theEObject;
        T result = caseChannelType(channelType);
        if (result == null) result = caseChannelDecl(channelType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.INTEGER_TYPE:
      {
        IntegerType integerType = (IntegerType)theEObject;
        T result = caseIntegerType(integerType);
        if (result == null) result = caseBasicType(integerType);
        if (result == null) result = caseType(integerType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.BOOL_TYPE:
      {
        BoolType boolType = (BoolType)theEObject;
        T result = caseBoolType(boolType);
        if (result == null) result = caseBasicType(boolType);
        if (result == null) result = caseType(boolType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.RANGE_TYPE:
      {
        RangeType rangeType = (RangeType)theEObject;
        T result = caseRangeType(rangeType);
        if (result == null) result = caseBasicType(rangeType);
        if (result == null) result = caseType(rangeType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.CLOCK_TYPE:
      {
        ClockType clockType = (ClockType)theEObject;
        T result = caseClockType(clockType);
        if (result == null) result = caseBasicType(clockType);
        if (result == null) result = caseType(clockType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.PROC_BODY:
      {
        ProcBody procBody = (ProcBody)theEObject;
        T result = caseProcBody(procBody);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.STATE_DECL:
      {
        StateDecl stateDecl = (StateDecl)theEObject;
        T result = caseStateDecl(stateDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.TRANSITION:
      {
        Transition transition = (Transition)theEObject;
        T result = caseTransition(transition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.SYNC:
      {
        Sync sync = (Sync)theEObject;
        T result = caseSync(sync);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.SEND:
      {
        Send send = (Send)theEObject;
        T result = caseSend(send);
        if (result == null) result = caseSync(send);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.RECV:
      {
        Recv recv = (Recv)theEObject;
        T result = caseRecv(recv);
        if (result == null) result = caseSync(recv);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.ASSIGNMENTS:
      {
        Assignments assignments = (Assignments)theEObject;
        T result = caseAssignments(assignments);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.ASSIGN:
      {
        Assign assign = (Assign)theEObject;
        T result = caseAssign(assign);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.VARIABLE_DECL:
      {
        VariableDecl variableDecl = (VariableDecl)theEObject;
        T result = caseVariableDecl(variableDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.DECL_ID:
      {
        DeclId declId = (DeclId)theEObject;
        T result = caseDeclId(declId);
        if (result == null) result = caseFormalDeclaration(declId);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.INITIALISER:
      {
        Initialiser initialiser = (Initialiser)theEObject;
        T result = caseInitialiser(initialiser);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.ARRAY_DECL:
      {
        ArrayDecl arrayDecl = (ArrayDecl)theEObject;
        T result = caseArrayDecl(arrayDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.INT_EXPRESSION:
      {
        IntExpression intExpression = (IntExpression)theEObject;
        T result = caseIntExpression(intExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.VAR_ACCESS:
      {
        VarAccess varAccess = (VarAccess)theEObject;
        T result = caseVarAccess(varAccess);
        if (result == null) result = caseIntExpression(varAccess);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.WRAP_BOOL_EXPR:
      {
        WrapBoolExpr wrapBoolExpr = (WrapBoolExpr)theEObject;
        T result = caseWrapBoolExpr(wrapBoolExpr);
        if (result == null) result = caseIntExpression(wrapBoolExpr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.CONSTANT:
      {
        Constant constant = (Constant)theEObject;
        T result = caseConstant(constant);
        if (result == null) result = caseIntExpression(constant);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.BOOLEAN_EXPRESSION:
      {
        BooleanExpression booleanExpression = (BooleanExpression)theEObject;
        T result = caseBooleanExpression(booleanExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.COMPARISON:
      {
        Comparison comparison = (Comparison)theEObject;
        T result = caseComparison(comparison);
        if (result == null) result = caseBooleanExpression(comparison);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.TRUE:
      {
        True true_ = (True)theEObject;
        T result = caseTrue(true_);
        if (result == null) result = caseBooleanExpression(true_);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.FALSE:
      {
        False false_ = (False)theEObject;
        T result = caseFalse(false_);
        if (result == null) result = caseBooleanExpression(false_);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.BINARY_INT_EXPRESSION:
      {
        BinaryIntExpression binaryIntExpression = (BinaryIntExpression)theEObject;
        T result = caseBinaryIntExpression(binaryIntExpression);
        if (result == null) result = caseIntExpression(binaryIntExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.BIT_COMPLEMENT:
      {
        BitComplement bitComplement = (BitComplement)theEObject;
        T result = caseBitComplement(bitComplement);
        if (result == null) result = caseIntExpression(bitComplement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.UNARY_MINUS:
      {
        UnaryMinus unaryMinus = (UnaryMinus)theEObject;
        T result = caseUnaryMinus(unaryMinus);
        if (result == null) result = caseIntExpression(unaryMinus);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.OR:
      {
        Or or = (Or)theEObject;
        T result = caseOr(or);
        if (result == null) result = caseBooleanExpression(or);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.AND:
      {
        And and = (And)theEObject;
        T result = caseAnd(and);
        if (result == null) result = caseBooleanExpression(and);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case TimedAutomataPackage.NOT:
      {
        Not not = (Not)theEObject;
        T result = caseNot(not);
        if (result == null) result = caseBooleanExpression(not);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>XTA</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>XTA</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseXTA(XTA object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Instantiable In System</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Instantiable In System</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInstantiableInSystem(InstantiableInSystem object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Instance</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Instance</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInstance(Instance object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>System</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>System</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSystem(fr.lip6.move.timedAutomata.System object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Proc Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Proc Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProcDecl(ProcDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Formal Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Formal Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFormalDeclaration(FormalDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameter(Parameter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeDecl(TypeDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseType(Type object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Basic Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Basic Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasicType(BasicType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Typedef Ref</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Typedef Ref</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypedefRef(TypedefRef object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Channel Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Channel Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseChannelDecl(ChannelDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Chan Id</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Chan Id</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseChanId(ChanId object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Channel Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Channel Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseChannelType(ChannelType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Integer Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Integer Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIntegerType(IntegerType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Bool Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Bool Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBoolType(BoolType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Range Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Range Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRangeType(RangeType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Clock Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Clock Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClockType(ClockType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Proc Body</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Proc Body</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProcBody(ProcBody object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>State Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>State Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStateDecl(StateDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTransition(Transition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Sync</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Sync</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSync(Sync object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Send</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Send</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSend(Send object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Recv</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Recv</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRecv(Recv object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Assignments</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Assignments</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAssignments(Assignments object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Assign</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Assign</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAssign(Assign object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariableDecl(VariableDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Decl Id</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Decl Id</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDeclId(DeclId object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Initialiser</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Initialiser</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInitialiser(Initialiser object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Array Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Array Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseArrayDecl(ArrayDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Int Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Int Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIntExpression(IntExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Var Access</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Var Access</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVarAccess(VarAccess object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Wrap Bool Expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Wrap Bool Expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWrapBoolExpr(WrapBoolExpr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstant(Constant object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBooleanExpression(BooleanExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Comparison</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Comparison</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComparison(Comparison object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>True</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>True</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrue(True object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>False</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>False</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFalse(False object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Binary Int Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Binary Int Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBinaryIntExpression(BinaryIntExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Bit Complement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Bit Complement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBitComplement(BitComplement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unary Minus</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unary Minus</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnaryMinus(UnaryMinus object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Or</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Or</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOr(Or object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>And</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>And</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnd(And object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Not</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Not</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNot(Not object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //TimedAutomataSwitch
