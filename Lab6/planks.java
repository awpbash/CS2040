import java.io.*;
import java.util.*;

public class planks {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numLines = Integer.parseInt(br.readLine());
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        //treeSet to store planks and allow efficient retrieval O(logN)
        TreeSet<Plank> planksSet = new TreeSet<>(new PlankComparator());
        int num = 0;
        for (int i = 0; i < numLines; i++) {
            String[] input = br.readLine().split(" ");
            if (input[0].equals("a")) {
                int weight = Integer.parseInt(input[1]);
                int length = Integer.parseInt(input[2]);
                Plank newPlank = new Plank(weight, length, num++);
                planksSet.add(newPlank);
                //System.out.println("Plank added: " + newPlank.weight + " " + newPlank.length + " " + newPlank.num);
            } else {
                int targetLength = Integer.parseInt(input[1]);
                long effort = chase(planksSet, targetLength);
                pw.println(effort); 
            }
        }
        pw.close();
    }

    // Comparator for comparing Plank objects
    public static class PlankComparator implements Comparator<Plank> {
        public int compare(Plank p1, Plank p2) {
            if (p1.length != p2.length) {
                return Long.compare(p1.length, p2.length);
            } else if (p1.weight != p2.weight) {
                return Long.compare(p2.weight, p1.weight);  // Heavier comes first if same length
            } else {
                return Integer.compare(p1.num, p2.num);  // Break ties using unique plank number
            }
        }
    }

    public static long chase(TreeSet<Plank> planksSet, int targetLength) {
        // Find Plank A: the longest plank ≤ targetLength using floor()
        Plank plankA = planksSet.floor(new Plank(Integer.MIN_VALUE, targetLength, Integer.MAX_VALUE)); // Dummy plank for search


        // Remove Plank A after finding it
        planksSet.remove(plankA);

        // Find Plank B: the shortest plank ≥ targetLength using ceiling()
        Plank plankB = planksSet.ceiling(new Plank(Integer.MAX_VALUE, targetLength, Integer.MAX_VALUE)); // Dummy plank for search

        //calculate the effort
        long effort = (1 + (plankA.weight + plankB.weight)) * (1 + Math.abs(plankA.length - plankB.length));

        //remove Plank B after calculating effort
        planksSet.remove(plankB);

        return effort;
    }
}

//plank class with weight, length, and unique identifier
class Plank {
    public long weight;
    public long length;
    public int num; //to distinguish duplicates

    public Plank(long weight, long length, int num) {
        this.weight = weight;
        this.length = length;
        this.num = num; 
    }
}
