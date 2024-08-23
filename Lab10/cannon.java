/* Strat
 * Make adjacency list of cannons and source and destination
 * calculate the distance between each cannon and source and destination
 * for every 2 points that are not source or destination, we can either run directly or
 * take cannon, aim directly to next cannon, travel 50m in 2s then run to the next cannon
 * run bellman ford O(VE) LOL SLOW
 * add flag to exit early if no more relaxation
 */

import java.io.*;
import java.util.*;
public class cannon {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] source = br.readLine().split(" "); //read first line
        double sx = Double.parseDouble(source[0]);
        double sy = Double.parseDouble(source[1]);
        
        String[] destination = br.readLine().split(" "); //read 2nd line
        double dx = Double.parseDouble(destination[0]);
        double dy = Double.parseDouble(destination[1]);

        int n = Integer.parseInt(br.readLine()); //number of input lines
        int vertices = n + 2; //number of cannons + source + destination

        double[] xCoord = new double[vertices+2]; //make array of x coordinates
        double[] yCoord = new double[vertices+2]; //make array of y coordinates
        xCoord[0] = sx; //put source into array
        yCoord[0] = sy;
        xCoord[1] = dx; //put destination into array
        yCoord[1] = dy;

        for (int i = 2; i < n + 2; i++) {
            String[] input = br.readLine().split(" "); //read input lines
            double ix = Double.parseDouble(input[0]);
            double iy = Double.parseDouble(input[1]);
            xCoord[i] = ix; //put cannon into array
            yCoord[i] = iy;
        }

        double[][] times = new double[vertices][vertices]; //make 2D array of time
        ArrayList<Integer>[] adjList = new ArrayList[vertices]; //make adjacency matrix
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new ArrayList<Integer>(); //initialize arraylists
            for (int j = 0; j < vertices; j++) {
                double d = Math.sqrt((xCoord[i] - xCoord[j])*(xCoord[i] - xCoord[j]) + (yCoord[i] - yCoord[j])*(yCoord[i] - yCoord[j])); //pythagoras bitch
                if (i == j) continue;

                if (i == 0) { //if start from source
                    times[i][j] = d / 5.0; //calculate time it takes to run from source to cannon
                }
                else if (i == 1) { //if start from destination
                    times[i][j] = 0; //time is 0 cuz we dont wanna leave SOURCE
                }
                else {
                    double time = Math.min(2 + Math.abs(50 - d) / 5.0, d / 5.0); //calculate time and compare either running directly or running to cannon faster
                    times[i][j] = time; //put time into array
                }
                adjList[i].add(j); //add to adjacency list
            }
        }

        //modified Bellman Ford
        double[] best = new double[vertices];
        for (int i = 0; i < vertices; i++) {
            best[i] = 78097087;
        }
        best[0] = 0;

        boolean changed = true; //flag to detect if any distance was updated

        //relax all edges V-1 times or until no changes occur
        for (int i = 0; i < vertices - 1 && changed; i++) {
            changed = false; //reset change flag
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (best[vertex] == Double.MAX_VALUE) continue; //dont need to check if not reachable

                for (int j = 0; j < adjList[vertex].size(); j++) {
                    int neighbor = adjList[vertex].get(j);
                    double weight = times[vertex][neighbor];

                    if (best[vertex] + weight < best[neighbor]) { //relaxation
                        best[neighbor] = best[vertex] + weight;
                        changed = true; //update flag since a change was made
                    }
                }
            }
        }
        System.out.println(best[1]);
    }
}
