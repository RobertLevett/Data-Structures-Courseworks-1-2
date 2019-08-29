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
public class Trie {

    public TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

//    public Trie exampleTrie() {
//       
//    }
    public void add(String word, int x) {
        if (root.add(word,x) == true) {
            System.out.println("Word \"" + word + "\" added.");
        } else {
            System.out.println("Word \"" + word + "\" not added.");
        }
    }

    public void contains(String word) {
        if (root.contains(word) == true) {
            System.out.println("Word found");
        } else {
            System.out.println("Word not found");
        }
    }

    public void outputBreadthFirstSearch() {
        System.out.println(root.outputBreadthFirstSearch());
        
    }
    
    public void outputDepthFirstSearch() {
        System.out.println(root.outputDepthFirstSearch());
    }
    
    public void getSubTrie(String prefix){
        root.getSubTrie(prefix);
    }
    
    public void getAllWords(){
        System.out.println(root.getAllWords());
    }

    public static void main(String[] args) {
        Trie test = new Trie();
        test.add("bat",5);              //Testing to make sure duplicates can't
        test.add("cat",0);              //be added.
        test.add("chat",0);
        test.add("cheers",1);
        test.add("cheese",2);
        test.add("alpha",3);

//        test.contains("hello");
//        test.contains("how");
//        test.contains("are");
//        test.contains("you");
//        test.contains("doing");
//        test.contains("he");
//        test.outputBreadthFirstSearch();
//        test.outputDepthFirstSearch();
        test.getAllWords();
        
//        test.getSubTrie("ch");
        
        
    }
}
