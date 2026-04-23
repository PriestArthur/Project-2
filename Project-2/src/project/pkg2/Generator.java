/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.pkg2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A bloated class with the sole purpose of generating permutations from 0 to n-1
 * 
 * @author abrahamhill
 */
public class Generator {
    
    public static ArrayList<int[]> permute(int n) {
        
        ArrayList<int[]> list = new ArrayList<>();
        int[] now = new int[n];
        
        //initialize base array
        for (int i = 0; i < n; i++)
            now[i] = i;
        
        list.add(0, Arrays.copyOf(now, n));
        
        //i = 0 would duplicate first set at the end
        for (int i = 1; i < fact(n); i++) {
            now = nextPermutation(now);
            list.add(Arrays.copyOf(now, n));
        }
        
        return list;
    }
    
    //breaks on final permutation
    private static int[] nextPermutation(int[] n) {
        
        //copy for safe mutation
        int[] m = Arrays.copyOf(n, n.length);
        
        int i = -1;
        int j = 0;
        
        for (int l = m.length - 2; l >= 0; l--) {
            if (m[l] < m[l + 1]) {
                i = l;
                break;
            } 
        }
        
        //if final permutation, returns first
        if (i < 0) return mirrorAt(m, 0);
        
        for (int l = m.length - 1; l >= 0; l--) {
            if (m[l] > m[i]) {
                j = l;
                break;
            }
        }
        
        //swap elements at i and j
        m[i] = m[i] + m[j];
        m[j] = m[i] - m[j];
        m[i] = m[i] - m[j];
        
        m = mirrorAt(m, i + 1);
        
        return m;
    }
    
    //helper method to mirror the elements of an array after a specified index
    private static int[] mirrorAt(int[] n, int i) {
        
        //copy for safe mutation
        int[] m = Arrays.copyOf(n, n.length);
        
        int j = m.length - 1;
        
        for (int l = i; l < j; l++) {
            m[l] = m[l] + m[j];
            m[j] = m[l] - m[j];
            m[l] = m[l] - m[j];
            
            j--;
        }
        
        return m;
    }
    
    private static int fact(int n) {
        return factHelper(1, n);
    }
    private static int factHelper(int a, int n) {
        if (n <= 1) return a;
        a *= n;
        n--;
        return factHelper(a, n);
    }
}
