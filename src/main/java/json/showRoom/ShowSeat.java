package json.showRoom;

import com.google.gson.annotations.Expose;
import types.enums.SeatStatus;

/**
 * Created by etrunon on 14/07/15.
 */
public class ShowSeat {

    @Expose
    private int seat_row;
    @Expose
    private int seat_column;
    @Expose
    private SeatStatus status;

    public ShowSeat(int seat_row, int seat_column, SeatStatus status) {
        this.seat_row = seat_row;
        this.seat_column = seat_column;
        this.status = status;
    }
}
