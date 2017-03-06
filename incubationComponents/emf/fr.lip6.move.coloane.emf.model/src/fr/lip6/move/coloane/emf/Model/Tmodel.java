/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tmodel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getNodes <em>Nodes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getArcs <em>Arcs</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getStickys <em>Stickys</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getFormalism <em>Formalism</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tmodel#getYposition <em>Yposition</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel()
 * @model extendedMetaData="name='tmodel' kind='elementOnly'"
 * @generated
 */
public interface Tmodel extends EObject {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference.
	 * @see #setNodes(Tnodes)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Nodes()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='nodes' namespace='##targetNamespace'"
	 * @generated
	 */
	Tnodes getNodes();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getNodes <em>Nodes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nodes</em>' containment reference.
	 * @see #getNodes()
	 * @generated
	 */
	void setNodes(Tnodes value);

	/**
	 * Returns the value of the '<em><b>Arcs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arcs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arcs</em>' containment reference.
	 * @see #setArcs(Tarcs)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Arcs()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='arcs' namespace='##targetNamespace'"
	 * @generated
	 */
	Tarcs getArcs();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getArcs <em>Arcs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arcs</em>' containment reference.
	 * @see #getArcs()
	 * @generated
	 */
	void setArcs(Tarcs value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(Tattributes)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Attributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	Tattributes getAttributes();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(Tattributes value);

	/**
	 * Returns the value of the '<em><b>Stickys</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stickys</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stickys</em>' containment reference.
	 * @see #setStickys(Tstickys)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Stickys()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='stickys' namespace='##targetNamespace'"
	 * @generated
	 */
	Tstickys getStickys();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getStickys <em>Stickys</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stickys</em>' containment reference.
	 * @see #getStickys()
	 * @generated
	 */
	void setStickys(Tstickys value);

	/**
	 * Returns the value of the '<em><b>Formalism</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formalism</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formalism</em>' attribute.
	 * @see #setFormalism(String)
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Formalism()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='formalism' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFormalism();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getFormalism <em>Formalism</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formalism</em>' attribute.
	 * @see #getFormalism()
	 * @generated
	 */
	void setFormalism(String value);

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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Xposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='xposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getXposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getXposition <em>Xposition</em>}' attribute.
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
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTmodel_Yposition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='yposition' namespace='##targetNamespace'"
	 * @generated
	 */
	String getYposition();

	/**
	 * Sets the value of the '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getYposition <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Yposition</em>' attribute.
	 * @see #getYposition()
	 * @generated
	 */
	void setYposition(String value);

} // Tmodel
