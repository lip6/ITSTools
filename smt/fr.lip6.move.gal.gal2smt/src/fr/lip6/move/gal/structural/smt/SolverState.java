package fr.lip6.move.gal.structural.smt;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.smtlib.ISolver;
import org.smtlib.SMT;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_declare_fun;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseBoolArray;
import android.util.SparseIntArray;

public class SolverState {

	private VarSet allVars = new VarSet();
	private VarSet declaredVars = new VarSet();
	private Map<String,VarType> varTypes = new HashMap<>();
	private SMT smt = new SMT();
	private SolutionType numericType = SolutionType.Real;
	private Map<String,Integer> minBounds = new HashMap<>();
	ISolver solver;
	
	
	public void setMinBounds(String var, int min) {
		minBounds.put(var, min);
	}

	public void addVars (String prefix, int nbVar, VarType type) {
		SparseBoolArray all = new SparseBoolArray();
		for (int i = 0; i < nbVar; i++) {
			all.append(i, true);
		}
		allVars.addVars(prefix, all);
		varTypes.put(prefix, type);
	}

	public SMT getSMT() {
		return smt ;
	}
	
	public SolutionType getNumericType() {
		return numericType;
	}
	
	public void setNumericType(SolutionType numericType) {
		this.numericType = numericType;
	}
	
	public void declareVars(VarSet toDeclare) {
		
		Script script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		
		toDeclare = VarSet.removeAll(toDeclare, declaredVars);
		
		if (toDeclare.size() == 0) {
			return;
		}
		
		for (Map.Entry<String, SparseBoolArray> entry : toDeclare.getVars().entrySet()) {
            String prefix = entry.getKey();
            SparseBoolArray values = entry.getValue();
            VarType type = varTypes.get(prefix);
    		org.smtlib.ISort.IApplication smtType;
    		
    		if (type == VarType.BOOL)
    			smtType = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool"));
    		else
    			// use current numeric type : Real or Int
    			smtType = smt.smtConfig.sortFactory.createSortExpression(ef.symbol(numericType.toString()));
            
    		Integer min = minBounds.get(prefix);
    		
            for (int i = 0; i < values.size(); i++) {
            	int index = values.keyAt(i);
            	ISymbol si = ef.symbol(prefix + index);
            	script.add(new C_declare_fun(si, Collections.emptyList(), smtType));
            	if (min != null) {
            		script.add(new C_assert(ef.fcn(ef.symbol(">="), si, ef.numeral(min))));
            	}
            }            
        }
		
		declaredVars.addAll(toDeclare);
		
		SMTUtils.execAndCheckResult(script, solver);
	}
	
	public ISolver getSolver() {
		return solver;
	}

	public void start(int timeout) {
		solver = SMTUtils.initSolver(smt, numericType==SolutionType.Real, timeout, timeout);
	}
	
	public void stop() {
		solver.exit();
		declaredVars = new VarSet();
	}
	
	public SparseIntArray getValues(String prefix) {
		SparseBoolArray bvars = declaredVars.getVars().getOrDefault(prefix, new SparseBoolArray());
		int nbVars = bvars.size();
		if (nbVars==0) {
			return new SparseIntArray();
		}
		IExpr [] vars = new IExpr[nbVars];
		for (int i=0, ie = nbVars; i < ie ; i++) {
			vars[i] = smt.smtConfig.exprFactory.symbol(prefix + bvars.keyAt(i));
		}
		IResponse reply = solver.get_value(vars);
		
		if (reply.isError()) {
			System.err.println("Error getting values : " + reply);
			return new SparseIntArray();
		}
		
		SparseIntArray result = new SparseIntArray(vars.length);
		
		if (reply instanceof ISeq) {
			ISeq seq = (ISeq) reply;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					String var = vseq.sexprs().get(0).toString();
					if (var.startsWith(prefix)) {
						int tid = Integer.parseInt(var.substring(1));
						int value ;
						try { value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() ); }
						catch (NumberFormatException e) { 
							return null;
						}
						if (value != 0) 
							result.put(tid, value);
					}
				}
			}
		}
		return result;
	}
	
	public VarSet getDeclaredVars() {
		return declaredVars;
	}
	
	public VarSet getAllVars() {
		return allVars;
	}
}
