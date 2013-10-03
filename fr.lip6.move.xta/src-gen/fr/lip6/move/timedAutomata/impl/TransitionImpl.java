/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.Assignments;
import fr.lip6.move.timedAutomata.BooleanExpression;
import fr.lip6.move.timedAutomata.StateDecl;
import fr.lip6.move.timedAutomata.Sync;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;
import fr.lip6.move.timedAutomata.Transition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.TransitionImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.TransitionImpl#getDest <em>Dest</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.TransitionImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.TransitionImpl#getSync <em>Sync</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.TransitionImpl#getAssign <em>Assign</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends MinimalEObjectImpl.Container implements Transition
{
  /**
   * The cached value of the '{@link #getSrc() <em>Src</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrc()
   * @generated
   * @ordered
   */
  protected StateDecl src;

  /**
   * The cached value of the '{@link #getDest() <em>Dest</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDest()
   * @generated
   * @ordered
   */
  protected StateDecl dest;

  /**
   * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGuard()
   * @generated
   * @ordered
   */
  protected BooleanExpression guard;

  /**
   * The cached value of the '{@link #getSync() <em>Sync</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSync()
   * @generated
   * @ordered
   */
  protected Sync sync;

  /**
   * The cached value of the '{@link #getAssign() <em>Assign</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssign()
   * @generated
   * @ordered
   */
  protected Assignments assign;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransitionImpl()
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
    return TimedAutomataPackage.Literals.TRANSITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl getSrc()
  {
    if (src != null && src.eIsProxy())
    {
      InternalEObject oldSrc = (InternalEObject)src;
      src = (StateDecl)eResolveProxy(oldSrc);
      if (src != oldSrc)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, TimedAutomataPackage.TRANSITION__SRC, oldSrc, src));
      }
    }
    return src;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl basicGetSrc()
  {
    return src;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSrc(StateDecl newSrc)
  {
    StateDecl oldSrc = src;
    src = newSrc;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__SRC, oldSrc, src));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl getDest()
  {
    if (dest != null && dest.eIsProxy())
    {
      InternalEObject oldDest = (InternalEObject)dest;
      dest = (StateDecl)eResolveProxy(oldDest);
      if (dest != oldDest)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, TimedAutomataPackage.TRANSITION__DEST, oldDest, dest));
      }
    }
    return dest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl basicGetDest()
  {
    return dest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDest(StateDecl newDest)
  {
    StateDecl oldDest = dest;
    dest = newDest;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__DEST, oldDest, dest));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanExpression getGuard()
  {
    return guard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetGuard(BooleanExpression newGuard, NotificationChain msgs)
  {
    BooleanExpression oldGuard = guard;
    guard = newGuard;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__GUARD, oldGuard, newGuard);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGuard(BooleanExpression newGuard)
  {
    if (newGuard != guard)
    {
      NotificationChain msgs = null;
      if (guard != null)
        msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__GUARD, null, msgs);
      if (newGuard != null)
        msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__GUARD, null, msgs);
      msgs = basicSetGuard(newGuard, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__GUARD, newGuard, newGuard));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Sync getSync()
  {
    return sync;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSync(Sync newSync, NotificationChain msgs)
  {
    Sync oldSync = sync;
    sync = newSync;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__SYNC, oldSync, newSync);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSync(Sync newSync)
  {
    if (newSync != sync)
    {
      NotificationChain msgs = null;
      if (sync != null)
        msgs = ((InternalEObject)sync).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__SYNC, null, msgs);
      if (newSync != null)
        msgs = ((InternalEObject)newSync).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__SYNC, null, msgs);
      msgs = basicSetSync(newSync, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__SYNC, newSync, newSync));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assignments getAssign()
  {
    return assign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAssign(Assignments newAssign, NotificationChain msgs)
  {
    Assignments oldAssign = assign;
    assign = newAssign;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__ASSIGN, oldAssign, newAssign);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssign(Assignments newAssign)
  {
    if (newAssign != assign)
    {
      NotificationChain msgs = null;
      if (assign != null)
        msgs = ((InternalEObject)assign).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__ASSIGN, null, msgs);
      if (newAssign != null)
        msgs = ((InternalEObject)newAssign).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.TRANSITION__ASSIGN, null, msgs);
      msgs = basicSetAssign(newAssign, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.TRANSITION__ASSIGN, newAssign, newAssign));
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
      case TimedAutomataPackage.TRANSITION__GUARD:
        return basicSetGuard(null, msgs);
      case TimedAutomataPackage.TRANSITION__SYNC:
        return basicSetSync(null, msgs);
      case TimedAutomataPackage.TRANSITION__ASSIGN:
        return basicSetAssign(null, msgs);
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
      case TimedAutomataPackage.TRANSITION__SRC:
        if (resolve) return getSrc();
        return basicGetSrc();
      case TimedAutomataPackage.TRANSITION__DEST:
        if (resolve) return getDest();
        return basicGetDest();
      case TimedAutomataPackage.TRANSITION__GUARD:
        return getGuard();
      case TimedAutomataPackage.TRANSITION__SYNC:
        return getSync();
      case TimedAutomataPackage.TRANSITION__ASSIGN:
        return getAssign();
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
      case TimedAutomataPackage.TRANSITION__SRC:
        setSrc((StateDecl)newValue);
        return;
      case TimedAutomataPackage.TRANSITION__DEST:
        setDest((StateDecl)newValue);
        return;
      case TimedAutomataPackage.TRANSITION__GUARD:
        setGuard((BooleanExpression)newValue);
        return;
      case TimedAutomataPackage.TRANSITION__SYNC:
        setSync((Sync)newValue);
        return;
      case TimedAutomataPackage.TRANSITION__ASSIGN:
        setAssign((Assignments)newValue);
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
      case TimedAutomataPackage.TRANSITION__SRC:
        setSrc((StateDecl)null);
        return;
      case TimedAutomataPackage.TRANSITION__DEST:
        setDest((StateDecl)null);
        return;
      case TimedAutomataPackage.TRANSITION__GUARD:
        setGuard((BooleanExpression)null);
        return;
      case TimedAutomataPackage.TRANSITION__SYNC:
        setSync((Sync)null);
        return;
      case TimedAutomataPackage.TRANSITION__ASSIGN:
        setAssign((Assignments)null);
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
      case TimedAutomataPackage.TRANSITION__SRC:
        return src != null;
      case TimedAutomataPackage.TRANSITION__DEST:
        return dest != null;
      case TimedAutomataPackage.TRANSITION__GUARD:
        return guard != null;
      case TimedAutomataPackage.TRANSITION__SYNC:
        return sync != null;
      case TimedAutomataPackage.TRANSITION__ASSIGN:
        return assign != null;
    }
    return super.eIsSet(featureID);
  }

} //TransitionImpl
