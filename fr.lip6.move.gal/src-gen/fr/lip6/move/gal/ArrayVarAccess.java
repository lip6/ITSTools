/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Var Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.ArrayVarAccess#getPrefix <em>Prefix</em>}</li>
 *   <li>{@link fr.lip6.move.gal.ArrayVarAccess#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getArrayVarAccess()
 * @model
 * @generated
 */
public interface ArrayVarAccess extends VarAccess
{
  /**
   * Returns the value of the '<em><b>Prefix</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Prefix</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prefix</em>' reference.
   * @see #setPrefix(ArrayPrefix)
   * @see fr.lip6.move.gal.GalPackage#getArrayVarAccess_Prefix()
   * @model
   * @generated
   */
  ArrayPrefix getPrefix();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.ArrayVarAccess#getPrefix <em>Prefix</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Prefix</em>' reference.
   * @see #getPrefix()
   * @generated
   */
  void setPrefix(ArrayPrefix value);

  /**
   * Returns the value of the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Index</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Index</em>' containment reference.
   * @see #setIndex(IntExpression)
   * @see fr.lip6.move.gal.GalPackage#getArrayVarAccess_Index()
   * @model containment="true"
   * @generated
   */
  IntExpression getIndex();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.ArrayVarAccess#getIndex <em>Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Index</em>' containment reference.
   * @see #getIndex()
   * @generated
   */
  void setIndex(IntExpression value);

} // ArrayVarAccess
