import java.io.*;

// AVL Tree Node class
class Node {
    Node(String input) {
        name = input;
        parent = null;
        left = null;
        right = null;
        height = 0;
        size = 1;
    }
/* By creating parent pointer for each node, we can quickly access the parent directly instead of traversing the tree to find the parent
 * i would say O(1)? but def better than O(n) lol
 */
    public Node parent, left, right;
    public String name;
    public int height, size;
}

// AVL Tree class
class AVLTree {
    public Node root;

    public AVLTree() {
        root = null;
    }

    //inserting a name into the AVL tree
    public void insert(String name) {
        root = insert(root, name);
    }

    //
    public Node insert(Node node, String name) {
        if (node == null) { //reached terminal node, create a new node
            return new Node(name);
        }

        if (name.compareTo(node.name) < 0) { //inserting name smaller than current node, recurse left
            node.left = insert(node.left, name);
            if (node.left != null) {
                node.left.parent = node;  //set parent of left child back to current node. "two way ting"
            }
        } else if (name.compareTo(node.name) > 0) { //recurse right
            node.right = insert(node.right, name);
            if (node.right != null) {
                node.right.parent = node; //set parent of right child
            }
        } else { //if inserting a duplicate name, do nothing
            return node;
        }

        //update height and size
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        node.size = 1 + getSize(node.left) + getSize(node.right);

        //balance the tree
        return balance(node);
    }

    //get height of a node
    public int getHeight(Node node) {
        if (node == null) return -1;
        return node.height;
    }

    public int getSize(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    public int balanceFactor(Node node) { //by convention left - right
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }
    /* The magical part. Rank here is assumed as the position of the node when u do inorder traversal meaning "sorted" array form
     * we store a size attribute to keep track of the number of nodes in left subtree of the node
     * if the node is a right child, we must add the size of the parent's left subtree to the rank as well
     * + 1 to account for the node itself hehe
     */
    public int getRank(Node node) {
        if (node == null) return 0;
        
        int rank = getSize(node.left) + 1; //size of left subtree + node
        Node parent = node.parent;
        while (parent != null) { //check if node is right child
            if (node == parent.right) {
                rank += getSize(parent.left) + 1;
            }
            node = parent;
            parent = parent.parent;
        }
        return rank;
    }
    //balancing of tree
    public Node balance(Node node) {
        if (balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0) { 
                node.left = leftRotate(node.left); // left-right case
            }
            node = rightRotate(node); //left-left case

        } else if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right); // right-left case
            }
            node = leftRotate(node); //right-right case
        }
        return node;
    }

    //Left rotation
    public Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.left = x;

        y.parent = x.parent;
        x.parent = y;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        x.size = 1 + getSize(x.left) + getSize(x.right);
        y.size = 1 + getSize(y.left) + getSize(y.right);

        return y;
    }

    //Right rotation
    public Node rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) x.right.parent = y;
        x.right = y;

        x.parent = y.parent;
        y.parent = x;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        y.size = 1 + getSize(y.left) + getSize(y.right);
        x.size = 1 + getSize(x.left) + getSize(x.right);

        return x;
    }

    //Perform search for leftmost and right most
    public int search(String prefix) {
        Node leftmost = findLeft(root, prefix);
        Node rightmost = findRight(root, prefix);
        
        if (leftmost == null || rightmost == null) {
            return 0;
        }

        //count how many nodes exist between leftmost and rightmost nodes
        return getRank(rightmost) - getRank(leftmost) + 1;
    }

    //leftmost node with prefix
    private Node findLeft(Node node, String prefix) {
        Node result = null;
        while (node != null) {
            if (node.name.startsWith(prefix)) {
                result = node; //store the leftmost node with prefix
                node = node.left; 
            } else if (node.name.compareTo(prefix) < 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return result;
    }

    //rightmost node with prefix
    private Node findRight(Node node, String prefix) {
        Node result = null;
        while (node != null) {
            if (node.name.startsWith(prefix)) {
                result = node; //store the rightmost node with prefix
                node = node.right;
            } else if (node.name.compareTo(prefix) < 0) {
                node = node.right; 
            } else {
                node = node.left;
            }
        }
        return result;
    }


}

public class nicknames {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        AVLTree nameTree = new AVLTree();
        int names = Integer.parseInt(br.readLine());

        //populate tree
        for (int i = 0; i < names; i++) {
            String name = br.readLine();
            nameTree.insert(name);
        }

        int nicknames = Integer.parseInt(br.readLine());

        //get nicknames
        for (int i = 0; i < nicknames; i++) {
            String nickname = br.readLine();
            int result = nameTree.search(nickname);
            pw.println(result);
        }

        br.close();
        pw.close();
    }
}
