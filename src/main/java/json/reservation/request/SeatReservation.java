package json.reservation.request;

import com.google.gson.annotations.Expose;

/**
 * Created by marco on 17/07/15.
 */
public class SeatReservation {
    @Expose
    int row;
    @Expose
    int column;
    @Expose
    String ticket_type;

    public SeatReservation(int row, int col, String type) {
        this.row = row;
        this.column = col;
        this.ticket_type = type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }
}
