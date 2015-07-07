package json;

/**
 * Created by marco on 07/07/15.
 */
public class OperationError extends OperationResult {
    private int error;
    public OperationError(int error)
    {
        this.error = error;
    }
}
