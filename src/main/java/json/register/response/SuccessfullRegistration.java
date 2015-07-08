package json.register.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 07/07/15.
 */
public class SuccessfullRegistration implements OperationResult {
    @Expose String email;
    public SuccessfullRegistration(String email)
    {
        this.email = email;
    }
}
