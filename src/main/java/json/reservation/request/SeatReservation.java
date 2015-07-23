package json.reservation.request;

import com.google.gson.annotations.Expose;

/**
 * Created by marco on 17/07/15.
 */
public class SeatReservation {
    @Expose
    String type;
    @Expose
    int column;
    @Expose
    int row;

    public SeatReservation(int row, int col, String type) {
        this.row = row;
        this.column = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTicket_type() {
        return type;
    }

    public void setTicket_type(String type) {
        this.type = type;
    }
}
