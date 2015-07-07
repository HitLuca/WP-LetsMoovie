package json.register.response;

import json.OperationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidRegistration extends OperationError {
    private List<String> invalidParameters;

    public InvalidRegistration(List<String> invalidParameters) {
        super("Invalid Parameters");
        this.invalidParameters = invalidParameters;
    }

    public InvalidRegistration(String invalidParameter) {
        super("Invalid Parameter");
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }
}
