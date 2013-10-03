/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Not</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Not#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getNot()
 * @model
 * @generated
 */
public interface Not extends BooleanExpression
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(BooleanExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getNot_Value()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getValue();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Not#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(BooleanExpression value);

} // Not
