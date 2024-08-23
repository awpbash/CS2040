import java.io.*;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    int count = 0; // to store the number of names with the prefix represented by this node

    public TrieNode() {}
}

class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, k -> new TrieNode());
            node.count++;
        }
    }

    public int findPrefix(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return 0;
            }
        }
        return node.count;
    }
}

public class NicknameMatcher {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int numNames = Integer.parseInt(br.readLine().trim());
        Trie trie = new Trie();

        for (int i = 0; i < numNames; ++i) {
            String name = br.readLine().trim();
            trie.insert(name);
        }

        int numNicknames = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < numNicknames; ++i) {
            String nickname = br.readLine().trim();
            bw.write(trie.findPrefix(nickname) + "\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
