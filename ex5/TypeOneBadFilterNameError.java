package filesprocessing;

/**
 * Class that represents a "type one error" were there's a problem with
 * the FILTER name.
 */
public class TypeOneBadFilterNameError extends TypeOneFilterError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeOneBadFilterNameError(){
        super("Type 1 filter error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeOneBadFilterNameError(String message){
        super(message);
    }
}
