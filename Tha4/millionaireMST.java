import java.io.*;
import java.util.*;
/* Using Prim's Algorithm we can find the minimum height difference between two cells in the grid.
 * Since going down is 0 cost, we use max(0, height difference) as the weight of the edge.
 * We first define the possible directions (right, down, left, up) and use a priority queue to store edges by minimum height difference.
 * then we create a visited array to track visited cells
 * we can run prims until all cells are visited
 * so at every cell, we compute the height difference of all 4 directions and add to the priority queue
 * we also update the maximum weight encountered on the path
 * if we reach the bottom-right corner, we output the result and exit
 * 
 * Kruskal's algo cannot work here bcos for any 2 vertices u,v, we will consider the edges (u,v) and (v,u)
 * this means that for example for a small grid [1,5,4], instead of travelling 1 -> 5 -> 4 and return (5-1) = 4
 * Kruskal's bidirectional edges will consider the edge (5,1) and (5,4) and since both are going down, return 0
 * So not ideal here
 */
public class millionaireMST {
    // Possible directions (right, down, left, up)
    static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Input grid dimensions
        String[] dims = br.readLine().split(" ");
        int rows = Integer.parseInt(dims[0]);
        int cols = Integer.parseInt(dims[1]);

        // Input the grid
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(line[j]);
            }
        }

        // Priority Queue to store edges by minimum height difference (weight)
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        // Visited array to track visited cells
        boolean[][] visited = new boolean[rows][cols];
        pq.add(new Edge(0, 0, 0));  // Start from top-left corner (0,0)

        int maxWeightOnPath = 0;  // This will store the max weight (max height diff) on the path

        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            int x = current.x;
            int y = current.y;
            int weight = current.weight;

            // If already visited, skip
            if (visited[x][y]) continue;

            // Mark as visited
            visited[x][y] = true;

            // Update the maximum weight encountered on the path
            maxWeightOnPath = Math.max(maxWeightOnPath, weight);

            // If we reach the bottom-right corner, output the result and exit
            if (x == rows - 1 && y == cols - 1) {
                System.out.println(maxWeightOnPath);
                return;
            }

            // Explore all 4 directions
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // Check bounds and if the new cell is visited
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY]) {
                    // Calculate the height difference (step cost)
                    int heightDifference = Math.max(0,(grid[newX][newY] - grid[x][y]));
                    pq.add(new Edge(newX, newY, heightDifference));  // Add to the priority queue
                }
            }
        }
    }

    // Class representing an edge from one cell to another with a certain weight
    static class Edge {
        int x, y, weight;

        public Edge(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }
}
