import java.io.*;
import java.util.*;

import org.w3c.dom.Node;

public class troll {

    public static class Quest {
        public int energy;
        public int gold;

        public Quest(int energy, int gold) {
            this.energy = energy;
            this.gold = gold;
        }
    }

    class questComparator implements Comparator<Quest> {
        public int compare (Quest q1, Quest q2){
            if (q1.energy == q2.energy) {
                return Integer.compare(q2.gold, q1.gold);
            }
            return Integer.compare(q2.energy,q1.energy);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Long commands = Long.parseLong(br.readLine());
        PriorityQueue<Quest> questHeap = new PriorityQueue<>(new questComparator());
        for (Long i = 0L; i < commands; i++) {
            String[] command = br.readLine().split(" ");
            if (command[0].equals("add")) {
                questHeap.add(new Quest(Integer.parseInt(command[1]),Integer.parseInt(command[2])));
            }

            else {
                while 
            }
        }
    }
}