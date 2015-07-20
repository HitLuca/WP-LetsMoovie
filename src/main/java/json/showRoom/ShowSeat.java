package json.showRoom;

import com.google.gson.annotations.Expose;
import types.enums.SeatStatus;

/**
 * Created by etrunon on 14/07/15.
 */
public class ShowSeat {

    @Expose
    private int row;
    @Expose
    private int column;
    @Expose
    private SeatStatus status;

    public ShowSeat(int row, int column, SeatStatus status) {
        this.row = row;
        this.column = column;
        this.status = status;
    }
}
