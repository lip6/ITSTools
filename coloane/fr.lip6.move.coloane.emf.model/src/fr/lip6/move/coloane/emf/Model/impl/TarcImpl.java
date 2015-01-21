/**
 */
package fr.lip6.move.coloane.emf.Model.impl;

import fr.lip6.move.coloane.emf.Model.ModelPackage;
import fr.lip6.move.coloane.emf.Model.Tarc;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tpi;

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
 * An implementation of the model object '<em><b>Tarc</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl#getTarcdesc <em>Tarcdesc</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl#getPi <em>Pi</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl#getId <em>Id</em>}</li>
 *   <li>{@link fr.lip6.move.coloane.emf.Model.impl.TarcImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TarcImpl extends EObjectImpl implements Tarc {
	/**
	 * The cached value of the '{@link #getTarcdesc() <em>Tarcdesc</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarcdesc()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap tarcdesc;

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
	protected TarcImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TARC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getTarcdesc() {
		if (tarcdesc == null) {
			tarcdesc = new BasicFeatureMap(this, ModelPackage.TARC__TARCDESC);
		}
		return tarcdesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tpi> getPi() {
		return getTarcdesc().list(ModelPackage.Literals.TARC__PI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tattributes> getAttributes() {
		return getTarcdesc().list(ModelPackage.Literals.TARC__ATTRIBUTES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TARC__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, ModelPackage.TARC__ANY_ATTRIBUTE);
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
			case ModelPackage.TARC__TARCDESC:
				return ((InternalEList<?>)getTarcdesc()).basicRemove(otherEnd, msgs);
			case ModelPackage.TARC__PI:
				return ((InternalEList<?>)getPi()).basicRemove(otherEnd, msgs);
			case ModelPackage.TARC__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case ModelPackage.TARC__ANY_ATTRIBUTE:
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
			case ModelPackage.TARC__TARCDESC:
				if (coreType) return getTarcdesc();
				return ((FeatureMap.Internal)getTarcdesc()).getWrapper();
			case ModelPackage.TARC__PI:
				return getPi();
			case ModelPackage.TARC__ATTRIBUTES:
				return getAttributes();
			case ModelPackage.TARC__ID:
				return getId();
			case ModelPackage.TARC__ANY_ATTRIBUTE:
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
			case ModelPackage.TARC__TARCDESC:
				((FeatureMap.Internal)getTarcdesc()).set(newValue);
				return;
			case ModelPackage.TARC__PI:
				getPi().clear();
				getPi().addAll((Collection<? extends Tpi>)newValue);
				return;
			case ModelPackage.TARC__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Tattributes>)newValue);
				return;
			case ModelPackage.TARC__ID:
				setId((String)newValue);
				return;
			case ModelPackage.TARC__ANY_ATTRIBUTE:
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
			case ModelPackage.TARC__TARCDESC:
				getTarcdesc().clear();
				return;
			case ModelPackage.TARC__PI:
				getPi().clear();
				return;
			case ModelPackage.TARC__ATTRIBUTES:
				getAttributes().clear();
				return;
			case ModelPackage.TARC__ID:
				setId(ID_EDEFAULT);
				return;
			case ModelPackage.TARC__ANY_ATTRIBUTE:
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
			case ModelPackage.TARC__TARCDESC:
				return tarcdesc != null && !tarcdesc.isEmpty();
			case ModelPackage.TARC__PI:
				return !getPi().isEmpty();
			case ModelPackage.TARC__ATTRIBUTES:
				return !getAttributes().isEmpty();
			case ModelPackage.TARC__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ModelPackage.TARC__ANY_ATTRIBUTE:
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
		result.append(" (tarcdesc: ");
		result.append(tarcdesc);
		result.append(", id: ");
		result.append(id);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //TarcImpl
