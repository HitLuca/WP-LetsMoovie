package json.reservation.request;

/**
 * Created by marco on 17/07/15.
 */
public class SeatReservation {
    int row;
    int column;
    String ticket_type;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getTicket_type() {
        return ticket_type;
    }
}
