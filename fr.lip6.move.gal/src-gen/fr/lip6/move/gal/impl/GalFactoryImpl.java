/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal.impl;

import fr.lip6.move.gal.Addition;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constante;
import fr.lip6.move.gal.Division;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Modulo;
import fr.lip6.move.gal.Multiplication;
import fr.lip6.move.gal.Power;
import fr.lip6.move.gal.Subtraction;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.UnitaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

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
public class GalFactoryImpl extends EFactoryImpl implements GalFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static GalFactory init()
  {
    try
    {
      GalFactory theGalFactory = (GalFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.lip6.fr/move/Gal"); 
      if (theGalFactory != null)
      {
        return theGalFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new GalFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GalFactoryImpl()
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
      case GalPackage.SYSTEM: return createSystem();
      case GalPackage.VARIABLE: return createVariable();
      case GalPackage.TRANSITION: return createTransition();
      case GalPackage.ASSIGNMENT: return createAssignment();
      case GalPackage.INT_EXPRESSION: return createIntExpression();
      case GalPackage.CONSTANTE: return createConstante();
      case GalPackage.VARIABLE_REF: return createVariableRef();
      case GalPackage.BOOLEAN_EXPRESSION: return createBooleanExpression();
      case GalPackage.ADDITION: return createAddition();
      case GalPackage.SUBTRACTION: return createSubtraction();
      case GalPackage.MULTIPLICATION: return createMultiplication();
      case GalPackage.DIVISION: return createDivision();
      case GalPackage.MODULO: return createModulo();
      case GalPackage.UNITARY_MINUS: return createUnitaryMinus();
      case GalPackage.POWER: return createPower();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public fr.lip6.move.gal.System createSystem()
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
  public Subtraction createSubtraction()
  {
    SubtractionImpl subtraction = new SubtractionImpl();
    return subtraction;
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
  public Division createDivision()
  {
    DivisionImpl division = new DivisionImpl();
    return division;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Modulo createModulo()
  {
    ModuloImpl modulo = new ModuloImpl();
    return modulo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnitaryMinus createUnitaryMinus()
  {
    UnitaryMinusImpl unitaryMinus = new UnitaryMinusImpl();
    return unitaryMinus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Power createPower()
  {
    PowerImpl power = new PowerImpl();
    return power;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GalPackage getGalPackage()
  {
    return (GalPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static GalPackage getPackage()
  {
    return GalPackage.eINSTANCE;
  }

} //GalFactoryImpl
