/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.util;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import libcore.util.EmptyArray;
/**
 * SparseIntArrays map integers to integers.  Unlike a normal array of integers,
 * there can be gaps in the indices.  It is intended to be more memory efficient
 * than using a HashMap to map Integers to Integers, both because it avoids
 * auto-boxing keys and values and its data structure doesn't rely on an extra entry object
 * for each mapping.
 *
 * <p>Note that this container keeps its mappings in an array data structure,
 * using a binary search to find keys.  The implementation is not intended to be appropriate for
 * data structures
 * that may contain large numbers of items.  It is generally slower than a traditional
 * HashMap, since lookups require a binary search and adds and removes require inserting
 * and deleting entries in the array.  For containers holding up to hundreds of items,
 * the performance difference is not significant, less than 50%.</p>
 *
 * <p>It is possible to iterate over the items in this container using
 * {@link #keyAt(int)} and {@link #valueAt(int)}. Iterating over the keys using
 * <code>keyAt(int)</code> with ascending values of the index will return the
 * keys in ascending order, or the values corresponding to the keys in ascending
 * order in the case of <code>valueAt(int)</code>.</p>
 */
public class SparseBoolArray implements Cloneable {
    private int[] mKeys;
    private int mSize;
    /**
     * Creates a new SparseIntArray containing no mappings.
     */
    public SparseBoolArray() {
        this(10);
    }
    /**
     * Creates a new SparseIntArray containing no mappings that will not
     * require any additional memory allocation to store the specified
     * number of mappings.  If you supply an initial capacity of 0, the
     * sparse array will be initialized with a light-weight representation
     * not requiring any additional array allocations.
     */
    public SparseBoolArray(int initialCapacity) {
        if (initialCapacity == 0) {
            mKeys = EmptyArray.INT;
        } else {
            mKeys = ArrayUtils.newUnpaddedIntArray(initialCapacity);
        }
        mSize = 0;
    }
    /** 
     * Convert a classic List<Int> to a sparse representation.
     * @param marks
     */
    public SparseBoolArray(List<Boolean> marks) {
    	// compute and set correct capacity
    	this ( (int) marks.stream().filter(e -> e).count());
    	for (int  i = 0, e = marks.size() ; i < e ; i++) {
    		boolean v = marks.get(i);
    		if (v) {
    			append(i, v);    			
    		}
    	}    	
	}
    
    // produce an entry for each set bit in the input
    public SparseBoolArray(BitSet bs) {
		// set capacity
		this(bs.cardinality());

		for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
			append(i,true);
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
	}
    
    public SparseBoolArray(int nbElements, boolean b) {
		this(nbElements);
		for (int i = 0; i < nbElements; i++) {
			append(i, b);
		}
	}
	public List<Boolean> toList (int size) {
    	List<Boolean> res = new ArrayList<> (size);
    	int  j = 0;
    	for (int i=0; i < size ; i++ ) {
    		if (j < size() && keyAt(j)==i) {
    			res.add(true);
    			++j;
    		} else {
    			res.add(false);
    		}    		
    	}
    	return res;
    }
    
	@Override
    public SparseBoolArray clone() {
        SparseBoolArray clone = null;
        try {
            clone = (SparseBoolArray) super.clone();
            clone.mKeys = mKeys.clone();
        } catch (CloneNotSupportedException cnse) {
            /* ignore */
        }
        return clone;
    }
    /**
     * Gets the int mapped from the specified key, or <code>0</code>
     * if no such mapping has been made.
     */
    public boolean get(int key) {
        return get(key, false);
    }
    /**
     * Gets the int mapped from the specified key, or the specified value
     * if no such mapping has been made.
     */
    public boolean get(int key, boolean valueIfKeyNotFound) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (i < 0) {
            return valueIfKeyNotFound;
        } else {
            return true;
        }
    }
    /**
     * Removes the mapping from the specified key, if there was any.
     */
    public void delete(int key) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (i >= 0) {
            removeAt(i);
        }
    }
    /**
     * Removes the mapping at the given index.
     */
    public void removeAt(int index) {
        System.arraycopy(mKeys, index + 1, mKeys, index, mSize - (index + 1));        
        mSize--;
    }
    /**
     * Adds a mapping from the specified key to the specified value,
     * replacing the previous mapping from the specified key if there
     * was one.
     */
    public void put(int key, boolean v) {    	
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (i >= 0) {
        	if (v) {
        		return;
        	} else {
        		removeAt(i);
        	}
        } else if (v) {
            i = ~i;
            mKeys = GrowingArrayUtils.insert(mKeys, mSize, i, key);            
            mSize++;
        }
    }
    /**
     * Returns the number of key-value mappings that this SparseIntArray
     * currently stores.
     */
    public int size() {
        return mSize;
    }
    /**
     * Given an index in the range <code>0...size()-1</code>, returns
     * the key from the <code>index</code>th key-value mapping that this
     * SparseIntArray stores.
     *
     * <p>The keys corresponding to indices in ascending order are guaranteed to
     * be in ascending order, e.g., <code>keyAt(0)</code> will return the
     * smallest key and <code>keyAt(size()-1)</code> will return the largest
     * key.</p>
     */
    public int keyAt(int index) {
        return mKeys[index];
    }

    /**
     * Returns the index for which {@link #keyAt} would return the
     * specified key, or a negative number if the specified
     * key is not mapped.
     */
    public int indexOfKey(int key) {
        return ContainerHelpers.binarySearch(mKeys, mSize, key);
    }
    /**
     * Removes all key-value mappings from this SparseIntArray.
     */
    public void clear() {
        mSize = 0;
    }
    /**
     * Puts a key/value pair into the array, optimizing for the case where
     * the key is greater than all existing keys in the array.
     */
    public void append(int key, boolean v) {
    	if (! v)
    		return;
        if (mSize != 0 && key <= mKeys[mSize - 1]) {
            put(key, v);
            return;
        }
        mKeys = GrowingArrayUtils.append(mKeys, mSize, key);
        mSize++;
    }
    /**
     * Provides a copy of keys.
     *
     * @hide
     * */
    public int[] copyKeys() {
        if (size() == 0) {
            return null;
        }
        return Arrays.copyOf(mKeys, size());
    }
    /**
     * Provides direct access to keys, client should not modify.
     * As side effect, trims the representation array if it's overlarge.
     * @return an array of sorted integers corresponding to true entries of this BoolArray
     */
	public int[] refKeys() {
        if (size() == 0) {
            return new int[0];
        }
        if (mKeys.length > size()) {
        	mKeys = Arrays.copyOf(mKeys, size()); 
        }
        return mKeys;
	}

    
    @Override
	public int hashCode() {
		final int prime = 1409;
		int result = 1;
		result = prime * result + ContainerHelpers.hashCode(mKeys,mSize);
		result = prime * result + mSize;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SparseBoolArray))
			return false;
		SparseBoolArray other = (SparseBoolArray) obj;		
		if (mSize != other.mSize)
			return false;

		if (!equalsRange(mKeys,other.mKeys,mSize))
			return false;
		return true;
	}
	private boolean equalsRange(int[] a, int[] b, int s) {
		if (a==b) {
			return true;
		}
		for (int i=0; i< s; i++) {
			if (a[i] != b[i])
				return false;
		}
		return true;
	}
	/**
     * {@inheritDoc}
     *
     * <p>This implementation composes a string by iterating over its mappings.
     */
    @Override
    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append('{');
        for (int i=0; i<mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = keyAt(i);
            buffer.append(key);
        }
        buffer.append('}');
        return buffer.toString();
    }
    
	public void clear(int j) {
		put (j,false);
	}
	public void set(int j) {
		put (j,true);
	}
	
	 /**
     * Delete an element at index and shift elements to the right by one.
     * @param i
     */
	public void deleteAndShift(int i) {
		if (mSize==0 || i > mKeys[mSize-1]) {
			return;
		}
		int k;
		for (k= mSize-1 ; k>=0 && mKeys[k]>i ; k--) {
			mKeys[k]--;			
		}
		if (k >= 0 && mKeys[k]==i) {
			removeAt(k);
		}
	}
	public static SparseBoolArray or(SparseBoolArray a, SparseBoolArray b) {
		int inter = 0;
		// step 1 evaluate size	
		int i=0;
		int j=0;
		for (int ie=a.size(), je=b.size() ; i < ie && j < je ; ) {
			if (a.mKeys[i] > b.mKeys[j]) {
				j++;
			} else if (a.mKeys[i] < b.mKeys[j]) {
				i++;
			} else {
				i++;
				j++;
				inter++;
			}
		}
		int resSize = a.size() + b.size() - inter;
		SparseBoolArray res = new SparseBoolArray(resSize);
		// step 2 assign
		int cur=0;
		i=0;
		j=0;
		for (int ie=a.size(), je=b.size() ; i < ie && j < je ; ) {
			if (a.mKeys[i] > b.mKeys[j]) {
				res.mKeys[cur++]=b.mKeys[j];
				j++;
			} else if (a.mKeys[i] < b.mKeys[j]) {
				res.mKeys[cur++]=a.mKeys[i];
				i++;
			} else {
				res.mKeys[cur++]=b.mKeys[j];
				i++;
				j++;
			}
		}
		// add remaining elements if any
		for (int ie=a.size(); i < ie ; i++) {
			res.mKeys[cur++]=a.mKeys[i];
		}
		for (int je=b.size(); j < je ; j++) {
			res.mKeys[cur++]=b.mKeys[j];
		}
		res.mSize = cur;
		return res;
	}
	
	
	/**
	 * Return an array c, such that c[i] if a[i] and !b[i].
	 * @param a
	 * @param b
	 * @return c
	 */
	public static SparseBoolArray removeAll(SparseBoolArray a, SparseBoolArray b) {
		SparseBoolArray c = new SparseBoolArray(a.size());

		int asz = a.size();
		int bsz = b.size();

		int i = 0;
		int j = 0;
		while (i < asz || j < bsz) {
			int ki;
			if (i == asz)
				break;
			else
				ki = a.keyAt(i);

			int kj = j == bsz ? Integer.MAX_VALUE : b.keyAt(j);
			if (ki == kj) {
				// dropping value in a
				i++;
				j++;
			} else if (ki < kj) {
				c.append(ki, true);
				i++;
			} else if (kj < ki) {
				j++;
			}
		}

		return c;
	}
	
	
	/**
	 * Test whether all keys in the second array are contained in the first array.
	 * 
	 * Returns true if for every key in s2, there is a corresponding key in s1.
	 * 
	 * @param s1 the array that should contain all keys from s2.
	 * @param s2 the array whose keys need to be contained in s1.
	 * @return true if all keys in s2 are contained in s1.
	 */
	public static boolean containsAll(SparseBoolArray s1, SparseBoolArray s2) {
	    int ss1 = s1.size(), ss2 = s2.size();

	    // If s2 has more entries than s1, s2 cannot be fully contained in s1
	    if (ss2 > ss1) {
	        return false;
	    }
	
	    int i = 0, j = 0;
	    for (; i < ss1 && j < ss2;) {
	        int sk1 = s1.keyAt(i);
	        int sk2 = s2.keyAt(j);

	        if (sk1 == sk2) {
	            // Move to the next key in both arrays
	            i++;
	            j++;
	        } else if (sk1 < sk2) {
	            // Use binary search to find the position in s1 that matches or exceeds sk2
	            int ii = ContainerHelpers.binarySearch(s1.mKeys, sk2, i + 1, ss1 - 1);

	            // If no such position is found, s1 cannot contain all keys from s2
	            if (ii < 0) {
	                return false;
	            }

	            // Update the index in s1 to the found position
	            i = ii;

	            // If there are not enough entries left in s1 to cover the rest of s2, return false
	            if (ss1 - i < ss2 - j) {
	                return false;
	            }
	        } else {
	            // If sk2 > sk1 and the binary search didn't find a match, s1 doesn't contain all keys
	            return false;
	        }
	    }

	    // If we have gone through all of s2's keys, return true; otherwise, return false
	    return j == ss2;
	}
	
	
    public static boolean intersects(SparseBoolArray s1, SparseBoolArray s2) {
		int s1sz = s1.size();
		int s2sz = s2.size();
		if (s1sz == 0 || s2sz == 0) {
			return false;
		}				
		
		for (int j = 0, i = 0  ; i < s1sz && j < s2sz ; ) {
			int sk1 = s1.keyAt(i); 
			int sk2 = s2.keyAt(j); 
			if (sk1 == sk2) {
				return true;
			} else if (sk1 > sk2) {
				j++;
			} else {
				i++;
			}
		}
		return false;
	}

	
	public static void main(String[] args) {
		SparseBoolArray a = new SparseBoolArray();
		a.set(1);a.set(3);a.set(5);
		SparseBoolArray b = new SparseBoolArray();
		b.set(2);b.set(3);b.set(4);
		
		System.out.println("a="+a+"\nb="+b+"\na||b="+or(a,b));
		System.out.println("\na="+a+"\nb="+b+"\nb||a="+or(b,a));
	}
	
}
