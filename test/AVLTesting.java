import org.junit.jupiter.api.*;
import tree.BinarySearchTree;
import tree.BinaryTree;

public class AVLTesting {


    @Test
    void rebalanceTest1(){
        BinarySearchTree test = new BinarySearchTree<>();
        test.avlInsert("a");
        assert(test.getTreeBalance() == 0);
        test.avlInsert("b");
        test.avlInsert("c");
        test.avlInsert("d");


    }
    @Test
    void rebalanceTest2(){
        BinarySearchTree test = new BinarySearchTree<>();
        test.avlInsert("a");
        test.avlInsert("b");
        System.out.println(test.getTreeBalance());
        assert(test.getTreeBalance() == 1);
        test.avlInsert("c");
        test.avlInsert("d");
        test.avlInsert("e");
        test.avlInsert("f");


    }
}
