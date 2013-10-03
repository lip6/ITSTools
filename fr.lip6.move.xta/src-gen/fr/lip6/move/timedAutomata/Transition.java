/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Transition#getSrc <em>Src</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Transition#getDest <em>Dest</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Transition#getGuard <em>Guard</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Transition#getSync <em>Sync</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.Transition#getAssign <em>Assign</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject
{
  /**
   * Returns the value of the '<em><b>Src</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Src</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Src</em>' reference.
   * @see #setSrc(StateDecl)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition_Src()
   * @model
   * @generated
   */
  StateDecl getSrc();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Transition#getSrc <em>Src</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Src</em>' reference.
   * @see #getSrc()
   * @generated
   */
  void setSrc(StateDecl value);

  /**
   * Returns the value of the '<em><b>Dest</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dest</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dest</em>' reference.
   * @see #setDest(StateDecl)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition_Dest()
   * @model
   * @generated
   */
  StateDecl getDest();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Transition#getDest <em>Dest</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dest</em>' reference.
   * @see #getDest()
   * @generated
   */
  void setDest(StateDecl value);

  /**
   * Returns the value of the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Guard</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Guard</em>' containment reference.
   * @see #setGuard(BooleanExpression)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition_Guard()
   * @model containment="true"
   * @generated
   */
  BooleanExpression getGuard();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Transition#getGuard <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Guard</em>' containment reference.
   * @see #getGuard()
   * @generated
   */
  void setGuard(BooleanExpression value);

  /**
   * Returns the value of the '<em><b>Sync</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sync</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sync</em>' containment reference.
   * @see #setSync(Sync)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition_Sync()
   * @model containment="true"
   * @generated
   */
  Sync getSync();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Transition#getSync <em>Sync</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sync</em>' containment reference.
   * @see #getSync()
   * @generated
   */
  void setSync(Sync value);

  /**
   * Returns the value of the '<em><b>Assign</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assign</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assign</em>' containment reference.
   * @see #setAssign(Assignments)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getTransition_Assign()
   * @model containment="true"
   * @generated
   */
  Assignments getAssign();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Transition#getAssign <em>Assign</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assign</em>' containment reference.
   * @see #getAssign()
   * @generated
   */
  void setAssign(Assignments value);

} // Transition
