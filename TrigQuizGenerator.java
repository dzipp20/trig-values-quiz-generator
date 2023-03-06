import java.util.*;
import java.io.*;

public class TrigQuizGenerator {
//Create all arrays and puts them into memory full
//Answer keys starts empty
   private static String[] functions = {"cos", "sin", "tan", "sec", "csc", "cot"};
   private static String[] radians = {"0", "\u03C0/6", "\u03C0/4", "\u03C0/3", "\u03C0/2", "2\u03C0/3", "3\u03C0/4", "5\u03C0/6", "\u03C0", "7\u03C0/6", "5\u03C0/4", "4\u03C0/3", "3\u03C0/2", "5\u03C0/3", "7\u03C0/4", "11\u03C0/6", "2\u03C0"};
   private static String[] degrees = {"0\u00B0", "30\u00B0", "45\u00B0", "60\u00B0", "90\u00B0", "120\u00B0", "135\u00B0", "150\u00B0", "180\u00B0", "210\u00B0", "225\u00B0", "240\u00B0", "270\u00B0", "300\u00B0", "315\u00B0", "330\u00B0", "360\u00B0"};
   private static String[][] results = new String[17][6];
   private static String[] cosines = {"1", "\u221A3/2", "\u221A2/2", "1/2", "0", "-1/2", "-\u221A2/2", "-\u221A3/2", "-1", "-\u221A3/2", "-\u221A2/2", "-1/2", "0", "1/2", "\u221A2/2", "\u221A3/2", "1"};
   private static String[] sines = {"0", "1/2", "\u221A2/2", "\u221A3/2", "1", "\u221A3/2", "\u221A2/2", "1/2", "0", "-1/2", "-\u221A2/2", "-\u221A3/2", "-1", "-\u221A3/2", "-\u221A2/2", "-1/2", "0"};
   private static String[] tangents = {"0", "\u221A3/3", "1", "\u221A3", "und", "-\u221A3", "-1", "-\u221A3/3", "0", "\u221A3/3", "1", "\u221A3", "und", "-\u221A3", "-1", "-\u221A3/3", "0"};
   private static String[] secants = {"1", "2\u221A3/3", "\u221A2", "2", "und", "-2", "-\u221A2", "-2\u221A3/3", "-1", "-2\u221A3/3", "-\u221A2", "-2", "und", "2", "\u221A2", "2\u221A3/3", "1"};
   private static String[] cosecants = {"und", "2", "\u221A2", "2\u221A3/3", "1", "2\u221A3/3", "\u221A2", "2", "und", "-2", "-\u221A2", "-2\u221A3/3", "-1", "-2\u221A3/3", "-\u221A2", "-2", "und"};
   private static String[] cotangents = {"und", "\u221A3", "1", "\u221A3/3", "0", "-\u221A3/3", "-1", "-\u221A3", "und", "\u221A3", "1", "\u221A3/3", "0", "-\u221A3/3", "-1", "-\u221A3", "und"};
   private static boolean[] hasRadQuad = {false, false, false, false, false};
   private static boolean[] hasDegQuad = {false, false, false, false, false};
   private static boolean[] funcUsed = {false, false, false, false, false, false};
   
   
   public static void main(String[] args) throws IOException {
      //Fills anwser key
      for(int i = 0; i < results.length; i++) {
         results[i][0] = cosines[i];
         results[i][1] = sines[i];
         results[i][2] = tangents[i];
         results[i][3] = secants[i];
         results[i][4] = cosecants[i];
         results[i][5] = cotangents[i];
      }
      
      //Necessary object construction
      //Seperate files and writers are made for creating a seperate answer key and quiz
      Scanner user = new Scanner(System.in);
      System.out.print("Name:   ");
      String name = new String(user.next());
      
      File quiz = new File(name + "'s quiz.txt");
      FileWriter writer = new FileWriter(quiz);
      
      File key = new File(name + "'s key.txt");
      FileWriter grader = new FileWriter(key);
      
      //Initializes number of quizzes for printing to files and answer keys to keep consistent
      int quizNo = 1;
      
      do {
         //Headers
         writer.write(name + "'s Quiz #" + quizNo + ": \n\n");
         writer.flush();
         
         grader.write(name + "'s Answer Key #" + quizNo + ": \n\n");
         grader.flush();
         //Initializing variables for counting
         int countOfRads = 0;
         int countOfDeg = 0;
         //Ensures no infinite loops when checking against arrays
         boolean allRadFull = false;
         boolean allDegFull = false;
         boolean functFull = false;
         //Cleans uniqueness arrays (I should write this as a method)
         for(int i = 0; i < hasDegQuad.length; i++) {
            hasDegQuad[i] = false;
         }
         for(int i = 0; i < hasRadQuad.length; i++) {
            hasRadQuad[i] = false;
         }
         for(int i = 0; i < funcUsed.length; i++) {
            funcUsed[i] = false;
         }
         //Random quiz generator body
         for(int i = 1; i <= 10; i++) {
            //Creates random numbers to define the unit (1 is radians, 2 is degrees) 
            int determiner = (int) ((Math.random() * 2) + 1); 
            //Checks to see if there are already too many degrees or not enough radians and a radian chosen 
            if((countOfRads < 5 && determiner == 1) || countOfDeg > 4) {
               int index = (int) (Math.random() * radians.length);
               //Checks that not all quadrants have been used and generates until its in an unsed quadrant
               //Ignores if the are all full
               if(!allRadFull) {
                  while(hasRadQuad[index / 4]) {
                     index = (int) (Math.random() * radians.length);
                  }
               }
               //Fills quadrant check with quadrant used
               hasRadQuad[index / 4] = true;
               //Same as above but for functions      
               int func = (int) (Math.random() * functions.length);
               if(!functFull) {
                  while(funcUsed[func]) {
                     func = (int) (Math.random() * functions.length);
                  }
               }
               funcUsed[func] = true;
               //Writing to files
               writer.write("" + i + ": " + functions[func] + " " + radians[index] + "\n"); 
               writer.flush();
               grader.write("" + i + ": " + results[index][func] + "\n"); //Writes the answer by looking into the answer key
               grader.flush();                                            //The Answer key has the same order of functions as the function graph and same order of angles
               //Adds to radian check count
               countOfRads++;
               //Checks to see if all quadrants and/or functions are used and sets the ignore value
               if(hasRadQuad[0] && hasRadQuad[1] && hasRadQuad[2] && hasRadQuad[3]) {
                  allRadFull = true;
               }
               if(funcUsed[0] && funcUsed[1] &&funcUsed[2] && funcUsed[3] && funcUsed[4] && funcUsed[5]) {
                  functFull = true;
               }
            } else if ((countOfDeg < 5 && determiner == 2) || countOfRads > 4) { //This is the same as the radian end but for degrees
               int index = (int) (Math.random() * degrees.length);
               if(!allDegFull){
                  while(hasDegQuad[index / 4] ) {
                     index = (int) (Math.random() * degrees.length);
                  }
               }
               hasDegQuad[index / 4] = true; 
               int func = (int) (Math.random() * functions.length);
               if(!functFull) {
                  while(funcUsed[func]) {
                     func = (int) (Math.random() * functions.length);
                  }
               }
               funcUsed[func] = true;
               writer.write("" + i + ": " + functions[func] + " " + degrees[index] + "\n"); 
               writer.flush();
               grader.write("" + i + ": " + results[index][func] + "\n");
               grader.flush();
               countOfDeg++;
               if(hasDegQuad[0] && hasDegQuad[1] && hasDegQuad[2] && hasDegQuad[3]) {
                  allDegFull = true;
               }
               if(funcUsed[0] && funcUsed[1] &&funcUsed[2] && funcUsed[3] && funcUsed[4] && funcUsed[5]) {
                  functFull = true;
               }
            }    
         }
            
         writer.write("\n\n\n\n");
         writer.flush();
         
         grader.write("\n\n\n\n");
         grader.flush();
         //Asks for repeat, breaks out on a no
         System.out.print("More (on " + quizNo + ", type n to stop)?    ");
         quizNo++;
         char test = user.next().charAt(0);
         System.out.println("\n\n");
         if (test == 'n') {
            break;
         }
      } while (true);
      
      System.out.println("\n\nProcessed");
      
   }
     
}