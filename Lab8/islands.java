/* Strat
 * Once we finish building the matrix, we scan through the matrix and whenever we find "L"
 * We run DFS. Since "C" can take any status, if "C" is connected to "L" then greedily we should treat it as "L"
 * So in our DFS, the stopping condition shld either be out of boundary OR we hit "W"
 * Instead of making a visited array, we can just change the ting to "W" to indicate visited
 * YEP then we DFS all 4 possible directions and when we stop DFS, we increment count by 1
 * If got no "L" at all then dont even bother DFS-ing all "C" can be "W" and we can just return 0
 * YEP
 * 
 */
import java.io.*;
import java.util.*;

public class islands {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] rows_columns = br.readLine().split(" ");
        int rows = Integer.parseInt(rows_columns[0]);
        int columns = Integer.parseInt(rows_columns[1]);

        //Forming of matrix/grid
        char[][] grid = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            String row = br.readLine();
            for (int j = 0; j < columns; j++) {
                /* 
                if(row.charAt(j) == 'C'){
                    grid[i][j] = 'W';
                 } //replace "C" with "W" as we want to find minimum islands
                else */ // SIKE DOESNT WORK COS LCL becomes 2 islands instead of 1 zzz
                grid[i][j] = row.charAt(j);
            }
        }

        System.out.println(countIslands(grid));
    }

    public static int countIslands(char[][] grid) {
        int count = 0;
        int n = grid.length;
        if (n == 0) return 0; //catch empty grid edge case
        int m = grid[0].length;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 'L') { //if got POSSIBILITY of ISLAND we cry and run DFS :<
                    //importantly the starting condition should be "L" only and not either "L" or "C" as we want minimum so we can assum
                    //"C" to first take "W" status
                    DFSdfsDFSdfsDFSdfsZZZ(grid, i, j); //keep DFS-ing to find connected islands and replace with water
                    ++count;
                }
        }    
        return count;
    }
    public static void DFSdfsDFSdfsDFSdfsZZZ(char[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length //Out of boundary
        || grid[x][y]== 'W') 
            return;
        grid[x][y] = 'W'; //visited island we replace with water go KABOOM
        DFSdfsDFSdfsDFSdfsZZZ(grid, x, y + 1);
        DFSdfsDFSdfsDFSdfsZZZ(grid, x, y - 1);
        DFSdfsDFSdfsDFSdfsZZZ(grid, x + 1, y);
        DFSdfsDFSdfsDFSdfsZZZ(grid, x - 1, y);
    }
}
