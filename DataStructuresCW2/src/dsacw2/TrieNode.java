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
public class TrieNode {

    private TrieNode parent;
    private TrieNode[] child;
    private boolean isLeaf;
    private boolean isKey;
    private char character;
    private int keyFreq;
    private boolean visited;
    private Queue<TrieNode> queue = new LinkedList<TrieNode>();

//    private int a = (int) 'a';          //For the ASCII value of 'a' to find position
    //In the array
    public TrieNode() {
        child = new TrieNode[26];
        isLeaf = true;
        isKey = false;
    }

    public TrieNode(char character) {
        this();
        this.character = character;
    }
    
    public int setNodeFreq(TrieNode n, int x){
        if(n.isKey == true){
            n.keyFreq = x;
        }else{
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
            child[charPos] = new TrieNode(word.charAt(0));
            child[charPos].parent = this;
        }

        if (word.length() > 1) {
            child[charPos].add(word.substring(1),freq); //Can't use '.charAt' because the method input is a string.
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
        TrieNode x = this;
        queue.add(x);
        x.visited = true;

        ArrayList<TrieNode> nodeHolder = new ArrayList();
        String bfs = "";
        int count = 0;

        while (!queue.isEmpty()) {
            TrieNode element = queue.remove();
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
        TrieNode x = this;
        String dfs = "";
        ArrayList<TrieNode> nodeHolder = new ArrayList();
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

    public Trie getSubTrie(String prefix) {

//        int charPos = prefix.charAt(0) - 'a';
        TrieNode newNode = this;
        Trie subTrie = new Trie();

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

    public List<String> getAllWords() {

        List<String> wordList = new ArrayList<String>();
        String word = "";
        TrieNode current = this;

        if (this != null) {
            getWords(word, current, wordList);
        }
        return wordList;
    }

    public void getWords(String word, TrieNode rootNode, List<String> wordList) {
        for (int i = 0; i < 26; i++) {
            if (rootNode.child[i] != null) {
                String nodeString = word + rootNode.child[i].character;

                if (rootNode.child[i].isKey == true) {
                    wordList.add(nodeString);
                }
                getWords(nodeString, rootNode.child[i], wordList);
            }
        }
    }
}
