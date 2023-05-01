package filesprocessing;
import java.io.File;

/**
 * Class representing a "size" type sort object.
 *  * Sorts file names by their size.
 */
public class MergeSortSize implements SortFiles{

    //*** Data members ***//

    private final boolean isReverseSuffix;


    /**
     * Constructor for MergeSortSize.
     * @param reverseSuffix A boolean value that indicates whether the reverse suffix
     *                      was added.
     */
    public MergeSortSize(boolean reverseSuffix){

        this.isReverseSuffix = reverseSuffix;
    }

    /**
     * Method that does the actual sorting of a given array by the merging process.
     * @param array The array with the file names to be sorted.
     * @param l The left index of the array.
     * @param m The middle index of the array.
     * @param r The right index of the array.
     */
    private void merge(String[] array, int l, int m, int r){

        int n1 = m - l + 1;
        int n2 = r - m;

        String[] left = new String[n1];
        String[] right = new String[n2];

        for (int i = 0 ; i < n1 ; i++){
            left[i] = array[l+i];
        }
        for(int j = 0 ; j < n2 ; j++){
            right[j] = array[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while(i < n1 && j < n2){
            File tempFileLeft = new File(left[i]);
            File tempFileRight = new File(right[j]);
            String fileLeftAbsoluteName  = tempFileLeft.getAbsolutePath();
            String fileRightAbsoluteName = tempFileRight.getAbsolutePath();

            if(isReverseSuffix){
                if(tempFileLeft.length() > tempFileRight.length()){
                    array[k] = left[i];
                    i++;
                }
                else if(tempFileLeft.length() < tempFileRight.length()){
                    array[k] = right[j];
                    j++;
                }
                else{
                    if(fileLeftAbsoluteName.compareTo(fileRightAbsoluteName) >= 0){
                        array[k] = left[i];
                        i++;
                    }
                    else {
                        array[k] = right[j];
                        j++;
                    }
                }
                k++;
                continue;
            }
            if(tempFileLeft.length() < tempFileRight.length()){
                array[k] = left[i];
                i++;
            }
            else if(tempFileLeft.length() > tempFileRight.length()){
                array[k] = right[j];
                j++;
            }
            else{
                if(fileLeftAbsoluteName.compareTo(fileRightAbsoluteName) <= 0){
                    array[k] = left[i];
                    i++;
                }
                else {
                    array[k] = right[j];
                    j++;
                }
            }
            k++;
        }
        while (i < n1){
            array[k] = left[i];
            i++;
            k++;
        }
        while (j < n2){
            array[k] = right[j];
            j++;
            k++;
        }
    }

    /**
     * Recursive method for sorting file names.
     * @param array The given array with file names to be sorted.
     * @param l The left index.
     * @param r The right index.
     */
    @Override
    public void sort(String[] array, int l, int r){

        if(l < r){
            int m = (l+ r) / 2;

            sort(array, l, m);
            sort(array, m + 1, r);

            merge(array, l, m, r);
        }
    }
}
