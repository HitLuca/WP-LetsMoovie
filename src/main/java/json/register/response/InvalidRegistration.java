package json.register.response;

import json.GenericOperationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidRegistration extends GenericOperationError {
    private List<String> invalidParameters;

    public InvalidRegistration(List<String> invalidParameters) {
        super("Invalid Parameters");
        success = false;
        this.invalidParameters = invalidParameters;
    }

    public InvalidRegistration(String invalidParameter) {
        super("Invalid Parameter");
        success = false;
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }
}
