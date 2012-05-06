/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Push</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.Push#getList <em>List</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Push#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getPush()
 * @model
 * @generated
 */
public interface Push extends Actions
{
  /**
   * Returns the value of the '<em><b>List</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>List</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>List</em>' reference.
   * @see #setList(List)
   * @see fr.lip6.move.gal.GalPackage#getPush_List()
   * @model
   * @generated
   */
  List getList();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Push#getList <em>List</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>List</em>' reference.
   * @see #getList()
   * @generated
   */
  void setList(List value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(IntExpression)
   * @see fr.lip6.move.gal.GalPackage#getPush_Value()
   * @model containment="true"
   * @generated
   */
  IntExpression getValue();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Push#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(IntExpression value);

} // Push
