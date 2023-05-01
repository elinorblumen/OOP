package filesprocessing;

/**
 * Class representing a "type two" error exception.
 */
public class TypeTwoError extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeTwoError(){
        super("Type 2 error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeTwoError(String message){
        super(message);
    }
}
