/*Strat
 * Sort all the input so all combis with same courses will show as the same input
 * Store combos as string
 * Create hashmap to store count of combos
 * Store string "combo" + ""count" as a string into an Array
 * Sort the Array by last digit
 * Find highest count and multiply it by number of combos with that count
 */

import java.util.*;
import java.io.*;

public class conformity {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        HashMap<String, Integer> comboCount = new HashMap<String, Integer>();
        for (int i = 0; i < n; i++) {
            String[] temp = br.readLine().split(" "); 
            int[] combo = new int[temp.length]; //Store combo as an integer Array
            for (int j = 0; j < temp.length; j++) {
                combo[j] = Integer.parseInt(temp[j]);
            }
            Arrays.sort(combo); //sort the Array of integer containing combo
            String strCombo = Arrays.toString(combo); //If combo in hashmap, value +1 else add combo to hashmap
            if (comboCount.containsKey(strCombo)) {
                comboCount.put(strCombo, comboCount.get(strCombo) + 1);
            }
            else {
                comboCount.put(strCombo, 1);
            }
        }
        String[] combosList = new String[comboCount.size()];
        int m = 0;
        for (Map.Entry<String, Integer> entry : comboCount.entrySet()) {
            combosList[m] = entry.getKey() + " : " + entry.getValue();
            m++;  //Create and Array where the combi is first then the last digit is count
        }
        Arrays.sort(combosList, Comparator.comparing((String k) -> k.charAt(k.length()-1))); //Sort based on last character

        //Go through sorted combosList to identify most popular combos and add frosh who take most populat combo
        int result = 0;
        int currMostPop = Character.getNumericValue(combosList[combosList.length - 1].charAt(combosList[combosList.length - 1].length() - 1)); //Get count of most popular combo
        for (int i = combosList.length - 1; i >= 0; i--) {
            int temp2 = Character.getNumericValue(combosList[i].charAt(combosList[i].length() - 1)); //if next most popular combo has same count as most popular
            if (temp2 == currMostPop) {
                result += temp2; //Add to result
            }
            else { //no more combo of same popularity means can quit
                break;
            }
        }

        System.out.println(result);
    }
}