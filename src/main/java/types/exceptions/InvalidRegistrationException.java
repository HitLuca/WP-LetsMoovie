package types.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidRegistrationException extends Exception{
    private List<String> invalidParameters;

    public InvalidRegistrationException()
    {
        invalidParameters = new ArrayList<String>();
    }

    public void addInvalidParameter(String parameter)
    {
        invalidParameters.add(parameter);
    }

    public List<String> getInvalidParameters()
    {
        return invalidParameters;
    }
}
