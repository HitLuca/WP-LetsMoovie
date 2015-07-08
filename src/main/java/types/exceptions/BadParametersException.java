package types.exceptions;

import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class BadParametersException extends Exception {
    private List<String> invalidParameters;
    private ErrorCode code;

    public ErrorCode getCode() {
        return code;
    }

    public BadParametersException(ErrorCode code, List<String> invalidParameters) {
        this.invalidParameters = invalidParameters;
        this.code = code;
    }

    public BadParametersException(ErrorCode code, String invalidParameter)
    {
        this.code = code;
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }
    public List<String> getInvalidParameters()
    {
        return invalidParameters;
    }

}
