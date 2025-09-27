// Classic order book price matching system use max heap and min heap lol
import java.util.*;

public class stockprices {
    static class Order {
        int shares;
        int price;
        
        Order(int shares, int price) {
            this.shares = shares;
            this.price = price;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        for (int t = 0; t < testCases; t++) {
            int numOrders = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // Priority queues for order books
            // Buy orders: max heap (highest price first)
            PriorityQueue<Order> buyOrders = new PriorityQueue<>((a, b) -> Integer.compare(b.price, a.price));
            // Sell orders: min heap (lowest price first)  
            PriorityQueue<Order> sellOrders = new PriorityQueue<>((a, b) -> Integer.compare(a.price, b.price));
            
            Integer lastTradePrice = null;
            
            for (int i = 0; i < numOrders; i++) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");
                
                String orderType = parts[0]; // "buy" or "sell"
                int shares = Integer.parseInt(parts[1]);
                int price = Integer.parseInt(parts[4]);
                
                if (orderType.equals("buy")) {
                    buyOrders.offer(new Order(shares, price));
                } else { // sell
                    sellOrders.offer(new Order(shares, price));
                }
                
                // Process trades
                while (!buyOrders.isEmpty() && !sellOrders.isEmpty() && 
                       buyOrders.peek().price >= sellOrders.peek().price) {
                    
                    Order buyOrder = buyOrders.peek();
                    Order sellOrder = sellOrders.peek();
                    
                    // Trade at the ask price (sell price)
                    int tradePrice = sellOrder.price;
                    lastTradePrice = tradePrice;
                    
                    // Determine how many shares to trade
                    int tradeShares = Math.min(buyOrder.shares, sellOrder.shares);
                    
                    // Update the orders
                    buyOrder.shares -= tradeShares;
                    sellOrder.shares -= tradeShares;
                    
                    // Remove fulfilled orders
                    if (buyOrder.shares == 0) {
                        buyOrders.poll();
                    }
                    if (sellOrder.shares == 0) {
                        sellOrders.poll();
                    }
                }
                
                // Determine current prices
                Integer askPrice = sellOrders.isEmpty() ? null : sellOrders.peek().price;
                Integer bidPrice = buyOrders.isEmpty() ? null : buyOrders.peek().price;
                
                // Format output
                String askStr = (askPrice != null) ? askPrice.toString() : "-";
                String bidStr = (bidPrice != null) ? bidPrice.toString() : "-";
                String stockStr = (lastTradePrice != null) ? lastTradePrice.toString() : "-";
                
                System.out.println(askStr + " " + bidStr + " " + stockStr);
            }
        }
        
        scanner.close();
    }
}
