/*Strat
 * Simply run either Kruskals or Prims I guess
 */
import java.io.*;
import java.util.*;

public class mapmapmap {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Map village = new Map(n); // Create a graph with n vertices

        for (int i = 0; i<n; i++){
            String[] input = br.readLine().split(" ");
            for (int j = 0; j<n; j++){
                int weight = Integer.parseInt(input[j]);
                if (j > i){
                    village.addEdge(i, j, weight);  //add edge from i to j with weight into EDGELIST
                }
            }
        }
        village.kruskalMST(); //BOOM BOOM KRUSKALS
    }
}
class Edge { //make comparator!!!!!!
    int start;
    int end;
    int weight;

    public Edge(int s, int e, int w){
        start = s;
        end = e;
        weight = w;
    }

}

class Map{
    int V; //number of VILLAGES
    List<Edge> edge_list; //PATHSSSSS

    public Map(int V){
        this.V = V;
        edge_list = new ArrayList<>(); //make edgelist
    }

    public void addEdge(int start, int end, int weight){
        edge_list.add(new Edge(start, end, weight)); //add edges into edgelist
    }

    public void kruskalMST() {
        //do sort here to make the edge list sorted
        edge_list.sort((edge1, edge2) -> Integer.compare(edge1.weight, edge2.weight));

        //UFDS PARENT ARRAY
        int[] parent = new int[V];
        for (int i = 0; i < V; i++){
            parent[i] = i;
        }

        List<Edge> mst = new ArrayList<>(); //To store the results of MST

        for (Edge edge : edge_list) { //find the roots of the 2 nodes of the edge, check if the roots are same
            int rootStart = find(parent, edge.start);
            int rootEnd = find(parent, edge.end);

            if (rootStart != rootEnd) { //if no cycle ie the roots are different we take those
                mst.add(edge);
                union(parent, rootStart, rootEnd);
            }
        }

        //print the damn mst
        for (Edge edge : mst) {
            System.out.println((edge.start + 1) + " " + (edge.end + 1));
        }
    }

    public int find(int[] parent, int i) { //recursive find parent
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    public void union(int[] parent, int x, int y) { //make the 2 vertices union by sharing parent
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX != rootY) {
            parent[rootY] = rootX;
        }
    }
}