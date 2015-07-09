package json.set_new_password;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by marco on 09/07/15.
 */
public class SetNewPasswordRequest {
    String password;
    String code;

    @toSanitize(name = "password", reg = Regex.PASSWORD)
    public String getPassword()
    {
        return password;
    }

    public String getCode() {
        return code;
    }
}
