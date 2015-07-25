package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 24/07/15.
 */
public class RoomRequest {
    private String room_number;

    @toSanitize(name = "room_number", reg = Regex.ID)
    public String getRoom_number() {
        return room_number;
    }
}
