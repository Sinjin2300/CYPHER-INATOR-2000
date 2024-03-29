import java.util.*;

import javax.imageio.plugins.tiff.ExifGPSTagSet;

import java.lang.*;
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.println("CYPHER-INATOR 2000");
        System.out.println("\t1. NIHLIST CYPHER");
        System.out.println("\t2. CAESER CYPHER");
        System.out.println("\t2. RAIL FENCE CYPHER");

        System.out.println("Type in the number corresponding to the cypher");
        int index = scan.nextInt();

        scan.nextLine();
         switch (index) {
             case 1:
            System.out.println("[E]ncode or [D]ecode");
            String code = scan.nextLine();
            code = code.toLowerCase();
            if(code.charAt(0) == 'e'){
                nihilistCipherEncode();
            }else if(code.charAt(0) == 'd'){
                nihilistCipherDecode();
            }else{
                System.out.println("BRUH");
            }
            break;
            case 2:
            System.out.println("[E]ncode or [D]ecode");
            code = scan.nextLine();
            code = code.toLowerCase();
            if(code.charAt(0) == 'e'){
                System.out.println("Enter Your Shift (Numeric)");
                int shift = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter Your Plaintext (Alphabetical)");
                String plain = scan.nextLine();
                System.out.println(caesarCipherEncode(shift, plain));
            }else if(code.charAt(0) == 'd'){
                System.out.println("Enter Your Shift (Numeric)");
                int shift = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter Your Ciphertext (Alphabetical)");
                String cipher = scan.nextLine();
                System.out.println(caesarCipherDecode(shift, cipher));
            }else{
                System.out.println("BRUH");
            }
            break;
            case 3:
                polybiusSquare(scan.nextLine());
            break;
            case 4:
            break;
            default:
            break;
        }
        
    }
/* #region Rail Fence Cipher */
    public static void railfenceEncode(){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int rails = scan.nextInt();
        char[][] ciphertext = new char[rails][(int)(input.length()/(rails-2))+1];
        int[] edges = railfenceEdge(input, rails);
        int edge1 = 0;
        int edge2 = 0;
        for(int i = 0; i < edges.length; i++){
            if(i%2 == 0){
                ciphertext[0][edge1] = input.charAt(i);
                edge1++; 
            }else{ 
                ciphertext[0][edge2] = input.charAt(i);
                edge2++;
            }
        } 
        String bits = toBits(input, rails);
        for(int i = 0; i < bits.length(); i++){
            
        }

    }
    public static String toBits(String in, int rails){
        int[] edges = railfenceEdge(in, rails);
        String[] end = new String[(int)(in.length()-edges.length)/rails-2];
        String comp = "";
        int count = 0;
        for(int i = 0; i < edges.length; i+=2){
            if(i != edges.length){
                end[count] = in.substring(edges[i]+1,edges[i+1]);
            }else{
                String temp = in.substring(edges[i]+1);
                String fin = "";
                for(int j = temp.length()-1; j >= 0; j--){
                    fin+=temp.charAt(j);
                }
                end[count] = fin;
            }
        }
        for(int i = 0 ; i < end.length; i++){
            comp += end[i];
        }
        return comp;
    }
    public static int[] railfenceEdge(String in, int rails){
        int[] pos = new int[(int)in.length()/rails];
        int count = 0;
        for(int i = 1; i < in.length(); i+=(count-1) * rails){
            pos[count] = in.charAt(i);
            count++;
        }
        return pos;
    }
/* #endregion */
/* #region Caesar Cipher */
    public static String caesarCipherEncode(int shift, String plainText){
        plainText = plainText.toUpperCase();
        String fin = "";
        for(int i = 0; i < plainText.length(); i++){
            char let = plainText.charAt(i);
            fin += (char)((((let-65)+shift)%26)+65);
        }
        return fin;
    }
    //IJFYM
    public static String caesarCipherDecode(int shift, String cipherText){
        cipherText = cipherText.toUpperCase();
        String fin = "";
        for(int i = 0; i < cipherText.length(); i++){
            char let = cipherText.charAt(i);
            fin += (char)((((let-65)-shift)%26)+65);
        }
        return fin;
    }
/* #endregion */
/* #region Nihilist Cypher and Polybius */   
    public static void nihilistCipherEncode(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Polybius Key (Alphabetical Without Repetition)");
        char[][] Square = polybiusSquare(scan.nextLine());
        System.out.println("Select your Key (Alphabetical)");
        String key = scan.nextLine();
        int[] keyInt = new int[key.length()];
        System.out.println("Type your Plaintext (Alphabetical)");
        String plaintext = scan.nextLine();
        //Form KeyInt
        for(int i = 0; i < key.length(); i++){
            keyInt[i] = searchPolybius(key.charAt(i), Square);
        }
        //Washing plaintext
        for(int i = 0; i < plaintext.length(); i++){
            if(plaintext.charAt(i) == ' '){
                plaintext = plaintext.substring(0,i) + plaintext.substring(i+1);
            }
        }
        int[] plainInt = new int[plaintext.length()];
        //Form plainInt
        for(int i = 0; i < plaintext.length(); i++){
            plainInt[i] = searchPolybius(plaintext.charAt(i), Square);
        }
        //Forming Cypher Int
        System.out.println();
        for(int i = 0; i < plainInt.length; i++){
            //System.out.print(plaintext.charAt(i));
            System.out.print(plainInt[i] + keyInt[i%keyInt.length] + " ");
        }
        System.out.println();
    }
    public static void nihilistCipherDecode(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Polybius Key (Alphabetical Without Repetition)");
        char[][] Square = polybiusSquare(scan.nextLine());
        System.out.println("Select your Key (Alphabetical)");
        String key = scan.nextLine();
        int[] keyInt = new int[key.length()];
        //Form KeyInt
        for(int i = 0; i < key.length(); i++){
            keyInt[i] = searchPolybius(key.charAt(i), Square);
        }
        System.out.println("Type your Ciphertext (Numeric)");
        String ciphertext = scan.nextLine();
        int[] cipherarray = parseIntArray(ciphertext);
        for(int i = 0; i < cipherarray.length; i++){
            System.out.print( referencePolybius(cipherarray[i], keyInt[i%keyInt.length],Square)+ " ");
        }
        System.out.println();
    }
    public static char referencePolybius(int in, int key, char[][] square){
        int first = ((in-key)/10) - 1;
        int second = ((in-key)%10) - 1;
        return square[first][second];
    }
    public static int[] parseIntArray(String in){
        ArrayList<Integer> integers = new ArrayList<Integer>();
        int count = -1;
        if(in.charAt(in.length()-1) == ' '){
            in = in.substring(0, in.length()-1);
        }
        for(int i = 0; i < in.length(); i++){
            if(in.charAt(i) == ' ' || i == in.length()-1){
                if(i == in.length()-1) {
                    integers.add(Integer.parseInt(in.substring(count + 1, i+1)));
                  //  System.out.println(in.substring(count + 1, i+1));
                }else{
                    integers.add(Integer.parseInt(in.substring(count + 1, i)));
                   // System.out.println(in.substring(count + 1, i));
                }
                count = i;
            }
        }
        int[] fin = new int[integers.size()];
        for(int i = 0; i < fin.length; i++){
            fin[i] = integers.get(i);
        }
        return fin;
    }
    public static int searchPolybius(char in, char[][] square){
       // 
       in = Character.toUpperCase(in);
       if(in == 'J'){
           in = 'I';
       }
       for(int i = 1; i <= 5; i++){
           for(int j = 1; j <= 5; j++){
               if(square[i-1][j-1] == in){
                   //System.out.println(in + ": " + ((10 * i) + j));
                   return 10 * i + j;
               }
           }
       }
       return -1;
    }
    public static char[][] polybiusSquare(String in){
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        in = in.toUpperCase();
        for(int i = 0; i < in.length(); i++){
            if(in.charAt(i) == 'J'){
                in = in.substring(0,i) + "I" + in.substring(i+1);
            }
        }
        //removes duplicates
        for(int i = 0; i < alphabet.length(); i++){
            for(int j = 0; j < in.length(); j++){
                if(in.charAt(j) == alphabet.charAt(i)){
                    alphabet = alphabet.substring(0,i) + alphabet.substring(i+1);
                    if(i != 0){
                    i--;
                    }
                }
            }
        }
        //Form Polybius Square
        in += alphabet;
        char[][] square = new char[5][5];

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                square[i][j] = in.charAt(j + i*5);
            }
        }
        //Print Square
        for(int i = 0; i<5;i++){
            System.out.println(Arrays.toString(square[i]));
        }
        return square;
    }
 /* #endregion */
/* #region XOR CYPHER */
    public static int[] stringToArray(String in) {
        int[] ret = new int[in.length()];

        for (int i=0; i<in.length(); i++) {
            ret[i] = in.charAt(i)-65;
        }

        return ret;
    }

    public static int[] xorCypher(int[] message, int[] key){
        int[] ret = new int[message.length];

        for (int i=0; i<message.length; i++) {
            ret[i] = message[i] ^ key[i%key.length];
        }

        return ret;
    }
/* #endregion */
}