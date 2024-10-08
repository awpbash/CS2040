/*Strat
 *Create 2 priority queues, 1 for workstations 1 for researchers
We order the priority queue by the arrival time of the researcher
We order PQUEUE for workstation by the time it is done being used by researcher
then for every researcher, we check the PQUEUE workstation
if workstation PQUEUE is empty or the arrival time is < any end time of workstation, we need to unlock
Also check that the workstation will lock after y minutes so we can remove from PQUEUE once y minutes is up
Rinse and repeat YAY
 */
import java.util.*;
import java.io.*;

class Researcher {
    public int startTime;
    public int endTime;
    public Researcher(int start, int duration) {
        startTime = start;
        endTime = startTime + duration;
    }
}

public class workstation{
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] num = br.readLine().split(" ");
        int x = Integer.parseInt(num[0]); //Number of researchers
        int y = Integer.parseInt(num[1]); //Minutes before the fking workstation locks

        PriorityQueue<Researcher> researchers = new PriorityQueue<Researcher>(y, Comparator.comparing(s -> s.startTime)); //Sort researcher by arrival time in ascending order
        for (int j = 0; j < x; j++) {
            String[] temp = br.readLine().split(" ");
            int start = Integer.parseInt(temp[0]); //Store the fking start time "a" when the researchers arrive
            int duration = Integer.parseInt(temp[1]); //Store the fking duration of how long they stay
            researchers.add(new Researcher(start, duration)); //Create PQUEUE for researchers by start time
        }
        PriorityQueue<Integer> workstations = new PriorityQueue<>(); //Create another PQUEUE for workstations that are UNLOCKED
        int count = 0;
        for (int i = 0; i < x; i++) {
            Researcher lol = researchers.peek(); //First resercher to arrive
            while (!workstations.isEmpty() && (workstations.peek() + y) < lol.startTime) {
                workstations.poll(); //Benchode this workstation close alr so remove
            }
            if (!workstations.isEmpty() && lol.startTime <= workstations.peek() + y && lol.startTime >= workstations.peek()) {
                workstations.poll(); //diswan means we can reuse the workstation as the start time is after previous end time and not yet closed
                count += 1; //Unlock 1 less WORKSTATION
            }
            Researcher nextRsAtWorkstation = researchers.poll();
            workstations.add(nextRsAtWorkstation.endTime);
        }

        System.out.println(count);
    }
}
