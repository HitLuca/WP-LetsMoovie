package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

import java.util.List;

/**
 * Created by hitluca on 24/07/15.
 */
public class RoomSeatRequest {
    private String room_number;
    private List<SeatRequest> seats;

    public List<SeatRequest> getSeats() {
        return seats;
    }

    @toSanitize(name = "room_number", reg = Regex.ID)
    public String getRoom_number() {
        return room_number;
    }
}
