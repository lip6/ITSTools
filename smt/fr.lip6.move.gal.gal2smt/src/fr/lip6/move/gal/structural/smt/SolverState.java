package fr.lip6.move.gal.structural.smt;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.smtlib.ISolver;
import org.smtlib.SMT;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.impl.Script;

import android.util.SparseBoolArray;

public class SolverState {

	private static final int DEBUG = 1;
	private VarSet allVars = new VarSet();
	private VarSet declaredVars = new VarSet();
	private Map<String,VarType> varTypes = new HashMap<>();
	private SMT smt = new SMT();
	private SolutionType numericType = SolutionType.Real;
	ISolver solver;
	

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
            
            for (int i = 0; i < values.size(); i++) {
            	int index = values.keyAt(i);
            	ISymbol si = ef.symbol(prefix + index);
            	script.add(new org.smtlib.command.C_declare_fun(si, Collections.emptyList(), smtType));
            }            
        }
		
		declaredVars.addAll(toDeclare);
		
		SMTUtils.execAndCheckResult(script, solver);
	}
	
	public ISolver getSolver() {
		return solver;
	}

	public void start(int timeout) {
		if (DEBUG >= 1) {
			String logfile;
			try {
				logfile = File.createTempFile("smtlog", ".smt2").getAbsolutePath();
				System.out.println("Logging solver outputs to " + logfile);
				smt.smtConfig.logfile = logfile;
			} catch (IOException e) {
				System.err.println("Failed to create log file");
				e.printStackTrace();
			}
		}
		solver = SMTUtils.initSolver(smt, numericType==SolutionType.Real, timeout/5, timeout);
	}
	
	public void stop() {
		solver.exit();
		declaredVars = new VarSet();
	}
	
	public VarSet getDeclaredVars() {
		return declaredVars;
	}
	
	public VarSet getAllVars() {
		return allVars;
	}
}
