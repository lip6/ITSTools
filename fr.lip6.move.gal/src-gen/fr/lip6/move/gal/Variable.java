/**
 * <copyright>
 * </copyright>
 *
 */
package fr.lip6.move.gal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.lip6.move.gal.Variable#getName <em>Name</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Variable#getInitVal <em>Init Val</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Variable#getAname <em>Aname</em>}</li>
 *   <li>{@link fr.lip6.move.gal.Variable#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.lip6.move.gal.GalPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see fr.lip6.move.gal.GalPackage#getVariable_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Variable#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Init Val</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Init Val</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Init Val</em>' attribute.
   * @see #setInitVal(int)
   * @see fr.lip6.move.gal.GalPackage#getVariable_InitVal()
   * @model
   * @generated
   */
  int getInitVal();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Variable#getInitVal <em>Init Val</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Init Val</em>' attribute.
   * @see #getInitVal()
   * @generated
   */
  void setInitVal(int value);

  /**
   * Returns the value of the '<em><b>Aname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Aname</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Aname</em>' attribute.
   * @see #setAname(String)
   * @see fr.lip6.move.gal.GalPackage#getVariable_Aname()
   * @model
   * @generated
   */
  String getAname();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Variable#getAname <em>Aname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Aname</em>' attribute.
   * @see #getAname()
   * @generated
   */
  void setAname(String value);

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
   * @see fr.lip6.move.gal.GalPackage#getVariable_Index()
   * @model containment="true"
   * @generated
   */
  IntExpression getIndex();

  /**
   * Sets the value of the '{@link fr.lip6.move.gal.Variable#getIndex <em>Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Index</em>' containment reference.
   * @see #getIndex()
   * @generated
   */
  void setIndex(IntExpression value);

} // Variable
