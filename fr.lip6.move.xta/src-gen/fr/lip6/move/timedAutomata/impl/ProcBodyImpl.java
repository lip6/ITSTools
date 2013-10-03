/**
 */
package fr.lip6.move.timedAutomata.impl;

import fr.lip6.move.timedAutomata.ProcBody;
import fr.lip6.move.timedAutomata.StateDecl;
import fr.lip6.move.timedAutomata.TimedAutomataPackage;
import fr.lip6.move.timedAutomata.Transition;
import fr.lip6.move.timedAutomata.TypeDecl;
import fr.lip6.move.timedAutomata.VariableDecl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proc Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getStates <em>States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getCommitStates <em>Commit States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getUrgentStates <em>Urgent States</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getInitState <em>Init State</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.impl.ProcBodyImpl#getTransitions <em>Transitions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcBodyImpl extends MinimalEObjectImpl.Container implements ProcBody
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
   * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypes()
   * @generated
   * @ordered
   */
  protected EList<TypeDecl> types;

  /**
   * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStates()
   * @generated
   * @ordered
   */
  protected EList<StateDecl> states;

  /**
   * The cached value of the '{@link #getCommitStates() <em>Commit States</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCommitStates()
   * @generated
   * @ordered
   */
  protected EList<StateDecl> commitStates;

  /**
   * The cached value of the '{@link #getUrgentStates() <em>Urgent States</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUrgentStates()
   * @generated
   * @ordered
   */
  protected EList<StateDecl> urgentStates;

  /**
   * The cached value of the '{@link #getInitState() <em>Init State</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitState()
   * @generated
   * @ordered
   */
  protected StateDecl initState;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProcBodyImpl()
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
    return TimedAutomataPackage.Literals.PROC_BODY;
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
      variables = new EObjectContainmentEList<VariableDecl>(VariableDecl.class, this, TimedAutomataPackage.PROC_BODY__VARIABLES);
    }
    return variables;
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
      types = new EObjectContainmentEList<TypeDecl>(TypeDecl.class, this, TimedAutomataPackage.PROC_BODY__TYPES);
    }
    return types;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<StateDecl> getStates()
  {
    if (states == null)
    {
      states = new EObjectContainmentEList<StateDecl>(StateDecl.class, this, TimedAutomataPackage.PROC_BODY__STATES);
    }
    return states;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<StateDecl> getCommitStates()
  {
    if (commitStates == null)
    {
      commitStates = new EObjectResolvingEList<StateDecl>(StateDecl.class, this, TimedAutomataPackage.PROC_BODY__COMMIT_STATES);
    }
    return commitStates;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<StateDecl> getUrgentStates()
  {
    if (urgentStates == null)
    {
      urgentStates = new EObjectResolvingEList<StateDecl>(StateDecl.class, this, TimedAutomataPackage.PROC_BODY__URGENT_STATES);
    }
    return urgentStates;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl getInitState()
  {
    if (initState != null && initState.eIsProxy())
    {
      InternalEObject oldInitState = (InternalEObject)initState;
      initState = (StateDecl)eResolveProxy(oldInitState);
      if (initState != oldInitState)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, TimedAutomataPackage.PROC_BODY__INIT_STATE, oldInitState, initState));
      }
    }
    return initState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateDecl basicGetInitState()
  {
    return initState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInitState(StateDecl newInitState)
  {
    StateDecl oldInitState = initState;
    initState = newInitState;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TimedAutomataPackage.PROC_BODY__INIT_STATE, oldInitState, initState));
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
      transitions = new EObjectContainmentEList<Transition>(Transition.class, this, TimedAutomataPackage.PROC_BODY__TRANSITIONS);
    }
    return transitions;
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
      case TimedAutomataPackage.PROC_BODY__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.PROC_BODY__TYPES:
        return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.PROC_BODY__STATES:
        return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
      case TimedAutomataPackage.PROC_BODY__TRANSITIONS:
        return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
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
      case TimedAutomataPackage.PROC_BODY__VARIABLES:
        return getVariables();
      case TimedAutomataPackage.PROC_BODY__TYPES:
        return getTypes();
      case TimedAutomataPackage.PROC_BODY__STATES:
        return getStates();
      case TimedAutomataPackage.PROC_BODY__COMMIT_STATES:
        return getCommitStates();
      case TimedAutomataPackage.PROC_BODY__URGENT_STATES:
        return getUrgentStates();
      case TimedAutomataPackage.PROC_BODY__INIT_STATE:
        if (resolve) return getInitState();
        return basicGetInitState();
      case TimedAutomataPackage.PROC_BODY__TRANSITIONS:
        return getTransitions();
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
      case TimedAutomataPackage.PROC_BODY__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends VariableDecl>)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__TYPES:
        getTypes().clear();
        getTypes().addAll((Collection<? extends TypeDecl>)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__STATES:
        getStates().clear();
        getStates().addAll((Collection<? extends StateDecl>)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__COMMIT_STATES:
        getCommitStates().clear();
        getCommitStates().addAll((Collection<? extends StateDecl>)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__URGENT_STATES:
        getUrgentStates().clear();
        getUrgentStates().addAll((Collection<? extends StateDecl>)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__INIT_STATE:
        setInitState((StateDecl)newValue);
        return;
      case TimedAutomataPackage.PROC_BODY__TRANSITIONS:
        getTransitions().clear();
        getTransitions().addAll((Collection<? extends Transition>)newValue);
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
      case TimedAutomataPackage.PROC_BODY__VARIABLES:
        getVariables().clear();
        return;
      case TimedAutomataPackage.PROC_BODY__TYPES:
        getTypes().clear();
        return;
      case TimedAutomataPackage.PROC_BODY__STATES:
        getStates().clear();
        return;
      case TimedAutomataPackage.PROC_BODY__COMMIT_STATES:
        getCommitStates().clear();
        return;
      case TimedAutomataPackage.PROC_BODY__URGENT_STATES:
        getUrgentStates().clear();
        return;
      case TimedAutomataPackage.PROC_BODY__INIT_STATE:
        setInitState((StateDecl)null);
        return;
      case TimedAutomataPackage.PROC_BODY__TRANSITIONS:
        getTransitions().clear();
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
      case TimedAutomataPackage.PROC_BODY__VARIABLES:
        return variables != null && !variables.isEmpty();
      case TimedAutomataPackage.PROC_BODY__TYPES:
        return types != null && !types.isEmpty();
      case TimedAutomataPackage.PROC_BODY__STATES:
        return states != null && !states.isEmpty();
      case TimedAutomataPackage.PROC_BODY__COMMIT_STATES:
        return commitStates != null && !commitStates.isEmpty();
      case TimedAutomataPackage.PROC_BODY__URGENT_STATES:
        return urgentStates != null && !urgentStates.isEmpty();
      case TimedAutomataPackage.PROC_BODY__INIT_STATE:
        return initState != null;
      case TimedAutomataPackage.PROC_BODY__TRANSITIONS:
        return transitions != null && !transitions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ProcBodyImpl
