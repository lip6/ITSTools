/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Int Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getLeft <em>Left</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getOp <em>Op</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getBinaryIntExpression()
 * @model
 * @generated
 */
public interface BinaryIntExpression extends IntExpression
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
   * @see #setLeft(IntExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getBinaryIntExpression_Left()
   * @model containment="true"
   * @generated
   */
  IntExpression getLeft();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(IntExpression value);

  /**
   * Returns the value of the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Op</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Op</em>' attribute.
   * @see #setOp(String)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getBinaryIntExpression_Op()
   * @model
   * @generated
   */
  String getOp();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getOp <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Op</em>' attribute.
   * @see #getOp()
   * @generated
   */
  void setOp(String value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(IntExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getBinaryIntExpression_Right()
   * @model containment="true"
   * @generated
   */
  IntExpression getRight();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.BinaryIntExpression#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(IntExpression value);

} // BinaryIntExpression
