import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Test class for BoopingSite class.
 */
public class BoopingSiteTest {


    // Data members //

    private static final Hotel[] hotelsArray = HotelDataset.getHotels("hotels_tst1.txt");


    /**
     * Test method that checks that hotelsInCityByRating() returns an empty array when
     * there are no hotels in the city.
     */
    @Test
    public void hotelsInCityByRating_noHotelsInCityTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst2.txt");
        Hotel[] emptyHotelArray = new Hotel[0];

        // When

        Hotel[] result = boopingSite.getHotelsInCityByRating("manali");

        // Then

        Assert.assertArrayEquals("Test failed: No hotels in city should've " +
                "returned an empty array.", emptyHotelArray, result);
    }


    /**
     * Test method that checks that hotelsInCityByRating() returns an array of hotels
     * in the given city.
     */
    @Test
    public void hotelsInCityByRating_inCityTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_dtaset.txt");

        // When

        Hotel[] result = boopingSite.getHotelsInCityByRating("manali");

        // Then

        for (Hotel hotel : result) {

            Assert.assertEquals("Test failed: Hotels are'nt in given city.",
                    "manali", hotel.getCity());

        }
    }


    /**
     * Test method that checks that hotelsInCityByRating() returns an array of hotels
     * sorted by their rating.
     */
    @Test
    public void hotelsInCityByRating_notSortedByRatingTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");

        // When

        Hotel[] result = boopingSite.getHotelsInCityByRating("manali");

        // Then

        for(int i = 0 ; i < result.length -1 ; i++){

            Assert.assertTrue("Test failed: Hotels are'nt sorted by rating.",
                    result[i].getStarRating() >= result[i + 1].getStarRating());

        }
    }


    /**
     * Test method that check that the method  hotelsInCityByRating() returns an array
     * organized by the hotels alphabet order when the hotels have the same rating
     */
    @Test
    public void hotelsInCityByRating_notSortedByAlphabetTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");
        String[] propertyNames = new String[]{"baragarh villa", "bharhka countryside cottage resort",
                "citrus manali resorts", "the serenity resort &amp; spa",
                "thomas villa, hotel and cottages"};

        // When

        Hotel[] result = boopingSite.getHotelsInCityByRating("manali");

        // Then

        for(int i = 0 ; i < 5 ; i++){
            Assert.assertEquals("Test failed: Hotels are'nt sorted by alphabetical" +
                            "order.", result[i].getPropertyName(), propertyNames[i]);
        }
    }


    /**
     * Test method that checks that hotelsInCityByRating() returns an array of hotels
     * sorted by their proximity.
     */
    @Test
    public void hotelsByProximity_notSortedByProximityTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");

        // When

        Hotel[] result = boopingSite.getHotelsByProximity(0, 0);

        // Then

        for(int i = 0 ; i < result.length -1 ; i++){

            Assert.assertTrue("Test failed: Hotels are'nt sorted by proximity.",
                    Math.sqrt(Math.pow(result[i].getLatitude(), 2) +
                            Math.pow(result[i].getLongitude(), 2))
                            <= Math.sqrt(Math.pow(result[i + 1].getLatitude(), 2) +
                            Math.pow(result[i + 1].getLongitude(), 2)));
        }
    }


    /**
     * Test method that checks that when hotelsByProximity() returns an array of hotels
     * sorted by their proximity, hotels with same proximity are sorted by the number
     * of points of interest in decreasing order.
     */
    @Test
    public void hotelsByProximity_notSortedByPointsOfInterestTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");

        // When

        Hotel[] result = boopingSite.getHotelsByProximity(0, 0);

        // Then

        for(int i = 0 ; i < result.length -1 ; i++){
            if(Math.sqrt(Math.pow(result[i].getLatitude(), 2) +
                    Math.pow(result[i].getLongitude(), 2))
                    == Math.sqrt(Math.pow(result[i + 1].getLatitude(), 2) +
                    Math.pow(result[i + 1].getLongitude(), 2))){

                Assert.assertTrue("Test failed: Hotels with same proximity " +
                                "are'nt sorted by points of interest.",
                        result[i].getNumPOI() >= result[i+1].getNumPOI());
            }
        }
    }


    /**
     * Test method that checks that getHotelsByProximity() returns an empty array
     * when input isn't legal.
     */
    @Test
    public void hotelsByProximity_IllegalInputTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");
        Hotel[] emptyArray = new Hotel[0];

        // When

        Hotel[] result = boopingSite.getHotelsByProximity(120, 200);

        // Then

        Assert.assertArrayEquals("Test failed: Method doesn't return an empty array" +
                "when input is illegal", emptyArray, result);
    }



    /**
     * Test method that checks that getHotelsInCityByProximity() returns an array of hotels
     * in the given city.
     */
    @Test
    public void hotelsInCityByProximity_inCityTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_dataset.txt");

        // When

        Hotel[] result = boopingSite.getHotelsInCityByProximity("manali", 0, 0);

        // Then

        for (Hotel hotel : result) {

            Assert.assertEquals("Test failed: Hotels are'nt in given city.",
                    "manali", hotel.getCity());
        }
    }


    /**
     * Test method that checks that hotelsInCityByProximity() returns an array of hotels
     * sorted by their proximity.
     */
    @Test
    public void hotelsInCityByProximity_notSortedByProximityTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");

        // When

        Hotel[] result = boopingSite.getHotelsInCityByProximity("manali", 0, 0);

        // Then

        for(int i = 0 ; i < result.length -1 ; i++){

            Assert.assertTrue("Test failed: Hotels are'nt sorted by proximity.",
                    Math.sqrt(Math.pow(result[i].getLatitude(), 2) +
                            Math.pow(result[i].getLongitude(), 2))
                            <= Math.sqrt(Math.pow(result[i + 1].getLatitude(), 2) +
                            Math.pow(result[i + 1].getLongitude(), 2)));
        }
    }


    /**
     * Test method that checks that when hotelsInCityByProximity() returns an array of hotels
     * sorted by their proximity, hotels with same proximity are sorted by the number
     * of points of interest in decreasing order.
     */
    @Test
    public void hotelsInCityByProximity_notSortedByPointsOfInterestTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");

        // When

        Hotel[] result = boopingSite.getHotelsInCityByProximity("manali", 0, 0);

        // Then

        for(int i = 0 ; i < result.length -1 ; i++){
            if(Math.sqrt(Math.pow(result[i].getLatitude(), 2) +
                    Math.pow(result[i].getLongitude(), 2))
                    == Math.sqrt(Math.pow(result[i + 1].getLatitude(), 2) +
                    Math.pow(result[i + 1].getLongitude(), 2))){

                Assert.assertTrue("Test failed: Hotels with same proximity " +
                                "are'nt sorted by points of interest.",
                        result[i].getNumPOI() >= result[i+1].getNumPOI());
            }
        }
    }


    /**
     * Test method that checks that getHotelsInCityByProximity() returns an empty array
     * when input isn't legal.
     */
    @Test
    public void hotelsInCityByProximity_IllegalInputTest(){

        // Given

        BoopingSite boopingSite = new BoopingSite("hotels_tst1.txt");
        Hotel[] emptyArray = new Hotel[0];

        // When

        Hotel[] result = boopingSite.getHotelsInCityByProximity("manali", 120, 200);

        // Then

        Assert.assertArrayEquals("Test failed: Method doesn't return an empty array" +
                "when input is illegal", emptyArray, result);
    }
}
