/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tarcs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarcs#getArc <em>Arc</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarcs()
 * @model extendedMetaData="name='tarcs' kind='elementOnly'"
 * @generated
 */
public interface Tarcs extends EObject {
	/**
	 * Returns the value of the '<em><b>Arc</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tarc}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arc</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arc</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarcs_Arc()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='arc' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Tarc> getArc();

} // Tarcs
