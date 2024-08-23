import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 /*Strat:
 You want to have 
 */
class Card {
    public int cardType;
    public long buyPrice;
    public long sellPrice;
    public int count;
    public Card(int ct, int c) {
        cardType = ct;
        count = c;
    }
    
    public void addBuyPrice (long buy){
        buyPrice = buy;
    }
    public void addSellPrice (long sell){
        sellPrice = sell;
    }
    
    /*This method calculates the opportunity cost
    As we want to form pairs and have highest most optimal deck, we compute opp cost and take the lowest pairs
    By holding on to the pair, he cannot sell it for profit
    if count of card T is less than 2, we need to buy cards if we want to use it for pairing
    thus opp cost is computed as (2 - count of T)*buy + count of T*sell
    */
    public long getOppCost() {
        long oppCost = (2 - count) * buyPrice + count * sellPrice;
        return oppCost;
    }
}

public class cardtrading {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] firstLine = br.readLine().split(" "); //Read the first input line and store in an array
        int[] num = new int[3]; //Array of 3 numbers
        for (int i = 0; i < 3; i++) {
            num[i] = Integer.parseInt(firstLine[i]);
        }
        
        String [] anthonyCard = br.readLine().split(" ");
        int[] cards = new int[num[0]]; //Initialise an array size based on N
        for (int j = 0; j < num[0]; j++) {
            cards[j] = Integer.parseInt(anthonyCard[j]);
        }
        
        int[] counts = new int[num[1]]; //Initialize an array size T to count different cards
        for (int k = 0; k < num[0]; k++){
            int temp = cards[k]; //Index of the array is (card type - 1), value is count
            counts[temp - 1]++;
        }
        Card[] deck = new Card[num[1]]; //make new array of size T
        for (int x = 0; x < num[1]; x++) {
            deck[x] = new Card(x+1, counts[x]);
        }
        
        //This line reads the subsequent K inputs to store price information about the cards
        for (int n = 0; n < num[1]; n++) {
            String[] prices = br.readLine().split(" ");
            deck[n].addBuyPrice(Long.parseLong(prices[0]));
            deck[n].addSellPrice(Long.parseLong(prices[1]));
        }
        //sort by opp cost
        Arrays.sort(deck, Comparator.comparing(s -> s.getOppCost()));

        //  Calculate net profit
        long netProfit = 0;
        for (int p = 0; p < num[1]; p++) {
            if (p < num[2]) {
                netProfit -= (2 - deck[p].count) * deck[p].buyPrice;
            }
            else {
                netProfit += deck[p].count * deck[p].sellPrice;
            }
        }
        System.out.println(netProfit);
    }
}
        
        