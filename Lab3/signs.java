import java.io.*;
import java.util.*;

public class signs {
    private static String getMiddleCharacter(String sign) {
        int len = sign.length();
        if (len % 2 == 0) {
            return sign.substring((len / 2) - 1, (len / 2) + 1); // For even-length strings, take middle 2 chars
        } else {
            return String.valueOf(sign.charAt(len / 2));      // For odd-length strings, take the exact middle character
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Read the number of signs
        int n = Integer.parseInt(br.readLine());
        
        // Read the signs into a list
        List<String> signs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            signs.add(br.readLine());   
        }
        

       //If len is 2, get middle 2. else get middle 1
        signs.sort((sign1, sign2) -> {
            String middleChar1 = getMiddleCharacter(sign1);
            String middleChar2 = getMiddleCharacter(sign2);
            return middleChar1.compareTo(middleChar2);
        });

        // Output the sorted signs
        for (String sign : signs) {
            System.out.println(sign);
        }
        br.close();

    }
}
