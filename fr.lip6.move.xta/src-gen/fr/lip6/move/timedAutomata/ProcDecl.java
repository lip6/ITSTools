/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proc Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcDecl#getParams <em>Params</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcDecl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcDecl()
 * @model
 * @generated
 */
public interface ProcDecl extends InstantiableInSystem
{
  /**
   * Returns the value of the '<em><b>Params</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.Parameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Params</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcDecl_Params()
   * @model containment="true"
   * @generated
   */
  EList<Parameter> getParams();

  /**
   * Returns the value of the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body</em>' containment reference.
   * @see #setBody(ProcBody)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcDecl_Body()
   * @model containment="true"
   * @generated
   */
  ProcBody getBody();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.ProcDecl#getBody <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body</em>' containment reference.
   * @see #getBody()
   * @generated
   */
  void setBody(ProcBody value);

} // ProcDecl
