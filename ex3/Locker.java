import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a locker object.
 */
public class Locker {


    // Data members //

    private LongTermStorage lts;
    private final int capacity;
    private int availableCapacity;
    private Item[][] constraints;
    private Map<String, Integer> inventory;
    private Item[][] newConstraints = new Item[1][2];


    /**
     * Constructor for locker object.
     * @param lts A long term storage object identified with the same spaceship.
     * @param capacity Non-negative integer representing the amount of storage units this
     *                 Locker has.
     * @param constraints An array of pairs of item objects which cannot reside
     *                    in the same locker.
     *
     */
    public Locker(LongTermStorage lts, int capacity,
                  Item[][] constraints){

        this.lts = lts;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.constraints = constraints;
        this.inventory = new HashMap<String, Integer>();
        this.newConstraints[0] = ItemFactory.createItems(new String[]{"football",
                "baseball bat"});
    }


    /**
     * Method that receives a specific item and and an integer representing
     * a quantity and tries to add that quantity of the given item to the locker.
     * @param item The item to be added.
     * @param n The number of items of the given type to be added.
     * @return If the addition is successful it returns 0.
     * If n items cannot be added to the locker at this time, no items
     * will be added, and it returns -1.
     * If this action succeeds but causes n* item to be moved to the long term
     * storage the method returns 1.
     * If this action requires n* items to be moved to the long term storage
     * but it doesn't have room to accommodate them, then no items will be added
     * and the method will return -1.
     */
    public int addItem(Item item, int n){


        int totalItemAmount = n;
        int initialItemCount = 0;

        // Plot Twist //

        if(item.toString().equals(newConstraints[0][0].toString())){
            if(!(inventory.get(newConstraints[0][1].toString()) == null)){
                print(errorMessageContainsContradictingItem(item.toString()));
                return -2;
            }
        }
        if(item.toString().equals(newConstraints[0][1].toString())){
            if(!(inventory.get(newConstraints[0][0].toString()) == null)){
                print(errorMessageContainsContradictingItem(item.toString()));
                return -2;
            }
        }

        // Checks for constrained items //
        if(isItemConstrained(item)){
            print(errorMessageNoRoomForNumItems(n, item.toString()));
            return -1;
        }

        if(!(inventory.get(item.toString()) == null)){
            initialItemCount = inventory.get(item.toString());
            totalItemAmount += initialItemCount;
        }
        if(doesItemTakeMoreThanFiftyPercent(item, n)){
            int[] numberToBeLeftAndMoved = itemsMovedForLessThanTwentyPercent(item, totalItemAmount);
            int result = lts.addItem(item,numberToBeLeftAndMoved[1]);
            if(result == 0){
                print(warningMessageItemsMovedToLts());
                availableCapacity += initialItemCount;
                availableCapacity -= numberToBeLeftAndMoved[0];
                inventory.put(item.toString(), numberToBeLeftAndMoved[0]);
                return 1;
            }
            print(errorMessageNoRoomForNumItems(numberToBeLeftAndMoved[1], item.toString()));
            return -1;
        }
        inventory.put(item.toString(), totalItemAmount);
        availableCapacity -= n * item.getVolume();
        return 0;
    }


    /**
     Method that receives a specific item and and an integer representing
     * a quantity and tries to remove that quantity of the given item from the locker.     * @param item
     * @param item The item to be removed.
     * @param n The number of items of the given type to be removed.
     * @return If the action is successful, this method returns 0.
     * If there are less than n items of this type in the locker, no items
     * should be removed, the method should return -1.
     * In case n is negative, no items will be removed and the method will return -1.
     */
    public int removeItem(Item item, int n){

        int numOfItemsInInventory = 0;

        if(!(inventory.get(item.toString()) == null)){

            numOfItemsInInventory = inventory.get(item.toString());
        }
        if(numOfItemsInInventory < n){
            print(errorMessageNotEnoughItemsInLocker(n, item.toString()));
            return -1;
        }
        if(n < 0){
            print(errorMessageCannotRemoveNegativeNumber(item.toString()));
            return -1;
        }
        numOfItemsInInventory -= n;
        inventory.put(item.toString(), numOfItemsInInventory);
        availableCapacity += n;
        return 0;
    }


    /**
     * Method that returns the number of items of the given type this locker
     * contains.
     * @param type The given item tyoe to be searched.
     * @return Integer representing the number of the given item type in
     * the locker.
     */
    public int getItemCount(String type){

        int itemCount = 0;

        if(!(inventory.get(type) == null)){
            return inventory.get(type);
        }
        return itemCount;
    }


    /**
     * Method that returns a map of all the item types contained in this locker,
     * and their respective quantities.
     * @return returns a map of all the item types contained in this locker,
     * and their respective quantities.
     */
    public Map<String, Integer> getInventory(){

        return inventory;
    }


    /**
     * Method that returns the total capacity of this locker.
     * @return An integer representing tha total capacity of this locker
     * in storage units.
     */
    public int getCapacity(){

        return capacity;
    }


    /**
     * Method that returns the locker's available capacity.
     * @return An integer representing the available storage units in the locker.
     */
    public int getAvailableCapacity(){

        return availableCapacity;
    }


    /**
     * Helper method that helps to determine whether an item is constrained.
     * @param item The item to be checked.
     * @return True if item is constrained and cannot be added to inventory,
     * false otherwise.
     */
    private boolean isItemConstrained(Item item){


        String itemString = item.toString();

        int numOfConstraints = constraints.length;

        for (int i = 0 ; i < numOfConstraints ; i++) {
            Item pairedItem = constraints[i][1];
            if ((itemString.equals(constraints[i][0].toString()))) {
                if (inventory.containsKey(pairedItem.toString())) {
                    return true;
                }
            }
            pairedItem = constraints[i][0];
            if ((itemString.equals(constraints[i][1].toString()))) {
                if (inventory.containsKey(pairedItem.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method that helps to print the error message:
     * ""Error: your request cannot be completed at this time. Problem: no room for n
     * items of type type"
     * @param n The number of items.
     * @param type The type of the items.
     * @return String representation of the error.
     */
    public static String errorMessageNoRoomForNumItems(int n, String type){

        return "Error: your request cannot be completed at this time." +
                "Problem: no room for " + n + " items of type " + type;
    }


    /**
     * Helper method that helps to print the warning message:
     * "Warning: Action successful, nut has caused items to be moved to storage"
     */
    private String warningMessageItemsMovedToLts(){

        return "Warning: Action successful, but has caused items to be moved to storage";
    }


    /**
     * Helper method that helps to print the error message:
     * ""Error: your request cannot be completed at this time. Problem: the locker does
     * not contain n items of type type."
     * @param n The number of items.
     * @param type The type of the items.
     * @return String representation of the error.
     */
    private String errorMessageNotEnoughItemsInLocker(int n, String type){

        return "Error: your request cannot be completed at this time." +
                "Problem: the locker does not contain " + n + " items of type " + type;
    }


    /**
     * Helper method that helps to print the error message:
     * ""Error: your request cannot be completed at this time. Problem: cannot remove a
     * negative number of items of type type"
     * @param type The type of the items.
     * @return String representation of the error.
     */
    private String errorMessageCannotRemoveNegativeNumber(String type){

        return "Error: your request cannot be completed at this time." +
                "Problem: cannot remove a negative number of items of type " + type;
    }


    /**
     * Helper method that helps to print the error message:
     * ""Error: your request cannot be completed at this time. Problem: The locker cannot
     * contain items of type type, as it contains a contradicting item"
     * @param type The type of the items.
     * @return String representation of the error.
     */
    private String errorMessageContainsContradictingItem(String type){

        return "Error: your request cannot be completed at this time. Problem: The locker cannot " +
                "contain items of type " + type + " as it contains a contradicting item";
    }


    /**
     * Helper method that helps printing more easily and elegantly.
     * @param string A string to be printed.
     */
    public static void print(String string){

        System.out.println(string);
    }


    /**
     * Helper method that checks if the given item will take more than 50% of than
     * the locker capacity.
     * @param item The item to be checked.
     * @param amountToBeAdded The amount to be added.
     * @return True if the item will take more than 50%, false otherwise.
     */
    private boolean doesItemTakeMoreThanFiftyPercent(Item item, int amountToBeAdded){

        double totalAmount = amountToBeAdded * item.getVolume();

        if(inventory.containsKey(item.toString())){
            totalAmount += (inventory.get(item.toString()) * item.getVolume());
        }

        double percentage = (totalAmount/capacity)*100;

        return percentage > 50.0;
    }


    /**
     * Helper method that helps determine how many items should be moved to the long
     * term storage and how many to be left in locker in order for them to take less than
     * 20% of the locker's capacity.
     * @param item The item to be checked.
     * @param totalAmount The total amount of the item.
     * @return An array of containing two integers. The first one is the number of of
     * items that should be left in the locker. The second one is the number of items that
     * should be moved to the long term storage.
     */
    private int[] itemsMovedForLessThanTwentyPercent(Item item, int totalAmount){

        int[] numberToBeMovedAndLeft = new int[2];

        float oneItemPercentage = ((float)item.getVolume()/capacity)*100;

        int itemsToBeLeft = Math.round(20/oneItemPercentage) - 1;

        int itemsToBeMoved = totalAmount - itemsToBeLeft;

        numberToBeMovedAndLeft[0] = itemsToBeLeft;
        numberToBeMovedAndLeft[1] = itemsToBeMoved;

        return numberToBeMovedAndLeft;
    }
}
