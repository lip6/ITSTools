/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.BooleanExpression#getEmpty <em>Empty</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getBooleanExpression()
 * @model
 * @generated
 */
public interface BooleanExpression extends EObject
{
  /**
   * Returns the value of the '<em><b>Empty</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Empty</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Empty</em>' attribute.
   * @see #setEmpty(String)
   * @see fr.lip6.move.gal.GalPackage#getBooleanExpression_Empty()
   * @model
   * @generated
   */
  String getEmpty();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.BooleanExpression#getEmpty <em>Empty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Empty</em>' attribute.
   * @see #getEmpty()
   * @generated
   */
  void setEmpty(String value);

} // BooleanExpression
