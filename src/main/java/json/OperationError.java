package json;

/**
 * Created by marco on 07/07/15.
 */
public class OperationError extends OperationResult {
    private String error;
    public OperationError(String error)
    {
        this.error = error;
    }
}
