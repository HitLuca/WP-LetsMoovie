package json.password_recovery.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by marco on 08/07/15.
 */
public class PasswordRecoveryRequest {
    private String email;

    @toSanitize(name = "email", reg = Regex.EMAIL)
    public String getEmail(){
        return email;
    }
}
