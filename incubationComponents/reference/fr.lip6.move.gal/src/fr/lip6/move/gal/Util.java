package fr.lip6.move.gal;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

public class Util {

	/**
	 * Just a trick to get foreach loops on Eobjects.
	 * Deep iteration into all contents.
	 * @param parent a parent of a subtree to explore
	 * @return an iterable appropriate for use in foreach loop
	 */
	public static Iterable<EObject> getAllChildren(final EObject parent) {
		return new Iterable<EObject>() {
			@Override
			public Iterator<EObject> iterator() {
				return parent.eAllContents();
			}
		};
	}

}
