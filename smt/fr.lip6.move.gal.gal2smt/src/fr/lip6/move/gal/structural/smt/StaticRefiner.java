package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IResponse;


/**
 * A refiner that simply stores a set of precomputed constraints.
 */
public class StaticRefiner implements IRefiner {
	private static final int DEBUG = 0;
	private String name;
	private List<IConstraint> constraints = new ArrayList<>();

	public StaticRefiner(String name) {
		this.name = name;
	}
	
	@Override
	public int refine(SolverState solver, ProblemSet problems, RefinementMode mode, VarSet current, long timeout)
	{
		int total = 0;
		for (IConstraint c : constraints) {
			if (c.isDone()) {
				continue;
			}
			VarSet toAdd = new VarSet();
			switch (mode) {
			case INCLUDED_ONLY: {
				if (! current.containsAll(c.getSupport())) {
					continue;
				}
				break;
			}
			case OVERLAPS: {				
				if (! VarSet.intersects(current,c.getSupport())) {
					continue;
				} else {
					toAdd.addAll(c.getSupport());
				}
				break;
			}
			case ALL: {
				toAdd.addAll(c.getSupport());
				break;
			}
			} // end switch mode
			
			solver.declareVars(toAdd);
			total++;
			IResponse response = c.getSMTConstraint().execute(solver.getSolver());
			if (!response.isOK()) {
				throw new IllegalStateException("Failed to add constraint " + c);
			} else {
				c.setDone(true);
			}
		}
		if (DEBUG >= 1) {
			System.out.println(this);
		}
		return total;
	}

	public void addConstraint(SMTConstraint smtConstraint) {
        constraints.add(smtConstraint);		
	}

	public List<IConstraint> getConstraints() {
		return constraints;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean isDone() {
		for (IConstraint c : constraints) {
			if (!c.isDone()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void reset() {
		for (IConstraint c : constraints) {
            c.setDone(false);
        }
    }

	@Override
	public String toString() {
		long done = constraints.stream().filter(IConstraint::isDone).count();
		return "Refiner " + name + ": " + done + "/" + constraints.size() + " constraints." ;
	}
	
	
}
