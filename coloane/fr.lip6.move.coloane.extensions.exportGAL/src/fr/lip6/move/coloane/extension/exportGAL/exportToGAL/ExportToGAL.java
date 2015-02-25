/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.extension.exportGAL.exportToGAL;


import static fr.lip6.move.gal.GF2.constant;

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.extensions.IExportTo;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.projects.its.expression.Add;
import fr.lip6.move.coloane.projects.its.expression.Div;
import fr.lip6.move.coloane.projects.its.expression.ExpressionFactory;
import fr.lip6.move.coloane.projects.its.expression.ExpressionParseResult;
import fr.lip6.move.coloane.projects.its.expression.IVariable;
import fr.lip6.move.coloane.projects.its.expression.Infinity;
import fr.lip6.move.coloane.projects.its.expression.IntegerExpression;
import fr.lip6.move.coloane.projects.its.expression.Minus;
import fr.lip6.move.coloane.projects.its.expression.Mult;
import fr.lip6.move.coloane.projects.its.expression.NaryExpression;
import fr.lip6.move.coloane.projects.its.expression.UnaryMinus;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Fixpoint;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.serialization.SerializationUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * Export models to GAL format
 *
 * @author Yann Thierry-Mieg
 */
public class ExportToGAL implements IExportTo {

	private boolean isEssentialStates;

	public ExportToGAL(boolean isEssentialStates) {
		this.isEssentialStates = isEssentialStates;
	}
	
	public ExportToGAL() {
		this(false);
	}

	/**
	 * Export a model to GAL formatted file
	 * @param model The model to export
	 * @param filePath The path of the destination file
	 * @param monitor A monitor to follow the export progression
	 * @throws ExtensionException Something wrong has happened.
	 */
	public final void export(IGraph model, String filePath, IProgressMonitor monitor) throws ExtensionException {

		// Filename checks
		if (filePath.equalsIgnoreCase("") || filePath == null) { //$NON-NLS-1$
			throw new ExtensionException("The filename is not correct. Please provide a valid filename");
		}

		int totalWork = model.getNodes().size() + model.getArcs().size();
		monitor.beginTask("Export to GAL", totalWork);


		String name = "model";
		final GalFactory gf = GalFactory.eINSTANCE;
		GALTypeDeclaration gal = gf.createGALTypeDeclaration();
		gal.setName(name);

		// now optional to specify transient
		//		Transient tr = gf.createTransient();
		//		False f = gf.createFalse();
		//		tr.setValue(f);
		//      gal.setTransient(tr);

		Map<IVariable,ConstParameter> evarMap = new HashMap<IVariable, ConstParameter>();

		
		Map<INode,Variable> varMap = new HashMap<INode, Variable>();
		// nodes : each place gives rise to a variable
		for (INode node : model.getNodes()) {
			if ("place".equals(node.getNodeFormalism().getName())) {
				Variable var = gf.createVariable();
				var.setName(node.getAttribute("name").getValue());
				var.setValue(toIntExpression(node.getAttribute("marking").getValue(), gal, evarMap));
				gal.getVariables().add(var);
				varMap.put(node,var);
			}
		}


		// transition clocks
		boolean isTimed = false;
		for (INode node : model.getNodes()) {
			if ("transition".equals(node.getNodeFormalism().getName())) {
				IntExpression eft = eft(node,gal,evarMap);
				IntExpression lft = lft(node,gal,evarMap);
				if (hasClock(eft,lft)) {
					isTimed = true;
					Variable var = gf.createVariable();
					var.setName(node.getAttribute("label").getValue()+".clock");
					var.setValue(constant(0));
					gal.getVariables().add(var);					
					varMap.put(node,var);
				}
				if ( isZero(lft)) {
					// urgent ! It does not produce a clock variable, but it still means overall we must constrain time elapse.
					isTimed = true;
				}
			}
		}

		// prepare the creation of the reset disabled transition
		Label labReset = GF2.createLabel("reset");

		Transition reset = gf.createTransition();
		reset.setName("reset");		
		reset.setLabel(labReset );
		reset.setGuard(gf.createTrue());


		// prepare the creation of the elapse disabled transition
		Label labElapse = GF2.createLabel("elapse");

		Transition elapse = gf.createTransition();
		elapse.setName("elapse");		
		elapse.setLabel(labElapse);
		elapse.setGuard(gf.createTrue());

		BooleanExpression canElapse = gf.createTrue();
		List<Statement> elapseAct = new ArrayList<Statement>();
		
		for (INode node : model.getNodes()) {
			if ("transition".equals(node.getNodeFormalism().getName())) {
				Transition t = gf.createTransition();
				t.setName(node.getAttribute("label").getValue());
				if ("public".equals(node.getAttribute("visibility").getValue())) {
					Label lab = gf.createLabel();
					lab.setName(node.getAttribute("label").getValue());
					t.setLabel(lab );
				}

				// build elapse effect
				IntExpression eft = eft(node,gal,evarMap);
				IntExpression lft = lft(node,gal,evarMap);

				BooleanExpression guard = computeGuard(node,gf,varMap,gal,evarMap);

				if (! (eft instanceof Constant) || ((Constant) eft).getValue() != 0) {					
					BooleanExpression comp = GF2.createComparison(GF2.createVariableRef(varMap.get(node)), ComparisonOperators.GE, eft);
					//createComparison(node, ComparisonOperators.GE, eft, gf, varMap);

					// and clock >= eft to guard condition					
					t.setGuard(GF2.and(guard, comp));
				} else {
					t.setGuard(guard);
				}

				// build actions : first action is reset, then take tokens, then put tokens
				for (IArc arc : node.getIncomingArcs()) {
					String arcType = arc.getArcFormalism().getName(); 

					if ("reset".equals(arcType) ) {
						// p= 0
						t.getActions().add(GF2.createAssignVarConst(varMap.get(arc.getSource()), 0)); 
					}
				}
				// build actions :  then take tokens
				for (IArc arc : node.getIncomingArcs()) {
					String arcType = arc.getArcFormalism().getName(); 

					if ("arc".equals(arcType) ) {

						// p -= valuation
						IntExpression val;
						IAttribute iaval = arc.getAttribute("valuation");
						if (iaval != null) {
							val = toIntExpression(iaval.getValue(),gal,evarMap);
						} else {
							val = constant(1);
						}
						Statement ass = decrementVar(arc.getSource(), val, gf, varMap);
						
						t.getActions().add(ass);
					}
				}
				// build actions :  then put tokens
				for (IArc arc : node.getOutgoingArcs()) {
					String arcType = arc.getArcFormalism().getName(); 

					if ("arc".equals(arcType) ) {

						IntExpression value ;
						IAttribute iaval = arc.getAttribute("valuation");
						if (iaval != null) {
							value =  toIntExpression(iaval.getValue(), gal, evarMap);
						} else {
							value = constant(1); 
						}
						// p= 0
						Statement ass = incrementVar(arc.getTarget(), value, gf, varMap);
						
						t.getActions().add(ass);
					}
				}


				// handle time constraints : reset this clock after firing
				if (isTimed) {
					if (hasClock(eft,lft)) {

						
						// p= 0
						Statement ass = GF2.createAssignVarConst(varMap.get(node), 0);
						
						t.getActions().add(ass);

						// also add a term to resetDisabled
						Ite ite = gf.createIte();
						Not notGuard = gf.createNot();
						BooleanExpression g = computeGuard(node, gf, varMap, gal, evarMap);
						notGuard.setValue(g);
						ite.setCond(notGuard);
						Statement ass2 = GF2.createAssignVarConst(varMap.get(node), 0);
						ite.getIfTrue().add(ass2);
						
						reset.getActions().add(ite);
					}

					// reset other disabled transition clocks
					// call(reset)
					SelfCall call = gf.createSelfCall();
					call.setLabel(labReset);
					t.getActions().add(call);

					
					if (isZero(eft) && isInf(lft)) {
						// [0,inf[
						// nop
					} else if (isZero(eft) && isZero(lft)) {
						// [0,0]
						// no clock, abort elapse if enabled
						Ite ite = gf.createIte();
						BooleanExpression guardd = computeGuard(node, gf, varMap,gal,evarMap); 
						ite.setCond(guardd);
						ite.getIfTrue().add(gf.createAbort());
						
						elapse.getActions().add(ite);
						
						// New condition on can elapse : transition is not enabled
						Not not = gf.createNot();
						not.setValue(EcoreUtil.copy(guardd));
						if ( EcoreUtil.equals(canElapse, gf.createTrue())) {
							canElapse = not; 
						} else {
							And and = gf.createAnd();
							and.setLeft(canElapse);
							and.setRight(not);
							canElapse = and;
						}
						// no transition elapse effects, since no clock 
											
					} else if (isInf(lft)) {
						// [a,inf[
						// increment clock variable up to a.
						Ite ite = gf.createIte();
						
						And and = gf.createAnd();
						and.setLeft(computeGuard(node, gf, varMap,gal,evarMap));

						BooleanExpression comp = createComparison(node, ComparisonOperators.LT, eft, gf, varMap);

						and.setRight(comp);
						// condition is : enabled && clock < eft(t)
						ite.setCond(and);

						// effect if true is :
						// clock= clock+1
						Statement ass = incrementVar(node, constant(1), gf, varMap);
						
						ite.getIfTrue().add(ass);
						elapse.getActions().add(ite);

						// No particular canelapse guard, since lft is infinite
						// New effect if firing elapse : increment clock if enabled and less than a
						elapseAct.add(EcoreUtil.copy(ite));
						
					} else {
						// [a,b] 
						// general case
						// ite(enabled, ite(clock < lft, clock++, abort ), nop )
						Ite ite = gf.createIte();
						BooleanExpression guardd = computeGuard(node, gf, varMap,gal,evarMap); 
						ite.setCond(guardd);
						
						Ite ite2 = gf.createIte();
						
						BooleanExpression comp = createComparison(node, ComparisonOperators.LT, lft, gf, varMap);	
						// condition is : clock < lft(t)
						ite2.setCond(comp);
						
						// effect if true is :
						// clock= clock+1
						Statement ass = incrementVar(node, constant(1), gf, varMap);
						
						ite2.getIfTrue().add(ass);

						// effect if false is abort !
						ite2.getIfFalse().add(gf.createAbort());
						
						ite.getIfTrue().add(ite2);
						
						elapse.getActions().add(ite);
						
						// canelapse guard : ! enabled || clock < lft(t)
						Not not = gf.createNot();
						not.setValue(EcoreUtil.copy(guardd));
						Or or = gf.createOr();
						or.setLeft(not);
						or.setRight(EcoreUtil.copy(comp));
						if ( EcoreUtil.equals(canElapse, gf.createTrue())) {
							canElapse = or; 
						} else {
							And and = gf.createAnd();
							and.setLeft(canElapse);
							and.setRight(or);
							canElapse = and;
						}
						
						
						// elapse Effect : if (enabled) { clock++ }
						Ite ite3 = gf.createIte();
						ite3.setCond(EcoreUtil.copy(guardd));
						ite3.getIfTrue().add(EcoreUtil.copy(ass));
						elapseAct.add(ite3);
						
					}
				}

				gal.getTransitions().add(t);
			}
			
		}
		if (isTimed) {
			
			if (isEssentialStates) {
				Label lab = gf.createLabel();
				lab.setName("succ");
				boolean first = true;
				for (Transition t : gal.getTransitions()) {
					if (t.getLabel() == null) {
						if (first){
							first = !first;
							t.setLabel(lab);
						} else {
							t.setLabel(EcoreUtil.copy(lab));
						}
					}
				}
				
				

				Transition elEffect = gf.createTransition();
				elEffect.setName("elapseEffect");
				Label elEffLab = gf.createLabel();
				elEffLab.setName("elapseEffect");
				elEffect.setLabel(elEffLab);
				elEffect.setGuard(canElapse);
				elEffect.getActions().addAll(elapseAct);
				gal.getTransitions().add(elEffect);

				Transition id = gf.createTransition();
				id.setName("id");
				id.setGuard(gf.createTrue());
				id.setLabel(EcoreUtil.copy(elEffLab));
				gal.getTransitions().add(id);

		
				Transition trel = gf.createTransition();
				trel.setName("nextState");
				trel.setGuard(gf.createTrue());
				trel.setGuard(gf.createTrue());				
				Fixpoint fix = gf.createFixpoint();
				SelfCall callEl = gf.createSelfCall();
				callEl.setLabel(elEffLab);
				fix.getActions().add(callEl);
			
				trel.getActions().add(fix);
			
				SelfCall call = gf.createSelfCall();
				call.setLabel(lab);
				trel.getActions().add(call);

				gal.getTransitions().add(trel);

				gal.getTransitions().add(reset);
			} else {
				gal.getTransitions().add(elapse);			
				gal.getTransitions().add(reset);
			}
		}

		
		try {
			
			Simplifier.simplify(gal);
			Specification spec = gf.createSpecification();
			spec.getTypes().add(gal);
			SerializationUtil.systemToFile(spec, filePath);
		} catch (FileNotFoundException fe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning("Echec lors de la création du fichier : Nom de fichier invalide");
			throw new ExtensionException("Invalid filename !");
		} catch (IOException ioe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning("Erreur lors de l'écriture dans le fichier");
			throw new ExtensionException("Write error :" + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExtensionException("Unexpected exception when building GAL file :" + e.getMessage());
		}
		monitor.done();
	}

	private boolean isZero(IntExpression eft) {
		if (eft instanceof Constant) {
			Constant cte = (Constant) eft;
			return cte.getValue() == 0;
		}
		return false;
	}

	private boolean isInf(IntExpression lft) {
		if (lft instanceof fr.lip6.move.gal.UnaryMinus) {
			fr.lip6.move.gal.UnaryMinus umin = (fr.lip6.move.gal.UnaryMinus) lft;
			if (umin.getValue() instanceof Constant) {
				return ((Constant) umin.getValue()).getValue() == 1;				
			}
		}		
		return false;
	}

	


	private BooleanExpression computeGuard(INode node, GalFactory gf, Map<INode, Variable> varMap, GALTypeDeclaration gal, Map<IVariable, ConstParameter> evarMap) {
		True tru = gf.createTrue();
		BooleanExpression guard = tru ;
		for (IArc arc : node.getIncomingArcs()) {
			String arcType = arc.getArcFormalism().getName(); 
			if ("arc".equals(arcType) || "inhibitor".equals(arcType) || "test".equals(arcType) ) {

				// A ref to the place : p
				INode p = arc.getSource();
				ComparisonOperators op =null;
				if ("arc".equals(arcType) || "test".equals(arcType) ) {
					// is greater or equal >=
					op = ComparisonOperators.GE;
				} else if ("inhibitor".equals(arcType) ) {
					// is strictly less than
					op = ComparisonOperators.LT;							
				} 
				IntExpression val;
				IAttribute iaval = arc.getAttribute("valuation");
				if (iaval != null) {
					val = toIntExpression(iaval.getValue(),gal,evarMap);
				} else {
					val = constant(1);
				}
				
				
				BooleanExpression cmp = createComparison(p, op, val,gf, varMap);				

				if (guard==tru) {
					guard = cmp;
				} else {
					And g2 = gf.createAnd();
					g2.setLeft(guard);
					g2.setRight(cmp);
					guard = g2;
				}
			} else if ("reset".equals(arcType)){
				// no effect on guard
			} else {
				throw new UnsupportedOperationException("unknown arc type!!");
			}
		}
		return guard;
	}
	
	private BooleanExpression createComparison (INode node, ComparisonOperators op, IntExpression value, GalFactory gf, Map<INode, Variable> varMap) {
		VariableReference pl = GF2.createVariableRef(varMap.get(node));

		return GF2.createComparison(pl, op, EcoreUtil.copy(value));
	}
	

	
	private Statement decrementVar (INode node, IntExpression value, GalFactory gf, Map<INode, Variable> varMap) {
		return touchVar(node, value, "-" , gf, varMap);
	}
	
	private Statement touchVar(INode node, IntExpression value, String op,
			GalFactory gf, Map<INode, Variable> varMap) {
		// A ref to the place : p
		VariableReference pl = GF2.createVariableRef(varMap.get(node));

		// p= p "op" val
		return GF2.createAssignment(pl, GF2.createBinaryIntExpression(EcoreUtil.copy(pl), op, value)); 
	}

	private Statement incrementVar (INode node, IntExpression value, GalFactory gf, Map<INode, Variable> varMap) {
		return touchVar(node, value, "+", gf, varMap);
	}
	
		
	private boolean hasClock(IntExpression eft, IntExpression lft) {
		// [0,0] and [0,inf[ clocks are discarded
		return  (! isZero(eft)) || ( ! isInf(lft) && ! isZero(lft));
	}

	private IntExpression eft(INode node, GALTypeDeclaration gal, Map<IVariable, ConstParameter> varMap) {
		return toIntExpression(node.getAttribute("earliestFiringTime").getValue(), gal, varMap);
	}

	private IntExpression lft(INode node, GALTypeDeclaration gal, Map<IVariable, ConstParameter> varMap) {
		return toIntExpression(node.getAttribute("latestFiringTime").getValue(), gal, varMap);
	}

	
	private IntExpression toIntExpression (String value, GALTypeDeclaration gal, Map<IVariable, ConstParameter> varMap) {
		GalFactory gf = GalFactory.eINSTANCE;
		
		ExpressionParseResult epr = ExpressionFactory.parseExpression(value);
		int nberr = epr.getErrorCount();
		if (nberr != 0) {
			java.lang.System.err.println("SYNTAX ERRORS IN MODEL, PLEASE RUN SYNTAX CHECK"
							+ epr.getErrors());
			return constant(0);
		} else {
			IntegerExpression expr = epr.getExpression();
			
			for (IVariable v : expr.supportingVariables()) {
				if (! varMap.containsKey(v)) {
					ConstParameter cp = gf.createConstParameter();
					cp.setName(v.getName());
					// TODO : this is totally arbitrary 
					cp.setValue(3);
					gal.getParams().add(cp);
					varMap.put(v, cp);
				}
			}
			
			IntExpression res = convertToGAL(expr,varMap);
			return res;
		}
	}

	private IntExpression convertToGAL(IntegerExpression expr,
			Map<IVariable, ConstParameter> varMap) {

		if (expr instanceof fr.lip6.move.coloane.projects.its.expression.Constant) {
			int val = ((fr.lip6.move.coloane.projects.its.expression.Constant) expr).getValue();
			return constant(val);
		} else if (expr instanceof Infinity) {
			//Infinity inf = (Infinity) expr;
			return constant(-1);
		} else if (expr instanceof IVariable) {
			IVariable var = (IVariable) expr;
			ParamRef pr = GalFactory.eINSTANCE.createParamRef();
			pr.setRefParam(varMap.get(var));
			return pr;
		} else if (expr instanceof UnaryMinus) {
			UnaryMinus um = (UnaryMinus) expr;
			IntExpression child = convertToGAL(um.getChildren().get(0),varMap);
			fr.lip6.move.gal.UnaryMinus minus = GalFactory.eINSTANCE.createUnaryMinus();
			minus.setValue(child);
			return minus;
		} else if (expr instanceof NaryExpression) {
			NaryExpression add = (NaryExpression) expr;
			BinaryIntExpression bin = GalFactory.eINSTANCE.createBinaryIntExpression();
			if (add instanceof Add) {
				bin.setOp("+");
			} else if (add instanceof Div) {
				bin.setOp("/");
			} else if (add instanceof Minus) {
				bin.setOp("-");
			} else if (add instanceof Mult) {
				bin.setOp("*");
			} 
			bin.setLeft(convertToGAL(add.getChildren().get(0),varMap));
			bin.setRight(convertToGAL(add.getChildren().get(1),varMap));
			return bin;
		} else {
			throw new RuntimeException("Unexpected unknown Expression type when parsing net attribute.");
		}
	}

}
