package json.showRoom;

import com.google.gson.annotations.Expose;

/**
 * Created by etrunon on 24/07/15.
 */
public class ParentSeat {
    @Expose
    private int row;
    @Expose
    private int column;

    public ParentSeat(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
