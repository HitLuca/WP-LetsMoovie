package json.confirm_registration;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 09/07/15.
 */
public class SuccessfullRegistration implements OperationResult{
    @Expose private String username;

    public SuccessfullRegistration(String username) {
        this.username = username;
    }
}
