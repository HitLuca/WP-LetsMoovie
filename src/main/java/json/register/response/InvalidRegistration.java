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
        super(2); //TODO usare il codice specifico
        this.invalidParameters = invalidParameters;
    }

    public InvalidRegistration(String invalidParameter) {
        super(2); //TODO usare il codice specifico
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }
}
