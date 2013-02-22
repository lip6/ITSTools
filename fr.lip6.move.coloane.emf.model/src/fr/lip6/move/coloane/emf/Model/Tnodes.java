/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tnodes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.Tnodes#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTnodes()
 * @model extendedMetaData="name='tnodes' kind='elementOnly'"
 * @generated
 */
public interface Tnodes extends EObject {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' containment reference list.
	 * The list contents are of type {@link fr.lip6.move.coloane.emf.Model.Tnode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' containment reference list.
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#getTnodes_Node()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='node' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Tnode> getNode();

} // Tnodes
