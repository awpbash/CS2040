import java.io.*;
import java.util.*;

public class VaultLadder {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = br.readLine().split(" ");
        int M = Integer.parseInt(firstLine[0]);
        int N = Integer.parseInt(firstLine[1]);

        int[][] grid = new int[M][N];
        for (int i = 0; i < M; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(line[j]);
            }
        }

        // Assuming we start at the northwest corner
        int maxPileHeight = grid[0][0];

        // Loop through the grid moving only to the south or to the east
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 && j == 0) {
                    // This is the starting point; no action needed
                } else if (i == 0) {
                    // If we're in the first row, we can only move from the west
                    maxPileHeight = Math.max(maxPileHeight, grid[i][j]);
                } else if (j == 0) {
                    // If we're in the first column, we can only move from the north
                    maxPileHeight = Math.max(maxPileHeight, grid[i][j]);
                } else {
                    // In the middle of the grid, we take the higher of the western or northern cell
                    maxPileHeight = Math.max(maxPileHeight, Math.max(grid[i][j], Math.max(grid[i-1][j], grid[i][j-1])));
                }
            }
        }

        // The height of the ladder needed is the difference between the max pile and the starting pile
        int ladderLength = maxPileHeight - grid[0][0];
        System.out.println(ladderLength);
    }
}
