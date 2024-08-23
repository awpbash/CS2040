import java.io.*;
import java.util.*;

/* Strategy
 * Use 2 hashmaps for O(1) retrieval. We try to keep both hashmaps of equal length
 * If right > left, we move 1 from right to left and vice versa
 * If odd number length, we prioritise left to be longer ie left - right = 1
 * When getting index, we need to. If index < left, we query index + left head, else right head + index - length of left
 */
public class Teque {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

         // Create two halves as HashMaps for O(1) retrieval
         HashMap<Integer, Integer> left = new HashMap<Integer, Integer>();
         HashMap<Integer, Integer> right = new HashMap<Integer, Integer>();

         // Head and tail pointers for each hashmap
        int lh = 1; //Head of left hashmap
        int lt = 0; //Tail of left hashmap
        int rh = 1; //Head of right hashmap
        int rt = 0; //Tail of right hashmap

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            //Reads the operations being carried out, split into (operation, integer)
            String[] operations = br.readLine().split(" ");
            String operation = operations[0]; // the operation can be "push_back", "push_front", "push_middle" or "get"
            int num = Integer.parseInt(operations[1]); // this one is the integer to be pushed into teque
            
            //Fancy if-else
            switch(operation){
                case "push_back":
                    rt++; //increase pointer value of right tail
                    right.put(rt, num); //hash into right tail

                    if (right.size() > left.size()) { // right half can at most be as big as left half, if right bigger, we
                        int temp = right.remove(rh); //Store value of "front" of right into temp
                        lt++; //Increase left tail
                        left.put(lt, temp); //Put temp at lt in left hash
                        rh++; //since right head is gone, we can increase the index
                    }
                    break;

                    case "push_front":
                    lh--;
                    left.put(lh, num);

                    if (left.size() - 1 > right.size()) { // left half can at most be as big as right.size() + 1
                        int temp = left.remove(lt); // Store value of "back" of left into temp
                        rh--; //Decrease right head
                        right.put(rh, temp); //put temp at rh in right hash
                        lt--; //since left tail is gone, can decrement the index
                    }
                    break;

                    case "push_middle":
                    if (left.size() > right.size()) { // Either add to left or add to right depending on the sizes
                        rh--;
                        right.put(rh, num);
                    }
                    else {
                        lt++;
                        left.put(lt, num);
                    }
                    break;

                    case "get":
                    if (num < left.size()) {
                        pw.println(left.get(lh + num));
                    }
                    else {
                        pw.println(right.get(rh + num - left.size()));
                    }
                    break;
                    }
                System.out.println(left.keySet());
            }
            pw.close(); //Close print writer
        }
    }
