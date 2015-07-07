package json.login.response;

import json.OperationResult;

/**
 * Created by marco on 06/07/15.
 */
public class SuccessfullLogin extends OperationResult {
    private String username;

    public SuccessfullLogin(String username) {
        this.username = username;
    }
}
