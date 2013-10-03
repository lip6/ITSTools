/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.IntegerType#isConst <em>Const</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getIntegerType()
 * @model
 * @generated
 */
public interface IntegerType extends BasicType
{
  /**
   * Returns the value of the '<em><b>Const</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Const</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Const</em>' attribute.
   * @see #setConst(boolean)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getIntegerType_Const()
   * @model
   * @generated
   */
  boolean isConst();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.IntegerType#isConst <em>Const</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Const</em>' attribute.
   * @see #isConst()
   * @generated
   */
  void setConst(boolean value);

} // IntegerType
