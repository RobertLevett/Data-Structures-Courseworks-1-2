package dsacw2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.*;

/**
 *
 * @author ajb
 */
public class DictionaryFinder {

    private static ArrayList<String> list = new ArrayList();

    public DictionaryFinder() {

    }

    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param f
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(" |,|\r\n");
        ArrayList<String> words = new ArrayList<>();
        String str;
        while (sc.hasNext()) {      //provided
            str = sc.next();
            str = str.trim();
            str = str.toLowerCase();
            words.add(str);
        }
        return words;
    }

    public void saveCollectionToFile(Collection<?> c, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Object w : c) {            //provided
            printWriter.println(w.toString());
        }
        printWriter.close();
    }

    public void formDictionary(ArrayList <String> x) {

        Set<String> unique = new HashSet<String>(x);
        for (String key : unique) {
            list.add(key + "," + Collections.frequency(x, key) + ",");
        }
        list.sort(String::compareToIgnoreCase);
        
        
    }

    public void saveToFile() throws IOException {

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter("test0.csv"));

        for (String string : list) {
            outputWriter.write(string);
        }

        outputWriter.flush();
        outputWriter.close();
    }

//    public static void main(String[] args) throws Exception {
//        DictionaryFinder df = new DictionaryFinder();
//        ArrayList<String> in = readWordsFromCSV("DSAfiles\\lotr.csv");
//        //DO STUFF TO df HERE in countFrequencies
//
////        list = in;
//        df.formDictionary(in);
//        df.saveToFile();
//    }

}
