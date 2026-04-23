package project.pkg2;

import java.util.Arrays;

/**
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
     * Currently broken, does not work with odd arrays
     * 
     * @param base
     * @return 
     */
    public static int[] merge(int[] base) {
        comparisons = 0;
        return mergeHelp(base);
    }
    private static int[] mergeHelp(int[] base) {
        
        if (base.length == 1) return base; //base case
        
        int splitPoint = base.length / 2;
        
        int[] sub1 = Arrays.copyOfRange(base, 0, splitPoint); 
        int[] sub2 = Arrays.copyOfRange(base, splitPoint, base.length);    
        
        sub1 = mergeHelp(sub1);
        sub2 = mergeHelp(sub2);
        
        int[] n = new int[base.length];
        
        int i1 = 0; //sub1 index tally
        int i2 = 0; //sub2 index tally
        for (int j = 0; j < n.length; j++) {
            
            comparisons++;
            
            if (sub1[i1] < sub2[i2]) {
                n[j] = sub1[i1++];
            }
            else {
                n[j] = sub2[i2++];
            }
            
            //check if end of either sub array reached
            if (i1 == sub1.length) break;
            if (i2 == sub2.length) break;
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
     * Non-mutating, non-working
     * @param base
     * @return 
     */
    public static int[] quick(int[] base) {
        comparisons = 0; //for project analysis
        return quickHelp(base);
    }
    //mutates
    private static int[] quickHelp(int[] base) {
        
        if (base.length == 1) return base; //base case
        
        int[] n = Arrays.copyOf(base, base.length);
        
        //indexes
        int pivot = n.length - 1; //pivot at end of array
        int left = -1;
        int right = -1;
        
        //left and right sub-arrays
        int[] subL;
        int[] subR;
        
        while (left <= right) {
            
            for (int i = 0; i < n.length; i++) {
                if (n[i] > n[pivot]) left = i;
            }
            if (left == -1) { //pivot is already in correct place (last)
                left = pivot;
                break;
            }

            for (int i = 0; i < n.length; i++) {
                if (n[i] < n[pivot]) right = i;
            }
            if (right == -1) { //pivot is already in correct place (last)
                left = 0;
                break;
            }

            swap(n, left, right);
        }
        
        //assigns pivot value to correct index
        swap(n, left, pivot);
        
        subL = Arrays.copyOfRange(n, 0, pivot);
        subR = Arrays.copyOfRange(n, pivot + 1, n.length);
        
        //recursively quicksort each array after the partition
        subL = quickHelp(subL);
        subR = quickHelp(subR);
        
        //combine sub-arrays with pivot
        for (int i = left + 1; i < n.length; i++) {
            n[i] = subL[i - left + 1];
        }
        for (int i = 0; i < subR.length; i++) {
            n[i] = subR[i];
        }
        
        return n;
    }
    
    /**
     * Fundamentally broken, doesn't even do shaker sort
     * 
     * Recursive; uses wrapper for comparison tracking
     * 
     * Sorts an array of integers per the Shakersort algorithm
     * Iterates through the array and swaps adjacent unordered pairs
     * Recursion occurs if array is not yet sorted
     * 
     * @param base
     * @return 
     */
    public static int[] shaker(int[] base) {
        comparisons = 0;
        return shakerHelp(base);
    }
    private static int[] shakerHelp(int[] base) {
        
        int[] n = Arrays.copyOf(base, base.length);
        
            for (int i = 1; i < base.length; i++) {
                
                comparisons++;
                
                if (base[i - 1] > base[i]) { //base case, no switching needed
                    swap(base, i, i - 1);
                    n = shakerHelp(base);
                    break; //break prevents unnecessary itterations
                }
            }
            
        return n;
    }
    
    public static int[] heap(int[] base) {
        
        comparisons = 0; //for project analysis
        
        int[] n = Arrays.copyOf(base, base.length);
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
    
    public static int comparisons() {
        return comparisons;
    }
    
}