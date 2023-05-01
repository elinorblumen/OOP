package filesprocessing;
import java.io.File;

/**
 * Class representing a contains filter.
 */
public class ContainsFilter implements Filter {

    //*** Data members ***//

    private String nameValue;
    private boolean isNotSuffixGiven;

    /**
     * Constructor for ContainsFilter.
     * @param value The value to be checked.
     * @param isNotSuffixAdded A boolean representation of the NOT suffix.
     *                         If true then the filter should return only the files that do not match the
     *                         filter criteria.
     */
    public ContainsFilter(String value, boolean isNotSuffixAdded){

        this.nameValue = value;
        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Filters files according to their name. filters only the files in which "value"
     * is contained with in the file name.
     * @param filesNamesArray An array with file names to be filtered.
     * @return A filtered array in which all files names equal "value".
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        String[] filteredArrayTemp = new String[filesNamesArray.length];
        int counter = 0;

        for(String fileName : filesNamesArray){
            File tempFile = new File(fileName);
            if(isNotSuffixGiven){
                if(!tempFile.getName().contains(nameValue)){
                    filteredArrayTemp[counter] = fileName;
                    counter++;
                }
                continue;
            }
            if(tempFile.getName().contains(nameValue)){
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
