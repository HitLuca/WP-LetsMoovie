package json;

/**
 * Created by marco on 07/07/15.
 */
public class GenericOperationError extends OperationStatus {
    private String error;
    public GenericOperationError(String error)
    {
        this.error = error;
        success = false;
    }
}
