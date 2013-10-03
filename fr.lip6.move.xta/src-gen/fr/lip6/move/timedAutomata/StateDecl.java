/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.StateDecl#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.StateDecl#getInvariant <em>Invariant</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getStateDecl()
 * @model
 * @generated
 */
public interface StateDecl extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getStateDecl_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.StateDecl#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Invariant</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Invariant</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invariant</em>' containment reference.
   * @see #setInvariant(BooleanExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getStateDecl_Invariant()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getInvariant();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.StateDecl#getInvariant <em>Invariant</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Invariant</em>' containment reference.
   * @see #getInvariant()
   * @generated
   */
  void setInvariant(BooleanExpression value);

} // StateDecl
