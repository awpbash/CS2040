import java.io.*;
import java.util.*;

class AVLNode {
    String key;
    int height;
    int count; // To store the number of occurrences of this string
    AVLNode left, right;

    public AVLNode(String key) {
        this.key = key;
        this.height = 1;
        this.count = 1;
    }
}

class AVLTree {
    private AVLNode root;

    private int height(AVLNode N) {
        if (N == null) return 0;
        return N.height;
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalance(AVLNode N) {
        if (N == null) return 0;
        return height(N.left) - height(N.right);
    }

    private AVLNode insert(AVLNode node, String key) {
        if (node == null) return (new AVLNode(key));

        if (key.compareTo(node.key) < 0) node.left = insert(node.left, key);
        else if (key.compareTo(node.key) > 0) node.right = insert(node.right, key);
        else { // Duplicate keys found
            node.count++;
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0) return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0) return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(String key) {
        root = insert(root, key);
    }

    // Inorder traversal to find all nodes with a given prefix
    private void findWithPrefix(AVLNode node, String prefix, List<String> results) {
        if (node != null) {
            findWithPrefix(node.left, prefix, results);
            if (node.key.startsWith(prefix)) {
                results.add(node.key);
            }
            findWithPrefix(node.right, prefix, results);
        }
    }

    public List<String> findWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        findWithPrefix(root, prefix, results);
        return results;
    }
}

public class NicknameMatcherAVL {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int numNames = Integer.parseInt(br.readLine().trim());
        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < numNames; i++) {
            String name = br.readLine().trim();
            avlTree.insert(name);
        }

        int numNicknames = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < numNicknames; i++) {
            String nickname = br.readLine().trim();
            List<String> results = avlTree.findWithPrefix(nickname);
            bw.write(results.size() + "\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
