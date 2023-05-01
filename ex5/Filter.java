package filesprocessing;

/**
 * Interface representing a filter mechanism.
 */
public interface Filter {

    public static final double ONE_KB_IN_BYTES = 1024;


    /**
     * Method that receives an array of files path names and filters the files
     * according to certain filter. returns a filtered array.
     * @param filesNamesArray An array with file names.
     * @return A filtered array with files names.
     */
    public String[] filterFiles(String[] filesNamesArray);
}
