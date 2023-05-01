package filesprocessing;

/**
 * Class that represents a "type 1 error" exception.
 */
public class TypeOneError extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeOneError(){
        super("Type 1 error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeOneError(String message){
        super(message);
    }

}
