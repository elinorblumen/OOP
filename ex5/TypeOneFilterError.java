package filesprocessing;
/**
 * Class that represents a "type one error" were there's a problem with
 * the FILTER Section.
 */
public class TypeOneFilterError extends TypeOneError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeOneFilterError(){
        super("Type 1 filter error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeOneFilterError(String message){
        super(message);
    }
}
