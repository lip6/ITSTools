/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.VariableDecl#getType <em>Type</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.VariableDecl#getDeclid <em>Declid</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getVariableDecl()
 * @model
 * @generated
 */
public interface VariableDecl extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(BasicType)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getVariableDecl_Type()
   * @model containment="true"
   * @generated
   */
  BasicType getType();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.VariableDecl#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(BasicType value);

  /**
   * Returns the value of the '<em><b>Declid</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.DeclId}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Declid</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Declid</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getVariableDecl_Declid()
   * @model containment="true"
   * @generated
   */
  EList<DeclId> getDeclid();

} // VariableDecl
