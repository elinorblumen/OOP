/**
 * A class that wraps an underlying collection and serves to both simplify its API and give it
 * a common type with the implemented SimpleHashSets.
 */
public class CollectionFacadeSet implements SimpleSet{


    //** Data members **//

    private java.util.Collection<java.lang.String> collection;


    /**
     * Constructor. Creates a new facade wrapping the specified collection.
     * @param collection The given collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){

        this.collection = collection;
    }


    @Override
    public boolean add(String newValue) {
        if(contains(newValue)){
            return false;
        }
        return collection.add(newValue);
    }

    @Override
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }

    @Override
    public boolean delete(String toDelete) {
        return collection.remove(toDelete);
    }

    @Override
    public int size() {
        return collection.size();
    }
}
