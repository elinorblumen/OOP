package filesprocessing;
import java.io.File;

/**
 * Class representing the "hidden filter".
 */
public class HiddenFilter implements Filter{

    //*** Data members ***//

    private boolean bool;
    private boolean isNotSuffixGiven;

    /**
     * Constructor for HiddenFilter.
     * @param value String representing a boolean value of either YES or NO,
     *              checks for files that are hidden permission if YES,
     *              or files that aren't executable if NO.
     * @param isNotSuffixAdded A boolean representation of the NOT suffix.
     *                         If true then the filter should return only the files that do not match the
     *                         filter criteria.
     */
    public HiddenFilter(String value, boolean isNotSuffixAdded){

        this.bool = value.equals("YES");
        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Filters files according to whether they're hidden or not.
     * @param filesNamesArray An array with file names to be filtered.
     * @return A filtered array in which all files are hidden if value equals "YES",
     * or a filtered array where all files aren't hidden if given value is "NO".
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        String[] filteredArrayTemp = new String[filesNamesArray.length];
        int counter = 0;

        for(String fileName : filesNamesArray){
            File tempFile = new File(fileName);
            if((bool && !isNotSuffixGiven) |
                    (!bool && isNotSuffixGiven)){
                if(tempFile.isHidden()){
                    filteredArrayTemp[counter] = fileName;
                    counter++;
                }
                continue;
            }
            if(!tempFile.isHidden()){
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
