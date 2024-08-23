/*Strat
 * errr simply kosaraju's algorithm
 * In kosaraju, the idea is to first DFS to get an order in the stack
 * Then we transpose the graph
 * Then we pop the stack and do a DFS on the transposed graph. If we cannot dfs further then that component must be an SCC
 * now if the SCC in the transposed graph has no incoming edges, then nothing will cause any of the dominos in the SCC to fall
 * So we need to manually intervene by pushing
 * Thus the number of dominos we need to push is equivalent to the number of SCCs WITHOUT INCOMING EDGES
 */
import java.util.*;
import java.io.*;

class Graph_111 {
    int nodes;

    Stack<Integer> stack; // stack to store vertices for DFS

    ArrayList<Integer>[] adj; // adj[i] contains list of integers representing vertices with outgoing edge from vertex i


    boolean[] visited; // tracks which vertices have been visited during dfs
    int[] scc_ID; // scc_ID is the id to which vertex i belongs

    ArrayList<Integer>[] transposed_adj; //for transpose

    public Graph_111(int tiles) {
        this.nodes = tiles; //make graph with number of tiles
        
        adj = new ArrayList[nodes]; 
        transposed_adj = new ArrayList[nodes];
        for (int i = 0; i <= nodes-1; i++) {
            adj[i] = new ArrayList<>();

            transposed_adj[i] = new ArrayList<>();
        }

        visited = new boolean[nodes]; //visited array we set to FALSE
        scc_ID = new int[nodes];
        stack = new Stack<>();
    }

    public void addEdge(int x, int y) {
        adj[x-1].add(y-1); //-1 to convert to 0 based indexing
        transposed_adj[y-1].add(x-1);
    }

    public int countSCC() {
        for (int i = 0; i <= nodes-1; i++) {
            if (!visited[i]) {
                first_dfs(i); //runs first dfs lol
            }
        }
        int sccCount = 0; //counts number of SCCs, double up as ID number
        Arrays.fill(visited, false); //visited reset to false
        
        while (!stack.isEmpty()) {
            int x = stack.pop();
            if (!visited[x]) { //if not visited we DFS,
                ++sccCount; //next id
                second_dfs(x, sccCount);
            }
        }
        //FOR EACH SCC IN TRANSPOSED WE CHEKC IF GOT INCOMING EDGES. IF DONT HAVE MEANS WE MUST PUSH!!!
        int[] inDegree = new int[sccCount+1]; //store in-degree of each SCC
        for (int y = 0; y <= nodes-1; y++) {//for each node
            for (int x : adj[y]) {//for each edge in adjaceny list
                if (scc_ID[y] != scc_ID[x]) {//if they are not from the same SCC then it means x MUST HAVE AN INCOMING EDGE
                    inDegree[scc_ID[x]]++; 
                }
            }
        }
        int dominoes_to_push = 0; //need to manually knock over as many dominos as there are components with in-degree 0
        for (int i = 1; i <= sccCount; i++) {
            if (inDegree[i] == 0) {
                dominoes_to_push++;
            }
        }
        return dominoes_to_push;
    }

    //first DFS, push vertex to stack after visiting all of its neighbours
    public void first_dfs(int x) {
        visited[x] = true;
        for (int y : adj[x]) {
            if (!visited[y]) {
                first_dfs(y);
            }
        }
        stack.push(x);
    }
    
    //second dfs on transpose of graph, visits all vertices reachable from x in the transpose of graph
    public void second_dfs(int x, int id) {
        visited[x] = true;
        scc_ID[x] = id; //assign id to SCCs kinda like ufds-ish
        for (int y : transposed_adj[x]) {
            if (!visited[y]) {
                second_dfs(y, id);
            }
        }
    }
    
    
}
public class kms {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine()); //number of test cases

        for (int i = 0; i < tc; i++) {
            String[] input = br.readLine().split(" ");
            int tiles = Integer.parseInt(input[0]); //number of tiles
            int lines = Integer.parseInt(input[1]); //number of lines
            Graph_111 g = new Graph_111(tiles); //create a graph with the number of tiles
            for (int j = 0; j < lines; j++) { //iterate through the lines
                String[] edge = br.readLine().split(" ");
                int x = Integer.parseInt(edge[0]);
                int y = Integer.parseInt(edge[1]);
                g.addEdge(x,y); //x causes y to fall, we represent this by a directed edge from x to y
            }
            int result = g.countSCC(); //get the number of dominos we need to push
            System.out.println(result);
        }
    }
}
