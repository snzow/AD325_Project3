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

    public void initializeReservedWords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Aodhan\\IdeaProjects\\AD325_Project3\\src\\reservedWords.txt"));
        String nextLine = br.readLine();
        while(nextLine != null){
            reservedWordsArrayList.add(nextLine);
            nextLine = br.readLine();
        }
        for (String t : reservedWordsArrayList){
            setBalancedBST(t,reservedWordsBST);
        }

    }

    public void setBalancedBST(String toAdd, BinarySearchTree tree){
        System.out.println(toAdd);
        tree.avlInsert(toAdd);
        System.out.println(toAdd + " has been added to the reserved words AVL tree");

    }

    public int getReservedWordsBalance(){
        return reservedWordsBST.getTreeBalance();
    }

    public void getIdentifiers(){
        String[] words;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Aodhan\\IdeaProjects\\AD325_Project3\\src\\Palindrome.java"));){
            String nextLine = br.readLine();
            while(nextLine != null){
                words = nextLine.split("[\\s;{}()\\]\\[+=><-]");
                for(int i = 0; i < words.length; i++){
                    identifiersArrayList.add(words[i]);
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
            if (reservedWordsBST.getEntry(s) != null) {
                System.out.println(s + " is a reserved word");
            }
            else{

                if(identifiersBST.getEntry(s) != null){
                    System.out.println(s + " is already contained in tree");

                }
                else{
                    System.out.println(s + " has been added to identifiers");
                    identifiersBST.avlInsert(s);
                }
            }

        }

    }



}
