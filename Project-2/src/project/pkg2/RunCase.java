/**
 * Team 7
 * Abraham Hill, Vanessa Nzamwita Ishimwe, Pong Vodmongkol
 * CS-2430-501-Spring 2026
 * Programming Project 2: Algorithm Performance
 */
package project.pkg2;

import java.util.Arrays;

/**
 *
 * @author abrahamhill
 */
public class RunCase {
    public final String algName;
    public final int[] unsorted;
    public final int[] sorted;
    public final int comps;
    
    public RunCase(String algName, int[] unsorted, int[] sorted, int comps) {
        this.algName = algName;
        this.unsorted = unsorted;
        this.sorted = sorted;
        this.comps = comps;
    }
    
    @Override
    public String toString(){
        return algName + " on " 
                + Arrays.toString(unsorted)
                + "->" + Arrays.toString(sorted)
                + " in " + comps + " comparisons";
    }
//    @Override
//    public String toString(){
//        return Arrays.toString(unsorted)
//                + " sorted in " + comps + " comparisons";
//    }
    
    public int compareTo(RunCase rc) {
        if (this.comps < rc.comps)
            return -1;
        if (this.comps > rc.comps)
            return 1;
        return 0;
    }
}
