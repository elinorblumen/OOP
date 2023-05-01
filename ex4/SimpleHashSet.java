/**
 * A super class for implementations of the hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {


    // Data members //

    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f; // The higher load factor of a newly
    // created hash set.

    protected static float DEFAULT_LOWER_CAPACITY = 0.25f; // The lower load factor of a newly
    // created hash set.

    protected static int INITIAL_CAPACITY = 16; // The capacity of a newly created hash set.

    protected float upperLoadFactor;

    protected float lowerLoadFactor;


    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY
     * and DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet(){

        upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY.
     * @param upperLoadFactor Upper load factor.
     * @param lowerLoadFactor Lower load factor.
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){

        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }


    @Override
    public abstract boolean add(String newValue);

    @Override
    public abstract boolean contains(String searchVal);

    @Override
    public abstract boolean delete(String toDelete);

    @Override
    public abstract int size();


    /**
     * A method that returns the current capacity (number of cells) of the table.
     * @return Current capacity.
     */
    public abstract int capacity();


    /**
     * A method that returns the lower load factor of the table.
     * @return Lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return this.lowerLoadFactor;
    }


    /**
     * A method that returns the higher load factor of the tabla.
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return this.upperLoadFactor;
    }


    /**
     * A method that clamps hashing indices to fit within the current table capacity.
     * @param index The index before clamping.
     * @return An index properly clamped.
     */
    protected abstract int clamp(int index);
}
