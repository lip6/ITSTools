/**
 */
package fr.lip6.move.coloane.emf.Model.util;

import fr.lip6.move.coloane.emf.Model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSwitch<Adapter> modelSwitch =
		new ModelSwitch<Adapter>() {
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseTarc(Tarc object) {
				return createTarcAdapter();
			}
			@Override
			public Adapter caseTarcs(Tarcs object) {
				return createTarcsAdapter();
			}
			@Override
			public Adapter caseTattribute(Tattribute object) {
				return createTattributeAdapter();
			}
			@Override
			public Adapter caseTattributes(Tattributes object) {
				return createTattributesAdapter();
			}
			@Override
			public Adapter caseTlink(Tlink object) {
				return createTlinkAdapter();
			}
			@Override
			public Adapter caseTmodel(Tmodel object) {
				return createTmodelAdapter();
			}
			@Override
			public Adapter caseTnode(Tnode object) {
				return createTnodeAdapter();
			}
			@Override
			public Adapter caseTnodes(Tnodes object) {
				return createTnodesAdapter();
			}
			@Override
			public Adapter caseTpi(Tpi object) {
				return createTpiAdapter();
			}
			@Override
			public Adapter caseTsticky(Tsticky object) {
				return createTstickyAdapter();
			}
			@Override
			public Adapter caseTstickys(Tstickys object) {
				return createTstickysAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tarc <em>Tarc</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc
	 * @generated
	 */
	public Adapter createTarcAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tarcs <em>Tarcs</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tarcs
	 * @generated
	 */
	public Adapter createTarcsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tattribute <em>Tattribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute
	 * @generated
	 */
	public Adapter createTattributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tattributes <em>Tattributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tattributes
	 * @generated
	 */
	public Adapter createTattributesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tlink <em>Tlink</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tlink
	 * @generated
	 */
	public Adapter createTlinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tmodel <em>Tmodel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel
	 * @generated
	 */
	public Adapter createTmodelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tnode <em>Tnode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode
	 * @generated
	 */
	public Adapter createTnodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tnodes <em>Tnodes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tnodes
	 * @generated
	 */
	public Adapter createTnodesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tpi <em>Tpi</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tpi
	 * @generated
	 */
	public Adapter createTpiAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tsticky <em>Tsticky</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky
	 * @generated
	 */
	public Adapter createTstickyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.lip6.move.coloane.emf.Model.Tstickys <em>Tstickys</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.lip6.move.coloane.emf.Model.Tstickys
	 * @generated
	 */
	public Adapter createTstickysAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ModelAdapterFactory
