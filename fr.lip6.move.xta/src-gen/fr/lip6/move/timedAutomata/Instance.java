/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Instance#getType <em>Type</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Instance#getArgs <em>Args</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getInstance()
 * @model
 * @generated
 */
public interface Instance extends InstantiableInSystem
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(ProcDecl)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getInstance_Type()
   * @model
   * @generated
   */
  ProcDecl getType();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Instance#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(ProcDecl value);

  /**
   * Returns the value of the '<em><b>Args</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.IntExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Args</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Args</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getInstance_Args()
   * @model containment="true"
   * @generated
   */
  EList<IntExpression> getArgs();

} // Instance
