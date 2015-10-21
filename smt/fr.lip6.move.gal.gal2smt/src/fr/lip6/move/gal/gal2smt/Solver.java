package fr.lip6.move.gal.gal2smt;

import org.smtlib.ISolver;
import org.smtlib.SMT.Configuration;



public enum Solver { 
	Z3 {
		@Override
		public
		ISolver getSolver(Configuration conf) {
			return new org.smtlib.solvers.Solver_z3_4_3(conf, conf.executable);
		}
	}
	, 
	YICES2 {
		@Override
		public
		ISolver getSolver(Configuration conf) {
			return new org.smtlib.solvers.Solver_yices2(conf, conf.executable);
		}
	} ;
	
	public abstract ISolver getSolver (Configuration conf);

};