package fr.lip6.move.gal.pnml.togal.utils;

import java.util.Iterator;


public class Utils {
	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');
		
		return res;
	}

	public static <T>
	Iterable<T> concat(final Iterable<T> in1, final Iterable<T> in2) {
		return new Iterable<T>() {
			
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					
					private Iterator<T> it1 = in1.iterator();
					private Iterator<T> it2 = in2.iterator();
					
					@Override
					public boolean hasNext() {
						return it1.hasNext() || it2.hasNext();
					}

					@Override
					public T next() {
						if (it1.hasNext()) {
							return it1.next();
						}
						return it2.next();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

}
