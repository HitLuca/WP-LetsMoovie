package json.reservation.response;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;
import json.OperationResult;
import json.reservation.ReservedSeat;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class ReservationDetail implements OperationResult {

    @Expose
    int id_show;
    @Expose
    private List<ReservedSeat> seatList;

    public ReservationDetail() {
        this.seatList = new ArrayList<>();
    }

    public ReservationDetail(ReservationRequest r) {
        id_show = r.getId_show();

        this.seatList = new ArrayList<>();
        addSeat(r.getReservation());
    }

    public void addSeat(List<SeatReservation> uList) {
        for (SeatReservation u : uList) {
            seatList.add(new ReservedSeat(u));
        }
    }
}
