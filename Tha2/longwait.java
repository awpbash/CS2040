import java.io.*;


public class longwait{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String[] inputs = br.readLine().split(" ");
        int Q = Integer.parseInt(inputs[0]);
        int K = Integer.parseInt(inputs[1]);
        
        /*Since we have Q operations, we can make an array of size Q and each index represents the position in the queue
        However, as we can have this stupid VIP and member function, we can use 2 arrays instead
        One array to store front, one array to store back and the end of the
        front array is where to insert VIP */
        int backNum = Math.max(Q - K, 1); //Size of back array
        int[] front = new int[Q];
        int[] back = new int[backNum];

        int frontIndex = 0, frontLength = 0, backIndex = 0, backLength = 0;
        /*the indexes are to tell me where to insert the next element
        In essence, the frontIndex is the front of the queue and will change when VIP is called
        The backIndex is the back of the queue and will change if member is called
        */
        for (int i = 0; i<Q; i++){
            String[] line = br.readLine().split(" ");
            String op = line[0];
            //System.out.println(Arrays.toString(line));
            int person = 0;
            if (line.length == 2){
                person = Integer.parseInt(line[1]);
            }

            if (op.equals("findID")){
                person--; //0-indexed
                if (person < frontLength){ //index is in front, circular array
                    pw.println(front[(person+frontIndex)%Q]);
                } else { //find in back queue lol, circular array
                    pw.println(back[(backIndex+person-frontLength)%backNum]);
                }
            } else if (op.equals("faster")){
                K--;
            } else if (op.equals("slower")){
                K++;
            } else if (op.equals("member")){
                backIndex = (backIndex - 1 + backNum) % backNum;
                back[backIndex] = person;
                backLength++;
            } else {
                if (op.equals("queue")){
                    back[(backIndex + backLength + backNum) % backNum] = person;
                    backLength++;
                } else { //Have to be VIP already
                    frontIndex = (frontIndex - 1 + Q) % Q; //"Start" of the front queue
                    front[frontIndex] = person;
                    frontLength++;
                }
            }
            //Handling the case where we need to move people from back queue to front queue
            if (frontLength < K && backLength > 0){
                front[(frontIndex + frontLength) % Q] = back[backIndex];
                backIndex = (backIndex + 1) % backNum;
                frontLength++;
                backLength--;
            }
            //Handling the case where we need to move people from front queue to back queue
            if (frontLength > K && frontLength > 0){
                backIndex = (backIndex - 1 + backNum) % backNum;
                back[backIndex] = front[(frontIndex + frontLength - 1 + Q) % Q];
                frontLength--;
                backLength++;
            }
        
            

        }
        pw.flush();
        br.close();
        pw.close();        
    }
}