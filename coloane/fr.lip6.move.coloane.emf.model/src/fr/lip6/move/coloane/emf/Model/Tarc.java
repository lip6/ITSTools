/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tarc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarc#getTarcdesc <em>Tarcdesc</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarc#getPi <em>Pi</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarc#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarc#getId <em>Id</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tarc#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc()
 * @model extendedMetaData="name='tarc' kind='elementOnly'"
 * @generated
 */
public interface Tarc extends EObject {
	/**
	 * Returns the value of the '<em><b>Tarcdesc</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tarcdesc</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tarcdesc</em>' attribute list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc_Tarcdesc()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='Tarcdesc:0'"
	 * @generated
	 */
	FeatureMap getTarcdesc();

	/**
	 * Returns the value of the '<em><b>Pi</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tpi}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pi</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pi</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc_Pi()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pi' namespace='##targetNamespace' group='Tarcdesc:0'"
	 * @generated
	 */
	EList<Tpi> getPi();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tattributes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc_Attributes()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='attributes' namespace='##targetNamespace' group='Tarcdesc:0'"
	 * @generated
	 */
	EList<Tattributes> getAttributes();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tarc#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTarc_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':4' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // Tarc
