/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gAL.impl;

import fr.lip6.move.gAL.Addition;
import fr.lip6.move.gAL.Assignment;
import fr.lip6.move.gAL.Constante;
import fr.lip6.move.gAL.Expression;
import fr.lip6.move.gAL.GALFactory;
import fr.lip6.move.gAL.GALPackage;
import fr.lip6.move.gAL.Multiplication;
import fr.lip6.move.gAL.Transition;
import fr.lip6.move.gAL.Variable;
import fr.lip6.move.gAL.VariableRef;

import org.eclipse.emf.ecore.EClass;
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
public class GALFactoryImpl extends EFactoryImpl implements GALFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static GALFactory init()
  {
    try
    {
      GALFactory theGALFactory = (GALFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.lip6.fr/move/GAL"); 
      if (theGALFactory != null)
      {
        return theGALFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new GALFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GALFactoryImpl()
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
      case GALPackage.SYSTEM: return createSystem();
      case GALPackage.VARIABLE: return createVariable();
      case GALPackage.TRANSITION: return createTransition();
      case GALPackage.CONSTANTE: return createConstante();
      case GALPackage.EXPRESSION: return createExpression();
      case GALPackage.VARIABLE_REF: return createVariableRef();
      case GALPackage.ASSIGNMENT: return createAssignment();
      case GALPackage.MULTIPLICATION: return createMultiplication();
      case GALPackage.ADDITION: return createAddition();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public fr.lip6.move.gAL.System createSystem()
  {
    SystemImpl system = new SystemImpl();
    return system;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable createVariable()
  {
    VariableImpl variable = new VariableImpl();
    return variable;
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
  public Constante createConstante()
  {
    ConstanteImpl constante = new ConstanteImpl();
    return constante;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableRef createVariableRef()
  {
    VariableRefImpl variableRef = new VariableRefImpl();
    return variableRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assignment createAssignment()
  {
    AssignmentImpl assignment = new AssignmentImpl();
    return assignment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Multiplication createMultiplication()
  {
    MultiplicationImpl multiplication = new MultiplicationImpl();
    return multiplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Addition createAddition()
  {
    AdditionImpl addition = new AdditionImpl();
    return addition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GALPackage getGALPackage()
  {
    return (GALPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static GALPackage getPackage()
  {
    return GALPackage.eINSTANCE;
  }

} //GALFactoryImpl
