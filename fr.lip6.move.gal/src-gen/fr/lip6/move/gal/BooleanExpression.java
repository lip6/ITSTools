/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.BooleanExpression#getLeft <em>Left</em>}</li>
 *   <li>{@link fr.lip6.move.gal.BooleanExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link fr.lip6.move.gal.BooleanExpression#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getBooleanExpression()
 * @model
 * @generated
 */
public interface BooleanExpression extends EObject
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(EObject)
   * @see fr.lip6.move.gal.GalPackage#getBooleanExpression_Left()
   * @model containment="true"
   * @generated
   */
  EObject getLeft();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.BooleanExpression#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(EObject value);

  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see #setOperator(String)
   * @see fr.lip6.move.gal.GalPackage#getBooleanExpression_Operator()
   * @model
   * @generated
   */
  String getOperator();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.BooleanExpression#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see #getOperator()
   * @generated
   */
  void setOperator(String value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(EObject)
   * @see fr.lip6.move.gal.GalPackage#getBooleanExpression_Right()
   * @model containment="true"
   * @generated
   */
  EObject getRight();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.BooleanExpression#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(EObject value);

} // BooleanExpression
