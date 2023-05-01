package filesprocessing;


/**
 * Class representing "all filter".
 */
public class AllFilter implements Filter{


    //*** Data members ***//

    private boolean isNotSuffixGiven;


    /**
     * Constructor for AllFilter.
     * @param isNotSuffixAdded A boolean representation of the NOT suffix.
     *                         If true then the filter should return only the files that do not match the
     *                         filter criteria.
     */
    public AllFilter(boolean isNotSuffixAdded){

        this.isNotSuffixGiven = isNotSuffixAdded;
    }

    /**
     * Method that returns all the files in the given files array.
     * @param filesNamesArray An array with file names.
     * @return An array of files names that contains all the files in the given array.
     */
    @Override
    public String[] filterFiles(String[] filesNamesArray) {

        if(isNotSuffixGiven){
            return new String[]{};
        }
        return filesNamesArray;
    }
}
