package tree;

import tree.BinaryNode;
import tree.BinaryTree;
import tree.BinaryTreeInterface;

import java.security.cert.X509Certificate;

public class BinarySearchTree<T extends Comparable<? super T>>
        extends BinaryTree<T> implements SearchTreeInterface<T>
{
    public BinarySearchTree()
    {
        super();
    } // end default constructor

    public BinarySearchTree(T rootEntry)
    {
        super();
        setRootNode(new BinaryNode<T>(rootEntry));
    } // end constructor

    // Disable setTree (see Segment 26.6)
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree)
    {
        throw new UnsupportedOperationException();
    } // end setTree

    public T getEntry(T anEntry)
    {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry

    private T findEntry(BinaryNode<T> rootNode, T anEntry)
    {
        T result = null;

        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();

            if (anEntry.equals(rootEntry))
                result = rootEntry;
            else if (anEntry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), anEntry);
            else
                result = findEntry(rootNode.getRightChild(), anEntry);
        } // end if

        return result;
    } // end findEntry

    public boolean contains(T entry)
    {
        return getEntry(entry) != null;
    } // end contains



    public T add(T newEntry)
    {
        T result = null;

        if (isEmpty())
            setRootNode(new BinaryNode<>(newEntry));
        else
            result = addEntry(getRootNode(), newEntry);

        return result;
    } // end add

    // Adds anEntry to the nonempty subtree rooted at rootNode.
    private T addEntry(BinaryNode<T> rootNode, T anEntry)
    {
        // Assertion: rootNode != null
        T result = null;
        int comparison = anEntry.compareTo(rootNode.getData());

        if (comparison == 0)
        {
            result = rootNode.getData();
            rootNode.setData(anEntry);
        }
        else if (comparison < 0)
        {
            if (rootNode.hasLeftChild())
                result = addEntry(rootNode.getLeftChild(), anEntry);
            else
                rootNode.setLeftChild(new BinaryNode<>(anEntry));
        }
        else
        {
            // Assertion: comparison > 0

            if (rootNode.hasRightChild())
                result = addEntry(rootNode.getRightChild(), anEntry);
            else
                rootNode.setRightChild(new BinaryNode<>(anEntry));
        } // end if

        return result;
    } // end addEntry

    public T remove(T anEntry)
    {
        ReturnObject oldEntry = new ReturnObject(null);
        BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
        setRootNode(newRoot);

        return oldEntry.get();
    } // end remove

    // Removes an entry from the tree rooted at a given node.
    // Parameters:
    //    rootNode A reference to the root of a tree.
    //    anEntry  The object to be removed.
    //    oldEntry An object whose data field is null.
    //    Returns: The root node of the resulting tree; if anEntry matches
    //             an entry in the tree, oldEntry's data field is the entry
    //             that was removed from the tree; otherwise it is null.
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry,
                                      ReturnObject oldEntry)
    {
        if (rootNode != null)
        {
            T rootData = rootNode.getData();
            int comparison = anEntry.compareTo(rootData);

            if (comparison == 0)       // anEntry == root entry
            {
                oldEntry.set(rootData);
                rootNode = removeFromRoot(rootNode);
            }
            else if (comparison < 0)   // anEntry < root entry
            {
                BinaryNode<T> leftChild = rootNode.getLeftChild();
                BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
                rootNode.setLeftChild(subtreeRoot);
            }
            else                       // anEntry > root entry
            {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                // A different way of coding than for left child:
                rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
            } // end if
        } // end if

        return rootNode;

    } // end removeEntry

    public void avlInsert(T w){
        if(getRootNode().equals(null)){
            setRootNode(new BinaryNode(w));
        }
        else if(findEntry(getRootNode(),w).equals(null)){
            avlInsert(getRootNode(), w);
        }
    }
    private void avlInsert(BinaryNode<T> n, T w){
        if (w.compareTo(n.getData()) < 0){
            if(!n.hasLeftChild()){
                n.setLeftChild(new BinaryNode(w));
                rebalance(n);
            }
            else{
                avlInsert(n.getLeftChild(),w);
                rebalance(n.getLeftChild());
            }
        }
        else{
            if(!n.hasRightChild()){
                n.setRightChild(new BinaryNode(w));
                rebalance(n);
            }
            else{
                avlInsert(n,w);
                rebalance(n.getRightChild());
            }
        }

    }

    public void rebalance(BinaryNode n) {
        if(balance(n) < -1){
            if (balance(n.getLeftChild()) < 0){
                rightRotate(n);
            }
            else{
                leftRotate(n.getLeftChild());
                rightRotate(n);
            }
        }
        else if (balance(n) > 1){
            if (balance(n.getRightChild()) < 0){
                rightRotate(n.getRightChild());
                leftRotate(n);
            }
            else{
                leftRotate(n);
            }
        }

    }

    private int balance(BinaryNode n){
        if(n == null || (!n.hasRightChild() && !n.hasLeftChild())){
            return 0;
        }
        else if(!n.hasLeftChild()){
            return n.getRightChild().getHeight() - 1;
        }
        else if(!n.hasRightChild()){
            return n.getLeftChild().getHeight() - 1;
        }
        else{
            return n.getRightChild().getHeight() - n.getLeftChild().getHeight();
        }
    }

    public void rightRotate(BinaryNode y){
        BinaryNode x = y.getLeftChild();
        y.setLeftChild(x.getRightChild());
        if(x.getRightChild() != null){
            x.getRightChild().setParent(y);
        }
        if(y.getParent() != null){
            x.setParent(y.getParent());
            if(y.getParent().getLeftChild().equals(y)){
                y.getParent().setLeftChild(x);
            }
            else{
                y.getParent().setRightChild(x);
            }
        }
        else{
            x.setParent(null);
        }
        x.setRightChild(y);
        y.setParent(x);

        if(getRootNode().equals(y)){
            setRootNode(x);
        }
    }

    public void leftRotate(BinaryNode x) {
        BinaryNode y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if (y.getLeftChild() != null) {
            y.getLeftChild().setParent(x);
        }
        if (x.getParent().equals(null)) {
            y.setParent(null);
        } else {
            y.setParent(x.getParent());
            if (x.getParent().getLeftChild().equals(x)) {
                x.getParent().setLeftChild(y);
            } else {
                x.getParent().setRightChild(y);
            }
        }
        y.setLeftChild(x);
        x.setParent(y);

        if (getRootNode().equals(x)) {
            setRootNode(y);
        }
    }






    // Removes the entry in a given root node of a subtree.
    // rootNode is the root node of the subtree.
    // Returns the root node of the revised subtree.
    private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode)
    {
        // Case 1: rootNode has two children
        if (rootNode.hasLeftChild() && rootNode.hasRightChild())
        {
            // Find node with largest entry in left subtree
            BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
            BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

            // Replace entry in root
            rootNode.setData(largestNode.getData());

            // Remove node with largest entry in left subtree
            rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
        } // end if

        // Case 2: rootNode has at most one child
        else if (rootNode.hasRightChild())
            rootNode = rootNode.getRightChild();
        else
            rootNode = rootNode.getLeftChild();

        // Assertion: If rootNode was a leaf, it is now null

        return rootNode;
    } // end removeFromRoot

    // Finds the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the node containing the largest entry in the tree.
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
            rootNode = findLargest(rootNode.getRightChild());

        return rootNode;
    } // end findLargest

    // Removes the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the root node of the revised tree.
    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
        {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            rightChild = removeLargest(rightChild);
            rootNode.setRightChild(rightChild);
        }
        else
            rootNode = rootNode.getLeftChild();

        return rootNode;
    } // end removeLargest

    private class ReturnObject
    {
        private T item;

        private ReturnObject(T entry)
        {
            item = entry;
        } // end constructor

        public T get()
        {
            return item;
        } // end get

        public void set(T entry)
        {
            item = entry;
        } // end set
    } // end ReturnObject
} // end BinarySearchTree