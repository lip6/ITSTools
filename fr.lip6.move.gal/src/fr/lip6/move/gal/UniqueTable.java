package fr.lip6.move.gal;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * A basic UniqueTable, for immutable objects.
 * Object are held via WeakReference, so the table may shrink under pressured memory conditions / don't expect it to keep track of all objects put into it. 
 * 
 * @author ythierry
 *
 * @param <T> type contained in the table. Should be hashable, equals comparable, and immutable. 
 */
public class UniqueTable<T> {

	private WeakHashMap<T, WeakReference<T>> canonical = new WeakHashMap<T, WeakReference<T>>();

	/** 
	 * Return a reference to the existing instance if there is one. 
	 * @param elt the element we want a unique ref to
	 * @return either the original element if it is new, or an older copy of it if we already built it.
	 */
	public T canonical(T elt) {
		WeakReference<T> found = canonical.get(elt);
		if (found != null) {
			return found.get();
		} else {
			canonical.put(elt, new WeakReference<T>(elt));
			return elt;
		}
	}

	@Override
	public String toString() {
		return " size=" + canonical.size();
	}

}
