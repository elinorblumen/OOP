import java.util.Iterator;

/**
 * A class implementing a closed hash set data structure.
 */
public class ClosedHashSet extends SimpleHashSet {


    //** Data members **//

    private int size = 0;

    private String[][] closedHashTable = new String[INITIAL_CAPACITY][2];

    private int roundCounter = 0;

    private int capacity = INITIAL_CAPACITY;



    /**
     * Default constructor.
     */
    public ClosedHashSet(){

    }


    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity.
     * @param upperLoadFactor Upper load factor.
     * @param lowerLoadFactor Lower load factor.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){

        super(upperLoadFactor, lowerLoadFactor);
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data The elements to be added.
     */
    public ClosedHashSet(java.lang.String[] data){

        super();
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

        if(contains(newValue)){
            return false;
        }
        for(int i = 0 ; i < capacity ; i++){
            int currentIndex = clamp(newValue.hashCode());
            if(closedHashTable[currentIndex][0] == null){
                closedHashTable[currentIndex][0] = newValue;
                size++;
                roundCounter = 0;
                increaseSizeHelper();
                return true;
            }
            roundCounter++;
        }
        roundCounter = 0;
        return false;
    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {

        for(int i = 0 ; i < capacity ; i++){
            if(closedHashTable[clamp(searchVal.hashCode())][0] != null){
                if(closedHashTable[clamp(searchVal.hashCode())][0].equals(searchVal)){
                    roundCounter = 0;
                    return true;
                }
                roundCounter++;
                continue;
            }
            if(closedHashTable[clamp(searchVal.hashCode())][1] == null){
                break;
            }
            roundCounter++;
        }
        roundCounter = 0;
        return false;
    }


    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {

        for(int i = 0 ; i < capacity ; i++){
            if(closedHashTable[clamp(toDelete.hashCode())][0] != null){
                if(closedHashTable[clamp(toDelete.hashCode())][0].equals(toDelete)){
                    roundCounter = 0;
                    closedHashTable[clamp(toDelete.hashCode())][0] = null;
                    closedHashTable[clamp(toDelete.hashCode())][1] = "flagged";
                    size--;
                    roundCounter = 0;
                    decreaseSizeHelper();
                    return true;
                }
                roundCounter++;
                continue;
            }
            if(closedHashTable[clamp(toDelete.hashCode())][1] == null){
                break;
            }
            roundCounter++;
        }
        roundCounter = 0;
        return false;
    }


    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * A method that returns the current capacity (number of cells) of the table.
     * @return Current capacity.
     */
    @Override
    public int capacity() {
        return capacity;
    }


    /**
     * A method that clamps hashing indices to fit within the current table capacity.
     * @param index The index before clamping.
     * @return An index properly clamped.
     */
    @Override
    protected int clamp(int index) {
        return (index + (roundCounter + roundCounter^2)/2) & (capacity - 1);
    }


    /**
     * Helper method that helps to increase the table size if needed.
     */
    private void increaseSizeHelper(){

        float loadFactor = ((float)size/capacity);

        if(loadFactor > getUpperLoadFactor()){

            int oldCapacity = capacity;
            capacity = oldCapacity*2;
            String[][] oldTable = closedHashTable;
            closedHashTable = new String[capacity][2];

            resizeTableHelper(oldCapacity, oldTable);
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
                String[][] oldTable = closedHashTable;
                closedHashTable = new String[capacity][2];

                resizeTableHelper(oldCapacity, oldTable);
            }
        }
    }


    /**
     * Helper method for increaseSizeHelper and decreaseSizeHelper.
     * @param oldCapacity int representing the old capacity of the table.
     * @param oldTable An array of arrays of strings representing the old table.
     */
    private void resizeTableHelper(int oldCapacity, String[][] oldTable){


        for(int i = 0 ; i < oldCapacity ; i++){
            if(oldTable[i][0] != null){
                for(int j = 0 ; j < capacity ; j++){
                    if(closedHashTable[clamp(oldTable[i][0].hashCode())][0] == null){
                        closedHashTable[clamp(oldTable[i][0].hashCode())][0] = oldTable[i][0];
                        roundCounter = 0;
                        break;
                    }
                    roundCounter++;
                }
            }
        }
        roundCounter = 0;
    }
}
