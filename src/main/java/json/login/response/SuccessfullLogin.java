package json.login.response;

import json.GenericSuccessfullOperation;

/**
 * Created by marco on 06/07/15.
 */
public class SuccessfullLogin extends GenericSuccessfullOperation {
    private String username;

    public SuccessfullLogin(String username) {
        this.username = username;
    }
}
