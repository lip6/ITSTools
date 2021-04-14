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
public class SparseIntArray implements Cloneable {
    private int[] mKeys;
    private int[] mValues;
    private int mSize;
    /**
     * Creates a new SparseIntArray containing no mappings.
     */
    public SparseIntArray() {
        this(10);
    }
    /**
     * Creates a new SparseIntArray containing no mappings that will not
     * require any additional memory allocation to store the specified
     * number of mappings.  If you supply an initial capacity of 0, the
     * sparse array will be initialized with a light-weight representation
     * not requiring any additional array allocations.
     */
    public SparseIntArray(int initialCapacity) {
        if (initialCapacity == 0) {
            mKeys = EmptyArray.INT;
            mValues = EmptyArray.INT;
        } else {
            mKeys = ArrayUtils.newUnpaddedIntArray(initialCapacity);
            mValues = new int[mKeys.length];
        }
        mSize = 0;
    }
    /** 
     * Convert a classic List<Int> to a sparse representation.
     * @param marks
     */
    public SparseIntArray(List<Integer> marks) {
    	// compute and set correct capacity
    	this ( (int) marks.stream().filter(e -> e != 0).count());
    	for (int  i = 0, e = marks.size() ; i < e ; i++) {
    		int v = marks.get(i);
    		if (v != 0) {
    			append(i, v);    			
    		}
    	}    	
	}
    
    public SparseIntArray(int[] marks) {
    	// compute and set correct capacity
    	this ( (int) Arrays.stream(marks).filter(e -> e != 0).count());
    	for (int  i = 0, e = marks.length ; i < e ; i++) {
    		int v = marks[i];
    		if (v != 0) {
    			append(i, v);    			
    		}
    	}
    }
    public int [] toArray (int size) {
    	int [] res = new int [size];
    	int j = 0;
    	for (int i=0; i < size ; i++ ) {
    		if (j < size() && keyAt(j)==i) {
    			res[i] = valueAt(j);
    			++j;
    		} else {
    			res[i]=0;
    		}    		
    	}
    	return res;
    }
    
	public List<Integer> toList (int size) {
    	List<Integer> res = new ArrayList<Integer> (size);
    	int  j = 0;
    	for (int i=0; i < size ; i++ ) {
    		if (j < size() && keyAt(j)==i) {
    			res.add(valueAt(j));
    			++j;
    		} else {
    			res.add(0);
    		}    		
    	}
    	return res;
    }
    
    /**
     * Acts like an std::move in c++ : steal the content of the argument.
     * This method updates the content of "this".
     * @param source an object that is invalidated by this operation.
     */
    public void move (SparseIntArray source) {
    	mKeys = source.mKeys;
    	source.mKeys = null;
    	mValues = source.mValues.clone();
    	source.mValues = null;
    	mSize = source.mSize;
    }
    
	@Override
    public SparseIntArray clone() {
        SparseIntArray clone = null;
        try {
            clone = (SparseIntArray) super.clone();
            clone.mKeys = mKeys.clone();
            clone.mValues = mValues.clone();
            clone.mSize = mSize;
        } catch (CloneNotSupportedException cnse) {
            /* ignore */
        }
        return clone;
    }
    /**
     * Gets the int mapped from the specified key, or <code>0</code>
     * if no such mapping has been made.
     */
    public int get(int key) {
        return get(key, 0);
    }
    /**
     * Gets the int mapped from the specified key, or the specified value
     * if no such mapping has been made.
     */
    public int get(int key, int valueIfKeyNotFound) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (i < 0) {
            return valueIfKeyNotFound;
        } else {
            return mValues[i];
        }
    }
    /**
     * Removes the mapping from the specified key, if there was any.
     */
    public int delete(int key) {    	
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (i >= 0) {
            removeAt(i);
        }
        return i;
    }
    /**
     * Removes the mapping at the given index.
     */
    public void removeAt(int index) {
        System.arraycopy(mKeys, index + 1, mKeys, index, mSize - (index + 1));
        System.arraycopy(mValues, index + 1, mValues, index, mSize - (index + 1));
        mSize--;
    }
    /**
     * Adds a mapping from the specified key to the specified value,
     * replacing the previous mapping from the specified key if there
     * was one.
     */
    public void put(int key, int value) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
        if (value==0) {
        	if (i >= 0) {
        		removeAt(i);
        	}
        } else {
        	if (i >= 0) {
        		mValues[i] = value;
        	} else {
        		i = ~i;
        		mKeys = GrowingArrayUtils.insert(mKeys, mSize, i, key);
        		mValues = GrowingArrayUtils.insert(mValues, mSize, i, value);
        		mSize++;
        	}
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
     * Given an index in the range <code>0...size()-1</code>, returns
     * the value from the <code>index</code>th key-value mapping that this
     * SparseIntArray stores.
     *
     * <p>The values corresponding to indices in ascending order are guaranteed
     * to be associated with keys in ascending order, e.g.,
     * <code>valueAt(0)</code> will return the value associated with the
     * smallest key and <code>valueAt(size()-1)</code> will return the value
     * associated with the largest key.</p>
     */
    public int valueAt(int index) {
        return mValues[index];
    }
    /**
     * Directly set the value at a particular index.
     * @hide
     */
    public void setValueAt(int index, int value) {
        mValues[index] = value;
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
     * Returns an index for which {@link #valueAt} would return the
     * specified key, or a negative number if no keys map to the
     * specified value.
     * Beware that this is a linear search, unlike lookups by key,
     * and that multiple keys can map to the same value and this will
     * find only one of them.
     */
    public int indexOfValue(int value) {
        for (int i = 0; i < mSize; i++)
            if (mValues[i] == value)
                return i;
        return -1;
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
    public void append(int key, int value) {
    	if (value == 0)
    		return;
        if (mSize != 0 && key <= mKeys[mSize - 1]) {
            put(key, value);
            return;
        }
        mKeys = GrowingArrayUtils.append(mKeys, mSize, key);
        mValues = GrowingArrayUtils.append(mValues, mSize, value);
        mSize++;
    }
    /**
     * Provides a copy of keys.
     *
     * @hide
     * */
    public int[] copyKeys() {
        if (size() == 0) {
            return new int[0];
        }
        return Arrays.copyOf(mKeys, size());
    }
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ContainerHelpers.hashCode(mKeys,mValues,mSize);
		result = prime * result + mSize;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SparseIntArray))
			return false;
		SparseIntArray other = (SparseIntArray) obj;		
		if (mSize != other.mSize)
			return false;

		if (!equalsRange(mKeys,other.mKeys,mSize))
			return false;
		if (!equalsRange(mValues, other.mValues, mSize))
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
            buffer.append('=');
            int value = valueAt(i);
            buffer.append(value);
        }
        buffer.append('}');
        return buffer.toString();
    }

    /**
     * Returns a vector with : alpha * ta + beta * tb in each cell.
     * @return
     */
    public static SparseIntArray sumProd(int alpha, SparseIntArray ta, int beta, SparseIntArray tb) {
    	return sumProd(alpha, ta, beta, tb, -1);
    }
    public static SparseIntArray sumProd(int alpha, SparseIntArray ta, int beta, SparseIntArray tb, int except) {
    	SparseIntArray flow = new SparseIntArray(Math.max(ta.size(), tb.size()));

    	int i = 0;
    	int j = 0; 
    	while (i < ta.size() || j < tb.size()) {					
    		int ki = i==ta.size() ? Integer.MAX_VALUE : ta.keyAt(i);
    		int kj = j==tb.size() ? Integer.MAX_VALUE : tb.keyAt(j);
    		if (ki == kj) {
    			int val = alpha * ta.valueAt(i)+ beta* tb.valueAt(j);
    			if (val != 0 && ki != except) {
    				flow.append(ki, val);
    			}
    			i++;
    			j++;
    		} else if (ki < kj) {
    			int val = alpha * ta.valueAt(i);
    			if (val != 0 && ki != except) flow.append(ki, val);
    			i++;
    		} else if (kj < ki) {
    			int val = beta * tb.valueAt(j);
    			if (val != 0 && kj != except) flow.append(kj, val);
    			j++;
    		}
    	}

    	return flow;
	}
    
    public static boolean keysIntersect(SparseIntArray s1, SparseIntArray s2) {
		if (s1.size() == 0 || s2.size() == 0) {
			return true;
		}				
		
		for (int j = 0, i = 0 , ss1 =  s1.size() , ss2 = s2.size() ; i < ss1 && j < ss2 ; ) {
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
    
    /**
     * Test whether all entries in s1 are greater or equal than the corresponding entry in s2.
     * Returns true iff. for all i, s1[i] >= s2[i]. 
     * @param s1 the greater array
     * @param s2 the smaller/lower valued array
     * @return true iff for all i, s1[i] >= s2[i].
     */
	public static boolean greaterOrEqual(SparseIntArray s1, SparseIntArray s2) {
		if (s1.size() < s2.size()) {
			return false;
		}
		if (s2.size() == 0) {
			return true;
		}
				
		
		for (int j = 0, i = 0 , ss1 =  s1.size() , ss2 = s2.size() ; i < ss1 && j < ss2 ; ) {
			int sk1 = s1.keyAt(i); 
			int sk2 = s2.keyAt(j); 
			if (sk1 == sk2) {
				if (s1.valueAt(i) < s2.valueAt(j)) {
					return false;
				} else {
					i++;
					j++;
				}
			} else if (sk1 > sk2) {
				// missing entries !
				return false;
			} else {
				// sk1 < sk2 : we must progress in s1
				// use a binary search for that
				int ii = ContainerHelpers.binarySearch(s1.mKeys, sk2, i+1, s1.mSize-1);
				if (ii < 0) {
					return false;
				}
				i = ii;
				if (ss1 - i  < ss2 - j) {
					return false;
				}
			}
		}
		return true;
	}
    
    public static int manhattanDistance (SparseIntArray ta, SparseIntArray tb) {
    	int dist = 0;
    	
    	int i = 0;
    	int j = 0; 
    	while (i < ta.size() || j < tb.size()) {					
    		int ki = i==ta.size() ? Integer.MAX_VALUE : ta.keyAt(i);
    		int kj = j==tb.size() ? Integer.MAX_VALUE : tb.keyAt(j);
    		if (ki == kj) {
    			dist += Math.abs(ta.valueAt(i) - tb.valueAt(j));
    			i++;
    			j++;
    		} else if (ki < kj) {
    			int val = ta.valueAt(i);
    			dist += Math.abs(val);
    			i++;
    		} else if (kj < ki) {
    			int val = tb.valueAt(j);
    			dist += Math.abs(val);
    			j++;
    		}
    	}
    	return dist;
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
	
	/**
	 * More efficient one pass version for removing many at once.
	 * @param todel a list of decreasing sorted indexes.
	 */
	public void deleteAndShift(List<Integer> todel) {
		if (mSize==0 || todel.isEmpty() || todel.get(todel.size()-1) > mKeys[mSize-1]) {
			return;
		}
		// start from rightmost key
		int k = mSize-1 ;
		for (int cur=0, e=todel.size() ; cur < e ; cur++) {
			int val = todel.get(cur);
			for ( ; k>=0 && mKeys[k]> val ; k--) {
				mKeys[k]-= todel.size() - cur;			
			}
			if (k >= 0 && mKeys[k]==val) {
				removeAt(k);
				--k;
			}
		}
	}
   

}
