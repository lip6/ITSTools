/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proc Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getTypes <em>Types</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getStates <em>States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getCommitStates <em>Commit States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getUrgentStates <em>Urgent States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getInitState <em>Init State</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ProcBody#getTransitions <em>Transitions</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody()
 * @model
 * @generated
 */
public interface ProcBody extends EObject
{
  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.VariableDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_Variables()
   * @model containment="true"
   * @generated
   */
  EList<VariableDecl> getVariables();

  /**
   * Returns the value of the '<em><b>Types</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.TypeDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Types</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_Types()
   * @model containment="true"
   * @generated
   */
  EList<TypeDecl> getTypes();

  /**
   * Returns the value of the '<em><b>States</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.StateDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>States</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>States</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_States()
   * @model containment="true"
   * @generated
   */
  EList<StateDecl> getStates();

  /**
   * Returns the value of the '<em><b>Commit States</b></em>' reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.StateDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Commit States</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Commit States</em>' reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_CommitStates()
   * @model
   * @generated
   */
  EList<StateDecl> getCommitStates();

  /**
   * Returns the value of the '<em><b>Urgent States</b></em>' reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.StateDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Urgent States</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Urgent States</em>' reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_UrgentStates()
   * @model
   * @generated
   */
  EList<StateDecl> getUrgentStates();

  /**
   * Returns the value of the '<em><b>Init State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Init State</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Init State</em>' reference.
   * @see #setInitState(StateDecl)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_InitState()
   * @model
   * @generated
   */
  StateDecl getInitState();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.ProcBody#getInitState <em>Init State</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Init State</em>' reference.
   * @see #getInitState()
   * @generated
   */
  void setInitState(StateDecl value);

  /**
   * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.Transition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transitions</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getProcBody_Transitions()
   * @model containment="true"
   * @generated
   */
  EList<Transition> getTransitions();

} // ProcBody
