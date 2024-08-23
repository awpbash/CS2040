import java.util.*;
// Make a class with attributes 1) player name, 2) status of hand
class Hand {
    public int playerName;
    public String status;
    
    public Hand(int name, String stat) {
        playerName = name;
        status = stat;
    }
}


public class coconut {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt(); // number of syllables said
        int n = sc.nextInt(); // number of players in the game

        Deque<Hand> hands = new ArrayDeque<>(); // Make a double ended queue
        /* Why we need double ended queue, it is because we want to enqueue in both front and back of the queue and a deque can 
        do it fast in O(1) time i think
         */
        for (int a = 0; a < n; a++) { /*  Put all the players hands in the queue, 
            got n people means n hands at first since all folded and count as one each */
            hands.addLast(new Hand(a+1, "folded"));
        }

        while (hands.size() > 1) {// Rotate hands based on the syllabus s, the front of the queue is the last touched
            for (int b = 0; b < s-1; b++) {
                Hand front = hands.pollFirst();
                hands.addLast(front);
        }
            // Handling of the different hand cases
            Hand lastTouched = hands.pollFirst();
            switch (lastTouched.status) {
                case "folded": { /*  if hand is folded, dequeue then enqueue 2 copies of "fist" to the FRONT, 
                add 2 in front bcos they say next round start with first half */
                    hands.addFirst(new Hand(lastTouched.playerName, "fist"));
                    hands.addFirst(new Hand(lastTouched.playerName, "fist"));
                    break;
                }
                case "fist": { // if hand is fist, dequeue then enqueue 1 copy of "palm" to back
                    hands.addLast(new Hand(lastTouched.playerName, "palm"));
                    break;
                }
                case "palm": { // if hand is palm, dequeue entirely to denote putting at back of person, effectively out of the queue
                    break;
                }
            }
        }

        System.out.println(hands.peek().playerName);
    }
}