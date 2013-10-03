/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.ChannelType#getChans <em>Chans</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ChannelType#isUrgent <em>Urgent</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.ChannelType#isBroadcast <em>Broadcast</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getChannelType()
 * @model
 * @generated
 */
public interface ChannelType extends ChannelDecl
{
  /**
   * Returns the value of the '<em><b>Chans</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.ChanId}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Chans</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Chans</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getChannelType_Chans()
   * @model containment="true"
   * @generated
   */
  EList<ChanId> getChans();

  /**
   * Returns the value of the '<em><b>Urgent</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Urgent</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Urgent</em>' attribute.
   * @see #setUrgent(boolean)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getChannelType_Urgent()
   * @model
   * @generated
   */
  boolean isUrgent();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.ChannelType#isUrgent <em>Urgent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Urgent</em>' attribute.
   * @see #isUrgent()
   * @generated
   */
  void setUrgent(boolean value);

  /**
   * Returns the value of the '<em><b>Broadcast</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Broadcast</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Broadcast</em>' attribute.
   * @see #setBroadcast(boolean)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getChannelType_Broadcast()
   * @model
   * @generated
   */
  boolean isBroadcast();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.ChannelType#isBroadcast <em>Broadcast</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Broadcast</em>' attribute.
   * @see #isBroadcast()
   * @generated
   */
  void setBroadcast(boolean value);

} // ChannelType
