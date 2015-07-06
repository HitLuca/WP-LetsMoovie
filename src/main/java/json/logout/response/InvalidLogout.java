package json.logout.response;

import json.GenericOperationError;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidLogout extends GenericOperationError{
    public InvalidLogout()
    {
        super("Not Logged In");
        success = false;
    }
}
