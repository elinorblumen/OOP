package filesprocessing;
import java.io.File;

/**
 * Class representing a Between filter.
 */
public class BetweenFilter implements Filter{

    //*** Data members ***//
    private double greaterThanNumber;
    private double smallerThanNumber;
    private boolean isNotSuffixGiven;


    /**
     * Constructor for BetweenFilter.
     * @param greaterThanNum given number to be greater than in kb size (inclusive).
     * @param smallerThanNum given number to be smaller than in kb size (inclusive).
     * @param isNotSuffixAdded A boolean representation of the NOT suffix.
     *                         If true then the filter should return only the files that do not match the
     *                         filter criteria.
     */
    public BetweenFilter(double greaterThanNum, double smallerThanNum, boolean isNotSuffixAdded){

        this.greaterThanNumber = greaterThanNum;
        this.smallerThanNumber = smallerThanNum;
        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Filters files according to their size in k-bytes, only files whose size are between the given numbers
     * (in kb) will pass the filter.
     * @param filesNamesArray An array with file names.
     * @return A filtered array in which all files are larger than or equal
     * greaterThanNumber and smaller than or equal smallerThanNumber in K-bytes.
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        String[] filteredArrayTemp = new String[filesNamesArray.length];
        int counter = 0;

        for(String fileName : filesNamesArray){
            File tempFile = new File(fileName);
            double tempFileSizeInKb = tempFile.length()/ONE_KB_IN_BYTES;
            if(isNotSuffixGiven){
                if(!(tempFileSizeInKb <= greaterThanNumber &&
                        tempFileSizeInKb >= smallerThanNumber)){
                    filteredArrayTemp[counter] = fileName;
                    counter++;
                }
                continue;
            }
            if(tempFileSizeInKb <= greaterThanNumber &&
            tempFileSizeInKb >= smallerThanNumber){
                filteredArrayTemp[counter] = fileName;
                counter++;
            }
        }

        String[] filteredArray = new String[counter];
        for(int i = 0 ; i < counter ; i++){
            filteredArray[i] = filteredArrayTemp[i];
        }
        return filteredArray;
    }
}
