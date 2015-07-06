package types.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidRegistrationException extends Exception{
    private List<String> invalidParameters;
    public InvalidRegistrationException(List<String> invalidParameters)
    {
        this.invalidParameters = invalidParameters;
    }
    public InvalidRegistrationException(String invalidParameter)
    {
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }
    public List<String> getInvalidParameters()
    {
        return invalidParameters;
    }

}
