import oop.ex3.searchengine.Hotel;
import java.util.Comparator;

/**
 * Class that implements ordering of hotel elements by their distance from a given point.
 */
public class SortByProximityToGivenLocation implements Comparator<Hotel> {

    // Data members //

    private double givenLatitude = 0;
    private double givenLongitude = 0;


    /**
     * Sets the given latitude.
     * @param latitude Given latitude.
     */
    public void setGivenLatitude(double latitude){

        this.givenLatitude = latitude;
    }

    /**
     * Sets the given longitude.
     * @param longitude Given longitude.
     */
    public void setGivenLongitude(double longitude){

        this.givenLongitude = longitude;
    }


    /**
     * Helper method that returns the Euclidean distance of the given hotel
     * from the given location.
     * @param hotel Given hotel.
     * @return The Euclidean distance of the given hotel
     * from the given location.
     */
    private double hotelDistanceFromPoint(Hotel hotel){

        double latitude = hotel.getLatitude() - this.givenLatitude;
        double longitude = hotel.getLongitude() - this.givenLongitude;

        return Math.sqrt(Math.pow(latitude, 2) + Math.pow(longitude, 2));
    }


    @Override
    public int compare(Hotel o1, Hotel o2) {

        if(hotelDistanceFromPoint(o1) <
        hotelDistanceFromPoint(o2)){
            return -1;
        }
        if(hotelDistanceFromPoint(o1) >
                hotelDistanceFromPoint(o2)){
            return 1;
        }
        return o1.getNumPOI() - o2.getNumPOI();
    }
}
