/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tlink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tlink#getLinkId <em>Link Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTlink()
 * @model extendedMetaData="name='tlink' kind='empty'"
 * @generated
 */
public interface Tlink extends EObject {
	/**
	 * Returns the value of the '<em><b>Link Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Id</em>' attribute.
	 * @see #setLinkId(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTlink_LinkId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='linkId' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLinkId();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tlink#getLinkId <em>Link Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Id</em>' attribute.
	 * @see #getLinkId()
	 * @generated
	 */
	void setLinkId(String value);

} // Tlink
