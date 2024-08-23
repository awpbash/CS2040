
import java.io.*;
import java.util.*;

public class joinstrings {
    public static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        //Create an array of stringbuilders based on input
        StringBuilder[] inputStrings = new StringBuilder[n];
        for (int i = 0; i < n; i++) {
            inputStrings[i] = new StringBuilder(br.readLine());
        }
        //Create a hashmap to store the index and order of each join operation
        HashMap< Integer, ArrayList<Integer> > joinMap = new HashMap< Integer, ArrayList<Integer> >();
        
        /*
        If joinMap does not contain key "a", create new array with b in it, insert at key "a"
        if joinMap contains key "a", add b to the array with key "a"
        
        Essentially in python
        dic = {}
        if a in dic:
          dic[a].append(b)
        else:
          dic[a] = [b]
        */
        int a = 0;
        int b = 0;
        for (int i = 0; i < n - 1; i++) {
            String string = br.readLine();
            StringTokenizer st = new StringTokenizer(string);
            a = Integer.parseInt(st.nextToken()) - 1;
            b = Integer.parseInt(st.nextToken()) - 1;

            if(!joinMap.containsKey(a)) {
                joinMap.put(a, new ArrayList<>());
            }
            joinMap.get(a).add(b);
        }
        rPrint(joinMap, inputStrings, a);
        pw.close();
    }
    /* 
    Print string that correspond to the index
    If index is s key in map, store array at that key in temp array
    Recrusively go through the array
    */
    public static void rPrint(HashMap< Integer, ArrayList<Integer> > map, StringBuilder[] sb, int index){
        pw.print(sb[index]);
    
        if (map.containsKey(index)) {
            ArrayList<Integer> tempArray = map.get(index); // 
            for (int j = 0; j < tempArray.size(); j++) {
                rPrint(map, sb, tempArray.get(j));
            }
        }
    }
}