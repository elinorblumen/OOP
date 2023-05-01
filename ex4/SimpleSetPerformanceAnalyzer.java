import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * A class that analyze the time performance of different
 * data structures.
 */


public class SimpleSetPerformanceAnalyzer {


    // * Data members * //


    private static SimpleSet[] dataStructuresArray;

    private static String[] setsNames;

    private static final int NUMBER_OF_CHECKUPS = 70000;

    private static final int FROM_NANO_TO_MILLI = 1000000;

    private static String[] wordArrayData1 = Ex4Utils.file2array("data1.txt");
    private static String[] wordArrayData2 = Ex4Utils.file2array("data2.txt");

    private static String[] containStringArray1 = new String[]{"hi",
            "-13170890158"};
    // Strings to be checked in all data structures after
    // they've been initialized with data1.txt.

    private static String[] containsStringArray2 = new String[]{"23", "hi"};
    // Strings to be checked in all data structures after
    // they've been initialized with data2.txt.


    /**
     * Helper method for main method. Receives an array of strings representing
     * the requested data structures and updates the static data member
     * dataStructuresArray into holding those data structures.
     *
     * @param stringArray An array of strings representing the requested
     *                    data structures.
     */
    private static void stringToFacadeCollection(String[] stringArray) {

        dataStructuresArray = new SimpleSet[stringArray.length];

        setsNames = stringArray;

        for (int index = 0; index < stringArray.length; index++) {

            if (stringArray[index].equals("OpenHashSet")) {

                OpenHashSet newOpenHashSet = new OpenHashSet();
                dataStructuresArray[index] = newOpenHashSet;
                continue;
            }
            if (stringArray[index].equals("ClosedHashSet")) {

                ClosedHashSet newClosedHashSet = new ClosedHashSet();
                dataStructuresArray[index] = newClosedHashSet;
                continue;
            }
            if (stringArray[index].equals("TreeSet")) {

                TreeSet<String> newTreeSet = new TreeSet<>();
                CollectionFacadeSet facadeTreeSet = new CollectionFacadeSet(newTreeSet);
                dataStructuresArray[index] = facadeTreeSet;
                continue;
            }
            if (stringArray[index].equals("LinkedList")) {

                LinkedList<String> newLinkedList = new LinkedList<>();
                CollectionFacadeSet facadeLinkedList = new CollectionFacadeSet(newLinkedList);
                dataStructuresArray[index] = facadeLinkedList;
                continue;
            }
            if (stringArray[index].equals("HashSet")) {

                HashSet<String> newHashSet = new HashSet<>();
                CollectionFacadeSet facadeHashSet = new CollectionFacadeSet(newHashSet);
                dataStructuresArray[index] = facadeHashSet;
                continue;
            }
            return;
        }
    }

    /**
     * Helper Method for main method.
     * Adds each word in data1.txt file to the given
     * data structure and measures how much time it took.
     *
     * @param set The given data structure.
     * @return Time it took in nanoseconds.
     */
    private static long data1Adding(SimpleSet set) {

        long timeBefore = System.nanoTime();
        for (String word : wordArrayData1) {
            set.add(word);
        }
        long difference = System.nanoTime() - timeBefore;
        return (difference / FROM_NANO_TO_MILLI);
    }

    /**
     * Helper Method for main method.
     * Adds each word in data2.txt file to the given
     * data structure and measures how much time it took.
     *
     * @param set The given data structure.
     * @return Time it took in nanoseconds.
     */
    private static long data2Adding(SimpleSet set) {

        long timeBefore = System.nanoTime();
        for (String word : wordArrayData2) {
            set.add(word);
        }
        long difference = (System.nanoTime() - timeBefore);
        return (difference / FROM_NANO_TO_MILLI);
    }

    /**
     * Helper method for main. performs "contains" with the
     * given string on the given data structure for 70000 times.
     * And measures the time.
     *
     * @param set    The given set.
     * @param string The given string.
     * @return The time it took in nanoseconds.
     */
    private static long containsString(SimpleSet set, String string) {

        long timeBefore = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_CHECKUPS; i++) {
            set.contains(string);
        }
        long difference = (System.nanoTime() - timeBefore);
        return difference / NUMBER_OF_CHECKUPS;
    }

    /**
     * Helper method for main. performs "contains" with the
     * given string on the given data structure for 70000 times.
     *
     * @param set    The given set.
     * @param string The given string.
     */
    private static void containsStringWarmUp(SimpleSet set, String string) {

        for (int i = 0; i < NUMBER_OF_CHECKUPS; i++) {
            set.contains(string);
        }
    }


    /**
     * Helper method for main method.
     * Does all the check ups and prints how much time each
     * action took.
     */
    private static void allCheckUps() {

        for (int i = 0; i < dataStructuresArray.length; i++) {
            System.out.println(i);

            SimpleSet set = dataStructuresArray[i];
            String setName = setsNames[i];

            long difference = data1Adding(set);

            System.out.println("Data structure of type " + setName +
                    " took " + difference + " milliseconds to add data1.txt");

            for (String word : containStringArray1) {

                if (!setsNames[i].equals("LinkedList")) {
                    containsStringWarmUp(set, word);
                }
                long containsDifference = containsString(set, word);

                System.out.println("Data structure of type " + setName +
                        " took " + containsDifference +
                        " nanoseconds to check if it contains "
                        + word + ". Initiated with data1.");
            }
        }

        stringToFacadeCollection(setsNames);

        for (int i = 0; i < dataStructuresArray.length; i++) {
            System.out.println(i);


            SimpleSet set = dataStructuresArray[i];
            String setName = setsNames[i];

            stringToFacadeCollection(setsNames);

            long difference2 = data2Adding(set);

            System.out.println("Data structure of type " + setName +
                    " took " + difference2 + " milliseconds to add data2.txt");

            for (String word : containsStringArray2) {

                if (!setsNames[i].equals("linkedList")) {
                    containsStringWarmUp(set, word);
                }
                long containsDifference = containsString(set, word);

                System.out.println("Data structure of type " + setName +
                        " took " + containsDifference +
                        " nanoseconds to check if it contains "
                        + word + ".Initiated with data2.");
            }
        }
    }


    public static void main(String[] args) {


        String[] fullArray = new String[]{"OpenHashSet", "ClosedHashSet", "TreeSet", "HashSet", "LinkedList"};

        stringToFacadeCollection(fullArray);

        allCheckUps();
    }

}


