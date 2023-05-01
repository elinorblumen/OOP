import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for testing the spaceship class.
 */
public class SpaceshipTest {


    // Data members //
    private static Item[] itemsArray;
    private static Item[][] allConstraintsArray;
    private Spaceship spaceship;
    private static Item[][] constraints;
    private static int[] crewIDs;
    private static LongTermStorage lts;
    private Locker[] lockersArray;


    /**
     * Creates an array of all possible items before all tests.
     */
    @BeforeClass
    public static void createItemsArray(){

        itemsArray = ItemFactory.createAllLegalItems();
        allConstraintsArray = ItemFactory.getConstraintPairs();
        constraints = new Item[1][2];
        constraints[0] = allConstraintsArray[0];
        crewIDs = new int[3];
        for(int i = 0 ; i < 3 ; i++){
            crewIDs[i] = i+1;
        }
        lts = new LongTermStorage();
    }


    /**
     * Before method that generates a new spaceship.
     */
    @Before
    public void createSpaceship(){

        spaceship = new Spaceship("USS", crewIDs, 3,
                constraints);
        lockersArray = new Locker[3];
    }


    /**
     * Test method that checks that a locker is not created when the
     * given crew member id is non valid.
     */
    @Test
    public void createLocker_idNotValidTest(){

        //Given

        Locker locker = new Locker(lts, 100, constraints);

        //When

        int nonValidID = 5;
        Locker[] firstResult = spaceship.getLockers();
        int secondResult = spaceship.createLocker(nonValidID, 100);

        //Then

        Assert.assertArrayEquals("Test failed:A locker was created with" +
                " a non valid id.", lockersArray, firstResult);
        Assert.assertEquals("Test failed: return value is incorrect",
                -1, secondResult);
    }


    /**
     * Test method that checks that a locker is not created when the
     * given capacity is non valid.
     */
    @Test
    public void createLocker_capacityNotValidTest(){

        //Given

        Locker locker = new Locker(lts, 100, constraints);

        //When

        int validID = 1;
        Locker[] firstResult = spaceship.getLockers();
        int secondResult = spaceship.createLocker(validID, -100);

        //Then

        Assert.assertArrayEquals("Test failed:A locker was created with" +
                " a non valid capacity.", lockersArray, firstResult);
        Assert.assertEquals("Test failed: Return value isn't correct.",
                -2, secondResult);
    }


    /**
     * Test method that checks that a locker is not created when the
     * space ship already has the maximum number of lockers allowed.
     */
    @Test
    public void createLocker_NotLockersLeftTest(){

        //Given

        for(int i = 1 ; i < 4 ; i++){
            spaceship.createLocker(i, 100);
        }

        //When

        int validID = 1;
        int result = spaceship.createLocker(validID, 100);

        //Then

        Assert.assertEquals("Test failed: A locker was created" +
                "although maximum capacity was passed.", -3, result);
    }


    /**
     * Test method that checks that a locker is created when the
     * space ship already has enough free lockers, the id value is valid,
     * and the capacity is valid.
     */
    @Test
    public void createLockerTest(){

        //Given

        int validID = 1;
        int validCapacity = 100;
        Locker locker = new Locker(lts, validCapacity, constraints);

        //When

        int secondResult = spaceship.createLocker(validID, validCapacity);

        //Then

        Assert.assertEquals("Test failed: Return value isn't correct.",
                0, secondResult);
        Assert.assertEquals("Test failed: a locker wasn't created.",
                locker.getCapacity(), spaceship.getLockers()[0].getCapacity());
    }


    /**
     * Test method that checks that a getCrewIDs returns the correct array.
     */
    @Test
    public void getCrewIDsTest(){

        //Given

        //When

        int[] result = spaceship.getCrewIDs();

        //Then

        Assert.assertArrayEquals("Test failed: Crew Ids " +
                        "weren't returned.", crewIDs, result);
    }


    /**
     * Test method that checks that a locker is created when the
     * space ship already has enough free lockers, the id value is valid,
     * and the capacity is valid.
     */
    @Test
    public void getLockersTest(){

        //Given

        int validCapacity = 100;
        for(int i = 1 ; i < 4 ; i++){
            spaceship.createLocker(i, validCapacity);
        }

        //When

        Locker[] result = spaceship.getLockers();

        //Then

        Assert.assertEquals("Test failed: getLockers didn't return the correct array.",
                3, result.length);
        Assert.assertEquals("Test failed: getLockers didn't return the correct array.",
                100, result[0].getCapacity());
    }
}
