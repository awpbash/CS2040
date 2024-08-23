/* Strat
 * Not much just UFDS i guess except we use more arrays to store information like parent, count and sum
 * Move is the unqiue one where it checks if p and q have different parents
 * If same then nothing done
 * If different then the set initially with p will reduce sum by p and count by 1. set with q will increment. The number p is
 * then moved
 */

import java.io.*;
import java.util.*;

public class almostUnionFind {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String iLine = null;
        while ((iLine = br.readLine()) != null) {
            String[] firstLine = iLine.split(" ");
            int numbers = Integer.parseInt(firstLine[0]);
            int m = Integer.parseInt(firstLine[1]);

            
            UFDS sets = new UFDS(numbers); // Create n disjoint sets

            for (int i = 0; i < m; i++) {
                String[] command = br.readLine().split(" ");
                int type = Integer.parseInt(command[0]);
                

                switch (type) {
                    case 1: {
                        int p = Integer.parseInt(command[1]);
                        int q = Integer.parseInt(command[2]);
                        sets.union(p, q); //Unionerino
                        /* 
                        System.out.println("Num array: " + sets.num);
                        System.out.println("Parents array: " + sets.parents);
                        System.out.println("Count array: " + sets.counts);
                        System.out.println("Sum array: " + sets.sums); */
                        break;
                    }
                    case 3: {
                        int p = Integer.parseInt(command[1]);
                        System.out.println(sets.getCount(p) + " " + sets.getSum(p)); //Printerino
                        break;
                    }
                    case 2: {
                        int p = Integer.parseInt(command[1]);
                        int q = Integer.parseInt(command[2]);
                        sets.move(p, q); //Moverino
                        //System.out.println("Num array: " + sets.num);
                //System.out.println("Parents array: " + sets.parents);
                //System.out.println("Count array: " + sets.counts);
                //System.out.println("Sum array: " + sets.sums);
                        break;
                    }
                    
                }
            }
        }
    }
}

class UFDS { 
    public ArrayList<Integer> num; // stores roots
    public ArrayList<Integer> parents; // stores parents
    public ArrayList<Long> sums;
    public ArrayList<Long> counts;
    
    public int getRoot(int i) { //Same as findSet
        int p = num.get(i); //Gets root of i in num
        while (p != parents.get(p)) { // Path compression
            p = parents.get(p);
        }
    
        num.set(i, p); //Replaces index i of num with element p
        return p;
    }
    
    public void union(int p, int q) {
        int pRoot = getRoot(p);
        int qRoot = getRoot(q);
        if (pRoot == qRoot) {
            return;
        }
    
        parents.set(pRoot, qRoot); //replaces index pRoot with element qRoot
        sums.set(qRoot, Long.valueOf(sums.get(qRoot) + sums.get(pRoot))); //update sum
        counts.set(qRoot, counts.get(qRoot) + counts.get(pRoot));//update count
    }

    public UFDS(long n) {
        num = new ArrayList<>();
        parents = new ArrayList<>();
        sums = new ArrayList<>();
        counts = new ArrayList<>();
        
        for (int i = 0; i <= n; i++) { //Start from 0 just cos indexing
            num.add(i);
            parents.add(i); //Each element is parent of itself
            Long j = Long.valueOf(i);
            sums.add(j);
            counts.add(1L);
        }
    }
    
    public long getSum(int p) {
        return sums.get(getRoot(p));
    }
    
    public long getCount(int p) {
        return counts.get(getRoot(p));
    }

    public void move(int p, int q) {
        int pRoot = getRoot(p);
        int qRoot = getRoot(q);
        
        if (pRoot == qRoot) { //if same parent root then skip
            return;
        }
    
        num.set(p, qRoot); //make root at index p to be qRoot -> qRoot parent of p
        sums.set(qRoot, Long.valueOf(sums.get(qRoot) + p));
        sums.set(pRoot, Long.valueOf(sums.get(pRoot) - p));
    
        counts.set(qRoot, counts.get(qRoot) + 1);
        counts.set(pRoot, counts.get(pRoot) - 1);
    }
}


