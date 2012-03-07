/**
 * <copyright>
 * </copyright>
 *
 */
package fr.move.lip6.gal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PROGRAM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.move.lip6.gal.PROGRAM#getName <em>Name</em>}</li>
 *   <li>{@link fr.move.lip6.gal.PROGRAM#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.move.lip6.gal.PROGRAM#getTransitions <em>Transitions</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.move.lip6.gal.GalPackage#getPROGRAM()
 * @model
 * @generated
 */
public interface PROGRAM extends EObject
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
   * @see fr.move.lip6.gal.GalPackage#getPROGRAM_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.move.lip6.gal.PROGRAM#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link fr.move.lip6.gal.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see fr.move.lip6.gal.GalPackage#getPROGRAM_Variables()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getVariables();

  /**
   * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
   * The list contents are of type {@link fr.move.lip6.gal.TRANSITION}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transitions</em>' containment reference list.
   * @see fr.move.lip6.gal.GalPackage#getPROGRAM_Transitions()
   * @model containment="true"
   * @generated
   */
  EList<TRANSITION> getTransitions();

} // PROGRAM
