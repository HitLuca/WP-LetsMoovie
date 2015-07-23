package json.reservation;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;
import json.reservation.request.SeatReservation;

/**
 * Created by etrunon on 22/07/15.
 */
public class ReservedSeat {

    @Expose
    private String ticket_type;
    @Expose
    private int s_row;
    @Expose
    private int s_column;

    public ReservedSeat(UserPayment u) {
        this.ticket_type = u.getTicket_type();
        this.s_row = u.getRow();
        this.s_column = u.getColumn();
    }

    public ReservedSeat(SeatReservation u) {
        this.ticket_type = u.getTicket_type();
        this.s_row = Integer.valueOf(u.getRow());
        this.s_column = Integer.valueOf(u.getColumn());
    }
}
