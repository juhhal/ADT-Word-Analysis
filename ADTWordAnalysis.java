import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ADTWordAnalysis {
   LinkedList<WordInformation>[] arrayOfDifferentLengths;
   WordInformation[] sortedArray;
   int n; //total words
   int m; //unique words

   public ADTWordAnalysis(int k) {
      
      n = 0;
      m = 0;
      arrayOfDifferentLengths = new LinkedList[k];
      int i = 0;
      do {
         arrayOfDifferentLengths[i] = new LinkedList();
         i++;
      } while (i < k);
   }

   //method to read the input file 
   public void readFileAndAnalyse(String input) throws FileNotFoundException {
      Scanner scan = new Scanner(new File(input));//scanner to read from the input file
      int line_num = 1;//current line number
      do//for all lines in file
      {
         String line = scan.nextLine();//split by spaces
         String[] str = line.split(" ");
         int i=0; //index counter
         int pos=1; //posiotion counter
         
         do//to each word in the line
         {
            String word = str[i].toLowerCase();//get word
            
            if(word.length()<1){
            i++;
            continue;
            }

            if(word.equals("\\n") ) //check if it's a new line character
            {
            line_num++;
            pos = 1;
            i++;
            continue; //could be multiple \n's
            }
                        
            
            //replace all punctuation from the end
            while (true) {
               if (Character.isLetterOrDigit(word.charAt(word.length() - 1))) {
                  break;
               } else {
                  word = word.substring(0, word.length() - 1);
               }
            }
            //replace all punctuation from the beginning
            while (true) {
               if (Character.isLetterOrDigit(word.charAt(0))) {
                  break;
               } else {
                  word = word.substring(1);
               }
            }
            
            //insert it in the array
            WordInformation e = new WordInformation(word, line_num, pos);
             //search for the word 
            if (arrayOfDifferentLengths[word.length()].Search(word) != null) {
               arrayOfDifferentLengths[word.length()].update(e);
               n++;
            } 
            else
            {
               //insert the element e in the array since we searched and its not found
               arrayOfDifferentLengths[word.length()].insert(e);
               m++;
               n++;
            }
            i++;
            pos++;
         }while (i < str.length);
         line_num++;//increase the line number
      }while (scan.hasNextLine());
      
      
      //init. the sorted array with size (m)
      sortedArray = new WordInformation[m];
      //insert every word in the sortedArray
      int j = 0;
      for (LinkedList<WordInformation> list : arrayOfDifferentLengths) {
      //get list of word for length i and insert words in the sortedArray
         Node<WordInformation> temp = list.getHead();
         while (temp != null) {
            sortedArray[j++] = temp.data;
            temp = temp.next;
         }
      }
      //Sort the sortedArray using merge sort algorithm
      mergeSort(sortedArray,0, m-1);
   }


   //merge sort algorithm to sort the element from the most frequent to the least
   void mergeSort(WordInformation array[], int lower, int upper) {
      if (lower >= upper) {
         return;//signifies that array contains only one element
      }
      mergeSort(array, lower, (lower + upper) / 2);//sorting the left portion of the array
      mergeSort(array, ((lower + upper) / 2) + 1, upper);//sorting the right portion of the array
      merge(array, lower, (lower + upper) / 2, upper);//merging the two portions once sorting is done
   }

   void merge(WordInformation array[], int lower, int middle, int upper) {
      int i, j, k;
      WordInformation left[] = new WordInformation[middle - lower + 1];//creating sub-array to store elements in the left portion of 'array[]'
      WordInformation right[] = new WordInformation[upper - middle];//creating sub-array to store elements in the right portion of 'array[]'
      i=0;
      while(i < middle - lower + 1)//copying appropriate elements from array[] to left[]
      {
         left[i] = array[lower + i];
         i++;
      }
      j=0;
      while( j < upper - middle)//copying appropriate elements from array[] to right[] 
      {
         right[j] = array[middle + 1 + j];
         j++;
      }
      i = 0; // initial index of sub-array left[] 
      j = 0; // initial index of sub-array right[]
      k = lower; //initial index of merged array 
      while(i < middle - lower + 1 && j < upper - middle)
      {//THIS CONDITION SORTS IN DESC ORDER 
         if (left[i].size > right[j].size) {
            array[k] = left[i++];//storing value of left[i] in array[k] if the former is smaller
         } else {
            array[k] = right[j++];//storing value of right[j] in array[j] if the former is smaller
         }
         k++;
      }
      while (i < middle - lower + 1) {
         array[k] = left[i];//copying back the remaining elements of left[] to array[]
         k++;
         i++;
      }
      while (j < upper - middle) {
         array[k] = right[j];//copying back the remaining elements of right[] to array[]
         k++;
         j++;
      }
   }
//     

   //operation 1 total number of words 
   public int documentLength() {
      return n;
   }

   //operation 2 unique words 
   public int uniqueWords() {
      return m;
   }

   //operation 3 total number of occurrences of a particular word 
   public int totalWord(String word) {
      word = word.toLowerCase();
      Node<WordInformation> node = arrayOfDifferentLengths[word.length()].Search(word);
      if (node == null) 
      {
         return 0;
      }
      return node.data.size;
   }

   //operation 4 total number of words with a particular lengths
   public int totalWordsForLength(int length) {
     
      LinkedList<WordInformation> list = arrayOfDifferentLengths[length];
      return list.size;
   }

   //operation 5 display unique words in the file sorted by the total occurrences of each word
   public void displayUniqueWords() {
      //print the words from the sorted array
      int i=0;
      do {
         System.out.print("(" + sortedArray[i].word + "," + sortedArray[i].size + ") ");
         i++;
      }while(i < sortedArray.length);
      System.out.println();
   }

   //operation 6 locations of the occurrences of a word starting from the top of the text file
   public LinkedList<WordOccurrence> occurrences(String word) {
      word = word.toLowerCase();
      Node<WordInformation> node = arrayOfDifferentLengths[word.length()].Search(word);//search for the word in the specific index
      if (node != null) //if found then print the occurrences list
         return node.data.occList;
      else
         return null;
      
   }

   //operation 7 examine if two words are occurring adjacent to each other in a text file
   public boolean checkAdjacent(String w1, String w2) 
   {
      w1 = w1.toLowerCase();
      w2 = w2.toLowerCase();
      Node<WordInformation> node1 = arrayOfDifferentLengths[w1.length()].Search(w1);
      Node<WordInformation> node2 = arrayOfDifferentLengths[w2.length()].Search(w2);
     
      if (node1 == null || node2 == null) //if found the words
         return false;
         
         
      LinkedList<WordOccurrence> list1 = node1.data.occList;
      list1.findFirst();
      LinkedList<WordOccurrence> list2 = node2.data.occList;
      list2.findFirst();
         
      if(w1.equals(w2)){
      
         while(!list1.last()){
            int line1 = list1.retrieve().lineNo;
            int pos1 = list1.retrieve().position;
            
            list1.findNext();
            int line2 = list1.retrieve().lineNo;
            
            if(line1 == line2){
               
                  
               int pos2 = list1.retrieve().position;
               if( pos1 - pos2 == -1 || pos1 - pos2 == 1  ) //before or after
                  return true;
               
            }
               
         
         }
         return false;
      }
               
      
      while (!list1.last() && !list2.last()) //check all before last node
      {
         int line1 = list1.retrieve().lineNo;
         int line2 = list2.retrieve().lineNo;
         if ( line1 == line2 )
         {
            int pos1 = list1.retrieve().position;
            int pos2 = list2.retrieve().position;
            if( pos1 - pos2 == -1 || pos1 - pos2 == 1  ) //before or after
               return true;
            else if( pos1>pos2 )
               list2.findNext();
            else
               list1.findNext();                      
         }
                        
         else if( line1 > line2 )
            list2.findNext();
         else
            list1.findNext();
      }
      
      while( list1.last() && !list2.last() ) //if list1 finished first
      {
         int line1 = list1.retrieve().lineNo;
         int line2 = list2.retrieve().lineNo;
         if ( line1 == line2 )
         {
            int pos1 = list1.retrieve().position;
            int pos2 = list2.retrieve().position;
            if( pos1 - pos2 == -1 || pos1 - pos2 == 1  ) //before or after
               return true;
         }
         list2.findNext();
      }
      
      
      while(!list1.last() && list2.last()  ) //if list2 finished first
      {
         int line1 = list1.retrieve().lineNo;
         int line2 = list2.retrieve().lineNo;
         if ( line1 == line2 )
         {
            int pos1 = list1.retrieve().position;
            int pos2 = list2.retrieve().position;
            if( pos1 - pos2 == -1 || pos1 - pos2 == 1  ) //before or after
               return true;
         }
         list1.findNext();
      }
      
      if( list1.last() && list2.last() ) //all lists reached last node
      {
         int line1 = list1.retrieve().lineNo;
         int line2 = list2.retrieve().lineNo;
         if ( line1 == line2 )
         {
            int pos1 = list1.retrieve().position;
            int pos2 = list2.retrieve().position;
            if( pos1 - pos2 == -1 || pos1 - pos2 == 1  ) //before or after
               return true;
         }                         
      }
      return false; 
   }
   
   
}
