import java.util.Scanner;

public class JumpGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // Reads the number of blocks from the input
        int[] heights = new int[N];

        for (int i = 0; i < N; i++) {
            heights[i] = scanner.nextInt(); // Reads each block's height
        }

        int[] minJumps = findMinJumps(heights);
        
        for (int jumps : minJumps) {
            System.out.print(jumps + " "); // Prints the minimum jumps for each block
        }
        
        scanner.close();
    }

    public static int[] findMinJumps(int[] heights) {
        int n = heights.length;
        int[] minJumps = new int[n];
        // Find the index of the highest block
        int highestBlockIndex = 0;
        for (int i = 1; i < n; i++) {
            if (heights[i] > heights[highestBlockIndex]) {
                highestBlockIndex = i;
            }
        }
        // The highest block has 0 jumps to itself
        minJumps[highestBlockIndex] = 0;
        
        // Check for the left side of the highest block
        for (int i = highestBlockIndex - 1; i >= 0; i--) {
            int minJump = Integer.MAX_VALUE;
            // Go right from the current block to find the closest higher block
            for (int j = i + 1; j < n; j++) {
                if (heights[j] > heights[i] && minJumps[j] < minJump) {
                    minJump = 1 + minJumps[j];
                }
            }
            minJumps[i] = minJump;
        }
        
        // Check for the right side of the highest block
        for (int i = highestBlockIndex + 1; i < n; i++) {
            int minJump = Integer.MAX_VALUE;
            // Go left from the current block to find the closest higher block
            for (int j = i - 1; j >= 0; j--) {
                if (heights[j] > heights[i] && minJumps[j] < minJump) {
                    minJump = 1 + minJumps[j];
                }
            }
            minJumps[i] = minJump;
        }
        
        return minJumps;
    }
}
