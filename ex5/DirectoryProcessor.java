
package filesprocessing;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DirectoryProcessor {

    //*** Data members ***//
    String sourceDirectory;
    String commandFile;
    File commandFileInFile;
    File sourceDirectoryInFile;
    String[] directoryFilesNames;
    ArrayList<String> commandFileArray;

    /**
     * Constructor for DirectoryProcessor.
     * @param sourcedir The given source directory path in either relative or absolute form.
     * @param commandfile The given command file path in either relative or absolute form.
     */
    public DirectoryProcessor(String sourcedir, String commandfile){

        this.sourceDirectory = sourcedir;
        this.commandFile = commandfile;
    }

    /**
     * A method that tries to convert the given source directory path and the command file path
     * into files.
     * @throws TypeTwoInvalidUsageError If path name argument is null;
     */
    private void pathToFile() throws TypeTwoInvalidUsageError{

        if(commandFile == null | sourceDirectory == null){
            throw new TypeTwoInvalidUsageError();
        }
        commandFileInFile = new File(commandFile);
        sourceDirectoryInFile = new File(sourceDirectory);
    }

    /**
     * Helper method that helps convert the command file into an array of strings
     * where each element in the array is a single line from the command file.
     * @throws TypeTwoError An error of type two.
     */
    private void commandFileToArray() throws TypeTwoError {

        try{
            Scanner fileReader = new Scanner(commandFileInFile);
            commandFileArray = new ArrayList<>();

            while(fileReader.hasNextLine()){
                commandFileArray.add(fileReader.nextLine());
            }
        }catch (FileNotFoundException e){
            throw new TypeTwoIoError();
        }

        int i = 0;
        //Loop that goes over the FILTER and ORDER sections and ensures they are legal.
        while ( i < commandFileArray.size() - 3){
            if(commandFileArray.get(i).equals("FILTER")){
                i = i + 2;
            }
            else{
                throw new TypeTwoError("Type 2 invalid usage error. Bad FILTER/ORDER" +
                        "sub section name.");
            }
            if(commandFileArray.get(i).equals("ORDER")){
                i++;
            }
            else{
                throw new TypeTwoError("Type 2 invalid usage error. Bad FILTER/ORDER " +
                        "sub section name.");
            }
            if(!commandFileArray.get(i).equals("FILTER")){
                i++;
            }
        }
    }

    /**
     * Method that processes the files in the given directory according to the given command file.
     * @throws TypeTwoError A Type two error.
     */
    private void directoryProcessing() throws TypeTwoError {

        pathToFile();//Turns the given paths into files objects.

        commandFileToArray();//Turns the given command file into an array, where each line of
        //the file is a single element in the array.

        setSourceDirectoryFilesNames();//Sets the source directory files names into
        //an array of strings.

        String[] filteredFilesNames = new String[0];
        FilterFactory filterFactory = new FilterFactory();
        SortFactory sortFactory = new SortFactory();

        int i = 0;

        while(i < commandFileArray.size() - 3){

            i++;
            Filter filter;

            try{
                filter = filterFactory.getFilter(commandFileArray.get(i));
            }
            catch (TypeOneError e){
                System.err.println("Warning in line " + i);
                filter = new AllFilter(commandFileArray.get(i).endsWith("NOT"));
            }

            filteredFilesNames = filter.filterFiles(directoryFilesNames);
            i++;
            SortFiles sortByOrder;
            assert filteredFilesNames != null;

            try{
                sortByOrder = sortFactory.getSort(commandFileArray.get(i + 1));
                i++;
            }
            catch (TypeOneOrderError e){
                System.err.println("Warning in line " + i );
                sortByOrder = new MergeSortAbs(commandFileArray.get(i + 1).endsWith("REVERSE"));
            }

            sortByOrder.sort(filteredFilesNames, 0, filteredFilesNames.length - 1);

            for(int j = 0 ; j < filteredFilesNames.length ; j ++){
                File tempFile = new File(filteredFilesNames[j]);
                String tempFileName = tempFile.getName();
                System.out.println(tempFileName);
            }
        }
    }

    /**
     * Helper method that sets the source directory files absolute names into an array of strings.
     */
    private void setSourceDirectoryFilesNames(){

        directoryFilesNames = sourceDirectoryInFile.list();

        for(int i = 0; i < directoryFilesNames.length ; i ++){
            String name = directoryFilesNames[i];
            directoryFilesNames[i] = sourceDirectory + "/" + name;
        }
    }

    /**
     * Main method.
     * @param args An array of length 2 where the first argument is "Source directory"
     *             and the second argument is commands file.
     */
    public static void main(String[] args) {

        DirectoryProcessor processing;
        try{
            if(!(args.length == 2)){
                System.err.println("here");
                throw new TypeTwoInvalidUsageError();
            }
            processing = new DirectoryProcessor(args[0],args[1]);
            processing.directoryProcessing();
        }
        catch (TypeTwoError e){
            System.err.println("ERROR: " + e.getMessage() + "\n");
        }
    }
}
