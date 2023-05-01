import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClosedHashSetTest {


    // Data members //

    private static float DEFAULT_HIGHER_CAPACITY = 0.75f; // The higher load factor of a newly
    // created hash set.

    private static float DEFAULT_LOWER_CAPACITY = 0.25f; // The lower load factor of a newly
    // created hash set.

    private String[][] closedHashTable;

    private ClosedHashSet closedHashSetTester;



    @Before
    public void setClosedHashTable(){

        closedHashTable = new String[16][2];
        closedHashSetTester = new ClosedHashSet();
    }

    @Test
    public void testAddMethod(){

        //Given//
        String value = "word";

        //When//

        boolean result = closedHashSetTester.add(value);
        boolean result2 = closedHashSetTester.add(value);

        //Then//

        Assert.assertTrue("Add method doesn't work properly", result);
        Assert.assertFalse("Add method doesn't work properly", result2);

    }

    @Test
    public void testDeleteMethod(){


        //Given//
        String value = "word";

        //When//

        boolean result = closedHashSetTester.delete(value);

        //Then//

        Assert.assertFalse("Delete method doesn't work properly", result);

    }

    @Test
    public void testCapacityMethod(){

        //Given//
        String value = "word";

        //When//

        int result = closedHashSetTester.capacity();

        //Then//

        Assert.assertEquals("Delete method doesn't work properly", 16,
                result);

    }


    @Test
    public void testContainsMethod(){

        //Given//
        String value = "word";
        closedHashSetTester.add(value);

        //When//

        boolean result = closedHashSetTester.contains(value);

        //Then//

        Assert.assertTrue("Delete method doesn't work properly", result);

    }


    @Test
    public void testAddMethodSizeChange(){

        //Given//
        for(int i = 0 ; i < 16 ; i++){
            String num = String.valueOf(i);
            closedHashSetTester.add(num);
        }


        //When//

        boolean result = closedHashSetTester.add(String.valueOf(1));
        boolean result2 = closedHashSetTester.add(String.valueOf(15));
        boolean result3 = closedHashSetTester.add("word");
        int result4 = closedHashSetTester.capacity();

        //Then//

        Assert.assertFalse("Add method doesn't work properly", result);
        Assert.assertFalse("Add method doesn't work properly", result2);
        Assert.assertTrue("Add method doesn't work properly", result3);
        Assert.assertTrue("Add method doesn't work properly", result4 > 16);


    }
}
