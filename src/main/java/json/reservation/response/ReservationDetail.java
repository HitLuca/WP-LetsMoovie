package json.reservation.response;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;
import json.OperationResult;

import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class ReservationDetail implements OperationResult {

    @Expose
    List<UserPayment> seats;
    @Expose
    int total;

    public ReservationDetail(List<UserPayment> seats, int total) {
        this.seats = seats;
        this.total = total;
    }
}
