import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class LongTermTest {

    // Data members //
    private static Item[] itemsArray;
    private LongTermStorage lts;


    /**
     * Creates an array of all possible items before all tests.
     */
    @BeforeClass
    public static void createItemsArray(){

        itemsArray = ItemFactory.createAllLegalItems();
    }

    /**
     * Before method that runs before each test and sets some of the data members.
     */
    @Before
    public void  init(){

        lts =  new LongTermStorage();
    }


    /**
     * Test method that checks that no items are added to the long term storage
     * when there's not enough space.
     */
    @Test
    public void addItem_noRoomFailTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)1000/thisItemsArray[0].getVolume()));

        // When

        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded -1); // After that there's no room left
        // in the long term storage for more than 1 item of this type.
        int firstResult = lts.addItem(thisItemsArray[0], 10);
        int secondResult = lts.getAvailableCapacity();


        // Then

        Assert.assertEquals("Test failed: Return value in addItem method isn't correct.",
                -1, firstResult);
        Assert.assertEquals("Test failed: Items were added to long term storage although there's not " +
                "enough room.",1000 - (numberOfItemsFirstAdded - 1)*thisItemsArray[0].getVolume(),
                secondResult);
    }


    /**
     * Test method that checks that items are added to the long term storage
     * when there's enough space..
     */
    @Test
    public void addItemTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        // When

        int numberOfItemsFirstAdded = (Math.round((float)500/thisItemsArray[0].getVolume()));
        int firstResult = lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        int secondResult = lts.getAvailableCapacity();


        // Then

        Assert.assertEquals("Test failed: Return value in addItem method isn't correct.",
                0, firstResult);
        Assert.assertEquals("Test failed: Items weren't added to long term storage although there's " +
                "enough room.",1000 - (numberOfItemsFirstAdded) * itemsArray[0].getVolume()
                , secondResult);
    }


    /**
     * Test method that checks that resetInventory method resets the inventory
     * to be empty and resets the available capacity to full.
     */
    @Test
    public void resetInventoryTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)500/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)200/thisItemsArray[1].getVolume()));

        // When

        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        lts.addItem(thisItemsArray[1] , numberOfItemsSecondlyAdded);
        lts.resetInventory();
        Map<String, Integer> firstResult = lts.getInventory();
        int secondResult = lts.getAvailableCapacity();


        // Then

        Assert.assertTrue("Test failed: Inventory wasn't reset to empty.",
                firstResult.isEmpty());
        Assert.assertEquals("Test failed: Long term storage available capacity wasn't reset.",
                1000, secondResult);
    }


    /**
     * Test method that checks that getItemCount method returns the correct count of the given
     * item type.
     */
    @Test
    public void getItemCountTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)200/thisItemsArray[0].getVolume()));
        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        int numberOfItemsSecondlyAdded = (Math.round((float)200/thisItemsArray[1].getVolume()));
        lts.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = lts.getItemCount(thisItemsArray[1].toString());

        // Then

        Assert.assertEquals("Test failed: Result different than item correct count.",
                numberOfItemsSecondlyAdded, result);
    }


    /**
     * Test method that checks that getInventory method returns the correct inventory.
     */
    @Test
    public void getInventoryTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)200/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)200/thisItemsArray[1].getVolume()));

        Map<String, Integer> inventoryMap = new HashMap<String, Integer>();
        inventoryMap.put(thisItemsArray[0].toString(), numberOfItemsFirstAdded);
        inventoryMap.put(thisItemsArray[1].toString(), numberOfItemsSecondlyAdded);

        // When

        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded );
        lts.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // Then

        Assert.assertTrue("Test failed: Inventory returned isn't correct.",
                inventoryMap.equals(lts.getInventory()));
    }


    /**
     * Test method that checks that getCapacity method returns the correct capacity.
     */
    @Test
    public void getCapacityTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));

        // When

        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        lts.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);
        int result = 1000;

        // Then

        Assert.assertEquals("Test failed: Capacity returned isn't correct.",
                lts.getCapacity(), result);
    }


    /**
     * Test method that checks that getAvailableCapacity method returns the correct
     * available capacity.
     */
    @Test
    public void getAvailableCapacityTest(){

        // Given

        Item[] thisItemsArray = new Item[5];

        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        int numberOfItemsFirstAdded = (Math.round((float)200/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)200/thisItemsArray[1].getVolume()));

        // When

        lts.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        lts.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);
        int result = (1000 - numberOfItemsFirstAdded * itemsArray[0].getVolume()
                - numberOfItemsSecondlyAdded * itemsArray[1].getVolume());

        // Then

        Assert.assertEquals("Test failed: Available capacity returned isn't correct.",
                lts.getAvailableCapacity(), result);
    }
}
