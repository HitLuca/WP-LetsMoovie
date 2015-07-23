package json.reservation.request;

import com.google.gson.annotations.Expose;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 17/07/15.
 * non necessita di validatori
 */
public class ReservationRequest implements OperationResult {

    @Expose
    int show;
    @Expose
    List<SeatReservation> reservation;

    public ReservationRequest() {
        this.reservation = new ArrayList<>();
    }

    public int getId_show() {
        return show;
    }

    public List<SeatReservation> getReservation() {
        return reservation;
    }

    public void setId_show(int id_show) {
        this.show = id_show;
    }

    public void addSeat(int row, int col, String type) {
        reservation.add(new SeatReservation(row, col, type));
    }
}
