import tree.BinarySearchTree;
import tree.BinaryTree;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class Parser {
    ArrayList<String> reservedWordsArrayList;
    ArrayList<String> identifiersArrayList;
    BinarySearchTree reservedWordsBST;
    BinarySearchTree identifiersBST;


    public Parser(){
        reservedWordsBST = new BinarySearchTree();
        identifiersBST = new BinarySearchTree();
        identifiersArrayList = new ArrayList<>();
        reservedWordsArrayList = new ArrayList<>();
    }

    /**
     * initializes the reserved words list, adding the words to an avl tree
     * @throws IOException if there is no reservedWords.txt
     */
    public void initializeReservedWords() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("reservedWords.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String nextLine = br.readLine();
        while(nextLine != null){
            reservedWordsArrayList.add(nextLine);
            nextLine = br.readLine();
        }
        for (String t : reservedWordsArrayList){
            setBalancedBST(t,reservedWordsBST);
        }
        in.close();

    }

    /**
     * adds values to an avl tree
     * @param toAdd the string to add to the tree
     * @param tree the tree to add them to
     */
    public void setBalancedBST(String toAdd, BinarySearchTree tree){
        tree.avlInsert(toAdd);

    }

    /**
     * scans through a file and puts all non-reserved identifiers into an avl tree
     * @throws IOException if there is no Palindrome.java
     */
    public void getIdentifiers() throws IOException {
        String[] words;
        try (BufferedReader br = new BufferedReader(new FileReader("src/Palindrome.java"))){
            String nextLine = br.readLine();
            while(nextLine != null){
                words = nextLine.split("[\\s;{}()\\]\\[+=>.<-]");
                for(int i = 0; i < words.length; i++){
                    if(words[i] != null){
                        identifiersArrayList.add(words[i]);
                    }
                }
                nextLine = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String s : identifiersArrayList){
            if(s == ""){
                continue;
            }
            if (reservedWordsBST.getEntry(s) == null) {
                if (identifiersBST.getEntry(s) == null) {
                    identifiersBST.avlInsert(s);
                }
            }


        }
    }



}
