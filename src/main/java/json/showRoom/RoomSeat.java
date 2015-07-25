package json.showRoom;

import com.google.gson.annotations.Expose;

/**
 * Created by etrunon on 14/07/15.
 */
public class RoomSeat extends ParentSeat {

    @Expose
    private String status;

    public RoomSeat(int row, int column, String status) {
        super(row, column);
        this.status = status;
    }
}
