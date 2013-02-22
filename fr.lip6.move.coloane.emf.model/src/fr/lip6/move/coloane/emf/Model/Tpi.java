/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tpi</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tpi#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tpi#getYposition <em>Yposition</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTpi()
 * @model extendedMetaData="name='tpi' kind='empty'"
 * @generated
 */
public interface Tpi extends EObject {
	/**
	 * Returns the value of the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xposition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xposition</em>' attribute.
	 * @see #setXposition(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTpi_Xposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='xposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getXposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tpi#getXposition <em>Xposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xposition</em>' attribute.
	 * @see #getXposition()
	 * @generated
	 */
	void setXposition(String value);

	/**
	 * Returns the value of the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Yposition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Yposition</em>' attribute.
	 * @see #setYposition(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTpi_Yposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='yposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getYposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tpi#getYposition <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Yposition</em>' attribute.
	 * @see #getYposition()
	 * @generated
	 */
	void setYposition(String value);

} // Tpi
