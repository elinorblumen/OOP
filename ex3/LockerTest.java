import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import java.util.HashMap;
import java.util.Map;


/**
 * Test class for Locker class.
 */
public class LockerTest {

    // Data members //
    private static Item[] itemsArray;
    private static Item[][] allConstraintsArray;
    private LongTermStorage lts;


    /**
     * Creates an array of all possible items before all tests.
     */
    @BeforeClass
    public static void createItemsArray(){

        itemsArray = ItemFactory.createAllLegalItems();
        allConstraintsArray = ItemFactory.getConstraintPairs();
    }

    /**
     * Before method that runs before each test and sets some of the data members.
     */
    @Before
    public void  init(){

        lts =  new LongTermStorage();
    }


    /**
     * Test method that checks that no items are added to the locker when there's not enough space.
     */
    @Test
    public void addItem_noRoomFailTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }

        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 0, constraints);


        // When

        int numberOfItemsFirstAdded = (Math.round((float)1000/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded -1); // After that there's no room left
        // in the long term storage.
        int result = locker.addItem(thisItemsArray[0], 10);

        // Then

        Assert.assertEquals("Test failed: Items were added although there's not enough room."
                ,-1, result);
    }

    /**
     * Test method that checks that no items are added to the locker when there are constraints
     * that don't allow it.
     */
    @Test
    public void addItem_constraintsFailTest(){

        // Given

        Item[][] constraints = new Item[2][2];

        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0] = allConstraintsArray[0];
        constraints[1][0] = thisItemsArray[0];
        constraints[1][1] = thisItemsArray[1];

        Locker locker = new Locker(lts, 200, constraints);

        // When

        locker.addItem(thisItemsArray[0],1);
        int result = locker.addItem(thisItemsArray[1], 1);

        // Then

        Assert.assertEquals("Test failed: Items were added although the given items were constrained."
                ,-1, result);
    }

    /**
     * Test method that checks that items are added to the locker when some items need to be
     * moved to the long term storage and there's enough space in the long term storage.
     */
    @Test
    public void addItem_itemsMoveToLongTermStorageTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);


        // When

        // Calculates the number of items to be added to take up 50% of the locker storage.
        int numberOfItemsFirstAdded = (Math.round((float)50/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        int result = locker.addItem(thisItemsArray[0], numberOfItemsSecondlyAdded);

        // Then

        Assert.assertEquals("Test failed: Items weren't added although some needed to be moved to the" +
                        "long term storage and there's enough room in long term storage."
                ,1, result);
    }

    /**
     * Test method that checks that items are added to the locker when there's enough space
     * and the constraints allow it.
     */
    @Test
    public void addItem_successTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        // When

        // Calculates the number of items to be added to take up 50% of the locker storage.
        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        int result = locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // Then

        Assert.assertEquals("Test failed: Items were not added although they should've been.", 0
        , result);
    }

    /**
     * Test method that checks that no items are removed from locker when there are less
     * items in the locker than the number of items that is asked to be removed.
     */
    @Test
    public void removeItem_notEnoughItemsToBeRemovedTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        // Calculates the number of items to be added to take up 50% of the locker storage.
        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = locker.removeItem(thisItemsArray[1], numberOfItemsSecondlyAdded + 1);

        // Then

        Assert.assertEquals("Test failed: Items were removed, although there weren't n" +
                        "items in the locker.", -1
                , result);
    }


    /**
     * Test method that checks that no items are removed from locker when n is negative.
     */
    @Test
    public void removeItem_nIsNegativeTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        // Calculates the number of items to be added to take up 50% of the locker storage.
        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = locker.removeItem(thisItemsArray[1], -numberOfItemsSecondlyAdded);

        // Then

        Assert.assertEquals("Test failed: Items were removed, although n" +
                        "is negative.", -1, result);
    }

    /**
     * Test method that checks that items are removed from locker when there's n items of the
     * given type in the locker and n isn't negative.
     */
    @Test
    public void removeItemTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = locker.removeItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // Then

        Assert.assertEquals("Test failed: Items weren't removed although they should've been.",
                0, result);
    }


    /**
     * Test method that checks that getItemCount method returns 0 when the given item type
     * isn't in locker.
     */
    @Test
    public void getItemCount_WhenNotInLockerTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = locker.getItemCount(thisItemsArray[2].toString());

        // Then

        Assert.assertEquals("Test failed: Result different than 0 when given item type" +
                        "doesn't exist in locker.",
                0, result);
    }


    /**
     * Test method that checks that getItemCount method returns the correct count of the given
     * item type.
     */
    @Test
    public void getItemCountTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // When

        int result = locker.getItemCount(thisItemsArray[1].toString());

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

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));

        Map<String, Integer> inventoryMap = new HashMap<String, Integer>();
        inventoryMap.put(thisItemsArray[0].toString(), numberOfItemsFirstAdded - 1);
        inventoryMap.put(thisItemsArray[1].toString(), numberOfItemsSecondlyAdded);

        // When

        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded - 1);
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);

        // Then

        Assert.assertTrue("Test failed: Inventory returned isn't correct.",
                inventoryMap.equals(locker.getInventory()));
    }


    /**
     * Test method that checks that getCapacity method returns the correct capacity.
     */
    @Test
    public void getCapacityTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));

        // When

        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);
        int result = 100;

        // Then

        Assert.assertEquals("Test failed: Capacity returned isn't correct.",
                locker.getCapacity(), result);
    }


    /**
     * Test method that checks that getAvailableCapacity method returns the correct capacity.
     */
    @Test
    public void getAvailableCapacityTest(){

        // Given

        Item[][] constraints = new Item[1][2];
        Item[] thisItemsArray = new Item[5];
        for(int i = 0 ; i < 5 ; i++){ // Creates a new list of items which contains 5 items.
            thisItemsArray[i] = itemsArray[i];
        }
        constraints[0][0] = thisItemsArray[3];
        constraints[0][1] = thisItemsArray[4];
        Locker locker = new Locker(lts, 100, constraints);

        int numberOfItemsFirstAdded = (Math.round((float)20/thisItemsArray[0].getVolume()));
        int numberOfItemsSecondlyAdded = (Math.round((float)20/thisItemsArray[1].getVolume()));

        // When

        locker.addItem(thisItemsArray[0], numberOfItemsFirstAdded);
        locker.addItem(thisItemsArray[1], numberOfItemsSecondlyAdded);
        int result = (100 - numberOfItemsFirstAdded * thisItemsArray[0].getVolume()
                - numberOfItemsSecondlyAdded * thisItemsArray[1].getVolume());

        // Then

        Assert.assertEquals("Test failed: Available capacity returned isn't correct.",
                locker.getAvailableCapacity(), result);
    }


    @Test
    public void plotTwistTest(){

        // Given

        Item[][] newConstraints = new Item[1][2];
        newConstraints[0] = ItemFactory.createItems(new String[]{"football",
                "baseball bat"});

        Locker locker = new Locker(lts, 200, newConstraints);

        // When

        locker.addItem(newConstraints[0][0],1);
        int result = locker.addItem(newConstraints[0][1], 1);

        // Then

        Assert.assertEquals("Test failed: Items were added although the given items were constrained" +
                        "by new constraints."
                ,-2, result);
    }


}
