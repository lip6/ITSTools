/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal.impl;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.Transient;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;

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
 * An implementation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getArrays <em>Arrays</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getLists <em>Lists</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.SystemImpl#getTransient <em>Transient</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SystemImpl extends MinimalEObjectImpl.Container implements fr.lip6.move.gal.System
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariables()
   * @generated
   * @ordered
   */
  protected EList<Variable> variables;

  /**
   * The cached value of the '{@link #getArrays() <em>Arrays</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArrays()
   * @generated
   * @ordered
   */
  protected EList<ArrayPrefix> arrays;

  /**
   * The cached value of the '{@link #getLists() <em>Lists</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLists()
   * @generated
   * @ordered
   */
  protected EList<List> lists;

  /**
   * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransitions()
   * @generated
   * @ordered
   */
  protected EList<Transition> transitions;

  /**
   * The cached value of the '{@link #getTransient() <em>Transient</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransient()
   * @generated
   * @ordered
   */
  protected Transient transient_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SystemImpl()
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
    return GalPackage.Literals.SYSTEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.SYSTEM__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable> getVariables()
  {
    if (variables == null)
    {
      variables = new EObjectContainmentEList<Variable>(Variable.class, this, GalPackage.SYSTEM__VARIABLES);
    }
    return variables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ArrayPrefix> getArrays()
  {
    if (arrays == null)
    {
      arrays = new EObjectContainmentEList<ArrayPrefix>(ArrayPrefix.class, this, GalPackage.SYSTEM__ARRAYS);
    }
    return arrays;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<List> getLists()
  {
    if (lists == null)
    {
      lists = new EObjectContainmentEList<List>(List.class, this, GalPackage.SYSTEM__LISTS);
    }
    return lists;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Transition> getTransitions()
  {
    if (transitions == null)
    {
      transitions = new EObjectContainmentEList<Transition>(Transition.class, this, GalPackage.SYSTEM__TRANSITIONS);
    }
    return transitions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Transient getTransient()
  {
    return transient_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTransient(Transient newTransient, NotificationChain msgs)
  {
    Transient oldTransient = transient_;
    transient_ = newTransient;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GalPackage.SYSTEM__TRANSIENT, oldTransient, newTransient);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTransient(Transient newTransient)
  {
    if (newTransient != transient_)
    {
      NotificationChain msgs = null;
      if (transient_ != null)
        msgs = ((InternalEObject)transient_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GalPackage.SYSTEM__TRANSIENT, null, msgs);
      if (newTransient != null)
        msgs = ((InternalEObject)newTransient).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GalPackage.SYSTEM__TRANSIENT, null, msgs);
      msgs = basicSetTransient(newTransient, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.SYSTEM__TRANSIENT, newTransient, newTransient));
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
      case GalPackage.SYSTEM__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case GalPackage.SYSTEM__ARRAYS:
        return ((InternalEList<?>)getArrays()).basicRemove(otherEnd, msgs);
      case GalPackage.SYSTEM__LISTS:
        return ((InternalEList<?>)getLists()).basicRemove(otherEnd, msgs);
      case GalPackage.SYSTEM__TRANSITIONS:
        return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
      case GalPackage.SYSTEM__TRANSIENT:
        return basicSetTransient(null, msgs);
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
      case GalPackage.SYSTEM__NAME:
        return getName();
      case GalPackage.SYSTEM__VARIABLES:
        return getVariables();
      case GalPackage.SYSTEM__ARRAYS:
        return getArrays();
      case GalPackage.SYSTEM__LISTS:
        return getLists();
      case GalPackage.SYSTEM__TRANSITIONS:
        return getTransitions();
      case GalPackage.SYSTEM__TRANSIENT:
        return getTransient();
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
      case GalPackage.SYSTEM__NAME:
        setName((String)newValue);
        return;
      case GalPackage.SYSTEM__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends Variable>)newValue);
        return;
      case GalPackage.SYSTEM__ARRAYS:
        getArrays().clear();
        getArrays().addAll((Collection<? extends ArrayPrefix>)newValue);
        return;
      case GalPackage.SYSTEM__LISTS:
        getLists().clear();
        getLists().addAll((Collection<? extends List>)newValue);
        return;
      case GalPackage.SYSTEM__TRANSITIONS:
        getTransitions().clear();
        getTransitions().addAll((Collection<? extends Transition>)newValue);
        return;
      case GalPackage.SYSTEM__TRANSIENT:
        setTransient((Transient)newValue);
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
      case GalPackage.SYSTEM__NAME:
        setName(NAME_EDEFAULT);
        return;
      case GalPackage.SYSTEM__VARIABLES:
        getVariables().clear();
        return;
      case GalPackage.SYSTEM__ARRAYS:
        getArrays().clear();
        return;
      case GalPackage.SYSTEM__LISTS:
        getLists().clear();
        return;
      case GalPackage.SYSTEM__TRANSITIONS:
        getTransitions().clear();
        return;
      case GalPackage.SYSTEM__TRANSIENT:
        setTransient((Transient)null);
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
      case GalPackage.SYSTEM__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case GalPackage.SYSTEM__VARIABLES:
        return variables != null && !variables.isEmpty();
      case GalPackage.SYSTEM__ARRAYS:
        return arrays != null && !arrays.isEmpty();
      case GalPackage.SYSTEM__LISTS:
        return lists != null && !lists.isEmpty();
      case GalPackage.SYSTEM__TRANSITIONS:
        return transitions != null && !transitions.isEmpty();
      case GalPackage.SYSTEM__TRANSIENT:
        return transient_ != null;
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //SystemImpl
