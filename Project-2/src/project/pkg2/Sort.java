/**
 * Team 7
 * Abraham Hill, Vanessa Nzamwita Ishimwe, Pong Vodmongkol
 * CS-2430-501-Spring 2026
 * Programming Project 2: Algorithm Performance
 */
package project.pkg2;

import java.util.Arrays;

/**
 * A collection of sorting algorithms (Mergesort, Quicksort, Shakersort, Heapsort)
 * 
 * Every recursive sorting method uses a wrapper for comparison tracking
 * 
 * @author abrahamhill
 */
public class Sort {
    
    /**
     * Cannot be instantiated
     */
    private Sort() {}
    
    private static int comparisons;
    
    /**
     * Recursive.
     * 
     * Sorts an array per the "Mergesort" algorithm.
     * 
     * Arrays are split in half, then Merge-sorted again, until
     * each array becomes size 1.
     * 
     * Then, the first smallest element from either array is added to the return
     * array repeatedly until all elements have been added.
     * 
     * Array is sorted.
     * 
     * @param base
     * @return 
     */
    public static int[] merge(int[] base) {
        comparisons = 0;
        return mergeHelp(base);
    }
    private static int[] mergeHelp(int[] base) {
        
        if (base.length <= 1) return base; //base case
        
        int splitPoint = base.length / 2;
        
        int[] sub1 = Arrays.copyOfRange(base, 0, splitPoint); 
        int[] sub2 = Arrays.copyOfRange(base, splitPoint, base.length);    
        
        sub1 = mergeHelp(sub1);
        sub2 = mergeHelp(sub2);
        
        int[] n = new int[base.length];
        
        int i1 = 0; //sub1 index tally
        int i2 = 0; //sub2 index tally
        
        int k = 0;

        while (i1 < sub1.length && i2 < sub2.length) {
            comparisons++;
            if (sub1[i1] < sub2[i2]) {
                n[k++] = sub1[i1++];
            } else {
                n[k++] = sub2[i2++];
            }
        }
        
        //clear arrays
        while (i1 < sub1.length) {
            n[i2 + i1] = sub1[i1++];
        }
        while (i2 < sub2.length) {
            n[i2 + i1] = sub2[i2++];
        }
        
        return n;
    }
    
    /**
     * Recursive.
     * 
     * Sorts an array per the "Quicksort" algorithm.
     * 
     * Selects a pivot point (end of array for simplicity), and
     * reorders smaller elements to the left of the pivot, and
     * larger elements to the right of the pivot.
     * 
     * Recursion occurs on both sides of the pivot, fully sorting array.
     * 
     * @param base
     * @return 
     */
    public static int[] quick(int[] base) {
        comparisons = 0; //for project analysis
        return quickHelp(base);
    }
    private static int[] quickHelp(int[] base) {
        
        if (base.length <= 1) return base; //base case
        
        int[] n = Arrays.copyOf(base, base.length);
        
        //indexes
        int pivot = n.length - 1; //pivot at end of array
        int left;
        int right;
        
        //left and right sub-arrays
        int[] subL;
        int[] subR;
        
        left = 0;
        right = pivot - 1;
        
        //partitions n > pivot to the right and n < pivot to left
        while (left <= right) {

            while (left <= right) {
                comparisons++;
                if (n[left] < n[pivot]) {
                    left++;
                } else {
                    break;
                }
            }

            while (left <= right) {
                comparisons++; 
                if (n[right] > n[pivot]) {
                    right--;
                } else {
                    break;
                }
            }

            if (left <= right) {
                swap(n, left, right);
                left++;
                right--;
            }
        }
        //assigns pivot value to its proper place
        swap(n, pivot, left);
        
        subL = Arrays.copyOfRange(n, 0, left);
        subR = Arrays.copyOfRange(n, left + 1, n.length);
        
        //recursively quicksort each array after the partition
        subL = quickHelp(subL);
        subR = quickHelp(subR);
        
        //combine sub-arrays with pivot
        for (int i = 0; i < subL.length; i++) {
            n[i] = subL[i];
        }
        for (int i = 0; i < subR.length; i++) {
            n[left + 1 + i] = subR[i];
        }
        
        return n;
    }
    
    /**
     * Iterative.
     * 
     * Sorts an array per the "Shakersort" algorithm.
     * 
     * Scans up the entire array, ordering each adjacent pair, then
     * repeats the process downward.
     * 
     * Stops if no swaps were made after a scan (array is sorted).
     * 
     * @param base
     * @return 
     */
    public static int[] shaker(int[] base) {
        comparisons = 0;
        return shakerHelp(base);
    }
    private static int[] shakerHelp(int[] base) {
        
        if (base.length <= 1) return base;
        
        int[] n = Arrays.copyOf(base, base.length);
        
        boolean unsorted = true;
        
        while (unsorted) {
            
            unsorted = false; //won't loop again unless another swap occurs
            
            //first loop scans upward
            for (int i = 1; i < n.length; i++) {

                int j = i - 1;

                comparisons++;
                if (n[j] > n[i]) {
                    swap(n, j, i);
                    unsorted = true;
                }
            }
            
            //second loop scans downward, skipped if array is already sorted
            if (unsorted) {
                
                unsorted = false;
                
                for (int i = n.length - 1; i > 0; i--) {

                    int j = i - 1;

                    comparisons++;
                    if (n[j] > n[i]) {
                        swap(n, j, i);
                        unsorted = true;
                    }
                }
            }
        }
        
        return n;
    }
    
    /**
     * Recursive by use of heapify().
     * 
     * Sorts an array per the "Heapsort" algorithm.
     * 
     * Interprets array as a binary tree, 
     * arranges sub-arrays in reverse order for tree to become a max heap,
     * replaces root (highest element) with last leaf and repeats on 
     * the rest of the array, excluding all previous highest roots.
     * 
     * @param base
     * @return 
     */
    public static int[] heap(int[] base) {
        comparisons = 0; //for project analysis
        return heapHelp(base);
    }
    private static int[] heapHelp(int[] base) {
        
        int[] n = Arrays.copyOf(base, base.length);
        
        //create max heap prior to loop
        maxHeapify(n);
        
        //swap root and leaf, heapify new root, shrink working array, repeat
        for (int s = 0; s < n.length - 1; s++) {
            int arraySize = n.length - s - 1;
            
            //as s grows, 'end' of array shrinks
            swap(n, 0, arraySize);
            
            heapify(n, 0, arraySize);
        }
        
        return n;
    }
    /**
     * Utilizes heapify().
     * 
     * Turns a binary tree into a max heap.
     * Heapifies each root, starting from the last and ending at the first
     * 
     * @param base 
     */
    private static void maxHeapify(int[] base) { //mutates
        
        //find index of last root in array
        int r = base.length/2 - 1;
        
        for (int i = r; i >= 0; i--) {
            heapify(base, i, base.length);
        }
    }
    /**
     * Recursive
     * 
     * Places element i at correct location in a binary tree
     * Array size parameter allows method to work on portions of array
     * 
     * @param base
     * @param i
     * @param arraySize 
     */
    private static void heapify(int[] base, int i, int arraySize) { //mutates
        
        //Base case, i is a leaf
        if (i >= arraySize/2) return;
        
        //identify children indices
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int max;
        
        //find largest child
        //if r outside of range, l must be only child
        if (r < arraySize) {
            comparisons++;
            if (base[l] < base[r])  {
                max = r;
            } else {
                max = l;
            }
        } else {
            max = l;
        }
        
        //if element at i is smaller than largest child, swap the two
        //repeat repeat until i has no larger children
        comparisons++;
        if(base[i] < base[max]) {
            swap(base, i, max);
            heapify(base, max, arraySize);
        }
    }
    
    /**
     * mutator, swaps values at 2 indices of an array
     * @param base
     * @param i1
     * @param i2
     */
    private static void swap(int[] base, int i1, int i2) {
        int t = base[i1];
                base[i1] = base[i2];
                               base[i2] = t;
    }
    
    public static int lastComparisons() {
        return comparisons;
    }
    
}