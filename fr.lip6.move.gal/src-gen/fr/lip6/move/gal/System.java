/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.System#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.gal.System#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.gal.System#getArrays <em>Arrays</em>}</li>
 *   <li>{@link fr.lip6.move.gal.System#getLists <em>Lists</em>}</li>
 *   <li>{@link fr.lip6.move.gal.System#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link fr.lip6.move.gal.System#getTransient <em>Transient</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getSystem()
 * @model
 * @generated
 */
public interface System extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see fr.lip6.move.gal.GalPackage#getSystem_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.System#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.gal.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see fr.lip6.move.gal.GalPackage#getSystem_Variables()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getVariables();

  /**
   * Returns the value of the '<em><b>Arrays</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.gal.ArrayPrefix}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arrays</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arrays</em>' containment reference list.
   * @see fr.lip6.move.gal.GalPackage#getSystem_Arrays()
   * @model containment="true"
   * @generated
   */
  EList<ArrayPrefix> getArrays();

  /**
   * Returns the value of the '<em><b>Lists</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.gal.List}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lists</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lists</em>' containment reference list.
   * @see fr.lip6.move.gal.GalPackage#getSystem_Lists()
   * @model containment="true"
   * @generated
   */
  EList<List> getLists();

  /**
   * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.gal.Transition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transitions</em>' containment reference list.
   * @see fr.lip6.move.gal.GalPackage#getSystem_Transitions()
   * @model containment="true"
   * @generated
   */
  EList<Transition> getTransitions();

  /**
   * Returns the value of the '<em><b>Transient</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transient</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transient</em>' containment reference.
   * @see #setTransient(Transient)
   * @see fr.lip6.move.gal.GalPackage#getSystem_Transient()
   * @model containment="true"
   * @generated
   */
  Transient getTransient();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.System#getTransient <em>Transient</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Transient</em>' containment reference.
   * @see #getTransient()
   * @generated
   */
  void setTransient(Transient value);

} // System
