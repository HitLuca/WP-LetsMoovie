package json;

/**
 * Created by marco on 07/07/15.
 */
public class OperationError extends OperationResult {
    private int errorCode;
    public OperationError(int errorCode)
    {
        this.errorCode = errorCode;
    }
}
