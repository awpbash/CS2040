import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<>();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    Graph getTranspose() {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++) {
            for (Integer i : adj[v]) {
                g.adj[i].add(v);
            }
        }
        return g;
    }

    void fillOrder(int v, boolean visited[], Stack<Integer> stack) {
        visited[v] = true;
        for (Integer i : adj[v]) {
            if (!visited[i]) {
                fillOrder(i, visited, stack);
            }
        }
        stack.push(v);
    }

    void DFSUtil(int v, boolean visited[]) {
        visited[v] = true;
        System.out.print(v + " ");
        for (Integer i : adj[v]) {
            if (!visited[i]) {
                DFSUtil(i, visited);
            }
        }
    }

    int kosaraju() {
        Stack<Integer> stack = new Stack<>();
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                fillOrder(i, visited, stack);
            }
        }

        Graph gr = getTranspose();

        Arrays.fill(visited, false);
        int count = 0;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                gr.DFSUtil(v, visited);
                System.out.println();
                count++;
            }
        }
        return count;
    }

    int countSCCsWithIndegreeZero() {
        int[] indegree = new int[V];
        for (int v = 0; v < V; v++) {
            for (Integer i : adj[v]) {
                indegree[i]++;
            }
        }
        int count = 0;
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                count++;
            }
        }
        return count;
    }
}

public class DominoesFalling {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            String[] inp = br.readLine().split(" ");
            int n = Integer.parseInt(inp[0]);
            int m = Integer.parseInt(inp[1]);

            Graph g = new Graph(n);
            for (int j = 0; j < m; j++) {
                String[] inp2 = br.readLine().split(" ");
                int x = Integer.parseInt(inp2[0]) - 1;
                int y = Integer.parseInt(inp2[1]) - 1;
                g.addEdge(x, y);
            }

            int res = g.countSCCsWithIndegreeZero();
            System.out.println(res);
        }
    }
}
