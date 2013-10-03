/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sync</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Sync#getChannel <em>Channel</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getSync()
 * @model
 * @generated
 */
public interface Sync extends EObject
{
  /**
   * Returns the value of the '<em><b>Channel</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Channel</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Channel</em>' reference.
   * @see #setChannel(ChanId)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getSync_Channel()
   * @model
   * @generated
   */
  ChanId getChannel();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.Sync#getChannel <em>Channel</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Channel</em>' reference.
   * @see #getChannel()
   * @generated
   */
  void setChannel(ChanId value);

} // Sync
