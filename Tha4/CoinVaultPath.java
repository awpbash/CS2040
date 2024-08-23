import java.io.*;
import java.util.*;

public class CoinVaultPath {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] dimensions = br.readLine().split(" ");
        int M = Integer.parseInt(dimensions[0]);
        int N = Integer.parseInt(dimensions[1]);

        int[][] vault = new int[M][N];
        for (int i = 0; i < M; i++) {
            String[] row = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                vault[i][j] = Integer.parseInt(row[j]);
            }
        }

        // Initialize the maximum height encountered array
        int[][] maxHeights = new int[M][N];
        maxHeights[0][0] = vault[0][0];

        // Fill the first row
        for (int j = 1; j < N; j++) {
            maxHeights[0][j] = Math.max(maxHeights[0][j - 1], vault[0][j]);
        }

        // Fill the first column
        for (int i = 1; i < M; i++) {
            maxHeights[i][0] = Math.max(maxHeights[i - 1][0], vault[i][0]);
        }

        // Complete filling the array with the maximum height encountered to that point
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                int maxHeightFromLeft = maxHeights[i][j - 1];
                int maxHeightFromTop = maxHeights[i - 1][j];
                int currentHeight = vault[i][j];
                maxHeights[i][j] = Math.max(currentHeight, Math.max(maxHeightFromLeft, maxHeightFromTop));
            }
        }

        // The length of the ladder needed is the difference between the maximum height encountered
        // and the height of the first stack
        int ladderLength = maxHeights[M - 1][N - 1] - vault[0][0];
        System.out.println(ladderLength);
    }
}
