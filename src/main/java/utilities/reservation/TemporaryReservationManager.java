package utilities.reservation;

import database.datatypes.seat.Seat;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.reservation.request.TemporaryReservationRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by marco on 21/07/15.
 */
public class TemporaryReservationManager {
    private final long TEMPORARY_RESERVATION_DURATION = 1000*60*5;
    ReservationCleanerThread reservationCleanerThread;

    public TemporaryReservationManager()
    {
        reservationCleanerThread = ReservationCleanerThreadFactory.getReservationCleanerThread();
    }

    public String addReservationRequest(ReservationRequest reservationRequest, SqlSession session) throws BadRequestException {
        Date date = new Date();
        return reservationCleanerThread.add(new TemporaryReservationRequest(reservationRequest,date.getTime()+TEMPORARY_RESERVATION_DURATION),session);
    }

    public ReservationRequest confirmReservationRequest(String registrationCode,SqlSession session) throws BadRequestException {
        return reservationCleanerThread.confirmReservationRequest(registrationCode, session);
    }

    public ReservationRequest getReservation(String reservationCode) throws BadRequestException {
        return reservationCleanerThread.getReservation(reservationCode);
    }

    public List<SeatReservation> getTemporaryReservedSeats(int showId) {
        return reservationCleanerThread.getTemporaryReservedSeats(showId);
    }
}
