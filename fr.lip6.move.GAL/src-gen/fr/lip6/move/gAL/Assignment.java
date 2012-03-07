/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gAL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gAL.Assignment#getVar <em>Var</em>}</li>
 *   <li>{@link fr.lip6.move.gAL.Assignment#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gAL.GALPackage#getAssignment()
 * @model
 * @generated
 */
public interface Assignment extends EObject
{
  /**
   * Returns the value of the '<em><b>Var</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Var</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var</em>' containment reference.
   * @see #setVar(VariableRef)
   * @see fr.lip6.move.gAL.GALPackage#getAssignment_Var()
   * @model containment="true"
   * @generated
   */
  VariableRef getVar();

  /**
   * Sets the value of the '{@link fr.lip6.move.gAL.Assignment#getVar <em>Var</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var</em>' containment reference.
   * @see #getVar()
   * @generated
   */
  void setVar(VariableRef value);

  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(Expression)
   * @see fr.lip6.move.gAL.GALPackage#getAssignment_Expr()
   * @model containment="true"
   * @generated
   */
  Expression getExpr();

  /**
   * Sets the value of the '{@link fr.lip6.move.gAL.Assignment#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(Expression value);

} // Assignment
