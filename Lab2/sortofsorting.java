import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
class Person {
    public String name;
    public String firstTwoLetters;
    public Person(String a){
        name = a;
        firstTwoLetters = name.substring(0,2); /*gets first 2 letter*/
    }
    public String getLetters() {
        return firstTwoLetters;
    }
    public String makeString(){
        return name;
    }
}

public class sortofsorting {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        while (n != 0){
            Person [] arrayOfFirstTwo = new Person[n];
            for (int j = 0; j < n; j++){
                String newName = br.readLine(); //read input and store in array//
                arrayOfFirstTwo[j] = new Person(newName);
            }
            Arrays.sort(arrayOfFirstTwo, Comparator.comparing(k -> k.getLetters())); //Lambda function to sort based on first 2 letters//
            for (int x = 0; x < n; x++){
                System.out.println(arrayOfFirstTwo[x]);
            }
            n = Integer.parseInt(br.readLine());
            if (n == 0) {
                break;
            }
            System.out.println();
        }
    }
}
//Strat is to extract the first 2 letters then sort by first 2 letters using stable sorting.
//Java API uses Timsort which is basically insertion sort for small n and mergesort for big n