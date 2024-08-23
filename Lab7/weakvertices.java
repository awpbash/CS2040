// Strat
//Bruteforce solution, check every vertex if it has a triangle or not
//O(n^3) solution
//We first form the adjacency matrix and then check for every vertex if it has a triangle or not
//The triangle function checks if there is an edge between vertex and i and vertex and j and i and j, if so return true
import java.util.*;
import java.io.*;

public class weakvertices {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String line = br.readLine();
            int n = Integer.parseInt(line);
            if (n == -1)
                break;

            int[][] ad_matrix = new int[n][n]; //make n by n matrix

            for (int row = 0; row < n; row++) { //make row
                String[] rowValues = br.readLine().split(" ");
                for (int col = 0; col < n; col++) { //make column
                    ad_matrix[row][col] = Integer.parseInt(rowValues[col]);
                }
            }

            for (int vertex = 0; vertex < n; vertex++) {
                if (check_triangle(vertex, ad_matrix))
                    continue;
                else
                    bw.write(vertex + " ");
            }

            bw.write("\n");
        }

        br.close();
        bw.close();
    }

    public static boolean check_triangle(int vertex, int[][] nums) {
        int length = nums.length; //get length of matrix
        for (int i = 0; i < length; i++) { 
            if (nums[vertex][i] == 1) { //if there is edge between vertex and i 
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[vertex][j] == 1 && nums[i][j] == 1) //if there is edge between vertex and j and i and j
                        return true; //wow triangle found
                }
            }
        }
        return false; //no triangle found
    }
}
