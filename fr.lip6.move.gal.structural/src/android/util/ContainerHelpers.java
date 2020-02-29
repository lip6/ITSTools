/*
 * Copyright (C) 2013 The Android Open Source Project
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
class ContainerHelpers {
    
	/** based of Arrays.hashCode, but with the length.*/
	public static int hashCode(int a[], int sz) {
		if (a == null)
			return 0;

		int result = 1;
		for (int i=0; i < sz && i < a.length ; i++)
			result = 31 * result + a[i];

		return result;
	}
	
	public static int hashCode(int a[], int b[], int sz) {
		if (a == null || b==null)
			return 0;

		int result = 1;
		for (int i=0; i < sz && i < a.length ; i++) {
			result = 31 * result + a[i];
			result = 31 * result + b[i];
		}
			
		return result;
	}
	
	static int binarySearch(int[] array, int size, int value) {
		int lo = 0;
        int hi = size - 1;
        
		return binarySearch(array, value, lo, hi); 
	}
	// This is Arrays.binarySearch(), but doesn't do any argument validation.
    static int binarySearch(int[] array, int value, int lo, int hi) {
        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];
            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }
    static int binarySearch(long[] array, int size, long value) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final long midVal = array[mid];
            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }
}