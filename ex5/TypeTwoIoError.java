package filesprocessing;
/**
 * Class representing a "type two I/O error" exception.
 */
public class TypeTwoIoError extends TypeTwoError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeTwoIoError(){
        super("Type 2 I/O. Problem Occurred while " +
                "accessing command file.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeTwoIoError(String message){
        super(message);
    }
}
