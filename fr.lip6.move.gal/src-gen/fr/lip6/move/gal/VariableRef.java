/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.VariableRef#getReferencedVar <em>Referenced Var</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getVariableRef()
 * @model
 * @generated
 */
public interface VariableRef extends VarAccess
{
  /**
   * Returns the value of the '<em><b>Referenced Var</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Referenced Var</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Referenced Var</em>' reference.
   * @see #setReferencedVar(Variable)
   * @see fr.lip6.move.gal.GalPackage#getVariableRef_ReferencedVar()
   * @model
   * @generated
   */
  Variable getReferencedVar();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.VariableRef#getReferencedVar <em>Referenced Var</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Referenced Var</em>' reference.
   * @see #getReferencedVar()
   * @generated
   */
  void setReferencedVar(Variable value);

} // VariableRef
