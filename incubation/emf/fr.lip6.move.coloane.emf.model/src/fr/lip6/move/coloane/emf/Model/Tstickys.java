/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tstickys</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tstickys#getSticky <em>Sticky</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTstickys()
 * @model extendedMetaData="name='tstickys' kind='elementOnly'"
 * @generated
 */
public interface Tstickys extends EObject {
	/**
	 * Returns the value of the '<em><b>Sticky</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tsticky}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sticky</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sticky</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTstickys_Sticky()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sticky' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Tsticky> getSticky();

} // Tstickys
