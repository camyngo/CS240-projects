package hangman;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.zip.GZIPInputStream;

public class EvilHangman {
    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        String dictionaryPath = "";
        int nguess = 0;
        int nwords = 0;
        boolean isWon = false;
        if (args.length == 3) {
            dictionaryPath = args[0];
            nguess = Integer.parseInt(args[2]);
            nwords = Integer.parseInt(args[1]);
        }
        File dictionary = new File(dictionaryPath);
        Scanner myScanner = new Scanner(System.in);
        EvilHangmanGame game = new EvilHangmanGame();
        game.setGuessLeft(nguess);
        if (nwords >= 2){
            game.startGame(new File(dictionaryPath), nwords);
            runGame(game,nguess,isWon,myScanner, nwords);
        }
    }

    public static void runGame(EvilHangmanGame game, int guesses, boolean isWon, Scanner myScanner, int wordLength) throws IOException,EmptyDictionaryException,GuessAlreadyMadeException {
        int guess_copy = guesses;
        while (guesses > 0) {
            if (guesses > 26) guesses = 26;
            //print how many guess
            if (guess_copy > 1) System.out.println("\nYou have " + guess_copy + " guesses remaining.");
            else System.out.println("\nYou have 1 guess remaining.");

            //printing all the use letters
            System.out.print("Used letters: ");
            for (char myChar : game.getGuessedLetters()) {
                System.out.print(myChar + " ");
            }

            // print out the word
            System.out.println();
            game.print_word();
            char myguess = 0;
            myguess = promptGuess(myguess);

            //throwing error if letter is used
            Set<String> guessedWords = new HashSet<String>();
            try {
                guessedWords = game.makeGuess(myguess);
                guesses --;
                int num = 0;
                for (int i = 0; i < wordLength; i++){
                    if (game.getWordKey()[i] == myguess) {
                        num++;
                    }
                }
                //elsea
                if (num == 0){
                    System.out.println("Sorry, there are no " + myguess + "\'s");
                    guess_copy--;
                }
                else{
                    System.out.println("Yes, there exists " + num + " " + myguess+ "\'s");
                }

            } catch (GuessAlreadyMadeException e){}

            boolean guessedAll = true;
            if (guesses == 0 || !keywordHasDash(game,wordLength)){
                String winningWord = null;
                for (String word : guessedWords) {
                    winningWord = word;
                    break;
                }
                for (int i = 0; i < wordLength; i++) {
                    if (!game.getGuessedLetters().contains(winningWord.charAt(i)))
                        guessedAll = false;
                }
                if(guessedAll){
                    guesses = 0;
                    isWon = true;
                    System.out.println("You Won!");
                    System.out.println("The correct word was " + winningWord);
                }
                if (!isWon){
                    for (String word: game.getMySet()){
                        winningWord = word;
                    }
                    System.out.println("You lost!");
                    System.out.println("The winning word was " + winningWord);
                }
            }
        }
        myScanner.close();
    }

    private static boolean keywordHasDash(EvilHangmanGame game, int wordLength) {
        //get the game keyword
        game.getWordKey();
        char [] wordkey;
        //loop through to see if there is a '-'
        for(int i = 0; i < game.getWordKey().length; i++){
            //if dash return true
            if (game.getWordKey()[i] == '-')
                return true;
        }
        //else return false after loop
        return false;
    }

    private static char promptGuess(char guess) throws IOException {
        System.out.print("Enter guess: ");
        Scanner myReader = new Scanner(new InputStreamReader(System.in));
        String input = "";
        input = myReader.nextLine().toLowerCase();
        // if the input is null or more than one or is not a character
        //prompt guess again
        if(input == null || input.length()!= 1 || !Character.isLetter(input.charAt(0)))
        {
            System.out.println("Only a letter");
            guess = promptGuess(guess);
        }
        else
            guess = input.charAt(0);
        return guess;
    }
}


