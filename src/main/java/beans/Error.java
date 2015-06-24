package beans;

import types.ErrorType;

/**
 * Created by etrunon on 24/06/15.
 */
public class Error {
    private ErrorType errorType;

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
