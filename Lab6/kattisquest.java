/* Strat
 * We want to first find highest energy quest that can be done and if multiple have same energy, do highest gold
 * So we create a tree with custom comparator. First sorted by energy then gold
 * When query is called, we create a dummy quest worth current energy with super high gold and use it to compare with elements in the tree
 * 
 * Additionally, we NEED to store ID as we need to differentiate between duplicates. If we have quests with same energy and gold, we will lose the duplicates
 * and this will affect the final answer. i.e removing duplicated nodes WITHOUT ID will only remove 1 such node and not as many as possible fk u test case 5
* THAT IS WHY WE NEED TO STORE ID
 */
import java.util.*;
import java.io.*;

public class kattisquest {
    public static void main(String[] args) throws Exception { //READ INPUTS
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numLines = Integer.parseInt(br.readLine());

        TreeSet<Quest> quests = new TreeSet<>(new compareQuests()); //Make a tree of QUESTS
        for (int i = 0; i < numLines; i++) {
            String[] input = br.readLine().split(" "); //split into command and numbers. Add will be ["add", energy, gold], query will ne ["query", energy]
            String command = input[0];
            String command_1 = "add";
            String command_2 = "query";
            if (command.equals(command_1)) {  //Check commands
                quests.add(new Quest(Integer.parseInt(input[1]), Integer.parseInt(input[2]),i));
            }
            
            else if (command.equals(command_2)) { //For every query command, start a while loop to "do quest" until not enough ene
                long curr_energy = Long.parseLong(input[1]);
                long gold_earned = 0;
                while (quests.floor(new Quest(curr_energy, 9999999,Integer.MAX_VALUE)) != null) { //Continues as long as there are available quests to DO!
                    Quest completedQuest = quests.floor(new Quest(curr_energy, 9999999,Integer.MAX_VALUE)); //Dummy object lolololol. Chooses highest energy quest
                    quests.remove(completedQuest); //remove quest if "completed"

                    gold_earned += completedQuest.gold; //add gold minus energy
                    curr_energy -= completedQuest.energy;
                }
                System.out.println(gold_earned);
            }
        }
    }
}

class Quest { //Make a separate class for each quest
    public long energy;
    public long gold;
    public int index;
    public Quest(long e, long g, int id) {
        energy = e;
        gold = g;
        index = id;
    }
}

class compareQuests implements Comparator<Quest> { //COMPARATOR METHOD TO SORT QUESTS PLEASE
    public int compare(Quest quest1, Quest quest2) {
        // First compare by energy
        int energyComparison = Long.compare(quest1.energy, quest2.energy);
        if (energyComparison != 0) {
            return energyComparison;
        }
        // Then compare by gold
        else if(quest1.gold != quest2.gold){
        return Long.compare(quest1.gold, quest2.gold);
        } else{
            return Integer.compare(quest1.index, quest2.index);}
    }
}
