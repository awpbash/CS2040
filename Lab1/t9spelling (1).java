import java.io.BufferedReader;
import java.io.InputStreamReader;

public class t9spelling {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        
        for (int i = 1; i <= n; i ++){
            String x = br.readLine();
            StringBuilder y = new StringBuilder("Case #");
            y.append(i);
            y.append(": "); /* This chucnk is to print out Case #i */
            
            for (int j = 0; j < x.length(); j++){
                int av = (int) x.charAt(j); /* This one gets Ascii value of the character */
                String keypad = ""; /* Initialise empty keypad */
                /*Refer to Ascii table to find the value then determine what to press*/
                if (av == 32){
                    keypad = "0";
                }
                else if (av <= 99){ /* 97, 98, 99 = a, b, c*/
                    keypad = "2".repeat(av - 96);
                }
                else if (av <= 102){ /* 100, 101, 102 = d, e, f*/
                    keypad = "3".repeat(av - 99);
                }
                else if (av <= 105){ /* 103, 104, 105 = g, h, i*/
                    keypad = "4".repeat(av - 102);
                }
                else if (av <= 108){ /* 106, 107, 108 = j, k, l*/
                    keypad = "5".repeat(av - 105);
                }
                else if (av <= 111){ /* 109, 110, 111 = m, n, o*/
                    keypad = "6".repeat(av - 108);
                }
                else if (av <= 115){ /*112, 113, 114, 115 = p, q, r, s*/
                    keypad = "7".repeat(av - 111);
                }
                else if (av <= 118){ /*116, 117, 118 = t, u, v*/
                    keypad = "8".repeat(av - 115);
                }
                else if (av <= 122){ /* 119, 120, 121, 122 = w, x, y, z*/
                    keypad = "9".repeat(av - 118);
                }
                if (y.charAt(y.length() - 1) == keypad.charAt(0)) {
                    y.append(" ");
                }
                y.append(keypad);
            }
            System.out.println(y.toString());
            }
        } 
    }
        