package json.reservation.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 17/07/15.
 */
public class SuccessfullReservation implements OperationResult {
    @Expose String reservationCode;
    @Expose double totalPrice;

    public SuccessfullReservation(String reservationCode, double totalPrice) {
        this.reservationCode = reservationCode;
        this.totalPrice = totalPrice;
    }
}
