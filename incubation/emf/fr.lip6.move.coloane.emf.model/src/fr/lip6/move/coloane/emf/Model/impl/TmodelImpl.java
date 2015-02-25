/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.ModelPackage;
import fr.lip6.move.coloane.emf.Model.Tarcs;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tmodel;
import fr.lip6.move.coloane.emf.Model.Tnodes;
import fr.lip6.move.coloane.emf.Model.Tstickys;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tmodel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getArcs <em>Arcs</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getStickys <em>Stickys</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getFormalism <em>Formalism</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TmodelImpl#getYposition <em>Yposition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TmodelImpl extends EObjectImpl implements Tmodel {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected Tnodes nodes;

	/**
	 * The cached value of the '{@link #getArcs() <em>Arcs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcs()
	 * @generated
	 * @ordered
	 */
	protected Tarcs arcs;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected Tattributes attributes;

	/**
	 * The cached value of the '{@link #getStickys() <em>Stickys</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStickys()
	 * @generated
	 * @ordered
	 */
	protected Tstickys stickys;

	/**
	 * The default value of the '{@link #getFormalism() <em>Formalism</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormalism()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMALISM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormalism() <em>Formalism</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormalism()
	 * @generated
	 * @ordered
	 */
	protected String formalism = FORMALISM_EDEFAULT;

	/**
	 * The default value of the '{@link #getXposition() <em>Xposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXposition()
	 * @generated
	 * @ordered
	 */
	protected static final String XPOSITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXposition() <em>Xposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXposition()
	 * @generated
	 * @ordered
	 */
	protected String xposition = XPOSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getYposition() <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYposition()
	 * @generated
	 * @ordered
	 */
	protected static final String YPOSITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getYposition() <em>Yposition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYposition()
	 * @generated
	 * @ordered
	 */
	protected String yposition = YPOSITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TmodelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TMODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tnodes getNodes() {
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNodes(Tnodes newNodes, NotificationChain msgs) {
		Tnodes oldNodes = nodes;
		nodes = newNodes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__NODES, oldNodes, newNodes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNodes(Tnodes newNodes) {
		if (newNodes != nodes) {
			NotificationChain msgs = null;
			if (nodes != null)
				msgs = ((InternalEObject)nodes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__NODES, null, msgs);
			if (newNodes != null)
				msgs = ((InternalEObject)newNodes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__NODES, null, msgs);
			msgs = basicSetNodes(newNodes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__NODES, newNodes, newNodes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tarcs getArcs() {
		return arcs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArcs(Tarcs newArcs, NotificationChain msgs) {
		Tarcs oldArcs = arcs;
		arcs = newArcs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__ARCS, oldArcs, newArcs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArcs(Tarcs newArcs) {
		if (newArcs != arcs) {
			NotificationChain msgs = null;
			if (arcs != null)
				msgs = ((InternalEObject)arcs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__ARCS, null, msgs);
			if (newArcs != null)
				msgs = ((InternalEObject)newArcs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__ARCS, null, msgs);
			msgs = basicSetArcs(newArcs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__ARCS, newArcs, newArcs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tattributes getAttributes() {
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributes(Tattributes newAttributes, NotificationChain msgs) {
		Tattributes oldAttributes = attributes;
		attributes = newAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__ATTRIBUTES, oldAttributes, newAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributes(Tattributes newAttributes) {
		if (newAttributes != attributes) {
			NotificationChain msgs = null;
			if (attributes != null)
				msgs = ((InternalEObject)attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__ATTRIBUTES, null, msgs);
			if (newAttributes != null)
				msgs = ((InternalEObject)newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__ATTRIBUTES, newAttributes, newAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tstickys getStickys() {
		return stickys;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStickys(Tstickys newStickys, NotificationChain msgs) {
		Tstickys oldStickys = stickys;
		stickys = newStickys;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__STICKYS, oldStickys, newStickys);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStickys(Tstickys newStickys) {
		if (newStickys != stickys) {
			NotificationChain msgs = null;
			if (stickys != null)
				msgs = ((InternalEObject)stickys).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__STICKYS, null, msgs);
			if (newStickys != null)
				msgs = ((InternalEObject)newStickys).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TMODEL__STICKYS, null, msgs);
			msgs = basicSetStickys(newStickys, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__STICKYS, newStickys, newStickys));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFormalism() {
		return formalism;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormalism(String newFormalism) {
		String oldFormalism = formalism;
		formalism = newFormalism;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__FORMALISM, oldFormalism, formalism));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getXposition() {
		return xposition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXposition(String newXposition) {
		String oldXposition = xposition;
		xposition = newXposition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__XPOSITION, oldXposition, xposition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getYposition() {
		return yposition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setYposition(String newYposition) {
		String oldYposition = yposition;
		yposition = newYposition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TMODEL__YPOSITION, oldYposition, yposition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TMODEL__NODES:
				return basicSetNodes(null, msgs);
			case ModelPackage.TMODEL__ARCS:
				return basicSetArcs(null, msgs);
			case ModelPackage.TMODEL__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
			case ModelPackage.TMODEL__STICKYS:
				return basicSetStickys(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.TMODEL__NODES:
				return getNodes();
			case ModelPackage.TMODEL__ARCS:
				return getArcs();
			case ModelPackage.TMODEL__ATTRIBUTES:
				return getAttributes();
			case ModelPackage.TMODEL__STICKYS:
				return getStickys();
			case ModelPackage.TMODEL__FORMALISM:
				return getFormalism();
			case ModelPackage.TMODEL__XPOSITION:
				return getXposition();
			case ModelPackage.TMODEL__YPOSITION:
				return getYposition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.TMODEL__NODES:
				setNodes((Tnodes)newValue);
				return;
			case ModelPackage.TMODEL__ARCS:
				setArcs((Tarcs)newValue);
				return;
			case ModelPackage.TMODEL__ATTRIBUTES:
				setAttributes((Tattributes)newValue);
				return;
			case ModelPackage.TMODEL__STICKYS:
				setStickys((Tstickys)newValue);
				return;
			case ModelPackage.TMODEL__FORMALISM:
				setFormalism((String)newValue);
				return;
			case ModelPackage.TMODEL__XPOSITION:
				setXposition((String)newValue);
				return;
			case ModelPackage.TMODEL__YPOSITION:
				setYposition((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.TMODEL__NODES:
				setNodes((Tnodes)null);
				return;
			case ModelPackage.TMODEL__ARCS:
				setArcs((Tarcs)null);
				return;
			case ModelPackage.TMODEL__ATTRIBUTES:
				setAttributes((Tattributes)null);
				return;
			case ModelPackage.TMODEL__STICKYS:
				setStickys((Tstickys)null);
				return;
			case ModelPackage.TMODEL__FORMALISM:
				setFormalism(FORMALISM_EDEFAULT);
				return;
			case ModelPackage.TMODEL__XPOSITION:
				setXposition(XPOSITION_EDEFAULT);
				return;
			case ModelPackage.TMODEL__YPOSITION:
				setYposition(YPOSITION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.TMODEL__NODES:
				return nodes != null;
			case ModelPackage.TMODEL__ARCS:
				return arcs != null;
			case ModelPackage.TMODEL__ATTRIBUTES:
				return attributes != null;
			case ModelPackage.TMODEL__STICKYS:
				return stickys != null;
			case ModelPackage.TMODEL__FORMALISM:
				return FORMALISM_EDEFAULT == null ? formalism != null : !FORMALISM_EDEFAULT.equals(formalism);
			case ModelPackage.TMODEL__XPOSITION:
				return XPOSITION_EDEFAULT == null ? xposition != null : !XPOSITION_EDEFAULT.equals(xposition);
			case ModelPackage.TMODEL__YPOSITION:
				return YPOSITION_EDEFAULT == null ? yposition != null : !YPOSITION_EDEFAULT.equals(yposition);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (formalism: ");
		result.append(formalism);
		result.append(", xposition: ");
		result.append(xposition);
		result.append(", yposition: ");
		result.append(yposition);
		result.append(')');
		return result.toString();
	}

} //TmodelImpl
