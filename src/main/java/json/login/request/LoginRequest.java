package json.login.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by marco on 06/07/15.
 */
public class LoginRequest {
    private String username;
    private String password;

    @toSanitize (name = "username", reg = Regex.USERNAME)
    public String getUsername() {
        return username;
    }

    @toSanitize(name = "password", reg = Regex.PASSWORD)
    public String getPassword() {
        return password;
    }
}
