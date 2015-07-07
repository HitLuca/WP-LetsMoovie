package json.register.response;

import json.OperationResult;

/**
 * Created by marco on 07/07/15.
 */
public class SuccessfullRegistration extends OperationResult {
    String email;
    public SuccessfullRegistration(String email)
    {
        this.email = email;
    }
}
