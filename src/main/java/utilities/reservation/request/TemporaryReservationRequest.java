package utilities.reservation.request;

import json.reservation.request.ReservationRequest;

/**
 * Created by marco on 21/07/15.
 */
public class TemporaryReservationRequest {
    private ReservationRequest reservationRequest;
    private long expireDate;

    public TemporaryReservationRequest(ReservationRequest reservationRequest, long expireDate) {
        this.reservationRequest = reservationRequest;
        this.expireDate = expireDate;
    }

    public ReservationRequest getReservationRequest() {
        return reservationRequest;
    }

    public long getExpireDate() {
        return expireDate;
    }
}
