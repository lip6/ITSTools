/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal.impl;

import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.impl.VariableRefImpl#getReferencedVar <em>Referenced Var</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VariableRefImpl extends VarAccessImpl implements VariableRef
{
  /**
   * The cached value of the '{@link #getReferencedVar() <em>Referenced Var</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReferencedVar()
   * @generated
   * @ordered
   */
  protected Variable referencedVar;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VariableRefImpl()
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
    return GalPackage.Literals.VARIABLE_REF;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable getReferencedVar()
  {
    if (referencedVar != null && referencedVar.eIsProxy())
    {
      InternalEObject oldReferencedVar = (InternalEObject)referencedVar;
      referencedVar = (Variable)eResolveProxy(oldReferencedVar);
      if (referencedVar != oldReferencedVar)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, GalPackage.VARIABLE_REF__REFERENCED_VAR, oldReferencedVar, referencedVar));
      }
    }
    return referencedVar;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable basicGetReferencedVar()
  {
    return referencedVar;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setReferencedVar(Variable newReferencedVar)
  {
    Variable oldReferencedVar = referencedVar;
    referencedVar = newReferencedVar;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.VARIABLE_REF__REFERENCED_VAR, oldReferencedVar, referencedVar));
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
      case GalPackage.VARIABLE_REF__REFERENCED_VAR:
        if (resolve) return getReferencedVar();
        return basicGetReferencedVar();
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
      case GalPackage.VARIABLE_REF__REFERENCED_VAR:
        setReferencedVar((Variable)newValue);
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
      case GalPackage.VARIABLE_REF__REFERENCED_VAR:
        setReferencedVar((Variable)null);
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
      case GalPackage.VARIABLE_REF__REFERENCED_VAR:
        return referencedVar != null;
    }
    return super.eIsSet(featureID);
  }

} //VariableRefImpl
