package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 21/07/15.
 */
public class ShowRequest {
    private String show_date;
    private String show_time;
    private String room_number;


    public String getShow_date() {
        return show_date;
    }

    public String getShow_time() {
        return show_time;
    }

    @toSanitize(name = "room_number", reg = Regex.ID)
    public String getRoom_number() {
        return room_number;
    }
}
