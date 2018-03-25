package fr.lip6.move.gal.semantics;

import java.util.BitSet;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;

/**
 * See also : class DependencyMatrix for more high level or repeated usage.
 * @author ythierry
 *
 */
public class NextSupportAnalyzer {

	/**
	 * Main user API, pass new BitSet() to run from empty supports.
	 * @param next a transition or part of it
	 * @param read the set of read variables
	 * @param write the set written variables
	 */
	public static void computeSupport(INext next, BitSet read, BitSet write, BitSet control) {
		next.accept(new SupportVisitor(read,write,control));
	}
	
	/**
	 * Adds all support variables of target EObject (and its descendants) to the provided support.
	 * @param target the object whose support we are interested in
	 * @param support the support we add supporting variables to
	 */
	static void computeSupport(EObject target, BitSet support, VariableIndexer index) {
		// test for terminal case
		if (! computeSupportTerminals(target, support,index) ) 
		{
			// iterate over descendants : this way of doing it avoids a costly recursion
			for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				
				if (computeSupportTerminals(obj, support,index)) {
					// prevent analysis of descendants of this node; next will skip to siblings of this node
					// the issue is tab[tab[i]] where we are not interested in child expression tab[i] 
					// we already have deduced by computeSupportTerminals that tab[*] is in support
					it.prune();
				}
			}
		}
	}

	/**
	 * If the current target is a terminal (ref to var or array cell) return true and and concerned var to support.
	 * For a plain variable ref this is just a SupportVariable. For an array cell reference, we test if
	 *  the index is a constant, e.g. tab[3] add tab[3] to suport, if we have a nested expression, we pessimistically
	 *  add all cells of tab to support. Computing domain of subexpression seems a bit too much.
	 * Otherwise (target is not a VarAccess) return false and do nothing.
	 * @param target the (possibly) terminal statement
	 * @param support the support to add to if we get a hit
	 * @return true if the node was terminal, false if we had a non constant index in there
	 */
	static boolean computeSupportTerminals(EObject target,	BitSet support,VariableIndexer index) {
		if (target instanceof VariableReference) {
			VariableReference vref = (VariableReference) target;
			
			if (vref.getIndex() == null) {
				support.set(index.getIndex(vref.getRef().getName()));
				return true;
			} else {
				if (vref.getIndex() instanceof Constant) {
					support.set(index.getIndex(vref.getRef().getName())  + ((Constant) vref.getIndex()).getValue());
					return true;
				} else {
					int pos = index.getIndex(vref.getRef().getName());
					int size = Instantiator.evalConst(((ArrayPrefix) vref.getRef()).getSize());
					support.set(pos, pos+size); 
					return false;
				}
			}
		}
		return false;
	}

	
	static boolean computeQualifiedSupportTerminals(EObject target,	BitSet support, INextBuilder nb) {
		if (target instanceof Reference) {
			support.set(nb.getIndex((Reference) target));
			return true;
		}
		return false;
	}

	public static void computeQualifiedSupport(EObject target, BitSet support, INextBuilder nb) {
		// test for terminal case
		if (! computeQualifiedSupportTerminals(target, support,nb) ) 
		{
			// iterate over descendants : this way of doing it avoids a costly recursion
			for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();

				if (computeQualifiedSupportTerminals(obj, support, nb)) {
					// prevent analysis of descendants of this node; next will skip to siblings of this node
					// the issue is tab[tab[i]] where we are not interested in child expression tab[i] 
					// we already have deduced by computeSupportTerminals that tab[*] is in support
					it.prune();
				}
			}
		}
	}
}

