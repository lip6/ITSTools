package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IResponse;

import android.util.SparseBoolArray;


/**
 * A refiner that simply stores a set of precomputed constraints.
 */
public class StaticRefiner implements IRefiner {
    private static final int DEBUG = 0;
    private String name;
    private List<IConstraint> constraints = new ArrayList<>();
    private SparseBoolArray unappliedConstraints = new SparseBoolArray();

    public StaticRefiner(String name) {
        this.name = name;
    }

    @Override
    public int refine(SolverState solver, ProblemSet problems, RefinementMode mode, VarSet current, long timeout) {
        int total = 0;
        // Iterate backward to safely modify the array during iteration
        for (int i = unappliedConstraints.size() - 1; i >= 0; i--) {
            int index = unappliedConstraints.keyAt(i);
            IConstraint c = constraints.get(index);
            if (canApplyConstraint(c, current, mode)) {
                applyConstraint(solver, c);
                unappliedConstraints.put(index, false);  // Automatically removes the index when set to false
                total++;
            }
        }
        if (DEBUG >= 1) {
            System.out.println(this);
        }
        return total;
    }

    private boolean canApplyConstraint(IConstraint constraint, VarSet current, RefinementMode mode) {
        switch (mode) {
            case INCLUDED_ONLY:
                return current.containsAll(constraint.getSupport());
            case OVERLAPS:
                return VarSet.intersects(current, constraint.getSupport());
            case ALL:
                return true;
            default:
                return false;
        }
    }

    private void applyConstraint(SolverState solver, IConstraint constraint) {
        VarSet toDeclare = constraint.getSupport();
        solver.declareVars(toDeclare);
        IResponse response = constraint.getSMTConstraint().execute(solver.getSolver());
        if (!response.isOK()) {
            throw new IllegalStateException("Failed to add constraint " + constraint);
        }
    }

    public void addConstraint(IConstraint constraint) {
        constraints.add(constraint);
        unappliedConstraints.append(constraints.size() - 1, true);  // Add the index to the list of unapplied constraints
    }

    public List<IConstraint> getConstraints() {
        return constraints;
    }

    public String getName() {
        return name;
    }

    @Override
    public void reset() {
        unappliedConstraints.clear();
        for (int i = 0; i < constraints.size(); i++) {
            unappliedConstraints.append(i, true);
        }
    }

    @Override
    public String toString() {
        long done = constraints.size() - unappliedConstraints.size();
        return name + ": " + done + "/" + constraints.size() + " constraints";
    }
}
