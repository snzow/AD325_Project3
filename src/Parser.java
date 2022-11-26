import tree.BinarySearchTree;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class Parser {
    ArrayList<String> reservedWordsArrayList;
    Stack identifiersStack;
    BinarySearchTree reservedWordsBST;
    BinarySearchTree identifiersBST;


    public Parser(){
        reservedWordsBST = new BinarySearchTree();
        identifiersBST = new BinarySearchTree();
        identifiersStack = new Stack();
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
            reservedWordsBST.add(t);
        }
    }

    public void setBalancedBST(String wordToAdd){
        reservedWordsBST.add(wordToAdd);

    }

}
