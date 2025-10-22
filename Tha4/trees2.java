/*
The key idea here is that instead of recursively searching the subtrees for the range and getting the maximum,
we can instead augment the bBST nodes to store the maximum volume in their respective subtrees.
So we can actually use this maxVol to prune the searchtree. So if the maxVol of the subtree < max then no point searching
WE NEED PRUNING OR ELSE TLE
so the idea is simple
if the maxVol of subtree is better, we check the range and traverse accordingly
our balance only works on the position ie the key.
*/
import java.io.*;

public class trees2{
    static class Node {
        int pos, vol;
        int maxVol; // max volume in this subtree
        Node left, right;
        int height;
        
        Node(int pos, int vol) {
            this.pos = pos;
            this.vol = vol;
            this.maxVol = vol;
            this.height = 1;
        }
    }
    
    static class AVL {
        Node root;
        
        int height(Node n) {
            if (n == null) {
                return 0;
            } else {
                return n.height;
            }
        }
        
        int maxVol(Node n) {
            if (n == null) {
                return 0;
            } else {
                return n.maxVol;
            }
        }
        
        void updateNode(Node n) {
            if (n == null) {
                return;}
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.maxVol = Math.max(n.vol, Math.max(maxVol(n.left), maxVol(n.right)));
        }
        
        Node rotateRight(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            
            x.right = y;
            y.left = T2;
            
            updateNode(y);
            updateNode(x);
            return x;
        }
        
        Node rotateLeft(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            
            y.left = x;
            x.right = T2;
            
            updateNode(x);
            updateNode(y);
            return y;
        }

        // By convention, balance = left height - right height
        int getBalance(Node n) {
            if (n == null) {
                return 0;
            } 
            else {
                return height(n.left) - height(n.right);
            }
        }
        
        // Returns old volume at position (0 if none)
        int oldVolume;
        
        Node insert(Node node, int pos, int vol) {
            if (node == null) {
                oldVolume = 0; // new node has no volume
                return new Node(pos, vol);
            }
            
            if (pos < node.pos) {
                node.left = insert(node.left, pos, vol);
            } else if (pos > node.pos) {
                node.right = insert(node.right, pos, vol);
            } else {
                // Position already exists, update volume
                oldVolume = node.vol;
                node.vol = vol;
                updateNode(node);
                return node;
            }
            
            updateNode(node);
            
            // Balance the tree
            int balance = getBalance(node);
            
            // Left Left
            if (balance > 1 && pos < node.left.pos)
                return rotateRight(node);
            
            // Right Right
            if (balance < -1 && pos > node.right.pos)
                return rotateLeft(node);
            
            // Left Right
            if (balance > 1 && pos > node.left.pos) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            
            // Right Left
            if (balance < -1 && pos < node.right.pos) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
            
            return node;
        }
        
        int rangeMax(Node node, int l, int r) {
            if (node == null) {
                return 0;
            }
            
            // Current node is outside range
            if (node.pos < l) {
                // Only check right subtree
                return rangeMax(node.right, l, r);
            }
            if (node.pos > r) {
                // Only check left subtree
                return rangeMax(node.left, l, r);
            }
            
            // Current node is in range [l, r]
            int max = node.vol;
            
            // Check left subtree if it might have nodes in range
            if (node.left != null && node.left.maxVol > max) {
                max = Math.max(max, rangeMax(node.left, l, r));
            }
            
            // Check right subtree if it might have nodes in range
            if (node.right != null && node.right.maxVol > max) {
                max = Math.max(max, rangeMax(node.right, l, r));
            }
            
            return max;
        }
        
        int update(int pos, int vol) {
            root = insert(root, pos, vol);
            return oldVolume;
        }
        
        int query(int l, int r) {
            return rangeMax(root, l, r);
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int n = Integer.parseInt(br.readLine());
        AVL tree = new AVL();
        
        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().split(" ");
            char op = parts[0].charAt(0);
            
            if (op == 'u') {
                int pos = Integer.parseInt(parts[1]);
                int vol = Integer.parseInt(parts[2]);
                sb.append(tree.update(pos, vol)).append('\n');
            } else {
                int l = Integer.parseInt(parts[1]);
                int r = Integer.parseInt(parts[2]);
                sb.append(tree.query(l, r)).append('\n');
            }
        }
        
        System.out.print(sb);
        br.close();
    }
}
