/* Big boi algo
 * First we do kosaraju to find SCCs
 * we also store information about sccSize and sccID of each vertex + indegree of each SCC
 * now we TRACE the edges, if u and v have different sccID, then they are in different SCCs, meaning we can send 1 less signal
 * if the SCC has only 1 vertex and indegree is 0, then it is a solobot
 * if the SCC has more than 1 vertex and indegree is 0, then it is a botnet that needs a signal
 */
import java.io.*;
import java.util.*;

public class bots {
    static ArrayList<Integer>[] adjList, adjListRev;
    static boolean[] visited;
    static Stack<Integer> topoStack;
    static int[] sccID, sccSize, inDegree;
    static int currentSCCID; //diswan track number of SCCs
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String[] firstLine = br.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]); 
        int m = Integer.parseInt(firstLine[1]); 

        adjList = new ArrayList[n]; 
        adjListRev = new ArrayList[n]; // Transposed graph
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
            adjListRev[i] = new ArrayList<>();
        }

        //build the fucken graph and transpose
        for (int i = 0; i < m; i++) {
            String[] edge = br.readLine().split(" ");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            adjList[u].add(v);
            adjListRev[v].add(u);
        }

        //Kosaraju's algorithm to find SCCs
        //first DFS pass
        topoStack = new Stack<>();
        visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        //second DFS pass on transposed graph
        Arrays.fill(visited, false);
        sccID = new int[n];
        sccSize = new int[n];
        currentSCCID = 0;
        while (!topoStack.isEmpty()) {
            int node = topoStack.pop();
            if (!visited[node]) {
                dfs2(node);
                currentSCCID++;
            }
        }

        //check edges. If u and v have different sccID means different SCCs are connected
        //so we can send 1 less signal
        inDegree = new int[currentSCCID]; //inDegree of each SCC
        for (int u = 0; u < n; u++) {
            for (int v : adjList[u]) {
                int sccU = sccID[u];
                int sccV = sccID[v];
                if (sccU != sccV) {
                    inDegree[sccV]++;
                }
            }
        }

        //count number of solobots and minimum signals in botnets
        int solobotCount = 0;
        int botnetCount = 0;
        for (int i = 0; i < currentSCCID; i++) {
            if (sccSize[i] == 1 && inDegree[i] == 0) {
                solobotCount++; //Solobot
            } else if (sccSize[i] > 1 && inDegree[i] == 0) {
                botnetCount++; //botnet that needs a signal
            }
        }

        // Output result
        pw.println(solobotCount + " " + botnetCount);
        pw.flush();
        br.close();
        pw.close();
    }

    // First DFS on the original graph to determine finishing times
    static void dfs1(int u) {
        visited[u] = true;
        for (int v : adjList[u]) {
            if (!visited[v]) {
                dfs1(v);
            }
        }
        topoStack.push(u); // Add node to stack on finish
    }

    // Second DFS on the transposed graph to identify SCCs
    static void dfs2(int u) {
        visited[u] = true;
        sccID[u] = currentSCCID;
        sccSize[currentSCCID]++;
        for (int v : adjListRev[u]) {
            if (!visited[v]) {
                dfs2(v);
            }
        }
    }
}
