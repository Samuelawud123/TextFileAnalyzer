/**
  This code provides functionalities to read a text file and perform different
  operations on it
 
 * @author Varik Hoang <varikmp@uw.edu>
 * @author Samuel Awud <samuelawud@seattlecolleges.edu>
 * @version __________
 */


package TextReader;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// TextReader class provides functionalities to read a text file and perform different operations on it
public class TextReader {
    // Maximum length for the dictionary array
    public static final int MAX_LENGTH = 1000000;
    // Constant to signify a word was not found in the dictionary
    public static final int NOT_FOUND = -1;

    // The main method that controls the flow of the program
    public static void main(String[] args) {
        // Array to hold unique words in the file
        String dictionary[] = new String[MAX_LENGTH];
        // Array to hold frequency of words corresponding to the dictionary
        int frequency[] = new int[MAX_LENGTH];
        int num_unique_words = 0;
        // Read words from file into the dictionary array
        num_unique_words = readWordFromFile("WarAndPeace.txt", dictionary, frequency);
        Scanner scanner = new Scanner(System.in);
        // Display the menu options to the user
        displayMenu();
        System.out.print("Please enter option [0-3]: ");
        int option = scanner.nextInt();
        // Loop until user chooses to quit
        while (option >=0 ) {
            if(option == 0){
                System.out.println("Thank you for using the program");
                break;
            }
            // Option to get the frequency of an entered word
            else if (option == 1) {
                System.out.print("Enter the word to find the frequency: ");
                String enteredWord = scanner.next();
                // Find the index of the entered word in the dictionary
                int index = getWordIndexFromArray(enteredWord.trim().toLowerCase(),dictionary,num_unique_words);
                // Print the frequency of the word
                System.out.println("The frequency of the word \""+enteredWord+"\" is: "+frequency[index]);
            }
            // Option to get all words with frequency greater than a given value
            else if(option == 2){
                System.out.print("Enter the least frequency to list all words: ");
                int value = scanner.nextInt();
                System.out.print("The list of words that have frequency greater than 20: ");
                for(int i=0; i < num_unique_words; i++){
                    if (frequency[i] > value){
                        System.out.print(dictionary[i]+" ");
                    }
                }
                System.out.println();
            }
            
            else if(option == 3){
               // Option reserved for extra functionalities
            }
            else {
                System.out.println("Sorry, I could not find the function for the option " + option);
            }

            displayMenu();
            System.out.print("Please enter option [0-3]: ");
            option = scanner.nextInt();
        }
        scanner.close();
    }

    // Method to display the menu options to the user
    public static void displayMenu() {
        System.out.println("======================================");
        System.out.println("Please select option below to operate:");
        System.out.println("  0. Quit the program");
        System.out.println("  1. Print out the frequency of an entered word");
        System.out.println("  2. Print out all words that have a frequency that is greater than an entered value");
        System.out.println("  3. Reserved for extra credit");
    }

    // Method to read words from a file and store them in a dictionary
    public static int readWordFromFile(String filename, String[] words, int[] frequencies) {
        int unique_words = 0;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            // Loop until all lines in the file have been read
            while (myReader.hasNextLine()) {
                // Read a line from the file
                String data = myReader.nextLine();
                // Remove punctuation from the line
                String cleanedData = removePunctuations(data);
                // Split the line into individual words
                String[] wordList = cleanedData.toLowerCase().trim().split("\\s+");
                for (String word:wordList) {
                    if(word.trim()!= " ") {
                        // Get the index of the word in the dictionary
                        int res = getWordIndexFromArray(word.trim().toLowerCase(),words,unique_words);
                        // If the word is already in the dictionary, increment its frequency
                        if(res!=-1){
                            frequencies[res] = frequencies[res] + 1;
                        }else {
                            // If the word is not in the dictionary, add it and set its frequency to 1
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
        // Return the number of unique words found in the file
        return unique_words;
    }

    // Method to remove punctuation from a word
    public static String removePunctuations(String word) {
        return word.replaceAll("\\p{P}","");
    }

    // Method to find the index of a word in the dictionary array
    public static int getWordIndexFromArray(String word, String[] words, int size) {
        for(int i=0; i < size; i++){
            if(words[i].equals(word)){
                return i;
            }
        }
        // Return NOT_FOUND if the word was not found in the dictionary
        return NOT_FOUND;
    }
}

