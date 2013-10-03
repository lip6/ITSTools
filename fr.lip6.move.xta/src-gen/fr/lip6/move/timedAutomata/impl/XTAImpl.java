/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.ChannelDecl;
import fr.lip6.move.timedAutomata.Instance;
import fr.lip6.move.timedAutomata.ProcDecl;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;
import fr.lip6.move.timedAutomata.TypeDecl;
import fr.lip6.move.timedAutomata.VariableDecl;
import fr.lip6.move.timedAutomata.XTA;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>XTA</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getChannels <em>Channels</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getTemplates <em>Templates</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.XTAImpl#getSystem <em>System</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XTAImpl extends MinimalEObjectImpl.Container implements XTA
{
  /**
   * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariables()
   * @generated
   * @ordered
   */
  protected EList<VariableDecl> variables;

  /**
   * The cached value of the '{@link #getChannels() <em>Channels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChannels()
   * @generated
   * @ordered
   */
  protected EList<ChannelDecl> channels;

  /**
   * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypes()
   * @generated
   * @ordered
   */
  protected EList<TypeDecl> types;

  /**
   * The cached value of the '{@link #getTemplates() <em>Templates</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTemplates()
   * @generated
   * @ordered
   */
  protected EList<ProcDecl> templates;

  /**
   * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstances()
   * @generated
   * @ordered
   */
  protected EList<Instance> instances;

  /**
   * The cached value of the '{@link #getSystem() <em>System</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSystem()
   * @generated
   * @ordered
   */
  protected fr.lip6.move.timedAutomata.System system;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected XTAImpl()
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
    return TimedAutomataPackage.Literals.XTA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VariableDecl> getVariables()
  {
    if (variables == null)
    {
      variables = new EObjectContainmentEList<VariableDecl>(VariableDecl.class, this, TimedAutomataPackage.XTA__VARIABLES);
    }
    return variables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ChannelDecl> getChannels()
  {
    if (channels == null)
    {
      channels = new EObjectContainmentEList<ChannelDecl>(ChannelDecl.class, this, TimedAutomataPackage.XTA__CHANNELS);
    }
    return channels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TypeDecl> getTypes()
  {
    if (types == null)
    {
      types = new EObjectContainmentEList<TypeDecl>(TypeDecl.class, this, TimedAutomataPackage.XTA__TYPES);
    }
    return types;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ProcDecl> getTemplates()
  {
    if (templates == null)
    {
      templates = new EObjectContainmentEList<ProcDecl>(ProcDecl.class, this, TimedAutomataPackage.XTA__TEMPLATES);
    }
    return templates;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Instance> getInstances()
  {
    if (instances == null)
    {
      instances = new EObjectContainmentEList<Instance>(Instance.class, this, TimedAutomataPackage.XTA__INSTANCES);
    }
    return instances;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public fr.lip6.move.timedAutomata.System getSystem()
  {
    return system;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSystem(fr.lip6.move.timedAutomata.System newSystem, NotificationChain msgs)
  {
    fr.lip6.move.timedAutomata.System oldSystem = system;
    system = newSystem;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.XTA__SYSTEM, oldSystem, newSystem);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSystem(fr.lip6.move.timedAutomata.System newSystem)
  {
    if (newSystem != system)
    {
      NotificationChain msgs = null;
      if (system != null)
        msgs = ((InternalEObject)system).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.XTA__SYSTEM, null, msgs);
      if (newSystem != null)
        msgs = ((InternalEObject)newSystem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TimedAutomataPackage.XTA__SYSTEM, null, msgs);
      msgs = basicSetSystem(newSystem, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.XTA__SYSTEM, newSystem, newSystem));
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
      case TimedAutomataPackage.XTA__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.XTA__CHANNELS:
        return ((InternalEList<?>)getChannels()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.XTA__TYPES:
        return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.XTA__TEMPLATES:
        return ((InternalEList<?>)getTemplates()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.XTA__INSTANCES:
        return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.XTA__SYSTEM:
        return basicSetSystem(null, msgs);
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
      case TimedAutomataPackage.XTA__VARIABLES:
        return getVariables();
      case TimedAutomataPackage.XTA__CHANNELS:
        return getChannels();
      case TimedAutomataPackage.XTA__TYPES:
        return getTypes();
      case TimedAutomataPackage.XTA__TEMPLATES:
        return getTemplates();
      case TimedAutomataPackage.XTA__INSTANCES:
        return getInstances();
      case TimedAutomataPackage.XTA__SYSTEM:
        return getSystem();
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
      case TimedAutomataPackage.XTA__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends VariableDecl>)newValue);
        return;
      case TimedAutomataPackage.XTA__CHANNELS:
        getChannels().clear();
        getChannels().addAll((Collection<? extends ChannelDecl>)newValue);
        return;
      case TimedAutomataPackage.XTA__TYPES:
        getTypes().clear();
        getTypes().addAll((Collection<? extends TypeDecl>)newValue);
        return;
      case TimedAutomataPackage.XTA__TEMPLATES:
        getTemplates().clear();
        getTemplates().addAll((Collection<? extends ProcDecl>)newValue);
        return;
      case TimedAutomataPackage.XTA__INSTANCES:
        getInstances().clear();
        getInstances().addAll((Collection<? extends Instance>)newValue);
        return;
      case TimedAutomataPackage.XTA__SYSTEM:
        setSystem((fr.lip6.move.timedAutomata.System)newValue);
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
      case TimedAutomataPackage.XTA__VARIABLES:
        getVariables().clear();
        return;
      case TimedAutomataPackage.XTA__CHANNELS:
        getChannels().clear();
        return;
      case TimedAutomataPackage.XTA__TYPES:
        getTypes().clear();
        return;
      case TimedAutomataPackage.XTA__TEMPLATES:
        getTemplates().clear();
        return;
      case TimedAutomataPackage.XTA__INSTANCES:
        getInstances().clear();
        return;
      case TimedAutomataPackage.XTA__SYSTEM:
        setSystem((fr.lip6.move.timedAutomata.System)null);
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
      case TimedAutomataPackage.XTA__VARIABLES:
        return variables != null && !variables.isEmpty();
      case TimedAutomataPackage.XTA__CHANNELS:
        return channels != null && !channels.isEmpty();
      case TimedAutomataPackage.XTA__TYPES:
        return types != null && !types.isEmpty();
      case TimedAutomataPackage.XTA__TEMPLATES:
        return templates != null && !templates.isEmpty();
      case TimedAutomataPackage.XTA__INSTANCES:
        return instances != null && !instances.isEmpty();
      case TimedAutomataPackage.XTA__SYSTEM:
        return system != null;
    }
    return super.eIsSet(featureID);
  }

} //XTAImpl
