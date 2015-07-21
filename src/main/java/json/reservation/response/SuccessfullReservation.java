package json.reservation.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by marco on 17/07/15.
 */
public class SuccessfullReservation implements OperationResult {

    @Expose String reservationCode;

    public SuccessfullReservation(String reservationCode) {
        this.reservationCode = reservationCode;
    }
}
