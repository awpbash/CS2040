/* Strat
 * Use some form of Dijkstra's. Implement our own comparator and PQ
 * We can create an array that stores the best height out of the 4 possible directions to traverse to
 * Then we dijkstra the "best" array to find the path with the lowest total weight, keeping track of the max in this path
 * We then return this max
 */

import java.io.*;
import java.util.*;

public class duck {
    //4 adjacent coordinates
    public static int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] rowsColumns = br.readLine().split(" ");
        int rows = Integer.parseInt(rowsColumns[0]);
        int columns = Integer.parseInt(rowsColumns[1]);
        
        int[][] grid = new int[rows][columns];
        int[][] best = new int[rows][columns]; //stores the minimum ladder height required to reach each cell in the maze
        for (int i = 0; i < rows; i++) {
            String[] line = br.readLine().split(" ");
            Arrays.fill(best[i], 100001); //set best heights to supa dupa big numba
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Integer.parseInt(line[j]);//populate the coin stack grid-dy
            } 
        }

        //best[0][0] = 0; //starting point's best height is its own height
        int count = 0;
        PriorityQueue<Coins> pq = new PriorityQueue<>(new CoinsComparator());
        pq.add(new Coins(0, 0, 0)); //start from top left vertex with weight 0
        while (!pq.isEmpty()) {
            Coins cur = pq.poll(); //take out the current vertex from PQ
            int x = cur.x;
            int y = cur.y;
            for (int direction = 0; direction < moves.length; direction++) {
                int new_x = x + moves[direction][0];
                int new_y = y + moves[direction][1];
                if (new_x <0 || new_x >= rows || new_y < 0 || new_y >= columns) {
                    continue; //if out of bounds, GO NEXT!!!
                } 
                //Meatiest part yum yum
                int temp = Math.max(0,grid[new_x][new_y]- grid[x][y]); //find the height difference between the two cells, if negative, set to 0
                temp = Math.max(temp, best[x][y]); //compare the difference to the current best height in best grid. If taller then we dowan to go that direction
                if (temp < best[new_x][new_y]) { //if the new height is BETTER than best, we want to take that path
                    best[new_x][new_y] = temp; //update new best height or weight
                    pq.add(new Coins(new_x, new_y, temp)); //Diskstra's part here, we add stuff into the PQ if the direction is better
                    count++;
                }
                
            }
        }
        //System.out.println(count);
        //System.out.println(Arrays.deepToString(best)); //see best array
        System.out.println(best[rows - 1][columns - 1]);
    }
}


class Coins {
    int x;
    int y;
    int weight;

    public Coins(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
}

class CoinsComparator implements Comparator<Coins> {
    @Override
    public int compare(Coins coin1, Coins coin2) {
        return coin1.weight - coin2.weight; //sort by weight
    }
}
