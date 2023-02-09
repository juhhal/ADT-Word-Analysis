import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {    
    
   public static void main(String[] args) {
      ADTWordAnalysis adt = new ADTWordAnalysis(50);
      Scanner scan = new Scanner(System.in);
      boolean flag = true;
      while(flag)
      {
         try{
            System.out.println("Please enter an existing file name with \".txt\" extension:");
            String filename = scan.next();
            adt.readFileAndAnalyse(filename);
            flag = false;
         }
         catch(FileNotFoundException e) { System.out.println("The file was not found. Try again."); }
         catch(Exception e) { System.out.println(e); }
      }
        
        
      do {
         System.out.println("*************************************************************\n"
                    + "\n#1 Total number of words"
                    + "\n#2 Total number of unique words"
                    + "\n#3 Total number of occurrences of a particular word"
                    + "\n#4 Total number of words with a particular lengths"
                    + "\n#5 Unique words in the file sorted by the total occurrences of each word"
                    + "\n#6 Locations of the occurrences of a word starting from the top of the text file"
                    + "\n#7 Examine if two words are occurring adjacent to each other in a text file"
                    + "\n#8 Exit"
                    +"\n\n Please enter your choice: ");
           
          
         String choice = scan.next();
         System.out.println("*************************************************************");
         switch (choice) {
            case "1": {
               System.out.println("Total number of words is: "+adt.documentLength() + ".");
               break;
            }
            case "2":
               System.out.println("Total number of unique words is: "+adt.uniqueWords() + ".");
               break;
            case "3": {
               System.out.print("Enter the word please: ");
               String word = scan.next();
               System.out.println("Total number of occurrences of \""+word+"\" is "+adt.totalWord(word) + ".");
               break;
            }
            case "4": {
               System.out.print("Enter the length please: ");
               while(true)
               {
                  try{
                     int length = Integer.parseInt(scan.next());
                     System.out.println("Total number of words with a length of " + length + " is " + adt.totalWordsForLength(length) + ".");
                     break;
                  }catch(NumberFormatException e) { System.out.println("Please enter a valid length."); }
               }
               break;
            }
            case "5":
               adt.displayUniqueWords();
               break;
            case "6":
               System.out.print("Enter the word please: ");
               String word = scan.next();
               LinkedList<WordOccurrence> occList = adt.occurrences(word);
               if(occList != null)
               {
                  System.out.println("Locations of the occurrences of \"" + word + "\" :");
               
                  occList.findFirst();
                  while(! occList.last() )
                  {
                     System.out.print(occList.retrieve()+ " ");
                     occList.findNext();
                  }
                  System.out.print(occList.retrieve()+ " ");
                  System.out.println();
               }
               else
                  System.out.println("\"" +word+ "\"" +" does not occur in the file.");
               break;
            case "7":
               System.out.print("Enter the word please: \n");
               System.out.println("First: ");
               String word_1 = scan.next();
               System.out.println("Second: ");
               String word_2 = scan.next();
               boolean result = adt.checkAdjacent(word_1, word_2);
               if(result)
                  System.out.println("True, the two words are occurring adjacent to each other.");
               else
                  System.out.println("False, the two words never occurr adjacent to each other.");
               break;
            case "8":
               System.exit(0);
               
            default:
               System.out.println("Please enter a valid value.");
         }
      } while (true);
   
   }

}
