/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://coloane.lip6.fr/model/1.0"); 
			if (theModelFactory != null) {
				return theModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ModelPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case ModelPackage.TARC: return createTarc();
			case ModelPackage.TARCS: return createTarcs();
			case ModelPackage.TATTRIBUTE: return createTattribute();
			case ModelPackage.TATTRIBUTES: return createTattributes();
			case ModelPackage.TLINK: return createTlink();
			case ModelPackage.TMODEL: return createTmodel();
			case ModelPackage.TNODE: return createTnode();
			case ModelPackage.TNODES: return createTnodes();
			case ModelPackage.TPI: return createTpi();
			case ModelPackage.TSTICKY: return createTsticky();
			case ModelPackage.TSTICKYS: return createTstickys();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tarc createTarc() {
		TarcImpl tarc = new TarcImpl();
		return tarc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tarcs createTarcs() {
		TarcsImpl tarcs = new TarcsImpl();
		return tarcs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tattribute createTattribute() {
		TattributeImpl tattribute = new TattributeImpl();
		return tattribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tattributes createTattributes() {
		TattributesImpl tattributes = new TattributesImpl();
		return tattributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tlink createTlink() {
		TlinkImpl tlink = new TlinkImpl();
		return tlink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tmodel createTmodel() {
		TmodelImpl tmodel = new TmodelImpl();
		return tmodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tnode createTnode() {
		TnodeImpl tnode = new TnodeImpl();
		return tnode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tnodes createTnodes() {
		TnodesImpl tnodes = new TnodesImpl();
		return tnodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tpi createTpi() {
		TpiImpl tpi = new TpiImpl();
		return tpi;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tsticky createTsticky() {
		TstickyImpl tsticky = new TstickyImpl();
		return tsticky;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tstickys createTstickys() {
		TstickysImpl tstickys = new TstickysImpl();
		return tstickys;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelPackage getModelPackage() {
		return (ModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl
