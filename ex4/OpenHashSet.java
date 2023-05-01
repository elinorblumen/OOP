import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class implementing a open hash set data structure.
 */
public class OpenHashSet extends SimpleHashSet {


    //** Data members **//

    private int size = 0;

    private int capacity = INITIAL_CAPACITY;

    private LinkedListFacadeSet[] openHashTable = new LinkedListFacadeSet[INITIAL_CAPACITY];


    /**
     * Default constructor.
     */
    public OpenHashSet(){

        newTableCreator();
    }


    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity.
     * @param upperLoadFactor Upper load factor.
     * @param lowerLoadFactor Lower load factor.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){

        super(upperLoadFactor, lowerLoadFactor);

        newTableCreator();
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data The elements to be added.
     */
    public OpenHashSet(java.lang.String[] data){

        super();
        newTableCreator();
        for(String value : data){
            this.add(value);
        }
    }


    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        int hashCode = newValue.hashCode();
        int clampedIndex = clamp(hashCode);

        if(openHashTable[clampedIndex].contains(newValue)){
            return false;
        }
        openHashTable[clampedIndex].add(newValue);
        size ++;
        increaseSizeHelper();

        return true;
    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {

        int clampedIndex = clamp(searchVal.hashCode());
        return openHashTable[clampedIndex].contains(searchVal);
    }


    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {

        int clampedIndex = clamp(toDelete.hashCode());

        if(contains(toDelete)){
            if(openHashTable[clampedIndex].delete(toDelete)){
                size--;
                decreaseSizeHelper();
                return true;
            }
        }
        return false;
    }


    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size(){
        return size;
    }


    /**
     * A method that returns the current capacity (number of cells) of the table.
     * @return Current capacity.
     */
    @Override
    public int capacity() {
        return this.capacity;
    }


    /**
     * A method that clamps hashing indices to fit within the current table capacity.
     * @param index The index before clamping.
     * @return An index properly clamped.
     */
    @Override
    protected int clamp(int index) {
        return (index & (capacity - 1));
    }


    /**
     * Helper method that helps to increase the table size if needed.
     */
    private void increaseSizeHelper(){

        float loadFactor = ((float)size/capacity);

        if(loadFactor > getUpperLoadFactor()){

            int oldCapacity = capacity;
            capacity = oldCapacity*2;
            LinkedListFacadeSet[] oldTable = openHashTable;
            openHashTable = new LinkedListFacadeSet[capacity];
            newTableCreator();

            for(int i = 0 ; i < oldCapacity ; i++){
                Iterator<String> iter = oldTable[i].iteratorWrapper();
                while (iter.hasNext()){
                    String currentValue = iter.next();
                    openHashTable[clamp(currentValue.hashCode())].add(currentValue);
                }
            }
        }
    }


    /**
     * Helper method that helps to decrease the table size if needed.
     */
    private void decreaseSizeHelper(){

        float loadFactor = ((float)size/capacity);

        if(loadFactor < getLowerLoadFactor()){
            if(capacity > 1){
                int oldCapacity = capacity;
                capacity = oldCapacity/2;
                LinkedListFacadeSet[] oldTable = openHashTable;
                openHashTable = new LinkedListFacadeSet[capacity];
                newTableCreator();

                for(int i = 0 ; i < oldCapacity ; i++){
                    Iterator<String> iter = oldTable[i].iteratorWrapper();
                    while (iter.hasNext()){
                        openHashTable[clamp(iter.next().hashCode())].add(iter.next());
                    }
                }
            }
        }
    }


    /**
     * Helper method that assigns a new LinkedListFacadeSet to each cell on the table.
     */
    private void newTableCreator(){

        for(int i = 0 ; i < capacity ; i++){
            LinkedList<String> linkedList = new LinkedList<>();
            openHashTable[i] = new LinkedListFacadeSet(linkedList);
        }

    }
}
