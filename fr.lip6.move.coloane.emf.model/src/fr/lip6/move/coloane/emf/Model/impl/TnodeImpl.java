/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.ModelPackage;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tnode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tnode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl#getYposition <em>Yposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TnodeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TnodeImpl extends EObjectImpl implements Tnode {
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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

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
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TnodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TNODE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TNODE__ATTRIBUTES, oldAttributes, newAttributes);
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
				msgs = ((InternalEObject)attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TNODE__ATTRIBUTES, null, msgs);
			if (newAttributes != null)
				msgs = ((InternalEObject)newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TNODE__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TNODE__ATTRIBUTES, newAttributes, newAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TNODE__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TNODE__XPOSITION, oldXposition, xposition));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TNODE__YPOSITION, oldYposition, yposition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, ModelPackage.TNODE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TNODE__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
			case ModelPackage.TNODE__ANY_ATTRIBUTE:
				return ((InternalEList<?>)getAnyAttribute()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.TNODE__ATTRIBUTES:
				return getAttributes();
			case ModelPackage.TNODE__ID:
				return getId();
			case ModelPackage.TNODE__XPOSITION:
				return getXposition();
			case ModelPackage.TNODE__YPOSITION:
				return getYposition();
			case ModelPackage.TNODE__ANY_ATTRIBUTE:
				if (coreType) return getAnyAttribute();
				return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
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
			case ModelPackage.TNODE__ATTRIBUTES:
				setAttributes((Tattributes)newValue);
				return;
			case ModelPackage.TNODE__ID:
				setId((String)newValue);
				return;
			case ModelPackage.TNODE__XPOSITION:
				setXposition((String)newValue);
				return;
			case ModelPackage.TNODE__YPOSITION:
				setYposition((String)newValue);
				return;
			case ModelPackage.TNODE__ANY_ATTRIBUTE:
				((FeatureMap.Internal)getAnyAttribute()).set(newValue);
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
			case ModelPackage.TNODE__ATTRIBUTES:
				setAttributes((Tattributes)null);
				return;
			case ModelPackage.TNODE__ID:
				setId(ID_EDEFAULT);
				return;
			case ModelPackage.TNODE__XPOSITION:
				setXposition(XPOSITION_EDEFAULT);
				return;
			case ModelPackage.TNODE__YPOSITION:
				setYposition(YPOSITION_EDEFAULT);
				return;
			case ModelPackage.TNODE__ANY_ATTRIBUTE:
				getAnyAttribute().clear();
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
			case ModelPackage.TNODE__ATTRIBUTES:
				return attributes != null;
			case ModelPackage.TNODE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ModelPackage.TNODE__XPOSITION:
				return XPOSITION_EDEFAULT == null ? xposition != null : !XPOSITION_EDEFAULT.equals(xposition);
			case ModelPackage.TNODE__YPOSITION:
				return YPOSITION_EDEFAULT == null ? yposition != null : !YPOSITION_EDEFAULT.equals(yposition);
			case ModelPackage.TNODE__ANY_ATTRIBUTE:
				return anyAttribute != null && !anyAttribute.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", xposition: ");
		result.append(xposition);
		result.append(", yposition: ");
		result.append(yposition);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //TnodeImpl
