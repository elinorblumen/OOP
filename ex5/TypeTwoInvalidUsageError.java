package filesprocessing;
/**
 * Class representing a "type two invalid usage error" exception.
 */
public class TypeTwoInvalidUsageError extends  TypeTwoError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeTwoInvalidUsageError(){
        super("Type 2 invalid usage error. Program arguments aren't valid.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeTwoInvalidUsageError(String message){
        super(message);
    }
}
