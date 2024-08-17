/**
  This code provides functionalities to read a text file and perform different
  operations on it
 
 * @author Varik Hoang <varikmp@uw.edu>
 * @author Samuel Awud <samuelawud@seattlecolleges.edu>
 * @version __________
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// ExtraCredit class provides functionalities to read a text file and perform different operations on it
public class ExtraCredit {
    // Maximum length for the dictionary array
    public static final int MAX_LENGTH = 1000000;
    // Constant to signify a word was not found in the dictionary
    public static final int NOT_FOUND = -1;

// The main method that controls the flow of the program
    public static void main(String[] args)
    {
        // Array to hold unique words in the file
        String dictionary[] = new String[MAX_LENGTH];
        // Array to hold frequency of words corresponding to the dictionary
        int frequencies[] = new int[MAX_LENGTH];
        int num_unique_wods = 0;
        // Read words from file into the dictionary array
        num_unique_wods =readWordFromFile("WarAndPeace.txt", dictionary, frequencies);
        Scanner scanner = new Scanner(System.in);

        // Display the menu options to the user
        displayMenu();
        // Ask user to enter an option from 0 to 3
        System.out.print("Please enter option [0-3]: ");
        // get option (value) from the scanner
        int option = scanner.nextInt();
        // Loop until user chooses to quit
        while (option >= 0)
        {
            if(option==0){
                System.out.println("Thank you for using the program");
                break;
  
            }
            else if (option==1) {
                System.out.print("Enter the word to find the frequency: ");
                String enteredWord = scanner.next();
                int index = getWordIndexFromArray(enteredWord.trim().toLowerCase(),dictionary,num_unique_wods);
                System.out.println("The frequency of the word \""+enteredWord+"\" is: "+frequencies[index]);
            }else if(option==2){
                System.out.print("Enter the least frequency to list all words: ");
                int value = scanner.nextInt();
                System.out.print("The list of words that have frequency greater than 20: ");
                for(int i=0; i < num_unique_wods; i++){
                    if (frequencies[i] > value){
                        System.out.print(dictionary[i]+" ");
                    }
                }
                System.out.println();
            } else if(option==3){

                int number_repeated_words= totalAmountRepeatedWords(frequencies,num_unique_wods);
                System.out.println("Total number of repeated words are: "+number_repeated_words);

                System.out.print("The most frequent word(s) in the text file: ");
                int max = maxium(frequencies,num_unique_wods);
                for(int i=0; i < num_unique_wods;i++){
                    if(frequencies[i]==max){
                        System.out.println("\""+dictionary[i]+"\"");
                    }
                }
                System.out.print("Enter word to look line number: ");
                String word = scanner.next();
                System.out.print("Line numbers for the word \""+word +"\"  " );
                lineNumbers("WarAndPeace.txt",word);
                System.out.println();

            } else {
                System.out.println("Sorry, I could not find the function for the option " + option);
            }
            
            displayMenu();
            System.out.print("Please enter option [0-3]: ");
            option = scanner.nextInt();
        }

        scanner.close();
    }

    // Method to display the menu options to the user
    public static void displayMenu()
    {
        System.out.println("======================================");
        System.out.println("Please select option below to operate:");
        System.out.println("  0. Quit the program");
        System.out.println("  1. Print out the frequency of an entered word");
        System.out.println("  2. Print out all words that have a frequency that is greater than an entered value");
        System.out.println("  3. Reserved for extra credit");
    }

     // Method to read words from a file and store them in a dictionary
    public static int readWordFromFile(String filename, String[] words, int[] frequencies)
    {
        int unique_words = 0;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String cleanedData = removePunctuations(data);
                String[] wordList = cleanedData.toLowerCase().trim().split("\\s+");
                for (String word:wordList) {
                    if(word.trim()!= " ") {
                        int res = getWordIndexFromArray(word.trim().toLowerCase(),words,unique_words);
                        if(res!=-1){
                            frequencies[res] = frequencies[res] + 1;
                        }else {
                            for(int i=0; i < words.length; i++){
                                if(words[i]==null){
                                    words[i] = word;
                                    frequencies[i] = 1;
                                    unique_words +=1;
                                    break;
                                }
                            }
                        }
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read file.");
            e.printStackTrace();
        }
        return unique_words;
    }

  // Method to remove punctuation from a word
    public static String removePunctuations(String word)
    {
        return word.replaceAll("\\p{P}","");
    }

  // Method to find the index of a word in the dictionary array
    public static int getWordIndexFromArray(String word, String[] words, int size)
    {
        for(int i=0; i < size; i++){

            if(words[i].equals(word)){
                return i;
            }
        }
        return  NOT_FOUND;
    }

// Method for calculating the total amount of repeated word in the text file
    public static int totalAmountRepeatedWords(int[] frequencies,int size){
        int count =0;
        for(int i=0; i < size; i++){

            if(frequencies[i] > 1){
                count+=1;
            }
        }
        return  count;
    }
// Method for indicating the word(s) that is used most in the text file
    public  static int maxium(int[] frequencies,int size){
        int max = frequencies[0];
        for(int i=1; i < size; i++){
            if(frequencies[i] > max){
                max = frequencies[i];
            }

        }
        return max;
    }
// Method for indicating all line numbers in the text file that has the word we are looking for
    public static void lineNumbers(String filename,String searchWord){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int line_number = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String cleanedData = removePunctuations(data);
                String[] wordList = cleanedData.toLowerCase().trim().split("\\s+");
                for (int i=0; i < wordList.length; i++){
                    if(wordList[i].equals(searchWord)){
                        System.out.print(line_number+", ");
                        break;
                    }
                }


                line_number+=1;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read file.");
            e.printStackTrace();
        }

    }
}
