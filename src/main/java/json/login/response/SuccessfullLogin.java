package json.login.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 06/07/15.
 */
public class SuccessfullLogin implements OperationResult {

    @Expose private String username;

    public SuccessfullLogin(String username) {
        this.username = username;
    }
}
