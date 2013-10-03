/**
 */
package fr.lip6.move.timedAutomata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Var Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.VarAccess#getRef <em>Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getVarAccess()
 * @model
 * @generated
 */
public interface VarAccess extends IntExpression
{
  /**
   * Returns the value of the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ref</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ref</em>' reference.
   * @see #setRef(FormalDeclaration)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getVarAccess_Ref()
   * @model
   * @generated
   */
  FormalDeclaration getRef();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.VarAccess#getRef <em>Ref</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ref</em>' reference.
   * @see #getRef()
   * @generated
   */
  void setRef(FormalDeclaration value);

} // VarAccess
