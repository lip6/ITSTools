/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.ChanId;
import fr.lip6.move.timedAutomata.ChannelType;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Channel Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ChannelTypeImpl#getChans <em>Chans</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ChannelTypeImpl#isUrgent <em>Urgent</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ChannelTypeImpl#isBroadcast <em>Broadcast</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelTypeImpl extends ChannelDeclImpl implements ChannelType
{
  /**
   * The cached value of the '{@link #getChans() <em>Chans</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChans()
   * @generated
   * @ordered
   */
  protected EList<ChanId> chans;

  /**
   * The default value of the '{@link #isUrgent() <em>Urgent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUrgent()
   * @generated
   * @ordered
   */
  protected static final boolean URGENT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isUrgent() <em>Urgent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUrgent()
   * @generated
   * @ordered
   */
  protected boolean urgent = URGENT_EDEFAULT;

  /**
   * The default value of the '{@link #isBroadcast() <em>Broadcast</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isBroadcast()
   * @generated
   * @ordered
   */
  protected static final boolean BROADCAST_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isBroadcast() <em>Broadcast</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isBroadcast()
   * @generated
   * @ordered
   */
  protected boolean broadcast = BROADCAST_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ChannelTypeImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return TimedAutomataPackage.Literals.CHANNEL_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ChanId> getChans()
  {
    if (chans == null)
    {
      chans = new EObjectContainmentEList<ChanId>(ChanId.class, this, TimedAutomataPackage.CHANNEL_TYPE__CHANS);
    }
    return chans;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isUrgent()
  {
    return urgent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUrgent(boolean newUrgent)
  {
    boolean oldUrgent = urgent;
    urgent = newUrgent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.CHANNEL_TYPE__URGENT, oldUrgent, urgent));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isBroadcast()
  {
    return broadcast;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBroadcast(boolean newBroadcast)
  {
    boolean oldBroadcast = broadcast;
    broadcast = newBroadcast;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.CHANNEL_TYPE__BROADCAST, oldBroadcast, broadcast));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.CHANNEL_TYPE__CHANS:
        return ((InternalEList<?>)getChans()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.CHANNEL_TYPE__CHANS:
        return getChans();
      case TimedAutomataPackage.CHANNEL_TYPE__URGENT:
        return isUrgent();
      case TimedAutomataPackage.CHANNEL_TYPE__BROADCAST:
        return isBroadcast();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.CHANNEL_TYPE__CHANS:
        getChans().clear();
        getChans().addAll((Collection<? extends ChanId>)newValue);
        return;
      case TimedAutomataPackage.CHANNEL_TYPE__URGENT:
        setUrgent((Boolean)newValue);
        return;
      case TimedAutomataPackage.CHANNEL_TYPE__BROADCAST:
        setBroadcast((Boolean)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.CHANNEL_TYPE__CHANS:
        getChans().clear();
        return;
      case TimedAutomataPackage.CHANNEL_TYPE__URGENT:
        setUrgent(URGENT_EDEFAULT);
        return;
      case TimedAutomataPackage.CHANNEL_TYPE__BROADCAST:
        setBroadcast(BROADCAST_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.CHANNEL_TYPE__CHANS:
        return chans != null && !chans.isEmpty();
      case TimedAutomataPackage.CHANNEL_TYPE__URGENT:
        return urgent != URGENT_EDEFAULT;
      case TimedAutomataPackage.CHANNEL_TYPE__BROADCAST:
        return broadcast != BROADCAST_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (urgent: ");
    result.append(urgent);
    result.append(", broadcast: ");
    result.append(broadcast);
    result.append(')');
    return result.toString();
  }

} //ChannelTypeImpl
