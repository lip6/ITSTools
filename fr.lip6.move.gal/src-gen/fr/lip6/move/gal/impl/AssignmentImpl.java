/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal.impl;

import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.VariableRef;

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
 * An implementation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.impl.AssignmentImpl#getVar <em>Var</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.AssignmentImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.AssignmentImpl#getAVar <em>AVar</em>}</li>
 *   <li>{@link fr.lip6.move.gal.impl.AssignmentImpl#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssignmentImpl extends MinimalEObjectImpl.Container implements Assignment
{
  /**
   * The cached value of the '{@link #getVar() <em>Var</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVar()
   * @generated
   * @ordered
   */
  protected VariableRef var;

  /**
   * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpr()
   * @generated
   * @ordered
   */
  protected IntExpression expr;

  /**
   * The cached value of the '{@link #getAVar() <em>AVar</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAVar()
   * @generated
   * @ordered
   */
  protected EList<VariableRef> aVar;

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
  protected AssignmentImpl()
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
    return GalPackage.Literals.ASSIGNMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableRef getVar()
  {
    return var;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetVar(VariableRef newVar, NotificationChain msgs)
  {
    VariableRef oldVar = var;
    var = newVar;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__VAR, oldVar, newVar);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVar(VariableRef newVar)
  {
    if (newVar != var)
    {
      NotificationChain msgs = null;
      if (var != null)
        msgs = ((InternalEObject)var).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__VAR, null, msgs);
      if (newVar != null)
        msgs = ((InternalEObject)newVar).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__VAR, null, msgs);
      msgs = basicSetVar(newVar, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__VAR, newVar, newVar));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntExpression getExpr()
  {
    return expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpr(IntExpression newExpr, NotificationChain msgs)
  {
    IntExpression oldExpr = expr;
    expr = newExpr;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__EXPR, oldExpr, newExpr);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpr(IntExpression newExpr)
  {
    if (newExpr != expr)
    {
      NotificationChain msgs = null;
      if (expr != null)
        msgs = ((InternalEObject)expr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__EXPR, null, msgs);
      if (newExpr != null)
        msgs = ((InternalEObject)newExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__EXPR, null, msgs);
      msgs = basicSetExpr(newExpr, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__EXPR, newExpr, newExpr));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VariableRef> getAVar()
  {
    if (aVar == null)
    {
      aVar = new EObjectContainmentEList<VariableRef>(VariableRef.class, this, GalPackage.ASSIGNMENT__AVAR);
    }
    return aVar;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__INDEX, oldIndex, newIndex);
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
        msgs = ((InternalEObject)index).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__INDEX, null, msgs);
      if (newIndex != null)
        msgs = ((InternalEObject)newIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GalPackage.ASSIGNMENT__INDEX, null, msgs);
      msgs = basicSetIndex(newIndex, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GalPackage.ASSIGNMENT__INDEX, newIndex, newIndex));
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
      case GalPackage.ASSIGNMENT__VAR:
        return basicSetVar(null, msgs);
      case GalPackage.ASSIGNMENT__EXPR:
        return basicSetExpr(null, msgs);
      case GalPackage.ASSIGNMENT__AVAR:
        return ((InternalEList<?>)getAVar()).basicRemove(otherEnd, msgs);
      case GalPackage.ASSIGNMENT__INDEX:
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
      case GalPackage.ASSIGNMENT__VAR:
        return getVar();
      case GalPackage.ASSIGNMENT__EXPR:
        return getExpr();
      case GalPackage.ASSIGNMENT__AVAR:
        return getAVar();
      case GalPackage.ASSIGNMENT__INDEX:
        return getIndex();
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
      case GalPackage.ASSIGNMENT__VAR:
        setVar((VariableRef)newValue);
        return;
      case GalPackage.ASSIGNMENT__EXPR:
        setExpr((IntExpression)newValue);
        return;
      case GalPackage.ASSIGNMENT__AVAR:
        getAVar().clear();
        getAVar().addAll((Collection<? extends VariableRef>)newValue);
        return;
      case GalPackage.ASSIGNMENT__INDEX:
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
      case GalPackage.ASSIGNMENT__VAR:
        setVar((VariableRef)null);
        return;
      case GalPackage.ASSIGNMENT__EXPR:
        setExpr((IntExpression)null);
        return;
      case GalPackage.ASSIGNMENT__AVAR:
        getAVar().clear();
        return;
      case GalPackage.ASSIGNMENT__INDEX:
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
      case GalPackage.ASSIGNMENT__VAR:
        return var != null;
      case GalPackage.ASSIGNMENT__EXPR:
        return expr != null;
      case GalPackage.ASSIGNMENT__AVAR:
        return aVar != null && !aVar.isEmpty();
      case GalPackage.ASSIGNMENT__INDEX:
        return index != null;
    }
    return super.eIsSet(featureID);
  }

} //AssignmentImpl
