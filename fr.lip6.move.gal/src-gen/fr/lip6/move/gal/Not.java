/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Not</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.Not#getVal <em>Val</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getNot()
 * @model
 * @generated
 */
public interface Not extends BooleanExpression
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
   * @see #setVal(BooleanExpression)
   * @see fr.lip6.move.gal.GalPackage#getNot_Val()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getVal();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Not#getVal <em>Val</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Val</em>' containment reference.
   * @see #getVal()
   * @generated
   */
  void setVal(BooleanExpression value);

} // Not
