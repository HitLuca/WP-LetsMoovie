package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 22/07/15.
 */
public class UserRankRequest {
    String top;

    @toSanitize(name = "top", reg = Regex.ID)
    public String getTop() {
        return top;
    }
}
