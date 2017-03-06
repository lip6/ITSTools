/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.ModelPackage;
import fr.lip6.move.coloane.emf.Model.Tlink;
import fr.lip6.move.coloane.emf.Model.Tsticky;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tsticky</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getTstickydesc <em>Tstickydesc</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getValue <em>Value</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getLink <em>Link</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getXposition <em>Xposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getYposition <em>Yposition</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TstickyImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TstickyImpl extends EObjectImpl implements Tsticky {
	/**
	 * The cached value of the '{@link #getTstickydesc() <em>Tstickydesc</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTstickydesc()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap tstickydesc;

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
	protected TstickyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TSTICKY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getTstickydesc() {
		if (tstickydesc == null) {
			tstickydesc = new BasicFeatureMap(this, ModelPackage.TSTICKY__TSTICKYDESC);
		}
		return tstickydesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getValue() {
		return getTstickydesc().list(ModelPackage.Literals.TSTICKY__VALUE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tlink> getLink() {
		return getTstickydesc().list(ModelPackage.Literals.TSTICKY__LINK);
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TSTICKY__XPOSITION, oldXposition, xposition));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TSTICKY__YPOSITION, oldYposition, yposition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, ModelPackage.TSTICKY__ANY_ATTRIBUTE);
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
			case ModelPackage.TSTICKY__TSTICKYDESC:
				return ((InternalEList<?>)getTstickydesc()).basicRemove(otherEnd, msgs);
			case ModelPackage.TSTICKY__LINK:
				return ((InternalEList<?>)getLink()).basicRemove(otherEnd, msgs);
			case ModelPackage.TSTICKY__ANY_ATTRIBUTE:
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
			case ModelPackage.TSTICKY__TSTICKYDESC:
				if (coreType) return getTstickydesc();
				return ((FeatureMap.Internal)getTstickydesc()).getWrapper();
			case ModelPackage.TSTICKY__VALUE:
				return getValue();
			case ModelPackage.TSTICKY__LINK:
				return getLink();
			case ModelPackage.TSTICKY__XPOSITION:
				return getXposition();
			case ModelPackage.TSTICKY__YPOSITION:
				return getYposition();
			case ModelPackage.TSTICKY__ANY_ATTRIBUTE:
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.TSTICKY__TSTICKYDESC:
				((FeatureMap.Internal)getTstickydesc()).set(newValue);
				return;
			case ModelPackage.TSTICKY__VALUE:
				getValue().clear();
				getValue().addAll((Collection<? extends String>)newValue);
				return;
			case ModelPackage.TSTICKY__LINK:
				getLink().clear();
				getLink().addAll((Collection<? extends Tlink>)newValue);
				return;
			case ModelPackage.TSTICKY__XPOSITION:
				setXposition((String)newValue);
				return;
			case ModelPackage.TSTICKY__YPOSITION:
				setYposition((String)newValue);
				return;
			case ModelPackage.TSTICKY__ANY_ATTRIBUTE:
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
			case ModelPackage.TSTICKY__TSTICKYDESC:
				getTstickydesc().clear();
				return;
			case ModelPackage.TSTICKY__VALUE:
				getValue().clear();
				return;
			case ModelPackage.TSTICKY__LINK:
				getLink().clear();
				return;
			case ModelPackage.TSTICKY__XPOSITION:
				setXposition(XPOSITION_EDEFAULT);
				return;
			case ModelPackage.TSTICKY__YPOSITION:
				setYposition(YPOSITION_EDEFAULT);
				return;
			case ModelPackage.TSTICKY__ANY_ATTRIBUTE:
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
			case ModelPackage.TSTICKY__TSTICKYDESC:
				return tstickydesc != null && !tstickydesc.isEmpty();
			case ModelPackage.TSTICKY__VALUE:
				return !getValue().isEmpty();
			case ModelPackage.TSTICKY__LINK:
				return !getLink().isEmpty();
			case ModelPackage.TSTICKY__XPOSITION:
				return XPOSITION_EDEFAULT == null ? xposition != null : !XPOSITION_EDEFAULT.equals(xposition);
			case ModelPackage.TSTICKY__YPOSITION:
				return YPOSITION_EDEFAULT == null ? yposition != null : !YPOSITION_EDEFAULT.equals(yposition);
			case ModelPackage.TSTICKY__ANY_ATTRIBUTE:
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
		result.append(" (tstickydesc: ");
		result.append(tstickydesc);
		result.append(", xposition: ");
		result.append(xposition);
		result.append(", yposition: ");
		result.append(yposition);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //TstickyImpl
