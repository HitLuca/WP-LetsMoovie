package json.reservation.request;

import java.util.List;

/**
 * Created by marco on 17/07/15.
 * non necessita di validatori
 */
public class ReservationRequest {
    int id_show;
    List<SeatReservation> reservation;

    public int getId_show() {
        return id_show;
    }

    public List<SeatReservation> getReservation() {
        return reservation;
    }
}
