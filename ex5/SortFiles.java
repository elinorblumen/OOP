package filesprocessing;

/**
 * Interface for sortFiles object.
 */
public interface SortFiles {

    /**
     * Method for sorting given array with file names.
     * @param array Array with files names.
     * @param l Left index.
     * @param r Right index.
     */
    public void sort(String[] array, int l, int r);
}
