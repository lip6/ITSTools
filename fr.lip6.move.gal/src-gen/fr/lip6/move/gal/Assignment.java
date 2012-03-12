/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.Assignment#getVar <em>Var</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Assignment#getExpr <em>Expr</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Assignment#getAVar <em>AVar</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Assignment#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getAssignment()
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
   * @see fr.lip6.move.gal.GalPackage#getAssignment_Var()
   * @model containment="true"
   * @generated
   */
  VariableRef getVar();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Assignment#getVar <em>Var</em>}' containment reference.
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
   * @see #setExpr(IntExpression)
   * @see fr.lip6.move.gal.GalPackage#getAssignment_Expr()
   * @model containment="true"
   * @generated
   */
  IntExpression getExpr();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Assignment#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(IntExpression value);

  /**
   * Returns the value of the '<em><b>AVar</b></em>' containment reference list.
   * The list contents are of type {@link fr.lip6.move.gal.VariableRef}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>AVar</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>AVar</em>' containment reference list.
   * @see fr.lip6.move.gal.GalPackage#getAssignment_AVar()
   * @model containment="true"
   * @generated
   */
  EList<VariableRef> getAVar();

  /**
   * Returns the value of the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Index</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Index</em>' containment reference.
   * @see #setIndex(IntExpression)
   * @see fr.lip6.move.gal.GalPackage#getAssignment_Index()
   * @model containment="true"
   * @generated
   */
  IntExpression getIndex();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Assignment#getIndex <em>Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Index</em>' containment reference.
   * @see #getIndex()
   * @generated
   */
  void setIndex(IntExpression value);

} // Assignment
