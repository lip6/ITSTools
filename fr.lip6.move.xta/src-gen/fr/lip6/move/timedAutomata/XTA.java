/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XTA</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getVariables <em>Variables</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getChannels <em>Channels</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getTypes <em>Types</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getTemplates <em>Templates</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getInstances <em>Instances</em>}</li>
 *   <li>{@link fr.lip6.move.timedAutomata.XTA#getSystem <em>System</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA()
 * @model
 * @generated
 */
public interface XTA extends EObject
{
  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.VariableDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_Variables()
   * @model containment="true"
   * @generated
   */
  EList<VariableDecl> getVariables();

  /**
   * Returns the value of the '<em><b>Channels</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.ChannelDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Channels</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Channels</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_Channels()
   * @model containment="true"
   * @generated
   */
  EList<ChannelDecl> getChannels();

  /**
   * Returns the value of the '<em><b>Types</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.TypeDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Types</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_Types()
   * @model containment="true"
   * @generated
   */
  EList<TypeDecl> getTypes();

  /**
   * Returns the value of the '<em><b>Templates</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.ProcDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Templates</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Templates</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_Templates()
   * @model containment="true"
   * @generated
   */
  EList<ProcDecl> getTemplates();

  /**
   * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.timedAutomata.Instance}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instances</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Instances</em>' containment reference list.
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_Instances()
   * @model containment="true"
   * @generated
   */
  EList<Instance> getInstances();

  /**
   * Returns the value of the '<em><b>System</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>System</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>System</em>' containment reference.
   * @see #setSystem(fr.lip6.move.timedAutomata.System)
   * @see fr.lip6.move.timedAutomata.TimedAutomataPackage#getXTA_System()
   * @model containment="true"
   * @generated
   */
  fr.lip6.move.timedAutomata.System getSystem();

  /**
   * Sets the value of the '{@link fr.lip6.move.timedAutomata.XTA#getSystem <em>System</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>System</em>' containment reference.
   * @see #getSystem()
   * @generated
   */
  void setSystem(fr.lip6.move.timedAutomata.System value);

} // XTA
