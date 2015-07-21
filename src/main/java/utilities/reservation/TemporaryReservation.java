package utilities.reservation;

import json.reservation.request.ReservationRequest;
import servlets.Reservation;

/**
 * Created by marco on 21/07/15.
 */
public class TemporaryReservation {
    ReservationCleanerThread reservationCleanerThread;

    public TemporaryReservation()
    {
        reservationCleanerThread = ReservationCleanerThreadFactory.getReservationCleanerThread();
    }

    public String addReservationRequest(ReservationRequest reservationRequest)
    {
        //Genero una nuova stringa, la ritorno e butto dentro al cleaner thread
        return null;
    }
}
