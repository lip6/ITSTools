/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.RangeType#isConst <em>Const</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.RangeType#getMin <em>Min</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.RangeType#getMax <em>Max</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getRangeType()
 * @model
 * @generated
 */
public interface RangeType extends BasicType
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
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getRangeType_Const()
   * @model
   * @generated
   */
  boolean isConst();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.RangeType#isConst <em>Const</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Const</em>' attribute.
   * @see #isConst()
   * @generated
   */
  void setConst(boolean value);

  /**
   * Returns the value of the '<em><b>Min</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Min</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Min</em>' attribute.
   * @see #setMin(int)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getRangeType_Min()
   * @model
   * @generated
   */
  int getMin();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.RangeType#getMin <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Min</em>' attribute.
   * @see #getMin()
   * @generated
   */
  void setMin(int value);

  /**
   * Returns the value of the '<em><b>Max</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Max</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max</em>' attribute.
   * @see #setMax(int)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getRangeType_Max()
   * @model
   * @generated
   */
  int getMax();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.RangeType#getMax <em>Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max</em>' attribute.
   * @see #getMax()
   * @generated
   */
  void setMax(int value);

} // RangeType
