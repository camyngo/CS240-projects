package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private Set<String> words;
    private int wlen;
    private Set<Character> guessed;
    private char [] wordKey; //a string of known letters in the word
    private long bit_map; // a bitmap of taken positions
    private int guessleft;
    public int lastGuessLetters()
    {
        return guessleft;
    }

    public int setGuessLeft (int guess) {
        this.guessleft = guess;
        return guessleft;
    }

    public Set<String> getMySet(){
        return words;
    }
    public char[] getWordKey(){
        return wordKey;
    }
    //debugging helper
    public static void print_set(Set<String> s)
    {
        Iterator<String> it = s.iterator();
        System.out.print("\nset([");
        while(it.hasNext())
        {
            System.out.print(it.next()+" ");
        }
        System.out.println("])");
    }

    public String randWord()
    {
        Iterator<String> it = words.iterator();
        if (!it.hasNext()) return "";
        else return it.next();
    }

    public void print_guessed()
    {
        Iterator<Character> it = guessed.iterator();
        while (it.hasNext())
        {
            System.out.print(it.next() + " ");
        }
        System.out.print("\n");
    }



    public void print_word()
    {
        System.out.print("Word: ");
        for (int i = 0; i < wlen; i++)
        {
            System.out.print(wordKey[i]);
        }
        System.out.print("\n");
    }

    @Override

    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        wlen = wordLength;
        guessed = new TreeSet<Character>();
        words = new HashSet<String>();
        wordKey = new char[wlen];
        bit_map = 0;
        guessleft = 0;
        if (dictionary.isFile() && dictionary.canRead() && wordLength >0) {
            FileReader file = new FileReader(dictionary);
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String next = s.next();
                if (next.length() == wlen) words.add(next);
            }
        }
        else throw new EmptyDictionaryException();
        if (words.size() == 0)
            throw new EmptyDictionaryException();
        for (int i = 0; i < wlen; i++)
        {
            wordKey[i] = '-';
        }
    }

    //First: map all chars in word to 1 if they are guess, 0 otherwise
    //Interpret the result as a binary number, reading right to left
    //This is to make comparisons between two word classes easier later on
    public static long partition_num(String word, char guess)
    {
        word = word.toLowerCase();
        long num = 0;
        for (int i = word.length()-1; i >=0; i--)
        {
            num *= 2;
            if (word.charAt(i) == guess)
            {
                num += 1;
            }
        }
        return num;
    }

    //adds word to the proper partition
    public static void add_to_partition(String word, HashMap<Long, Set<String>> partition, char guess)
    {
        word = word.toLowerCase();
        //get the index of the word
        long i = partition_num(word, guess);
        // if the map key doesnt have the key, add the key to the map list
        if (!partition.containsKey(i))
        {
            partition.put(i, new HashSet<String>());
        }
        (partition.get(i)).add(word);
    }

    public boolean is_compatible(long num)
    {
        if ((num & bit_map) == 0) return true;
        else return false;
    }

    public static int partition_letters(long partition_num)
    {
        int total = 0;
        while (partition_num !=0)
        {
            total += partition_num % 2;
            partition_num /= 2;
        }
        return total ;
    }

    //updates current displayed correct guesses
    private void update_guesses(long num, char guess)
    {
        // pass in how many correct guessed letters
        guessleft = partition_letters(num);
        guessleft = guessleft - 1;
        for (int i = 0; i < wlen; i++)
        {
            // which mean an odd num
            if (num % 2 == 1)
                wordKey[i] = guess;
            num /= 2;
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        //take care of capital case letter guess
        if(Character.isUpperCase(guess))
            guess+=32;
        if (guessed.contains(guess))
            throw new GuessAlreadyMadeException();
        else
            guessed.add(guess);
        HashMap<Long, Set<String>> partition = new HashMap<Long, Set<String>>();
        //generate partitions
        Iterator<String> it  = words.iterator();
        while (it.hasNext())
        {
            //create a binary map of correct guess word
            add_to_partition(it.next(),partition,guess);
        }
        //best_num doesnt exist in the list
        long best_num = -1;

        // loops through all the word list in the map
        Iterator<Long> itp = partition.keySet().iterator();
        while (itp.hasNext())
        {
            long num = itp.next();
            if (is_compatible(num) && is_better_part(num,best_num,partition) )
                best_num = num;
        }
        words = partition.get(best_num);
        //	print_set(words);
        update_guesses(best_num, guess);
        return words;
    }

    //Is p1 better than p2?
    public boolean is_better_part(long p1_num, long p2_num, HashMap<Long, Set<String>> partition)
    {
        if (p2_num < 0) return true; //parition not initialized
        long p1_size = partition.get(p1_num).size();
        long p2_size = partition.get(p2_num).size();
        int p1_let = partition_letters(p1_num);
        int p2_let = partition_letters(p2_num);


        if (p1_size > p2_size) return true;
        else if (p1_size < p2_size) return false;

        if (p1_let < p2_let) return true;
        if (p2_let < p1_let) return false;

        return (p1_num > p2_num);
    }

    @Override

    public SortedSet<Character> getGuessedLetters() {
        return (SortedSet<Character>) guessed;
    }
}
