import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JustForFun {


    public static void main(String[] args) {


        Hotel[] hotelsArray = HotelDataset.getHotels("hotels_tst1.txt");
        for (int i = 0 ; i < hotelsArray.length ; i ++){
            if(hotelsArray[i].getStarRating() ==4){
                System.out.println(i);
                System.out.println(hotelsArray[i].getStarRating());
                System.out.println(hotelsArray[i].getPropertyName());
            }
        }
        Hotel[] emptyArray = new Hotel[0];
        Hotel[] secondEmpty = new Hotel[0];
        System.out.println("here");
        for(Hotel hotel : emptyArray){
            System.out.println(hotel.toString());
        }
    }
}
