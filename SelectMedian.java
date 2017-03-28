package SelectMedian;

import java.util.Arrays;

/**
 * @Author Liam Arason - V00853066
 * January 30, 2017
 * CSC 226 - Spring 2017
 * Assignemt 1 - Programming
 */

public class SelectMedian {
    
    /**
     * Selects the k^th smallest element from an array A of integers.
     * That is, when A is sorted in ascending order, Linear Select returns
     * the k^th element in A in O(n) worst case running time.
     * 
     * @param A - The array on which selection is performed.
     * @param k - the position of the element when sorted in ascending order.
     * 
     * @return The k^th smallest element in A.
     */   
    public static int LinearSelect(int[] A, int k) {
    	if (A == null) {
    		return -1;
    	}

    	return LinearSelect(A, 0, A.length - 1, k - 1);
    }

    /**
     * Recursive method that executes the Linear Select algorithm.
     * The algorith picks a smart pivot (to guarantee O(n) worst case running time)
     * and recurses on A relative to the pivot.
     * 
     * @param A - The array on which selection is performed.
     * @param start, end - The current start and end index of A.
     * @param k - The index of the element when sorted in ascending order.
     * 
     * @return The k^th smallest element in A.
     */
    private static int LinearSelect(int[] A, int start, int end, int k) {
    	if (start == end) {
    		return A[start];
    	}

        int pivotIndex = getSmartPivot(A, start, end);
    	pivotIndex = partition(A, start, end, pivotIndex);
    	
        if (k < pivotIndex) {
    		return LinearSelect(A, start, pivotIndex - 1, k);
    	} else if (k == pivotIndex) {
    		return A[k];
    	} else {
    		return LinearSelect(A, pivotIndex + 1, end, k);
    	}
    }

    /**
     * Selects a smart pivot from the array A.
     * 
     * @param A - The array to select a pivot from.
     * @param start, end - The current start and end index of A.
     * 
     * @return Index of the smart pivot.
     */
    private static int getSmartPivot(int[] A, int start, int end) {
        // gets the medians of sub-arrays of A (size 7)
        int[] medians = getMedians(A, start, end);

        // call LinearSelect to determine median of the medians
        int pivot = LinearSelect(medians, 0, medians.length - 1, (medians.length + 1)/2);
        
        // get the pivot index
        int pivotIndex = start;
        for (int i = start; i <= end; i ++) {
            if (A[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        return pivotIndex;
    }

    /**
     * Gets an array of the medians of A when divided into groups of size 7.
     * Runs in O(n) worst case running time.
     * 
     * @param A - The array from which the medians are generated.
     * @param start, end - The current start and end index of A.
     * 
     * @return Integer array of the medians.
     */
    private static int[] getMedians(int[] A, int start, int end) {
         int[] medians;

        if ((end - start + 1) % 7 == 0) {
            medians = new int[(end - start + 1)/7];
        } else {
            medians = new int[(end - start + 1)/7 + 1];
        }
        int medianCount = 0;

        for (int i = start + 7; i <= (end + 1); i += 7) {
            int[] temp = new int[7];

            int index = i - 7;
            for (int j = 0; j < 7; j ++) {
                temp[j] = A[index];
                index ++;
            }

            Arrays.sort(temp);
            medians[medianCount] = temp[temp.length/2];
            medianCount ++;
        }

        // If the array's size is not divisible by 7, we also need to take into
        // consideration the extra elements that are not part of a group of 7.
        if ((end - start + 1) % 7 != 0) {
            int startIndex = start + 7*((end - start + 1)/7);
            int[] overflow = new int[end - startIndex + 1];
            int index = 0;

            for (int i = startIndex; i <= end; i ++) {
                overflow[index] = A[i];
                index ++;
            }

            Arrays.sort(overflow);
            medians[medianCount] = overflow[overflow.length/2];
            medianCount ++;
        }
        return medians;
    }

    /**
     * Partitions the array A based on a pivot.
     * 
     * @param A - The array on which the partitioning is performed.
     * @param start, end - The current start and end index of A.
     * @param pivotIndex - The index of the pivot
     * 
     * @return Index of the pivot after partition.
     */
    private static int partition(int[] A, int start, int end, int pivotIndex) {
    	int pivot = A[pivotIndex];
    	swap(A, pivotIndex, end);
    	int index = start;

    	for (int i = start; i < end; i++) {
    		if (A[i] < pivot) {
    			swap(A, index, i);
    			index ++;
    		}
    	}
    	swap(A, end, index);
    	return index;
    }

    /**
     * Swaps two values in array A.
     * 
     * @param A - The array with values to be swapped.
     * @param x, y - The indexes of the elements to be swapped.
     */
    private static void swap(int[] A, int x, int y) {
    	int temp = A[x];
    	A[x] = A[y];
    	A[y] = temp;
    }

    /**
     * Used for testing the Linear Select algorithm.
     * 
     * @param args - Not used.
     */
    public static void main(String[] args) {
        int[] A = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59};
        int[] B = {5, 3, 6, 7, 1, -1, 10};
        int[] C = {3, 75, 12, 20};
        int[] D = {3, 2, 4, 5, 1};
        int[] E = {75};
        int[] F = null;
        int[] G = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        System.out.println("The median weight is " + LinearSelect(A, (A.length + 1)/2));
    }
    
}
