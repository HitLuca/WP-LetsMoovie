package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 23/07/15.
 */
public class ShowRequest {
    private String id_show;

    @toSanitize(name = "id_show", reg = Regex.ID)
    public String getId_show() {
        return id_show;
    }
}
