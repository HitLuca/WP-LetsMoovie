package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 21/07/15.
 */
public class RankedRoomRequest {
    private int room_number;
    private int top;

    @toSanitize(name = "room_number", reg = Regex.ID)
    public int getRoom_number() {
        return room_number;
    }

    @toSanitize(name = "top", reg = Regex.ID)
    public int getTop() {
        return top;
    }
}
