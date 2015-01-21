/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tsticky</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getTstickydesc <em>Tstickydesc</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getValue <em>Value</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getLink <em>Link</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getYposition <em>Yposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tsticky#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky()
 * @model extendedMetaData="name='tsticky' kind='elementOnly'"
 * @generated
 */
public interface Tsticky extends EObject {
	/**
	 * Returns the value of the '<em><b>Tstickydesc</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tstickydesc</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tstickydesc</em>' attribute list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_Tstickydesc()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='Tstickydesc:0'"
	 * @generated
	 */
	FeatureMap getTstickydesc();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_Value()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='value' namespace='##targetNamespace' group='Tstickydesc:0'"
	 * @generated
	 */
	EList<String> getValue();

	/**
	 * Returns the value of the '<em><b>Link</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tlink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_Link()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='link' namespace='##targetNamespace' group='Tstickydesc:0'"
	 * @generated
	 */
	EList<Tlink> getLink();

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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_Xposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='xposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getXposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getXposition <em>Xposition</em>}' attribute.
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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_Yposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='yposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getYposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getYposition <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Yposition</em>' attribute.
	 * @see #getYposition()
	 * @generated
	 */
	void setYposition(String value);

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTsticky_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':5' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Tsticky
