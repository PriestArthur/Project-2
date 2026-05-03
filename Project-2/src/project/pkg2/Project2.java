/**
 * Team 7
 * Abraham Hill, Vanessa Nzamwita Ishimwe, Pong Vodmongkol
 * CS-2430-501-Spring 2026
 * Programming Project 2: Algorithm Performance
 */
package project.pkg2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abrahamhill
 */
public class Project2 {
    
    //tables of best and worst run cases from each algorithm
    private static ArrayList<RunCase>[] mergeCases  = new ArrayList[3];
    private static ArrayList<RunCase>[] quickCases  = new ArrayList[3];
    private static ArrayList<RunCase>[] shakerCases  = new ArrayList[3];
    private static ArrayList<RunCase>[] heapCases  = new ArrayList[3];
    
    /**
     * 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //initilaize tables
        for (int i = 0; i < 3; i++) {
            mergeCases[i] = new ArrayList<RunCase>();
            quickCases[i] = new ArrayList<RunCase>();
            shakerCases[i] = new ArrayList<RunCase>();
            heapCases[i] = new ArrayList<RunCase>();
        }
        
        //create permutations on 4, 6, and 8
        ArrayList<int[]> small = Generator.permute(4);
        ArrayList<int[]> med   = Generator.permute(6);
        ArrayList<int[]> large = Generator.permute(8);
        
        //perform sorting algs on every permutation
        for (int[] unsorted : small) {
            addRunCases(unsorted);
        }
        for (int[] unsorted : med) {
            addRunCases(unsorted);
        }
        for (int[] unsorted : large) {
            addRunCases(unsorted);
        }
        
//        for (RunCase rc : heapCases[0]) {
//            System.out.println(rc);
//        }
//            
        printAllResults();
    }
    
    /**
     * For a single unsorted array, records 4 separate runcases for
     * each algorithm.
     * 
     * Used while iterating through each permutation
     *      
     * @param unsorted 
     */
    private static void addRunCases(int[] unsorted) {
        
        //finds data table row index based on array size
        int rowInd = unsorted.length / 2 - 2;
        
        RunCase rc;
        int[] sorted;
        int comps;

        sorted = Sort.merge(unsorted);
        comps = Sort.lastComparisons();
        rc = new RunCase("Mergesort", unsorted, sorted, comps);
//        System.out.println(rc);
        filter(mergeCases[rowInd], rc);
        
        sorted = Sort.quick(unsorted);
        comps = Sort.lastComparisons();
        rc = new RunCase("Quicksort", unsorted, sorted, comps);
//        System.out.println(rc);
        filter(quickCases[rowInd], rc);

        sorted = Sort.shaker(unsorted);
        comps = Sort.lastComparisons();
        rc = new RunCase("Shakersort", unsorted, sorted, comps);
//        System.out.println(rc);
        filter(shakerCases[rowInd], rc);

        sorted = Sort.heap(unsorted);
        comps = Sort.lastComparisons();
        rc = new RunCase("Heapsort", unsorted, sorted, comps);
//        System.out.println(rc);
        filter(heapCases[rowInd], rc);
    }
    
    /**
     * Sorts a RunCase into a given list, assuming the sent list is already ordered
     * @param list
     * @param rc 
     */
    private static void filter(ArrayList<RunCase> list, RunCase rc) {
        
        if (list.isEmpty()) {
            list.add(rc);
            return;
        }
        
        if (list.size() == 1) {
            if (rc.compareTo(list.get(0)) < 1)
                list.addFirst(rc);
            else
                list.addLast(rc);
        }
        
        for (int i = 1; i < list.size(); i++) {
            if(rc.compareTo(list.get(i)) < 0) {
                list.add(i - 1, rc);
                return;
            }
        }
        
        //element is largest in list, added to the end
        list.addLast(rc);
    }
    
    /**
     * returns the ten RunCases with the highest comparison number
     * @param rcs
     * @return 
     */
    private static ArrayList<RunCase> worstTen(ArrayList<RunCase> rcs) {
        return new ArrayList<RunCase>(rcs.subList(rcs.size() - 10, rcs.size()));
    }
    
    /**
     * returns the ten RunCases with the lowest comparison number
     * @param rcs
     * @return 
     */
    private static ArrayList<RunCase> bestTen(ArrayList<RunCase> rcs) {
        return new ArrayList<RunCase>(rcs.subList(0, 10));
    }
    
    /**
     * returns the mean average of comparisons in a list of RunCases
     * @param rcs
     * @return 
     */
    private static double average(ArrayList<RunCase> rcs) {
        double total = 0.0;
        int quant = rcs.size();
        
        for (RunCase rc : rcs)
            total += rc.comps;
        
        return total / quant;
    }
    
    private static String results(ArrayList<RunCase> rcs) {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Best 10 Cases: \n");
        
        for (RunCase rc : bestTen(rcs))
            sb.append(rc + "\n");
        
//        sb.append("\n");
        sb.append("Worst 10 Cases: \n");
        
        for (RunCase rc : worstTen(rcs))
            sb.append(rc + "\n");
        
//        sb.append("\n");
        sb.append("Average No. of Comparisons: ");
        sb.append(String.format("%.2f", average(rcs)));
        
        return sb.toString();
    }
    
    private static void printAllResults() {
        System.out.println("Programming Project 2: Algorithm Performance_PLO-CS-3");
        System.out.println("");
        
        for (int i = 0; i < 3; i++) {
            System.out.println("");
            System.out.println("---------------------------------------------");
            System.out.println("  --------Cases for Permutations on " + ((2 * i) + 4)+"------");
            System.out.println("---------------------------------------------");
            System.out.println("");

            System.out.println("-Mergesort-");
            System.out.println(results(mergeCases[i]));
            System.out.println("");

            System.out.println("-Quicksort-");
            System.out.println(results(quickCases[i]));
            System.out.println("");

            System.out.println("-Shakersort-");
            System.out.println(results(shakerCases[i]));
            System.out.println("");

            System.out.println("-Heapsort-");
            System.out.println(results(heapCases[i]));
            System.out.println("");
        }
    }
            
}