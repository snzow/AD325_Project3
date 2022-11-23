package tree;

import java.util.Iterator;

public interface TreeIteratorInterface<T>
{
    public Iterator<T> getPreorderIterator();
    public Iterator<T> getPostorderIterator();
    public Iterator<T> getInorderIterator();
} // end TreeIteratorInterface
