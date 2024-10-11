/* Strat (IDK why u want use AVL Tree but here u go)
 * First right u must create the fucking AVL tree class properly
 * Then right since we confirm plus chop got 10 char limit, just create array of size 10
 * Then for each name hor, lets say it has length i so there can be i differen substrings from length 1 to i
 * Then right, we iterate through the names and fomr substring
 * Each value in the fucking array will hold an AVL Tree. The Tree at array[2] will hold the Tree of all length 3 substrings etc
 * ie. Array[n] holds AVL Tree of strings length n-1
 * Then right, every substring we create, we attempt to insert inside
 * To save time and space, we make attribute of "count" for each node
 * So right everytime we insert, if got duplicate ie key == node.key; we just increase the count to save space
 * Then hor every insert we balance
 * When we query or call the nickname right, we just search the array with same length then traverse like bBST to find the exact node
 * Return node.counts
 * That's it. Trie would have been SOOOOO much faster lmaoo
 * Goodnight
 */
import java.io.*;
import java.util.*;
public class nickname{
    public static class AVL_Trees {
        public Node root;

        public class Node {
            int height;
            String key;
            Node left;
            Node right;
            Node parent;
            int counts; //Important to store the number of times a prefix is repeated
            Node(String key) {
                this.key = key;
                height = 1; // Initialize height to 1
                counts = 1;
            }
            
        }
        public int height(Node node){
            if (node == null){
                return 0;
            } else { 
            return node.height;
            }
        }
        public void updateHeight(Node node){
            if (node == null){
                return;
            }
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);
            node.height = Math.max(leftHeight, rightHeight) + 1;
        }

        Node rightRotate(Node chosen){
            Node left_child = chosen.left;

            chosen.left = left_child.right;
            left_child.right = chosen;
            updateHeight(chosen);
            updateHeight(left_child);
            return left_child;
        }

        Node leftRotate(Node chosen){
            Node right_child = chosen.right;

            chosen.right = right_child.left;
            right_child.left = chosen;
            updateHeight(chosen);
            updateHeight(right_child);
            return right_child;
        }

        public int size(){
            return size(root);
        }
        public int size(Node node) {
            if (node == null) {
                return 0;
            } else {
                return size(node.left) + size(node.right) + 1;
            }
        }
        public int balanceFactor(Node node) {
            if (node == null) {
                return 0;
            } else {
                return height(node.left) - height(node.right);
            }
        }
        public void insert(String key) {
            root = insert(root, key);
        }/* BRUHHHHH
        public Node insert(Node node, String key){
            if (node == null) { //if node does not exist
                return new Node(key); //creates a new node
            } 
            int comparisonResult = key.compareTo(node.key);
            if (comparisonResult == 0) {
                node.counts++;
            } //if the key is already in the tree, increment the count
            else if (comparisonResult < 0) { // key to insert is less than node, insert into left subtree
                node.left = insert(node.left, key);
            } else{ // key to insert is more than node, insert into right subtree. Same value also put right tree
                node.right = insert(node.right, key);
            }
        

            updateHeight(node);
            int bf = balanceFactor(node);
            //Case 1 Left-Left -> do 1 right rotation
            if (bf > 1 && balanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }
            // Case 2 Right-Right -> do 1 left rotation
            if (bf < -1 && balanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }
            // Case 3 Left-Right -> do right then left
            if (bf > 1 && balanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            // Case 4 Right-Left -> do left then right
            if (bf < -1 && balanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
 
            return node;
        }*/
        public Node insert(Node node, String key){
            if (node == null || key.equals(node.key)) {
                return new Node(key); // Create a new node if the current node is null lmao
            }
        
            int comparisonResult = key.compareTo(node.key);
            if (comparisonResult == 0) {
                if (node.counts == 0) {
                    node.counts = 1; //set counts to 1. NEVER USE ANYWAYS
                } else {
                    node.counts++; // COUNT +1 if exist in tree
                }
            } else if (comparisonResult < 0) { 
                node.left = insert(node.left, key); //Insert into the left subtree
            } else {
                node.right = insert(node.right, key); //Insert into the right subtree
            }
        
            // Update height and balance factor
            updateHeight(node);
            int bf = balanceFactor(node);
            if (bf > 1) {
                if (key.compareTo(node.left.key) < 0) {
                    // Left-Left case -> right rotation
                    return rightRotate(node);
                } else {
                    // Left-Right case -> double rotation (left then right)
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                }
            } else if (bf < -1) {
                if (key.compareTo(node.right.key) > 0) {
                    // Right-Right case -> left rotation
                    return leftRotate(node);
                } else {
                    // Right-Left case -> double rotation (right then left)
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            }
            return node;
        }
        
        /* BULLSHIT METHOD I MEMORY EXCEEDED
        //counting of matching nicknames
        public int count(String prefix) {
            return count(root, prefix);
        }
        
        public int count(Node node, String prefix) {
            if (node == null) {
                return 0;
            }

            int diff = prefix.compareTo(node.key);
            if (diff == 0) {
                return 1 + count(node.left, prefix) + count(node.right, prefix);
            } else if (diff < 0) {
                return count(node.left, prefix);
            } else {
                return count(node.right, prefix);
            }
        } */
        
        public void printInOrder() {
            printInOrder(root);
        }
        
        public void printInOrder(Node node) {
            if (node != null) {
                printInOrder(node.left); // Recur on the left subtree
                System.out.println(node.key); // Print the current node's key
                printInOrder(node.right); // Recur on the right subtree
            }
        }
        public Node find(String key) {
            return find(root, key);
        }
        
        public Node find(Node node, String key) {
            if (node == null || key.equals(node.key)) {
                return node;
            }
        
            if (key.compareTo(node.key) < 0) {
                return find(node.left, key);
            } else {
                return find(node.right, key);
            }
        }
        
    }
        
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int numLines = Integer.parseInt(br.readLine());
        int maxLength = 10; //Max length of nicknames is 10 characters
    
        AVL_Trees[] treesArray = new AVL_Trees[maxLength]; //Making an array of AVL trees each one containing substrings of length i
        for (int i = 0; i < maxLength; i++) {
            treesArray[i] = new AVL_Trees();
        }   
    
        for (int j = 0; j < numLines; j++) {
        String name = br.readLine();
        StringBuilder subNameBuilder = new StringBuilder();
        for (int k = 0; k < name.length(); k++) {
            subNameBuilder.append(name.charAt(k));
            String subName = subNameBuilder.toString();
            AVL_Trees.Node node = treesArray[k].find(subName);
            if (node == null) {
                treesArray[k].insert(subName);
            } else {
                node.counts++;  // Directly increment the count if already found
        }
    }
}

    
        int queries = Integer.parseInt(br.readLine());
        for (int x = 0; x < queries; x++) {
            String nickname = br.readLine(); //This is the nickname to be matched
            int length = nickname.length();
            AVL_Trees.Node res = treesArray[length-1].find(nickname);
            if(res == null) {
                pw.println(0);
            } else {
                pw.println(res.counts);
            }
        }/*
        for (int o = 0; o < maxLength; o++) {
            System.out.println(treesArray[o].size());
            treesArray[o].printInOrder();
        }*/
        pw.flush(); 
        pw.close(); 
    }
}



