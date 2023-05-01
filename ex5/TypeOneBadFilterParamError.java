package filesprocessing;
/**
 * Class that represents a "type one error" were there's a problem with
 * the FILTER parameters.
 */
public class TypeOneBadFilterParamError extends TypeOneFilterError{

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TypeOneBadFilterParamError(){
        super("Type 1 filter param error.");
    }

    /**
     * Message constructor.
     * @param message A message to giv information as to the exception.
     */
    public TypeOneBadFilterParamError(String message){
        super(message);
    }
}
