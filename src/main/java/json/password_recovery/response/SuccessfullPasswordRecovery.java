package json.password_recovery.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 09/07/15.
 */
public class SuccessfullPasswordRecovery implements OperationResult{
    @Expose String email;

    public SuccessfullPasswordRecovery(String email) {
        this.email = email;
    }
}
