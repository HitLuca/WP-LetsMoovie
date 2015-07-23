package json.reservation;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;
import database.mappers.NotDecidedMapper;
import json.reservation.request.SeatReservation;

/**
 * Created by etrunon on 22/07/15.
 */
public class ReservedSeatResponse {

    @Expose
    private String ticket_type;
    @Expose
    private int s_row;
    @Expose
    private int s_column;
    @Expose
    private float price;

    public float getPrice() {
        return price;
    }

    public ReservedSeatResponse(SeatReservation u, NotDecidedMapper notDecidedMapper) {
        this.ticket_type = u.getTicket_type();
        this.s_row = Integer.valueOf(u.getRow());
        this.s_column = Integer.valueOf(u.getColumn());

        this.price = notDecidedMapper.getPrice(ticket_type);

    }
}
