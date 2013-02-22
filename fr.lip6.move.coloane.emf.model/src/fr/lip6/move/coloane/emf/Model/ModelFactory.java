/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.coloane.emf.Model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelFactory eINSTANCE = fr.lip6.move.coloane.emf.Model.impl.ModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Tarc</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tarc</em>'.
	 * @generated
	 */
	Tarc createTarc();

	/**
	 * Returns a new object of class '<em>Tarcs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tarcs</em>'.
	 * @generated
	 */
	Tarcs createTarcs();

	/**
	 * Returns a new object of class '<em>Tattribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tattribute</em>'.
	 * @generated
	 */
	Tattribute createTattribute();

	/**
	 * Returns a new object of class '<em>Tattributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tattributes</em>'.
	 * @generated
	 */
	Tattributes createTattributes();

	/**
	 * Returns a new object of class '<em>Tlink</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tlink</em>'.
	 * @generated
	 */
	Tlink createTlink();

	/**
	 * Returns a new object of class '<em>Tmodel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tmodel</em>'.
	 * @generated
	 */
	Tmodel createTmodel();

	/**
	 * Returns a new object of class '<em>Tnode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tnode</em>'.
	 * @generated
	 */
	Tnode createTnode();

	/**
	 * Returns a new object of class '<em>Tnodes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tnodes</em>'.
	 * @generated
	 */
	Tnodes createTnodes();

	/**
	 * Returns a new object of class '<em>Tpi</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tpi</em>'.
	 * @generated
	 */
	Tpi createTpi();

	/**
	 * Returns a new object of class '<em>Tsticky</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tsticky</em>'.
	 * @generated
	 */
	Tsticky createTsticky();

	/**
	 * Returns a new object of class '<em>Tstickys</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tstickys</em>'.
	 * @generated
	 */
	Tstickys createTstickys();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelPackage getModelPackage();

} //ModelFactory
