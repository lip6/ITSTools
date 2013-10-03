/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.System#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getSystem()
 * @model
 * @generated
 */
public interface System extends EObject
{
  /**
   * Returns the value of the '<em><b>Instances</b></em>' reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.InstantiableInSystem}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instances</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Instances</em>' reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getSystem_Instances()
   * @model
   * @generated
   */
  EList<InstantiableInSystem> getInstances();

} // System
