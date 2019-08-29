/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsacw2;

import dsacw2.DictionaryFinder.*;
import static dsacw2.DictionaryFinder.readWordsFromCSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yqm15fqu
 */
public class AutoCompletion {

    private static ArrayList<String> words = new ArrayList<String>();

    public static void formDictionary() throws FileNotFoundException, IOException {
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = readWordsFromCSV("DSAfiles\\lotr.csv");

        df.formDictionary(in);
        df.saveToFile();
    }
    
    public static void writeTofile(ArrayList<String> x) throws IOException{
        DictionaryFinder last = new DictionaryFinder();
        
        last.formDictionary(x);
        last.saveCollectionToFile(x, "output.csv");
        
    }

    public static void constructTrie(AutoCompletionTrie x, ArrayList<String> dictoFreq) {
        for (int i = 0; i < dictoFreq.size(); i++) {
//            System.out.println(dictoFreq.get(i));
            int tempNum = Integer.parseInt(dictoFreq.get(i + 1));
            x.add(dictoFreq.get(i), tempNum);
            i = i + 1;
        }
    }

    public static List searchQueries(AutoCompletionTrie dictoTrie, String prefix) throws FileNotFoundException {

        String append = "";
        dictoTrie.resetRoot(dictoTrie);
        List<String> allWords = new ArrayList<String>();

        dictoTrie = dictoTrie.getSubTrie(prefix, dictoTrie);
        allWords = dictoTrie.getAllWords(prefix);

        for (int i = 0; i < allWords.size(); i++) {
            words.add(allWords.get(i));
        }

        return allWords;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayList<String> wordContain = new ArrayList<String>();
        List<String> prefixHolder = new ArrayList<String>();
        formDictionary();

        AutoCompletionTrie dictionaryTrie = new AutoCompletionTrie();
        dictionaryTrie.root = new AutoCompletionTrieNode();

        ArrayList<String> readWords = readWordsFromCSV("test0.csv");
        constructTrie(dictionaryTrie, readWords);

        ArrayList<String> readQueries
                = readWordsFromCSV("DSAfiles\\lotrQueries.csv");

        List<String> wordList = new ArrayList<String>();

        for (String readQuery : readQueries) {
            int count = 0;
            int total = 0;
            prefixHolder = searchQueries(dictionaryTrie, readQuery);

            float[] freq = new float[prefixHolder.size()];
            float[] highest = new float[3];
            for (int i = 0; i < readWords.size(); i++) {
                if (prefixHolder.contains(readWords.get(i)) == true) {
                    wordContain.add(readWords.get(i));
                    freq[count] = Integer.parseInt(readWords.get(i + 1));
                    count++;
                    i++;                    //when a word is found, every other
                }                           //item is the occurance of that word
            }
            for (int i = 0; i < prefixHolder.size(); i++) {
                total += freq[i];
            }
//            
//            int[] position = new int[3];
//            position[0] = 0;
//            position[1] = 0;
//            position[2] = 0;
//            float gold = 0, silver = 0, bronze = 0;
//
//            for (int i = 0; i < wordContain.size(); i++) {
//
//                if (freq[i] > gold) {
//                    bronze = silver;
//                    position[2] = position[1];
//                    silver = gold;
//                    position[1] = position[0];
//                    gold = freq[i];
//                    position[0] = i;
//                }
//
//                System.out.println("Three most common are: \t"
//                        + wordContain.get(position[0]) + gold / total + "\t"
//                        + wordContain.get(position[1]) + silver / total + "\t"
//                        + wordContain.get(position[2]) + bronze / total);
//
//            }
            for (int i = 0; i < prefixHolder.size(); i++) {
                System.out.println(prefixHolder.get(i) + "\tFrequency\t"
                        + freq[i] + "\ttotal Words predicted\t" + total
                        + "\tProbability:\t " + (freq[i] / total));
            }
        }
        

//        words.sort(String::compareToIgnoreCase);
//        for (String string : words) {
//            System.out.println(string);
//
//        }
//        for (String readWord : readWords) {
//            System.out.println(readWord);
//        }

        wordContain = words;
        writeTofile(words);
    }

}
