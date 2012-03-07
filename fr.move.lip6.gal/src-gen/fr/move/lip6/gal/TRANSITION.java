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
 * A representation of the model object '<em><b>TRANSITION</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.move.lip6.gal.TRANSITION#getName <em>Name</em>}</li>
 *   <li>{@link fr.move.lip6.gal.TRANSITION#getLabel <em>Label</em>}</li>
 *   <li>{@link fr.move.lip6.gal.TRANSITION#getAssignments <em>Assignments</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.move.lip6.gal.GalPackage#getTRANSITION()
 * @model
 * @generated
 */
public interface TRANSITION extends EObject
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
   * @see fr.move.lip6.gal.GalPackage#getTRANSITION_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.move.lip6.gal.TRANSITION#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see #setLabel(String)
   * @see fr.move.lip6.gal.GalPackage#getTRANSITION_Label()
   * @model
   * @generated
   */
  String getLabel();

  /**
   * Sets the value of the '{@link fr.move.lip6.gal.TRANSITION#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see #getLabel()
   * @generated
   */
  void setLabel(String value);

  /**
   * Returns the value of the '<em><b>Assignments</b></em>' containment reference list.
   * The list contents are of type {@link fr.move.lip6.gal.Assignment}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assignments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignments</em>' containment reference list.
   * @see fr.move.lip6.gal.GalPackage#getTRANSITION_Assignments()
   * @model containment="true"
   * @generated
   */
  EList<Assignment> getAssignments();

} // TRANSITION
