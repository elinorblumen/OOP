package filesprocessing;
import java.io.File;

/**
 * Class representing "writable filter".
 */
public class WritableFilter implements Filter{

    //*** Data members ***//

    private boolean bool;
    private boolean isNotSuffixGiven;

    /**
     * Constructor for WritableFilter.
     * @param value String representing a boolean value of either YES or NO,
     *              checks for files that have a writing permission if YES,
     *              or files that don't have writing permission if NO.
     * @param isNotSuffixAdded A boolean representation of the NOT suffix.
     *                         If true then the filter should return only the files that do not match the
     *                         filter criteria.
     */
    public WritableFilter(String value, boolean isNotSuffixAdded){

        this.bool = value.equals("YES");
        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Filters files according to their name. filters only the files in which "value"
     * is contained with in the file name prefix.
     * @param filesNamesArray An array with file names to be filtered.
     * @return A filtered array in which all files are writable if value equals "YES",
     * or a filtered array where all files aren't writable if givan value is "NO".
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        String[] filteredArrayTemp = new String[filesNamesArray.length];
        int counter = 0;

        for(String fileName : filesNamesArray){
            File tempFile = new File(fileName);
            if((bool && !isNotSuffixGiven) |
                    (!bool && isNotSuffixGiven)){
                if(tempFile.canWrite()){
                    filteredArrayTemp[counter] = fileName;
                    counter++;

                }
                continue;
            }
            if(!tempFile.canWrite()){
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
