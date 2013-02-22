/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tattribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tattribute#getValue <em>Value</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tattribute#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tattribute#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tattribute#getYposition <em>Yposition</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTattribute()
 * @model extendedMetaData="name='tattribute' kind='simple'"
 * @generated
 */
public interface Tattribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTattribute_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTattribute_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTattribute_Xposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='xposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getXposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getXposition <em>Xposition</em>}' attribute.
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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTattribute_Yposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='yposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getYposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getYposition <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Yposition</em>' attribute.
	 * @see #getYposition()
	 * @generated
	 */
	void setYposition(String value);

} // Tattribute
