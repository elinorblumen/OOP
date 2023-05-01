package filesprocessing;
/**
 * Class that represents a "type one error" were there's a problem with
 * the Order Section.
 */
public class TypeOneOrderError extends TypeOneError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeOneOrderError(){
        super("Type 1 order error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeOneOrderError(String message){
        super(message);
    }
}
