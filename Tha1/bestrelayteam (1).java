import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/* Strategy: Sort everyone by 1st time first then sort everyone by 2nd time and take all permutations; compare overall
So for each time T1, find T1 + next best 3 T2 (Dont double count runner)
Sort all and get lowest;
Return names*/

class Runner {
    public String name;
    public double firstLegTime;
    public double legTime;
    public Runner(String nm, double flt, double lt) {
        name = nm;
        firstLegTime = flt;
        legTime = lt;
    }
}
public class bestrelayteam {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        Runner[] runners = new Runner[n]; /*This line is to make array of n runners */
        for (int i = 0; i < n; i++) {
            String[] runnerInfo = br.readLine().split(" ");
            runners[i] = new Runner(runnerInfo[0], Double.parseDouble(runnerInfo[1]), Double.parseDouble(runnerInfo[2]));
        } //Store info
        
        Runner[][] relayTeam = new Runner[n][4]; //Make 2D array for all possible teams
        Runner[] temp = new Runner[n];
        for (int j = 0; j < n; j++){
            temp[j] = runners[j];
        }
        Arrays.sort(temp, Comparator.comparing(fella -> fella.legTime)); //Sort based on legTime
        for (int k = 0; k < n; k++) {
            relayTeam[k][0] = runners[k]; //make kth runner the starter
            //Make double pointer
            int a = 1; //pointer a is to count number of permutations
            int b = 0; //pointer b is to count runner in team
            while (relayTeam[k][3] == null) {//If team not complete, look for next best runner based on legTime
                if  (temp[b].name.equals(relayTeam[k][0].name)) {//If runner is starting, dont count him in 2nd time
                    b++;
                    continue;
                }
                relayTeam[k][a] = temp[b]; //store team a in relayTeam
                a++;
                b++;
            }
        }
        Runner[] fastestTeam = new Runner[4]; //Array size 4 for 4 runners
        double fastestTime = 1e9; //Make big number then decrease to faster times
        for (int p = 0; p < n; p++) {
            double time = 0; //compute relay time
            time = relayTeam[p][0].firstLegTime + relayTeam[p][1].legTime + relayTeam[p][2].legTime + relayTeam[p][3].legTime;
            if (time < fastestTime) { //if relay time is faster than fastest, relay will become fastest
                fastestTime = time;
                fastestTeam[0] = relayTeam[p][0];
                fastestTeam[1] = relayTeam[p][1];
                fastestTeam[2] = relayTeam[p][2];
                fastestTeam[3] = relayTeam[p][3];
            }
        }
        System.out.println(fastestTime);
        for (int x = 0; x < 4; x++) {
            System.out.println(fastestTeam[x].name);
        }
    }
}//plez work