/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.ChanId;
import fr.lip6.move.timedAutomata.Sync;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sync</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.SyncImpl#getChannel <em>Channel</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SyncImpl extends MinimalEObjectImpl.Container implements Sync
{
  /**
   * The cached value of the '{@link #getChannel() <em>Channel</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChannel()
   * @generated
   * @ordered
   */
  protected ChanId channel;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SyncImpl()
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
    return TimedAutomataPackage.Literals.SYNC;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChanId getChannel()
  {
    if (channel != null && channel.eIsProxy())
    {
      InternalEObject oldChannel = (InternalEObject)channel;
      channel = (ChanId)eResolveProxy(oldChannel);
      if (channel != oldChannel)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, TimedAutomataPackage.SYNC__CHANNEL, oldChannel, channel));
      }
    }
    return channel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChanId basicGetChannel()
  {
    return channel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setChannel(ChanId newChannel)
  {
    ChanId oldChannel = channel;
    channel = newChannel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.SYNC__CHANNEL, oldChannel, channel));
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
      case TimedAutomataPackage.SYNC__CHANNEL:
        if (resolve) return getChannel();
        return basicGetChannel();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TimedAutomataPackage.SYNC__CHANNEL:
        setChannel((ChanId)newValue);
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
      case TimedAutomataPackage.SYNC__CHANNEL:
        setChannel((ChanId)null);
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
      case TimedAutomataPackage.SYNC__CHANNEL:
        return channel != null;
    }
    return super.eIsSet(featureID);
  }

} //SyncImpl
