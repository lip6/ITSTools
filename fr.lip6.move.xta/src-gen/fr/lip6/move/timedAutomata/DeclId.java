/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Decl Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.DeclId#getArrays <em>Arrays</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.DeclId#getInit <em>Init</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getDeclId()
 * @model
 * @generated
 */
public interface DeclId extends FormalDeclaration
{
  /**
   * Returns the value of the '<em><b>Arrays</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.ArrayDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arrays</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arrays</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getDeclId_Arrays()
   * @model containment="true"
   * @generated
   */
  EList<ArrayDecl> getArrays();

  /**
   * Returns the value of the '<em><b>Init</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Init</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Init</em>' containment reference.
   * @see #setInit(Initialiser)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getDeclId_Init()
   * @model containment="true"
   * @generated
   */
  Initialiser getInit();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.DeclId#getInit <em>Init</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Init</em>' containment reference.
   * @see #getInit()
   * @generated
   */
  void setInit(Initialiser value);

} // DeclId
