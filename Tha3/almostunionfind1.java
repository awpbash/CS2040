import java.io.*;
import java.util.ArrayList;

public class almostunionfind1 {

    public ArrayList<Integer> parent, rank, elements;
    public ArrayList<Long> sum;

    public almostunionfind1(int N) {
        //Parent array
        parent = new ArrayList<>();
        //Rank array
        rank = new ArrayList<>();
        //Elements array
        elements = new ArrayList<>();
        //Sum array
        sum = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            parent.add(i);
            rank.add(0);
            elements.add(1);
            sum.add((long) i);
        }
    }

    //Find root of set
    public int findSet(int i) {
        if (parent.get(i) == i) return i;
        else {
            parent.set(i, findSet(parent.get(i)));
            return parent.get(i);
        }
    }

    //Check whether two are in the same set
    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    //Function 1
    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i), y = findSet(j);
            // rank is used to keep the tree short
            if (rank.get(x) > rank.get(y)) {
                parent.set(y, x);
                elements.set(x, elements.get(x) + elements.get(y));
                sum.set(x, sum.get(x) + sum.get(y));
            } else {
                parent.set(x, y);
                if (rank.get(x).equals(rank.get(y)))
                    rank.set(y, rank.get(y) + 1);
                elements.set(y, elements.get(y) + elements.get(x));
                sum.set(y, sum.get(y) + sum.get(x));
            }
        }
    }

    //Function 2
    public void move(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j);
            if (x == y) {
                return;
            }
            if (x == findSet(i)) {
                int placeholder = -1;
                for (int k = 1; k < parent.size(); k++) {
                    if (parent.get(k) == x && (k != x)) {
                        if (placeholder == -1) {
                            placeholder = k;
                            parent.set(k, k);
                        } else {
                            parent.set(k, placeholder);
                        }
                    }
                }
            }
            parent.set(i, y);
            elements.set(y, elements.get(y) + 1);
            sum.set(y, sum.get(y) + i);
            elements.set(x, elements.get(x) - 1);
            sum.set(x, 0L);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);

        almostunionfind1 disjointSet = new almostunionfind1(n);
        for (int i = 0; i < m; i++) {
            line = br.readLine().split(" ");
            if (line[0].equals("1")) {
                disjointSet.unionSet(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            } else if (line[0].equals("2")) {
                disjointSet.move(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            } else if (line[0].equals("3")) {
                int root = disjointSet.findSet(Integer.parseInt(line[1]));
                pw.println(disjointSet.elements.get(root) + " " + disjointSet.sum.get(root));
            }
        }

        pw.flush();
        pw.close();
        br.close();
    }
}
