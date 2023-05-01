import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing a booping site object.
 */
public class BoopingSite {


    // Data members //

    private SortByProximityToGivenLocation sortByProximity;
    private SortByRating sortByRating;
    private Hotel[] hotelsArray;


    /**
     * Constructor for booping site object.
     * @param name Data set file name.
     */
    public BoopingSite(String name){


        this.sortByProximity = new SortByProximityToGivenLocation();
        this.sortByRating = new SortByRating();
        this.hotelsArray = HotelDataset.getHotels(name);
    }

    /**
     * Method  that returns an array hotels located in the given city
     * sorted from the highest star-rating to the lowest.
     * @return An array hotels located in the given city
     * sorted from the highest star-rating to the lowest. Hotels that
     * have the same rating will be organized according to the alphabet
     * order of the hotel's (property) name. In case there are no hotels
     * in the given city, this method returns an empty array.
     * @param city The city in reference.
     */
    public Hotel[] getHotelsInCityByRating(String city){

        ArrayList<Hotel> hotelsInCityArrayList = new ArrayList<Hotel>();

        for(Hotel hotel : hotelsArray){
            if(hotel.getCity().equals(city)){
                hotelsInCityArrayList.add(hotel);
            }
        }
        Collections.sort(hotelsInCityArrayList, sortByRating);

        Hotel[] sortedHotels = new Hotel[hotelsInCityArrayList.size()];

        for(int i = 0 ; i < hotelsInCityArrayList.size() ; i++){

            sortedHotels[i] = hotelsInCityArrayList.get(i);
        }
        return sortedHotels;
    }


    /**
     * Method that returns an array of hotels, sorted according to their Auclidean
     * distance from the given geographic location, in ascending order.
     * @return An array of hotels, sorted according to their Auclidean
     * distance from the given geographic location, in ascending order. Hotels that
     * are at the same distance from the given location are organized according to the
     * number of points-of-interest for which they are close to (in a decreasing order).
     * In case of illegal input, this method returns an empty array.
     * @param latitude Latitude.
     * @param longitude Longitude.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude){

        if((latitude > 90) | (latitude < -90)){
            return new Hotel[0];
        }
        if((longitude > 180) | (longitude < -180)){
            return new Hotel[0];
        }

        this.sortByProximity.setGivenLatitude(latitude);
        this.sortByProximity.setGivenLongitude(longitude);

        ArrayList<Hotel> hotelsInCityArrayList = new ArrayList<Hotel>();

        Collections.addAll(hotelsInCityArrayList, hotelsArray);
        Collections.sort(hotelsInCityArrayList, sortByProximity);

        Hotel[] sortedHotels = new Hotel[hotelsInCityArrayList.size()];

        for(int i = 0 ; i < hotelsInCityArrayList.size() ; i++){

            sortedHotels[i] = hotelsInCityArrayList.get(i);
        }
        return sortedHotels;
    }


    /**
     * Method that returns an array of hotels in the given city, sorted according to their
     * Auclidean distance from the given geographic location, in ascending order.
     * @return An array of hotels in the given city, sorted according to their
     * Auclidean distance from the given geographic location, in ascending order.
     * Hotels that are at the same distance from the given location are organized according
     * to the number of points-of-interest for which they are close to (in a decreasing
     * order). In case of illegal input, this method returns an empty array.
     * @param city The city in reference.
     * @param latitude Latitude.
     * @param longitude Longitude.
     */
    public  Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){

        if((latitude > 90) | (latitude < -90)){
            return new Hotel[0];
        }
        if((longitude > 180) | (longitude < -180)){
            return new Hotel[0];
        }

        this.sortByProximity.setGivenLatitude(latitude);
        this.sortByProximity.setGivenLongitude(longitude);

        ArrayList<Hotel> hotelsInCityArrayList = new ArrayList<Hotel>();

        for(Hotel hotel : hotelsArray){
            if(hotel.getCity().equals(city)){
                hotelsInCityArrayList.add(hotel);
            }
        }
        Collections.sort(hotelsInCityArrayList, sortByProximity);

        Hotel[] sortedHotels = new Hotel[hotelsInCityArrayList.size()];

        for(int i = 0 ; i < hotelsInCityArrayList.size() ; i++){

            sortedHotels[i] = hotelsInCityArrayList.get(i);
        }
        return sortedHotels;
    }
}

