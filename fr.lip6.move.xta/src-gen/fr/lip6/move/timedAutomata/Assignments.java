/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignments</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.Assignments#getAssigns <em>Assigns</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAssignments()
 * @model
 * @generated
 */
public interface Assignments extends EObject
{
  /**
   * Returns the value of the '<em><b>Assigns</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.Assign}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assigns</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assigns</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getAssignments_Assigns()
   * @model containment="true"
   * @generated
   */
  EList<Assign> getAssigns();

} // Assignments
