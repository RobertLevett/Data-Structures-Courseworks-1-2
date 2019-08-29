/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsacw2;

import java.util.*;

/**
 *
 * @author yqm15fqu
 */
public class AutoCompletionTrieNode {

    private AutoCompletionTrieNode parent;
    private AutoCompletionTrieNode[] child;
    private boolean isLeaf;
    private boolean isKey;
    private char character;
    private int keyFreq;
    private boolean visited;
    private Queue<AutoCompletionTrieNode> queue = new LinkedList<AutoCompletionTrieNode>();

//    private int a = (int) 'a';          //For the ASCII value of 'a' to find position
    //In the array
    public AutoCompletionTrieNode() {
        child = new AutoCompletionTrieNode[26];
        isLeaf = true;
        isKey = false;
    }

    public AutoCompletionTrieNode(char character) {
        this();
        this.character = character;
    }

    public int setNodeFreq(AutoCompletionTrieNode n, int x) {
        if (n.isKey == true) {
            n.keyFreq = x;
        } else {
            System.out.println("Node is not a key.");
        }
        return x;
    }

    public boolean add(String word, int freq) {   //Method recursively calls itself whilst
        //There's still letters left in a word.
        int charPos = word.charAt(0) - 'a';//For getting to the correct array position.

        if (contains(word) == true) {
            return false;
        }
        isLeaf = false;

        if (child[charPos] == null) {     //if a letter is found and it's position is null, add it to array.
            child[charPos] = new AutoCompletionTrieNode(word.charAt(0));
            child[charPos].parent = this;
        }

        if (word.length() > 1) {
            child[charPos].add(word.substring(1), freq); //Can't use '.charAt' because the method input is a string.
        } else {
            child[charPos].isKey = true;
            child[charPos].keyFreq = freq;
        }
        return true;
    }

    public boolean contains(String word) {
        int charPos = word.charAt(0) - 'a';

        if (child[charPos] == null) {
            return false;
        }

        if (child[charPos] != null && word.length() > 1) {
            return child[charPos].contains(word.substring(1));
        } else if (child[charPos].isKey == true && word.length() == 1) {
            return true;
        } else if (word.length() == 1 && child[charPos].isKey == false) {
            return true;        // USED FOR PREFIX (Q2.5)
        }
        return false;
    }

    public String outputBreadthFirstSearch() {
        AutoCompletionTrieNode x = this;
        queue.add(x);
        x.visited = true;

        ArrayList<AutoCompletionTrieNode> nodeHolder = new ArrayList();
        String bfs = "";
        int count = 0;

        while (!queue.isEmpty()) {
            AutoCompletionTrieNode element = queue.remove();
            bfs += (element.character);
            for (int i = 0; i < 26; i++) {
                if (x.child[i] != null && !x.child[i].visited) {
                    nodeHolder.add(x.child[i]);
                    queue.add(x.child[i]);
                    x.child[i].visited = true;
                }
            }
            x = nodeHolder.get(count);
            if (count < nodeHolder.size() - 1) {
                count++;
            }
        }
        return bfs;
    }

    public String outputDepthFirstSearch() {
        AutoCompletionTrieNode x = this;
        String dfs = "";
        ArrayList<AutoCompletionTrieNode> nodeHolder = new ArrayList();
        int count = 0;
        x.visited = true;

        for (int i = 0; i < 26; i++) {
            if (x.child[i] != null && !x.child[i].visited) {
                nodeHolder.add(x.child[i]);
                dfs += nodeHolder.get(count).outputDepthFirstSearch();
                count++;
            }
        }
        dfs += x.character;
        return dfs;
    }

    public AutoCompletionTrie getSubTrie(String prefix, AutoCompletionTrie n) {

//        int charPos = prefix.charAt(0) - 'a';
        String temp = "";

        AutoCompletionTrieNode newNode = n.root;
        AutoCompletionTrie subTrie = n;

        if (contains(prefix) == true) {
            for (int i = 0; i <= prefix.length() - 1; i++) {
                int charPos = prefix.charAt(i) - 'a';
                newNode = newNode.child[charPos];
            }
            subTrie.root = newNode;
        } else {
            return null;
        }
        return subTrie;
    }

    public AutoCompletionTrieNode resetRoot(AutoCompletionTrieNode n) {
        while (n.parent != null) {
            n = n.parent;
        }
        return n;
    }
//
//    public String prefixWord(AutoCompletionTrieNode n) {           // attempt at fixing words
//        List<String> word = new ArrayList<String>();//which are their prefix
//        String forcedWord = "";
//        String charW = "";
//        while (n.parent != null) {
//            n = n.parent;
//        }
//
////        word = n.getAllWords();
//
//        forcedWord = word.get(0);
//
//        return forcedWord;
//
//    }

    public List<String> getAllWords(String prefix) {

        List<String> wordList = new ArrayList<String>();
        List<String> temp = new ArrayList<String>();
        ArrayList<Character> test = new ArrayList<Character>();
        String word = "";
        int allNull = 0;
        AutoCompletionTrieNode current = this;

        
        if (current.isKey == true) {
            while (current.parent != null) {
                test.add(current.character);
                current = current.parent;
            }
            StringBuilder sb = new StringBuilder(temp.size());
            for (int i = test.size(); i > 0; i--) {
                sb.append(test.get(i-1));
            }
            wordList.add(sb.toString());
            return wordList;
        }

        if (current != null) {
            getWords(prefix, word, current, wordList);
        }
        return wordList;
    }

    public void getWords(String prefix, String word, AutoCompletionTrieNode rootNode, List<String> wordList) {
        String freq;
        for (int i = 0; i < 26; i++) {
            if (rootNode.child[i] != null) {
                String nodeString = word + rootNode.child[i].character;
                if (rootNode.child[i].isKey == true) {
                    freq = Integer.toString(rootNode.child[i].keyFreq);
                    wordList.add(prefix + nodeString);
//                    wordList.add(freq);
                }
                getWords(prefix, nodeString, rootNode.child[i], wordList);
            }
        }
    }
}
