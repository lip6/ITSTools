/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal.impl;

import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Variable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.impl.VariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.VariableImpl#getInitVal <em>Init Val</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.VariableImpl#getAname <em>Aname</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.VariableImpl#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VariableImpl extends MinimalEObjectImpl.Container implements Variable
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
   * The default value of the '{@link #getInitVal() <em>Init Val</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitVal()
   * @generated
   * @ordered
   */
  protected static final int INIT_VAL_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getInitVal() <em>Init Val</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitVal()
   * @generated
   * @ordered
   */
  protected int initVal = INIT_VAL_EDEFAULT;

  /**
   * The default value of the '{@link #getAname() <em>Aname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAname()
   * @generated
   * @ordered
   */
  protected static final String ANAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAname() <em>Aname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAname()
   * @generated
   * @ordered
   */
  protected String aname = ANAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getIndex() <em>Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIndex()
   * @generated
   * @ordered
   */
  protected IntExpression index;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VariableImpl()
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
    return GalPackage.Literals.VARIABLE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getInitVal()
  {
    return initVal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInitVal(int newInitVal)
  {
    int oldInitVal = initVal;
    initVal = newInitVal;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE__INIT_VAL, oldInitVal, initVal));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAname()
  {
    return aname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAname(String newAname)
  {
    String oldAname = aname;
    aname = newAname;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE__ANAME, oldAname, aname));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntExpression getIndex()
  {
    return index;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIndex(IntExpression newIndex, NotificationChain msgs)
  {
    IntExpression oldIndex = index;
    index = newIndex;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE__INDEX, oldIndex, newIndex);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIndex(IntExpression newIndex)
  {
    if (newIndex != index)
    {
      NotificationChain msgs = null;
      if (index != null)
        msgs = ((InternalEObject)index).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GalPackage.VARIABLE__INDEX, null, msgs);
      if (newIndex != null)
        msgs = ((InternalEObject)newIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GalPackage.VARIABLE__INDEX, null, msgs);
      msgs = basicSetIndex(newIndex, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE__INDEX, newIndex, newIndex));
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
      case GalPackage.VARIABLE__INDEX:
        return basicSetIndex(null, msgs);
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
      case GalPackage.VARIABLE__NAME:
        return getName();
      case GalPackage.VARIABLE__INIT_VAL:
        return getInitVal();
      case GalPackage.VARIABLE__ANAME:
        return getAname();
      case GalPackage.VARIABLE__INDEX:
        return getIndex();
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
      case GalPackage.VARIABLE__NAME:
        setName((String)newValue);
        return;
      case GalPackage.VARIABLE__INIT_VAL:
        setInitVal((Integer)newValue);
        return;
      case GalPackage.VARIABLE__ANAME:
        setAname((String)newValue);
        return;
      case GalPackage.VARIABLE__INDEX:
        setIndex((IntExpression)newValue);
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
      case GalPackage.VARIABLE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case GalPackage.VARIABLE__INIT_VAL:
        setInitVal(INIT_VAL_EDEFAULT);
        return;
      case GalPackage.VARIABLE__ANAME:
        setAname(ANAME_EDEFAULT);
        return;
      case GalPackage.VARIABLE__INDEX:
        setIndex((IntExpression)null);
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
      case GalPackage.VARIABLE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case GalPackage.VARIABLE__INIT_VAL:
        return initVal != INIT_VAL_EDEFAULT;
      case GalPackage.VARIABLE__ANAME:
        return ANAME_EDEFAULT == null ? aname != null : !ANAME_EDEFAULT.equals(aname);
      case GalPackage.VARIABLE__INDEX:
        return index != null;
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
    result.append(", initVal: ");
    result.append(initVal);
    result.append(", aname: ");
    result.append(aname);
    result.append(')');
    return result.toString();
  }

} //VariableImpl
