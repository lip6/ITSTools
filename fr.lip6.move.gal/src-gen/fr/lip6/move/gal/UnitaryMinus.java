/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unitary Minus</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.UnitaryMinus#getVal <em>Val</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getUnitaryMinus()
 * @model
 * @generated
 */
public interface UnitaryMinus extends IntExpression
{
  /**
   * Returns the value of the '<em><b>Val</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Val</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Val</em>' containment reference.
   * @see #setVal(IntExpression)
   * @see fr.lip6.move.gal.GalPackage#getUnitaryMinus_Val()
   * @model containment="true"
   * @generated
   */
  IntExpression getVal();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.UnitaryMinus#getVal <em>Val</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Val</em>' containment reference.
   * @see #getVal()
   * @generated
   */
  void setVal(IntExpression value);

} // UnitaryMinus
