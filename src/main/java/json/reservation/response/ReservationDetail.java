package json.reservation.response;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;
import database.mappers.NotDecidedMapper;
import json.OperationResult;
import json.reservation.ReservedSeat;
import json.reservation.ReservedSeatResponse;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class ReservationDetail implements OperationResult {

    @Expose
    private int id_show;
    @Expose
    private float totalPrice;
    @Expose
    private List<ReservedSeatResponse> seatList;

    public ReservationDetail() {
        this.seatList = new ArrayList<>();
    }

    public ReservationDetail(ReservationRequest r, NotDecidedMapper notDecidedMapper) {

        id_show = r.getIntIdShow();

        this.seatList = new ArrayList<>();
        addSeat(r.getReservation(), notDecidedMapper);

        float total = 0;
        for (ReservedSeatResponse reservedSeat : seatList) {
            total += reservedSeat.getPrice();
        }
        totalPrice = total;
    }

    private void addSeat(List<SeatReservation> uList, NotDecidedMapper notDecidedMapper) {
        for (SeatReservation u : uList) {
            seatList.add(new ReservedSeatResponse(u, notDecidedMapper));
        }
    }
}
