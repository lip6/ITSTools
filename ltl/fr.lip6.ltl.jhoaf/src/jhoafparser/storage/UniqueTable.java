//==============================================================================
//	
//	Copyright (c) 2014-
//	Authors:
//	* Joachim Klein <klein@tcs.inf.tu-dresden.de>
//	* David Mueller <david.mueller@tcs.inf.tu-dresden.de>
//	
//------------------------------------------------------------------------------
//	
//	This file is part of the jhoafparser library, http://automata.tools/hoa/jhoafparser/
//
//	The jhoafparser library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	The jhoafparser library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
//	
//==============================================================================

package jhoafparser.storage;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A unique table, providing findOrAdd(T e) insertion: If an element that equals e is already
 * stored, return that element, otherwise add e.
 *
 * Provides the methods of the Set interface as well.
 */
public class UniqueTable<T> extends AbstractSet<T>
{
	/** The unique table */
	private HashMap<T,T> table = new HashMap<T,T>();

	/**
	 * Add the given object to the unique table, if it not yet exists.
	 * Returns the object stored in the unique table, i.e.,
	 * if an equal object already exists, returns that,
	 * otherwise, returns the newly added object.
	 */
	public T findOrAdd(T e) {
		T inTable = table.get(e);
		if (inTable == null) {
			table.put(e, e);
			inTable = e;
		}

		return inTable;
	}
	
	@Override
	public int size()
	{
		return table.size();
	}

	@Override
	public boolean contains(Object o)
	{
		return table.containsKey(o);
	}

	@Override
	public Iterator<T> iterator()
	{
		return table.keySet().iterator();
	}

	@Override
	public boolean add(T e) throws UnsupportedOperationException
	{
		if (e == null) {
			throw new UnsupportedOperationException();
		}
		if (table.containsKey(e)) {
			return false;
		} else {
			table.put(e, e);
			return true;
		}
	}

	@Override
	public boolean remove(Object o)
	{
		if (o == null) {
			return false;
		}
		return (table.remove(o) != null);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return table.keySet().containsAll(c);
	}

	@Override
	public void clear()
	{
		table.clear();
	}
}
