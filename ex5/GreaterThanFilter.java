package filesprocessing;
import java.io.File;

/**
 * Class representing a greater than filter.
 */
public class GreaterThanFilter implements Filter{

    //*** Data members ***//
    private double greaterThanNumber;
    private boolean isNotSuffixGiven;

    /**
     * Constructor for GreaterThanFilter.
     * @param greaterThanNum Non negative double representing the number that the file
     *                       size should be strictly greater than in K-bytes.
     @param isNotSuffixAdded A boolean representation of the NOT suffix.
      *                      If true then the filter should return only the files that do not match the
      *                      filter criteria.
      */
    public GreaterThanFilter(double greaterThanNum, boolean isNotSuffixAdded){

        this.greaterThanNumber = greaterThanNum;
        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Filters files according to their size in k-bytes in comparison with the given
     * greaterThanNumber number.
     * @param filesNamesArray An array with file names.
     * @return A filtered array in which all files are strictly larger than
     * greaterThanNumber in K-bytes.
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        String[] filteredArrayTemp = new String[filesNamesArray.length];
        int counter = 0;

        for(String fileName : filesNamesArray){
            File tempFile = new File(fileName);
            if(isNotSuffixGiven){
                if(!(tempFile.length()/ONE_KB_IN_BYTES < greaterThanNumber)){
                    filteredArrayTemp[counter] = fileName;
                    counter++;
                }
                continue;
            }
            if(tempFile.length()/ONE_KB_IN_BYTES > greaterThanNumber){
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
