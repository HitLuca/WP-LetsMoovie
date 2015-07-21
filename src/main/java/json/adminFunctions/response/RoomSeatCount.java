package json.adminFunctions.response;

import com.google.gson.annotations.Expose;

/**
 * Created by hitluca on 21/07/15.
 */
public class RoomSeatCount {

    @Expose
    private int seat_row;
    @Expose
    private int seat_column;
    @Expose
    private int count;

    public RoomSeatCount(int seat_row, int seat_column, int count) {
        this.seat_row = seat_row;
        this.seat_column = seat_column;
        this.count = count;
    }
}
