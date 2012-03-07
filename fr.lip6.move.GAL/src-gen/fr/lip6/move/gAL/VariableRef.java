/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gAL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gAL.VariableRef#getVar <em>Var</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gAL.GALPackage#getVariableRef()
 * @model
 * @generated
 */
public interface VariableRef extends Expression
{
  /**
   * Returns the value of the '<em><b>Var</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Var</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var</em>' reference.
   * @see #setVar(Variable)
   * @see fr.lip6.move.gAL.GALPackage#getVariableRef_Var()
   * @model
   * @generated
   */
  Variable getVar();

  /**
   * Sets the value of the '{@link fr.lip6.move.gAL.VariableRef#getVar <em>Var</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var</em>' reference.
   * @see #getVar()
   * @generated
   */
  void setVar(Variable value);

} // VariableRef
