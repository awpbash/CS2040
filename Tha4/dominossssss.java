/*Strat
 * errr simply kosaraju's algorithm
 * In kosaraju, the idea is to first DFS to get an order in the stack
 * Then we transpose the graph
 * Then we pop the stack and do a DFS on the transposed graph. If we cannot dfs further then that componenet must be an SCC
 * The SCC will then have no incoming edges which means NOTHING will cause any of the dominoes in the SCC to fall
 * So we need to manually intervene by pushing
 * Thus the number of dominos we need to push is equivalent to the number of SCCs
 */
import java.util.*;
import java.io.*;
public class dominossssss {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine()); //number of test cases

        for (int i = 0; i < tc; i++) {
            String[] input = br.readLine().split(" ");
            int tiles = Integer.parseInt(input[0]); //number of tiles
            int lines = Integer.parseInt(input[1]); //number of lines
            Graphss g = new Graphss(tiles); //create a graph with the number of tiles
            for (int j = 0; j < lines; j++) { //iterate through the lines
                String[] edge = br.readLine().split(" ");
                int x = Integer.parseInt(edge[0]);
                int y = Integer.parseInt(edge[1]);
                g.insertEdge(x,y); //x causes y to fall, we represent this by a directed edge from x to y
            }
            int result = g.transpose().countSCCsWithIndegreeZero(); //get the number of dominos we need to push
            System.out.println(result);
        }
    }
}
//Kosaraju's algorithm
class Graphss {
    public int nodes; //number of tiles
    public LinkedList<Integer>[] adj; //adjacency list instead of matrix for space efficiency

    Graphss(int tiles){
        nodes = tiles;
        adj = new LinkedList[tiles]; //initialize adjacency list of size "tiles". We use LinkedList for fast insertion and deletion
        for (int i = 0; i < tiles; i++) {
            adj[i] = new LinkedList<>();
        }
    }
    Graphss transpose(){
        Graphss g_transposed = new Graphss(nodes); //create a new graph with the same number of nodes
        for (int vertex = 0; vertex < nodes; vertex++) {
            for (int i : adj[vertex]) {
                g_transposed.adj[i].add(vertex); //add the reverse edge
            }
        }
        return g_transposed;
    }
    void DFS(int node, boolean[] visited, Stack<Integer> stack){
        visited[node] = true;
        for (int i : adj[node]) {
            if (!visited[i]) {
                DFS(i, visited,stack); //if not in visited, add into stack to visit next
            }
        }
        stack.push(node); //push the node into the stack
    }
    void special_sauce(){
        Stack<Integer> stack = new Stack<>(); //stack to store the order of nodes
        boolean[] visited = new boolean[nodes]; //visited array to keep track of visited nodes
        for (int i = 0; i < nodes; i++) {
            if (!visited[i]) {
                DFS(i, visited, stack); //DFS to get the order of nodes
            }
        }
        Graphss g_transposed = transpose(); //transpose the graph
        int count = 0; //initialize count of SCCs = 0 WOWOWOWOW
        Arrays.fill(visited, false); //reset visited array
        
        while (!stack.isEmpty()) {
            int node = stack.pop(); //pop the stack
            if (!visited[node]) {
                g_transposed.DFS(node, visited, stack); //DFS on the transposed graph
                 //increment count of SCCs
                 count++;
            }
        } //return the number of dominos we need to push
    }
    int countSCCsWithIndegreeZero() { //this part does not use kosaraju's algorithm, just do a count on indegree
        int[] indegree = new int[nodes]; //lets hope it works
        for (int v = 0; v < nodes; v++) {
            for (Integer i : adj[v]) {
                indegree[i]++;
            }
        }
        int count = 0;
        for (int i = 0; i < nodes; i++) {
            if (indegree[i] == 0) {
                count++;
            }
        }
        return count;
    }
    void insertEdge(int x, int y){
        adj[x-1].add(y-1); //add edge from x to y. -1 to account for 0-based indexing
    }
}