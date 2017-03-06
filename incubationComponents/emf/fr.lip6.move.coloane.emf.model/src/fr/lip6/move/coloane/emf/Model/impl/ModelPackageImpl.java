/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.DocumentRoot;
import fr.lip6.move.coloane.emf.Model.ModelFactory;
import fr.lip6.move.coloane.emf.Model.ModelPackage;
import fr.lip6.move.coloane.emf.Model.Tarc;
import fr.lip6.move.coloane.emf.Model.Tarcs;
import fr.lip6.move.coloane.emf.Model.Tattribute;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tlink;
import fr.lip6.move.coloane.emf.Model.Tmodel;
import fr.lip6.move.coloane.emf.Model.Tnode;
import fr.lip6.move.coloane.emf.Model.Tnodes;
import fr.lip6.move.coloane.emf.Model.Tpi;
import fr.lip6.move.coloane.emf.Model.Tsticky;
import fr.lip6.move.coloane.emf.Model.Tstickys;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tarcEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tarcsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tattributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tattributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tlinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tmodelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tnodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tnodesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tpiEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tstickyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tstickysEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see fr.lip6.move.coloane.emf.Model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Model() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTarc() {
		return tarcEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTarc_Tarcdesc() {
		return (EAttribute)tarcEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTarc_Pi() {
		return (EReference)tarcEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTarc_Attributes() {
		return (EReference)tarcEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTarc_Id() {
		return (EAttribute)tarcEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTarc_AnyAttribute() {
		return (EAttribute)tarcEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTarcs() {
		return tarcsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTarcs_Arc() {
		return (EReference)tarcsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTattribute() {
		return tattributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTattribute_Value() {
		return (EAttribute)tattributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTattribute_Name() {
		return (EAttribute)tattributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTattribute_Xposition() {
		return (EAttribute)tattributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTattribute_Yposition() {
		return (EAttribute)tattributeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTattributes() {
		return tattributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTattributes_Attribute() {
		return (EReference)tattributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTlink() {
		return tlinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTlink_LinkId() {
		return (EAttribute)tlinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTmodel() {
		return tmodelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTmodel_Nodes() {
		return (EReference)tmodelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTmodel_Arcs() {
		return (EReference)tmodelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTmodel_Attributes() {
		return (EReference)tmodelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTmodel_Stickys() {
		return (EReference)tmodelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTmodel_Formalism() {
		return (EAttribute)tmodelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTmodel_Xposition() {
		return (EAttribute)tmodelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTmodel_Yposition() {
		return (EAttribute)tmodelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTnode() {
		return tnodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTnode_Attributes() {
		return (EReference)tnodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTnode_Id() {
		return (EAttribute)tnodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTnode_Xposition() {
		return (EAttribute)tnodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTnode_Yposition() {
		return (EAttribute)tnodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTnode_AnyAttribute() {
		return (EAttribute)tnodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTnodes() {
		return tnodesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTnodes_Node() {
		return (EReference)tnodesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTpi() {
		return tpiEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTpi_Xposition() {
		return (EAttribute)tpiEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTpi_Yposition() {
		return (EAttribute)tpiEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTsticky() {
		return tstickyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTsticky_Tstickydesc() {
		return (EAttribute)tstickyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTsticky_Value() {
		return (EAttribute)tstickyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTsticky_Link() {
		return (EReference)tstickyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTsticky_Xposition() {
		return (EAttribute)tstickyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTsticky_Yposition() {
		return (EAttribute)tstickyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTsticky_AnyAttribute() {
		return (EAttribute)tstickyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTstickys() {
		return tstickysEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTstickys_Sticky() {
		return (EReference)tstickysEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MODEL);

		tarcEClass = createEClass(TARC);
		createEAttribute(tarcEClass, TARC__TARCDESC);
		createEReference(tarcEClass, TARC__PI);
		createEReference(tarcEClass, TARC__ATTRIBUTES);
		createEAttribute(tarcEClass, TARC__ID);
		createEAttribute(tarcEClass, TARC__ANY_ATTRIBUTE);

		tarcsEClass = createEClass(TARCS);
		createEReference(tarcsEClass, TARCS__ARC);

		tattributeEClass = createEClass(TATTRIBUTE);
		createEAttribute(tattributeEClass, TATTRIBUTE__VALUE);
		createEAttribute(tattributeEClass, TATTRIBUTE__NAME);
		createEAttribute(tattributeEClass, TATTRIBUTE__XPOSITION);
		createEAttribute(tattributeEClass, TATTRIBUTE__YPOSITION);

		tattributesEClass = createEClass(TATTRIBUTES);
		createEReference(tattributesEClass, TATTRIBUTES__ATTRIBUTE);

		tlinkEClass = createEClass(TLINK);
		createEAttribute(tlinkEClass, TLINK__LINK_ID);

		tmodelEClass = createEClass(TMODEL);
		createEReference(tmodelEClass, TMODEL__NODES);
		createEReference(tmodelEClass, TMODEL__ARCS);
		createEReference(tmodelEClass, TMODEL__ATTRIBUTES);
		createEReference(tmodelEClass, TMODEL__STICKYS);
		createEAttribute(tmodelEClass, TMODEL__FORMALISM);
		createEAttribute(tmodelEClass, TMODEL__XPOSITION);
		createEAttribute(tmodelEClass, TMODEL__YPOSITION);

		tnodeEClass = createEClass(TNODE);
		createEReference(tnodeEClass, TNODE__ATTRIBUTES);
		createEAttribute(tnodeEClass, TNODE__ID);
		createEAttribute(tnodeEClass, TNODE__XPOSITION);
		createEAttribute(tnodeEClass, TNODE__YPOSITION);
		createEAttribute(tnodeEClass, TNODE__ANY_ATTRIBUTE);

		tnodesEClass = createEClass(TNODES);
		createEReference(tnodesEClass, TNODES__NODE);

		tpiEClass = createEClass(TPI);
		createEAttribute(tpiEClass, TPI__XPOSITION);
		createEAttribute(tpiEClass, TPI__YPOSITION);

		tstickyEClass = createEClass(TSTICKY);
		createEAttribute(tstickyEClass, TSTICKY__TSTICKYDESC);
		createEAttribute(tstickyEClass, TSTICKY__VALUE);
		createEReference(tstickyEClass, TSTICKY__LINK);
		createEAttribute(tstickyEClass, TSTICKY__XPOSITION);
		createEAttribute(tstickyEClass, TSTICKY__YPOSITION);
		createEAttribute(tstickyEClass, TSTICKY__ANY_ATTRIBUTE);

		tstickysEClass = createEClass(TSTICKYS);
		createEReference(tstickysEClass, TSTICKYS__STICKY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Model(), this.getTmodel(), null, "model", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(tarcEClass, Tarc.class, "Tarc", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTarc_Tarcdesc(), ecorePackage.getEFeatureMapEntry(), "tarcdesc", null, 0, -1, Tarc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTarc_Pi(), this.getTpi(), null, "pi", null, 0, -1, Tarc.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTarc_Attributes(), this.getTattributes(), null, "attributes", null, 0, -1, Tarc.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getTarc_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, Tarc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTarc_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, Tarc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tarcsEClass, Tarcs.class, "Tarcs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTarcs_Arc(), this.getTarc(), null, "arc", null, 0, -1, Tarcs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tattributeEClass, Tattribute.class, "Tattribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTattribute_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, Tattribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTattribute_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Tattribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTattribute_Xposition(), theXMLTypePackage.getString(), "xposition", null, 0, 1, Tattribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTattribute_Yposition(), theXMLTypePackage.getString(), "yposition", null, 0, 1, Tattribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tattributesEClass, Tattributes.class, "Tattributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTattributes_Attribute(), this.getTattribute(), null, "attribute", null, 0, -1, Tattributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tlinkEClass, Tlink.class, "Tlink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTlink_LinkId(), theXMLTypePackage.getString(), "linkId", null, 1, 1, Tlink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tmodelEClass, Tmodel.class, "Tmodel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTmodel_Nodes(), this.getTnodes(), null, "nodes", null, 1, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTmodel_Arcs(), this.getTarcs(), null, "arcs", null, 1, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTmodel_Attributes(), this.getTattributes(), null, "attributes", null, 0, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTmodel_Stickys(), this.getTstickys(), null, "stickys", null, 0, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTmodel_Formalism(), theXMLTypePackage.getString(), "formalism", null, 1, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTmodel_Xposition(), theXMLTypePackage.getString(), "xposition", null, 0, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTmodel_Yposition(), theXMLTypePackage.getString(), "yposition", null, 0, 1, Tmodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tnodeEClass, Tnode.class, "Tnode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTnode_Attributes(), this.getTattributes(), null, "attributes", null, 0, 1, Tnode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTnode_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, Tnode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTnode_Xposition(), theXMLTypePackage.getString(), "xposition", null, 0, 1, Tnode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTnode_Yposition(), theXMLTypePackage.getString(), "yposition", null, 0, 1, Tnode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTnode_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, Tnode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tnodesEClass, Tnodes.class, "Tnodes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTnodes_Node(), this.getTnode(), null, "node", null, 0, -1, Tnodes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tpiEClass, Tpi.class, "Tpi", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTpi_Xposition(), theXMLTypePackage.getString(), "xposition", null, 0, 1, Tpi.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTpi_Yposition(), theXMLTypePackage.getString(), "yposition", null, 0, 1, Tpi.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tstickyEClass, Tsticky.class, "Tsticky", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTsticky_Tstickydesc(), ecorePackage.getEFeatureMapEntry(), "tstickydesc", null, 0, -1, Tsticky.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTsticky_Value(), theXMLTypePackage.getString(), "value", null, 0, -1, Tsticky.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTsticky_Link(), this.getTlink(), null, "link", null, 0, -1, Tsticky.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getTsticky_Xposition(), theXMLTypePackage.getString(), "xposition", null, 0, 1, Tsticky.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTsticky_Yposition(), theXMLTypePackage.getString(), "yposition", null, 0, 1, Tsticky.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTsticky_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, Tsticky.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tstickysEClass, Tstickys.class, "Tstickys", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTstickys_Sticky(), this.getTsticky(), null, "sticky", null, 0, -1, Tstickys.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "qualified", "false"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Model(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "model",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tarcEClass, 
		   source, 
		   new String[] {
			 "name", "tarc",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTarc_Tarcdesc(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "Tarcdesc:0"
		   });		
		addAnnotation
		  (getTarc_Pi(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "pi",
			 "namespace", "##targetNamespace",
			 "group", "Tarcdesc:0"
		   });		
		addAnnotation
		  (getTarc_Attributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attributes",
			 "namespace", "##targetNamespace",
			 "group", "Tarcdesc:0"
		   });		
		addAnnotation
		  (getTarc_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTarc_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##any",
			 "name", ":4",
			 "processing", "lax"
		   });		
		addAnnotation
		  (tarcsEClass, 
		   source, 
		   new String[] {
			 "name", "tarcs",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTarcs_Arc(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "arc",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tattributeEClass, 
		   source, 
		   new String[] {
			 "name", "tattribute",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getTattribute_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getTattribute_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTattribute_Xposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTattribute_Yposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "yposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tattributesEClass, 
		   source, 
		   new String[] {
			 "name", "tattributes",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTattributes_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attribute",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tlinkEClass, 
		   source, 
		   new String[] {
			 "name", "tlink",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getTlink_LinkId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "linkId",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tmodelEClass, 
		   source, 
		   new String[] {
			 "name", "tmodel",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTmodel_Nodes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "nodes",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Arcs(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "arcs",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Attributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attributes",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Stickys(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "stickys",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Formalism(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "formalism",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Xposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTmodel_Yposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "yposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tnodeEClass, 
		   source, 
		   new String[] {
			 "name", "tnode",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTnode_Attributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attributes",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTnode_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTnode_Xposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTnode_Yposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "yposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTnode_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##any",
			 "name", ":4",
			 "processing", "lax"
		   });		
		addAnnotation
		  (tnodesEClass, 
		   source, 
		   new String[] {
			 "name", "tnodes",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTnodes_Node(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "node",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tpiEClass, 
		   source, 
		   new String[] {
			 "name", "tpi",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getTpi_Xposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTpi_Yposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "yposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (tstickyEClass, 
		   source, 
		   new String[] {
			 "name", "tsticky",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTsticky_Tstickydesc(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "Tstickydesc:0"
		   });		
		addAnnotation
		  (getTsticky_Value(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "value",
			 "namespace", "##targetNamespace",
			 "group", "Tstickydesc:0"
		   });		
		addAnnotation
		  (getTsticky_Link(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "link",
			 "namespace", "##targetNamespace",
			 "group", "Tstickydesc:0"
		   });		
		addAnnotation
		  (getTsticky_Xposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTsticky_Yposition(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "yposition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTsticky_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##any",
			 "name", ":5",
			 "processing", "lax"
		   });		
		addAnnotation
		  (tstickysEClass, 
		   source, 
		   new String[] {
			 "name", "tstickys",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTstickys_Sticky(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sticky",
			 "namespace", "##targetNamespace"
		   });
	}

} //ModelPackageImpl
