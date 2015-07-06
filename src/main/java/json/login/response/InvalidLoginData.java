package json.login.response;

import json.GenericOperationError;

/**
 * Created by marco on 07/07/15.
 */
public class InvalidLoginData extends GenericOperationError {
    public InvalidLoginData()
    {
        super("Invalid Data");
    }
}
