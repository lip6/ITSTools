/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>And</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.And#getLeft <em>Left</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.And#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAnd()
 * @model
 * @generated
 */
public interface And extends BooleanExpression
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(BooleanExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAnd_Left()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getLeft();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.And#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(BooleanExpression value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(BooleanExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAnd_Right()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getRight();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.And#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(BooleanExpression value);

} // And
