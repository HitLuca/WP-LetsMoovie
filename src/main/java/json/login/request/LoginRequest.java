package json.login.request;

import utilities.InputValidator.Regex;
import types.annotations.toSanitize;

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
