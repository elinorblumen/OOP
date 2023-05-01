import oop.ex3.spaceship.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Class representing a long term storage object.
 */
public class LongTermStorage {

    private int capacity = 1000;
    private int availableCapacity = 1000;
    private Map<String, Integer> inventory;


    /**
     * Constructor for a long term storage object.
     */
    public LongTermStorage(){

        inventory = new HashMap<String, Integer>();
    }


    /**
     * Method that attempts to add n items of the given type to this
     * long term storage object.
     * @param item The item to be added.
     * @param n The number of items of the given type to be added.
     * @return If the action is successful it returns 0.
     * If n items cannot be added to the storage at this time no items will
     * be added and it returns -1.
     */
    public int addItem(Item item,int n){

        if((item.getVolume()*n) > availableCapacity){
            Locker.print(Locker.errorMessageNoRoomForNumItems(n, item.toString()));
            return -1;
        }
        int currentItemAmount = 0;
        if(!(inventory.get(item.toString()) == null)){
            currentItemAmount += inventory.get(item.toString());
        }
        availableCapacity -= item.getVolume()*n;
        inventory.put(item.toString(), (n + currentItemAmount));
        return 0;
    }


    /**
     * Method that resets the inventory of this long term storage to be empty.
     */
    public void resetInventory(){

        inventory = new HashMap<String, Integer>();
        availableCapacity = capacity;
    }


    /**
     * Method that returns the number of items of the given type this
     * long ter storage contains.
     * @return Integer representing the number of items of the given
     * type in the inventory.
     */
    public int getItemCount(String type){

        if(!(inventory.get(type) == null)){
            return inventory.get(type);
        }
        return 0;
    }


    /**
     * Method that returns a map of all the items contained in this
     * long term storage and their respective quantities.
     * @return Returns a map of all the items contained in this
     *  long term storage and their respective quantities.
     */
    public Map<String, Integer> getInventory(){

        return inventory;
    }


    /**
     * Method that returns the long term storage total capacity.
     * @return The long term storage total capacity.
     */
    public int getCapacity(){

        return capacity;
    }


    /**
     * Method that returns this long term storage available capacity.
     * @return This long term storage available capacity.
     */
    public int getAvailableCapacity(){

        return availableCapacity;
    }
}
