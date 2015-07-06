package json.register.response;

import json.GenericSuccessfullOperation;

/**
 * Created by marco on 07/07/15.
 */
public class SuccessfullRegistration extends GenericSuccessfullOperation {
    String email;
    public SuccessfullRegistration(String email)
    {
        this.email = email;
    }
}
