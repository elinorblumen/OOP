import java.util.Iterator;
import java.util.LinkedList;

/**
 * Wrapper class that extends CollectionFacadeSet and wraps a given linked list.
 */
public class LinkedListFacadeSet extends CollectionFacadeSet {


    private LinkedList<String> linkedList;



    /**
     * Constructor. Creates a new facade wrapping the linked list.
     * @param linkedList The given linked list to wrap.
     */
    public LinkedListFacadeSet(LinkedList<String> linkedList) {
        super(linkedList);
        this.linkedList = linkedList;
    }


    /**
     * Returns an iterator over the elements in this deque in reverse sequential order.
     * @return An iterator over the elements in this deque in reverse sequential order.
     */
    public Iterator<String> iteratorWrapper(){

        return this.linkedList.iterator();
    }
}
