/**
 */
package fr.lip6.move.timedAutomata;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.lip6.move.timedAutomata.TimedAutomataPackage
 * @generated
 */
public interface TimedAutomataFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TimedAutomataFactory eINSTANCE = fr.lip6.move.timedAutomata.impl.TimedAutomataFactoryImpl.init();

  /**
   * Returns a new object of class '<em>XTA</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>XTA</em>'.
   * @generated
   */
  XTA createXTA();

  /**
   * Returns a new object of class '<em>Instantiable In System</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instantiable In System</em>'.
   * @generated
   */
  InstantiableInSystem createInstantiableInSystem();

  /**
   * Returns a new object of class '<em>Instance</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Instance</em>'.
   * @generated
   */
  Instance createInstance();

  /**
   * Returns a new object of class '<em>System</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>System</em>'.
   * @generated
   */
  System createSystem();

  /**
   * Returns a new object of class '<em>Proc Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Proc Decl</em>'.
   * @generated
   */
  ProcDecl createProcDecl();

  /**
   * Returns a new object of class '<em>Formal Declaration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Formal Declaration</em>'.
   * @generated
   */
  FormalDeclaration createFormalDeclaration();

  /**
   * Returns a new object of class '<em>Parameter</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parameter</em>'.
   * @generated
   */
  Parameter createParameter();

  /**
   * Returns a new object of class '<em>Type Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Decl</em>'.
   * @generated
   */
  TypeDecl createTypeDecl();

  /**
   * Returns a new object of class '<em>Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type</em>'.
   * @generated
   */
  Type createType();

  /**
   * Returns a new object of class '<em>Basic Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Basic Type</em>'.
   * @generated
   */
  BasicType createBasicType();

  /**
   * Returns a new object of class '<em>Typedef Ref</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Typedef Ref</em>'.
   * @generated
   */
  TypedefRef createTypedefRef();

  /**
   * Returns a new object of class '<em>Channel Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Channel Decl</em>'.
   * @generated
   */
  ChannelDecl createChannelDecl();

  /**
   * Returns a new object of class '<em>Chan Id</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Chan Id</em>'.
   * @generated
   */
  ChanId createChanId();

  /**
   * Returns a new object of class '<em>Channel Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Channel Type</em>'.
   * @generated
   */
  ChannelType createChannelType();

  /**
   * Returns a new object of class '<em>Integer Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Integer Type</em>'.
   * @generated
   */
  IntegerType createIntegerType();

  /**
   * Returns a new object of class '<em>Bool Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bool Type</em>'.
   * @generated
   */
  BoolType createBoolType();

  /**
   * Returns a new object of class '<em>Range Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Type</em>'.
   * @generated
   */
  RangeType createRangeType();

  /**
   * Returns a new object of class '<em>Clock Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Clock Type</em>'.
   * @generated
   */
  ClockType createClockType();

  /**
   * Returns a new object of class '<em>Proc Body</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Proc Body</em>'.
   * @generated
   */
  ProcBody createProcBody();

  /**
   * Returns a new object of class '<em>State Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>State Decl</em>'.
   * @generated
   */
  StateDecl createStateDecl();

  /**
   * Returns a new object of class '<em>Transition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Transition</em>'.
   * @generated
   */
  Transition createTransition();

  /**
   * Returns a new object of class '<em>Sync</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Sync</em>'.
   * @generated
   */
  Sync createSync();

  /**
   * Returns a new object of class '<em>Send</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Send</em>'.
   * @generated
   */
  Send createSend();

  /**
   * Returns a new object of class '<em>Recv</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Recv</em>'.
   * @generated
   */
  Recv createRecv();

  /**
   * Returns a new object of class '<em>Assignments</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Assignments</em>'.
   * @generated
   */
  Assignments createAssignments();

  /**
   * Returns a new object of class '<em>Assign</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Assign</em>'.
   * @generated
   */
  Assign createAssign();

  /**
   * Returns a new object of class '<em>Variable Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable Decl</em>'.
   * @generated
   */
  VariableDecl createVariableDecl();

  /**
   * Returns a new object of class '<em>Decl Id</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Decl Id</em>'.
   * @generated
   */
  DeclId createDeclId();

  /**
   * Returns a new object of class '<em>Initialiser</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Initialiser</em>'.
   * @generated
   */
  Initialiser createInitialiser();

  /**
   * Returns a new object of class '<em>Array Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Array Decl</em>'.
   * @generated
   */
  ArrayDecl createArrayDecl();

  /**
   * Returns a new object of class '<em>Int Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Int Expression</em>'.
   * @generated
   */
  IntExpression createIntExpression();

  /**
   * Returns a new object of class '<em>Var Access</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Var Access</em>'.
   * @generated
   */
  VarAccess createVarAccess();

  /**
   * Returns a new object of class '<em>Wrap Bool Expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Wrap Bool Expr</em>'.
   * @generated
   */
  WrapBoolExpr createWrapBoolExpr();

  /**
   * Returns a new object of class '<em>Constant</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Constant</em>'.
   * @generated
   */
  Constant createConstant();

  /**
   * Returns a new object of class '<em>Boolean Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Boolean Expression</em>'.
   * @generated
   */
  BooleanExpression createBooleanExpression();

  /**
   * Returns a new object of class '<em>Comparison</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Comparison</em>'.
   * @generated
   */
  Comparison createComparison();

  /**
   * Returns a new object of class '<em>True</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>True</em>'.
   * @generated
   */
  True createTrue();

  /**
   * Returns a new object of class '<em>False</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>False</em>'.
   * @generated
   */
  False createFalse();

  /**
   * Returns a new object of class '<em>Binary Int Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Binary Int Expression</em>'.
   * @generated
   */
  BinaryIntExpression createBinaryIntExpression();

  /**
   * Returns a new object of class '<em>Bit Complement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bit Complement</em>'.
   * @generated
   */
  BitComplement createBitComplement();

  /**
   * Returns a new object of class '<em>Unary Minus</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Unary Minus</em>'.
   * @generated
   */
  UnaryMinus createUnaryMinus();

  /**
   * Returns a new object of class '<em>Or</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Or</em>'.
   * @generated
   */
  Or createOr();

  /**
   * Returns a new object of class '<em>And</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>And</em>'.
   * @generated
   */
  And createAnd();

  /**
   * Returns a new object of class '<em>Not</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Not</em>'.
   * @generated
   */
  Not createNot();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  TimedAutomataPackage getTimedAutomataPackage();

} //TimedAutomataFactory
