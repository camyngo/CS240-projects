package spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    private Trie dict;
    public SpellCorrector()
    {
        dict = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File fil = new File(dictionaryFileName);

        FileReader freader = new FileReader(fil);

        BufferedReader fbuf = new BufferedReader(freader);

        Scanner s = new Scanner(fbuf);

        s.useDelimiter("([^a-zA-Z]+)");

        while (s.hasNext()) {

            String w = s.next();

            dict.add(w);
        }
    }
    public static List<String> gen_edits(String word)

    {
        List<String> edits = new ArrayList<String>();

        int l = word.length();
        //deletion and transposition edits

        for (int i = 0; i < l; i++)
        {
            if (i > 0) {

                edits.add(word.substring(0,i)+word.substring(i+1));

                edits.add(word.substring(0,i-1) + word.charAt(i) + (word.charAt(i-1) + word.substring(i+1)));

            }

            else edits.add(word.substring(i+1));

        }
        //alteration edits
        for (int i = 0; i < l; i++)
        {
            for (char c = 'a'; c <= 'z'; c++)
            {
                edits.add(word.substring(0,i) + c + word.substring(i+1));

            }
        }
        //insertion edits
        for (int i = 0; i <= l; i++)
        {
            for (char c = 'a'; c <= 'z'; c++)
            {
                edits.add(word.substring(0,i) + c + word.substring(i));
// 				System.out.println(edits.get(edits.size() - 1));
            }
        }
        //in total, 54*words.length() + 25 strings of edit distance 1 away
        return edits;
    }

    // returns frequency of word in dict
    public int word_count(String word)
    {
        Node w = (Node)dict.find(word);
        if (w != null) return w.getValue();
        return 0;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();

        if (word_count(inputWord) > 0) return inputWord;

        List<String> edits = gen_edits(inputWord);

        String best_cand = "";

        int best_val = 0;

        String temp;

        int tcount;
        for (int i = 0; i < edits.size(); i++)
        {
            temp = edits.get(i);
            if ((tcount=word_count(temp))>0)
            {
                if (tcount > best_val)
                {
                    best_val = tcount;
                    best_cand = temp;
                }
                else if (tcount == best_val)
                {
                    if (temp.compareTo(best_cand) < 0) best_cand = temp;
                }
            }
        }

        if (best_val > 0) return best_cand;
        // now check all strings edit distance 2 away

        for (int i = 0; i < edits.size(); i++)
        {
            List<String> edits2 = gen_edits(edits.get(i));
            for (int j = 0; j < edits2.size(); j++)

            {

                temp = edits2.get(j);

                if ((tcount=word_count(temp))>0)

                {

                    if (tcount > best_val)

                    {

                        best_val = tcount;

                        best_cand = temp;

                    }

                    else if (tcount == best_val)
                    {
                        if (temp.compareTo(best_cand) < 0) best_cand = temp;
                    }
                }
            }
        }
        if (best_val> 0) return best_cand;
        else return null;
    }
}
