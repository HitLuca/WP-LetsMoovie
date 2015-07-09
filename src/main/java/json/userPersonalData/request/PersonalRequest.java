package json.userPersonalData.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by etrunon on 08/07/15.
 */
public class PersonalRequest {

    private String username;

    @toSanitize(name = "username", reg = Regex.USERNAME)
    public String getUsername() {
        return username;
    }
}
