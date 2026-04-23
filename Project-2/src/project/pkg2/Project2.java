package project.pkg2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author abrahamhill
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int[] m : Generator.permute(6)) {
            System.out.println(Arrays.toString(Sort.quick(m)));
        }
    }
}