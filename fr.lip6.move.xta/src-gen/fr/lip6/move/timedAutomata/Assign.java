/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assign</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Assign#getLhs <em>Lhs</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Assign#getRhs <em>Rhs</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAssign()
 * @model
 * @generated
 */
public interface Assign extends EObject
{
  /**
   * Returns the value of the '<em><b>Lhs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lhs</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lhs</em>' containment reference.
   * @see #setLhs(VarAccess)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAssign_Lhs()
   * @model containment="true"
   * @generated
   */
  VarAccess getLhs();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Assign#getLhs <em>Lhs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lhs</em>' containment reference.
   * @see #getLhs()
   * @generated
   */
  void setLhs(VarAccess value);

  /**
   * Returns the value of the '<em><b>Rhs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rhs</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rhs</em>' containment reference.
   * @see #setRhs(IntExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAssign_Rhs()
   * @model containment="true"
   * @generated
   */
  IntExpression getRhs();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Assign#getRhs <em>Rhs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Rhs</em>' containment reference.
   * @see #getRhs()
   * @generated
   */
  void setRhs(IntExpression value);

} // Assign
