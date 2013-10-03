/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initialiser</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Initialiser#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getInitialiser()
 * @model
 * @generated
 */
public interface Initialiser extends EObject
{
  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(IntExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getInitialiser_Expr()
   * @model containment="true"
   * @generated
   */
  IntExpression getExpr();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Initialiser#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(IntExpression value);

} // Initialiser
