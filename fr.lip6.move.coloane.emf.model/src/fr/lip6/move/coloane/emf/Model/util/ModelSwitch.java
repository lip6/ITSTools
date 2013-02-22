/**
 */
package fr.lip6.move.coloane.emf.Model.util;

import fr.lip6.move.coloane.emf.Model.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ModelPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TARC: {
				Tarc tarc = (Tarc)theEObject;
				T result = caseTarc(tarc);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TARCS: {
				Tarcs tarcs = (Tarcs)theEObject;
				T result = caseTarcs(tarcs);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TATTRIBUTE: {
				Tattribute tattribute = (Tattribute)theEObject;
				T result = caseTattribute(tattribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TATTRIBUTES: {
				Tattributes tattributes = (Tattributes)theEObject;
				T result = caseTattributes(tattributes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TLINK: {
				Tlink tlink = (Tlink)theEObject;
				T result = caseTlink(tlink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TMODEL: {
				Tmodel tmodel = (Tmodel)theEObject;
				T result = caseTmodel(tmodel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TNODE: {
				Tnode tnode = (Tnode)theEObject;
				T result = caseTnode(tnode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TNODES: {
				Tnodes tnodes = (Tnodes)theEObject;
				T result = caseTnodes(tnodes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TPI: {
				Tpi tpi = (Tpi)theEObject;
				T result = caseTpi(tpi);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TSTICKY: {
				Tsticky tsticky = (Tsticky)theEObject;
				T result = caseTsticky(tsticky);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TSTICKYS: {
				Tstickys tstickys = (Tstickys)theEObject;
				T result = caseTstickys(tstickys);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tarc</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tarc</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTarc(Tarc object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tarcs</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tarcs</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTarcs(Tarcs object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tattribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tattribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTattribute(Tattribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tattributes</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tattributes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTattributes(Tattributes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tlink</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tlink</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTlink(Tlink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tmodel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tmodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTmodel(Tmodel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tnode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tnode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTnode(Tnode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tnodes</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tnodes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTnodes(Tnodes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tpi</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tpi</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTpi(Tpi object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tsticky</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tsticky</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTsticky(Tsticky object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tstickys</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tstickys</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTstickys(Tstickys object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ModelSwitch
