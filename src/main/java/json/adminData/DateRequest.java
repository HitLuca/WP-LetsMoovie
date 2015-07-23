package json.adminData;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;


/**
 * Created by etrunon on 22/07/15.
 */
public class DateRequest {

    private String date;

    @toSanitize(name = "date", reg = Regex.DATE)
    public String getDate() {
        return date;
    }
}
