import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * Class that implements ordering of hotel elements by their hotel star rating.
 */
public class SortByRating implements Comparator<Hotel> {


    /**
     * Compares by hotels star rating, if rating is equal it compares by hotels
     * names alphabet order.
     * @param o1 First hotel.
     * @param o2 Second Hotel.
     * @return Negative integer if second hotel is "bigger",
     * positive integer if first hotel is "bigger", 0 if equal.
     */
    @Override
    public int compare(Hotel o1, Hotel o2) {

        if((o1.getStarRating() - o2.getStarRating()) == 0){
            return o1.getPropertyName().compareTo(o2.getPropertyName());
        }
        return o1.getStarRating() - o2.getStarRating();
    }
}
