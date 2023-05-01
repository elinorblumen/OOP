package filesprocessing;

/**
 * Factory class for SortFile objects.
 */
public class SortFactory {


    /**
     * Method that receives a string input and returns the compatible filter object.
     * @param SortType The type of the filter.
     * @return The compatible filter.
     * @throws TypeTwoError An error of type two.
     */
    public SortFiles getSort(String SortType) throws TypeOneOrderError{

        String[] orderInfo = SortType.split("#");


        if(orderInfo[0].equals("type")){

            return new MergeSortType(orderInfo.length == 2);
        }
        if(orderInfo[0].equals("size")){

            return new MergeSortSize(orderInfo.length == 2);
        }
        if(orderInfo[0].equals("abs")){
            return new MergeSortAbs(orderInfo.length == 2);
        }
        throw new TypeOneOrderError();
    }
}
