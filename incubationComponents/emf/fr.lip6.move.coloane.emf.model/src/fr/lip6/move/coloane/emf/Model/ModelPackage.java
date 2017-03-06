/**
 */
package fr.lip6.move.coloane.emf.Model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *    			Model description schema for Coloane.
 *    			LIP6 / MoVe - Jean-Baptiste Voron
 *    			http://coloane.lip6.fr
 *   		
 * <!-- end-model-doc -->
 * @see fr.lip6.move.coloane.emf.Model.ModelFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://coloane.lip6.fr/model/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.DocumentRootImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MODEL = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl <em>Tarc</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TarcImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTarc()
	 * @generated
	 */
	int TARC = 1;

	/**
	 * The feature id for the '<em><b>Tarcdesc</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC__TARCDESC = 0;

	/**
	 * The feature id for the '<em><b>Pi</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC__PI = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC__ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC__ID = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Tarc</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARC_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TarcsImpl <em>Tarcs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TarcsImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTarcs()
	 * @generated
	 */
	int TARCS = 2;

	/**
	 * The feature id for the '<em><b>Arc</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARCS__ARC = 0;

	/**
	 * The number of structural features of the '<em>Tarcs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARCS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TattributeImpl <em>Tattribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TattributeImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTattribute()
	 * @generated
	 */
	int TATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTE__XPOSITION = 2;

	/**
	 * The feature id for the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTE__YPOSITION = 3;

	/**
	 * The number of structural features of the '<em>Tattribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TattributesImpl <em>Tattributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TattributesImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTattributes()
	 * @generated
	 */
	int TATTRIBUTES = 4;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTES__ATTRIBUTE = 0;

	/**
	 * The number of structural features of the '<em>Tattributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TATTRIBUTES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TlinkImpl <em>Tlink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TlinkImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTlink()
	 * @generated
	 */
	int TLINK = 5;

	/**
	 * The feature id for the '<em><b>Link Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TLINK__LINK_ID = 0;

	/**
	 * The number of structural features of the '<em>Tlink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TLINK_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl <em>Tmodel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TmodelImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTmodel()
	 * @generated
	 */
	int TMODEL = 6;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__NODES = 0;

	/**
	 * The feature id for the '<em><b>Arcs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__ARCS = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Stickys</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__STICKYS = 3;

	/**
	 * The feature id for the '<em><b>Formalism</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__FORMALISM = 4;

	/**
	 * The feature id for the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__XPOSITION = 5;

	/**
	 * The feature id for the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL__YPOSITION = 6;

	/**
	 * The number of structural features of the '<em>Tmodel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMODEL_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl <em>Tnode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TnodeImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTnode()
	 * @generated
	 */
	int TNODE = 7;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE__ID = 1;

	/**
	 * The feature id for the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE__XPOSITION = 2;

	/**
	 * The feature id for the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE__YPOSITION = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Tnode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TnodesImpl <em>Tnodes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TnodesImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTnodes()
	 * @generated
	 */
	int TNODES = 8;

	/**
	 * The feature id for the '<em><b>Node</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODES__NODE = 0;

	/**
	 * The number of structural features of the '<em>Tnodes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNODES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TpiImpl <em>Tpi</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TpiImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTpi()
	 * @generated
	 */
	int TPI = 9;

	/**
	 * The feature id for the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TPI__XPOSITION = 0;

	/**
	 * The feature id for the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TPI__YPOSITION = 1;

	/**
	 * The number of structural features of the '<em>Tpi</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TPI_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl <em>Tsticky</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TstickyImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTsticky()
	 * @generated
	 */
	int TSTICKY = 10;

	/**
	 * The feature id for the '<em><b>Tstickydesc</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__TSTICKYDESC = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Link</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__LINK = 2;

	/**
	 * The feature id for the '<em><b>Xposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__XPOSITION = 3;

	/**
	 * The feature id for the '<em><b>Yposition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__YPOSITION = 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY__ANY_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>Tsticky</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKY_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link fr.lip6.move.coloane.emf.Model.impl.TstickysImpl <em>Tstickys</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.lip6.move.coloane.emf.Model.impl.TstickysImpl
	 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTstickys()
	 * @generated
	 */
	int TSTICKYS = 11;

	/**
	 * The feature id for the '<em><b>Sticky</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKYS__STICKY = 0;

	/**
	 * The number of structural features of the '<em>Tstickys</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TSTICKYS_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.DocumentRoot#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Model</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.DocumentRoot#getModel()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Model();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tarc <em>Tarc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tarc</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc
	 * @generated
	 */
	EClass getTarc();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tarc#getTarcdesc <em>Tarcdesc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Tarcdesc</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc#getTarcdesc()
	 * @see #getTarc()
	 * @generated
	 */
	EAttribute getTarc_Tarcdesc();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tarc#getPi <em>Pi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pi</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc#getPi()
	 * @see #getTarc()
	 * @generated
	 */
	EReference getTarc_Pi();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tarc#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc#getAttributes()
	 * @see #getTarc()
	 * @generated
	 */
	EReference getTarc_Attributes();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tarc#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc#getId()
	 * @see #getTarc()
	 * @generated
	 */
	EAttribute getTarc_Id();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tarc#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarc#getAnyAttribute()
	 * @see #getTarc()
	 * @generated
	 */
	EAttribute getTarc_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tarcs <em>Tarcs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tarcs</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarcs
	 * @generated
	 */
	EClass getTarcs();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tarcs#getArc <em>Arc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arc</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tarcs#getArc()
	 * @see #getTarcs()
	 * @generated
	 */
	EReference getTarcs_Arc();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tattribute <em>Tattribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tattribute</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute
	 * @generated
	 */
	EClass getTattribute();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute#getValue()
	 * @see #getTattribute()
	 * @generated
	 */
	EAttribute getTattribute_Value();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute#getName()
	 * @see #getTattribute()
	 * @generated
	 */
	EAttribute getTattribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getXposition <em>Xposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute#getXposition()
	 * @see #getTattribute()
	 * @generated
	 */
	EAttribute getTattribute_Xposition();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tattribute#getYposition <em>Yposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattribute#getYposition()
	 * @see #getTattribute()
	 * @generated
	 */
	EAttribute getTattribute_Yposition();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tattributes <em>Tattributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tattributes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattributes
	 * @generated
	 */
	EClass getTattributes();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tattributes#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tattributes#getAttribute()
	 * @see #getTattributes()
	 * @generated
	 */
	EReference getTattributes_Attribute();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tlink <em>Tlink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tlink</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tlink
	 * @generated
	 */
	EClass getTlink();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tlink#getLinkId <em>Link Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Link Id</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tlink#getLinkId()
	 * @see #getTlink()
	 * @generated
	 */
	EAttribute getTlink_LinkId();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tmodel <em>Tmodel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tmodel</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel
	 * @generated
	 */
	EClass getTmodel();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Nodes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getNodes()
	 * @see #getTmodel()
	 * @generated
	 */
	EReference getTmodel_Nodes();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getArcs <em>Arcs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Arcs</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getArcs()
	 * @see #getTmodel()
	 * @generated
	 */
	EReference getTmodel_Arcs();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getAttributes()
	 * @see #getTmodel()
	 * @generated
	 */
	EReference getTmodel_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getStickys <em>Stickys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stickys</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getStickys()
	 * @see #getTmodel()
	 * @generated
	 */
	EReference getTmodel_Stickys();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getFormalism <em>Formalism</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formalism</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getFormalism()
	 * @see #getTmodel()
	 * @generated
	 */
	EAttribute getTmodel_Formalism();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getXposition <em>Xposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getXposition()
	 * @see #getTmodel()
	 * @generated
	 */
	EAttribute getTmodel_Xposition();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tmodel#getYposition <em>Yposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tmodel#getYposition()
	 * @see #getTmodel()
	 * @generated
	 */
	EAttribute getTmodel_Yposition();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tnode <em>Tnode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tnode</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode
	 * @generated
	 */
	EClass getTnode();

	/**
	 * Returns the meta object for the containment reference '{@link fr.lip6.move.coloane.emf.Model.Tnode#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode#getAttributes()
	 * @see #getTnode()
	 * @generated
	 */
	EReference getTnode_Attributes();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tnode#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode#getId()
	 * @see #getTnode()
	 * @generated
	 */
	EAttribute getTnode_Id();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tnode#getXposition <em>Xposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode#getXposition()
	 * @see #getTnode()
	 * @generated
	 */
	EAttribute getTnode_Xposition();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tnode#getYposition <em>Yposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode#getYposition()
	 * @see #getTnode()
	 * @generated
	 */
	EAttribute getTnode_Yposition();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tnode#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnode#getAnyAttribute()
	 * @see #getTnode()
	 * @generated
	 */
	EAttribute getTnode_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tnodes <em>Tnodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tnodes</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnodes
	 * @generated
	 */
	EClass getTnodes();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tnodes#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tnodes#getNode()
	 * @see #getTnodes()
	 * @generated
	 */
	EReference getTnodes_Node();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tpi <em>Tpi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tpi</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tpi
	 * @generated
	 */
	EClass getTpi();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tpi#getXposition <em>Xposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tpi#getXposition()
	 * @see #getTpi()
	 * @generated
	 */
	EAttribute getTpi_Xposition();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tpi#getYposition <em>Yposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tpi#getYposition()
	 * @see #getTpi()
	 * @generated
	 */
	EAttribute getTpi_Yposition();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tsticky <em>Tsticky</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tsticky</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky
	 * @generated
	 */
	EClass getTsticky();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getTstickydesc <em>Tstickydesc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Tstickydesc</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getTstickydesc()
	 * @see #getTsticky()
	 * @generated
	 */
	EAttribute getTsticky_Tstickydesc();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getValue()
	 * @see #getTsticky()
	 * @generated
	 */
	EAttribute getTsticky_Value();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getLink()
	 * @see #getTsticky()
	 * @generated
	 */
	EReference getTsticky_Link();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getXposition <em>Xposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getXposition()
	 * @see #getTsticky()
	 * @generated
	 */
	EAttribute getTsticky_Xposition();

	/**
	 * Returns the meta object for the attribute '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getYposition <em>Yposition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yposition</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getYposition()
	 * @see #getTsticky()
	 * @generated
	 */
	EAttribute getTsticky_Yposition();

	/**
	 * Returns the meta object for the attribute list '{@link fr.lip6.move.coloane.emf.Model.Tsticky#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tsticky#getAnyAttribute()
	 * @see #getTsticky()
	 * @generated
	 */
	EAttribute getTsticky_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link fr.lip6.move.coloane.emf.Model.Tstickys <em>Tstickys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tstickys</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tstickys
	 * @generated
	 */
	EClass getTstickys();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.lip6.move.coloane.emf.Model.Tstickys#getSticky <em>Sticky</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sticky</em>'.
	 * @see fr.lip6.move.coloane.emf.Model.Tstickys#getSticky()
	 * @see #getTstickys()
	 * @generated
	 */
	EReference getTstickys_Sticky();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.DocumentRootImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MODEL = eINSTANCE.getDocumentRoot_Model();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl <em>Tarc</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TarcImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTarc()
		 * @generated
		 */
		EClass TARC = eINSTANCE.getTarc();

		/**
		 * The meta object literal for the '<em><b>Tarcdesc</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARC__TARCDESC = eINSTANCE.getTarc_Tarcdesc();

		/**
		 * The meta object literal for the '<em><b>Pi</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARC__PI = eINSTANCE.getTarc_Pi();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARC__ATTRIBUTES = eINSTANCE.getTarc_Attributes();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARC__ID = eINSTANCE.getTarc_Id();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARC__ANY_ATTRIBUTE = eINSTANCE.getTarc_AnyAttribute();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TarcsImpl <em>Tarcs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TarcsImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTarcs()
		 * @generated
		 */
		EClass TARCS = eINSTANCE.getTarcs();

		/**
		 * The meta object literal for the '<em><b>Arc</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARCS__ARC = eINSTANCE.getTarcs_Arc();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TattributeImpl <em>Tattribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TattributeImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTattribute()
		 * @generated
		 */
		EClass TATTRIBUTE = eINSTANCE.getTattribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TATTRIBUTE__VALUE = eINSTANCE.getTattribute_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TATTRIBUTE__NAME = eINSTANCE.getTattribute_Name();

		/**
		 * The meta object literal for the '<em><b>Xposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TATTRIBUTE__XPOSITION = eINSTANCE.getTattribute_Xposition();

		/**
		 * The meta object literal for the '<em><b>Yposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TATTRIBUTE__YPOSITION = eINSTANCE.getTattribute_Yposition();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TattributesImpl <em>Tattributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TattributesImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTattributes()
		 * @generated
		 */
		EClass TATTRIBUTES = eINSTANCE.getTattributes();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TATTRIBUTES__ATTRIBUTE = eINSTANCE.getTattributes_Attribute();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TlinkImpl <em>Tlink</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TlinkImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTlink()
		 * @generated
		 */
		EClass TLINK = eINSTANCE.getTlink();

		/**
		 * The meta object literal for the '<em><b>Link Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TLINK__LINK_ID = eINSTANCE.getTlink_LinkId();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl <em>Tmodel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TmodelImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTmodel()
		 * @generated
		 */
		EClass TMODEL = eINSTANCE.getTmodel();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMODEL__NODES = eINSTANCE.getTmodel_Nodes();

		/**
		 * The meta object literal for the '<em><b>Arcs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMODEL__ARCS = eINSTANCE.getTmodel_Arcs();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMODEL__ATTRIBUTES = eINSTANCE.getTmodel_Attributes();

		/**
		 * The meta object literal for the '<em><b>Stickys</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMODEL__STICKYS = eINSTANCE.getTmodel_Stickys();

		/**
		 * The meta object literal for the '<em><b>Formalism</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TMODEL__FORMALISM = eINSTANCE.getTmodel_Formalism();

		/**
		 * The meta object literal for the '<em><b>Xposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TMODEL__XPOSITION = eINSTANCE.getTmodel_Xposition();

		/**
		 * The meta object literal for the '<em><b>Yposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TMODEL__YPOSITION = eINSTANCE.getTmodel_Yposition();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl <em>Tnode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TnodeImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTnode()
		 * @generated
		 */
		EClass TNODE = eINSTANCE.getTnode();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TNODE__ATTRIBUTES = eINSTANCE.getTnode_Attributes();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TNODE__ID = eINSTANCE.getTnode_Id();

		/**
		 * The meta object literal for the '<em><b>Xposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TNODE__XPOSITION = eINSTANCE.getTnode_Xposition();

		/**
		 * The meta object literal for the '<em><b>Yposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TNODE__YPOSITION = eINSTANCE.getTnode_Yposition();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TNODE__ANY_ATTRIBUTE = eINSTANCE.getTnode_AnyAttribute();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TnodesImpl <em>Tnodes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TnodesImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTnodes()
		 * @generated
		 */
		EClass TNODES = eINSTANCE.getTnodes();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TNODES__NODE = eINSTANCE.getTnodes_Node();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TpiImpl <em>Tpi</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TpiImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTpi()
		 * @generated
		 */
		EClass TPI = eINSTANCE.getTpi();

		/**
		 * The meta object literal for the '<em><b>Xposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TPI__XPOSITION = eINSTANCE.getTpi_Xposition();

		/**
		 * The meta object literal for the '<em><b>Yposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TPI__YPOSITION = eINSTANCE.getTpi_Yposition();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl <em>Tsticky</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TstickyImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTsticky()
		 * @generated
		 */
		EClass TSTICKY = eINSTANCE.getTsticky();

		/**
		 * The meta object literal for the '<em><b>Tstickydesc</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TSTICKY__TSTICKYDESC = eINSTANCE.getTsticky_Tstickydesc();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TSTICKY__VALUE = eINSTANCE.getTsticky_Value();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TSTICKY__LINK = eINSTANCE.getTsticky_Link();

		/**
		 * The meta object literal for the '<em><b>Xposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TSTICKY__XPOSITION = eINSTANCE.getTsticky_Xposition();

		/**
		 * The meta object literal for the '<em><b>Yposition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TSTICKY__YPOSITION = eINSTANCE.getTsticky_Yposition();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TSTICKY__ANY_ATTRIBUTE = eINSTANCE.getTsticky_AnyAttribute();

		/**
		 * The meta object literal for the '{@link fr.lip6.move.coloane.emf.Model.impl.TstickysImpl <em>Tstickys</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.lip6.move.coloane.emf.Model.impl.TstickysImpl
		 * @see fr.lip6.move.coloane.emf.Model.impl.ModelPackageImpl#getTstickys()
		 * @generated
		 */
		EClass TSTICKYS = eINSTANCE.getTstickys();

		/**
		 * The meta object literal for the '<em><b>Sticky</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TSTICKYS__STICKY = eINSTANCE.getTstickys_Sticky();

	}

} //ModelPackage
