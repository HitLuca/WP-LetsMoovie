package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 21/07/15.
 */
public class RankedRoomRequest {
    private String room_number;
    private String top;

    @toSanitize(name = "room_number", reg = Regex.ID)
    public String getRoom_number() {
        return room_number;
    }

    @toSanitize(name = "top", reg = Regex.ID)
    public String getTop() {
        return top;
    }
}
