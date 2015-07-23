package json.reservation.request;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 17/07/15.
 * non necessita di validatori
 */
public class ReservationRequest implements OperationResult {

    @Expose
    String show;
    @Expose
    List<SeatReservation> seats;

    public ReservationRequest() {
        this.seats = new ArrayList<>();
    }

    @toSanitize(name = "show", reg = Regex.INTEGER)
    public String getId_show() {
        return show;
    }

    public int getIntIdShow() {
        return Integer.valueOf(getId_show());
    }

    public List<SeatReservation> getReservation() {
        return seats;
    }

    public void setId_show(int id_show) {
        this.show = String.valueOf(id_show);
    }

    public void addSeat(String row, String col, String type) {
        seats.add(new SeatReservation(row, col, type));
    }
}
