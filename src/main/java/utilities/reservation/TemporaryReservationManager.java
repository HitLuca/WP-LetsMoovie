package utilities.reservation;

import json.reservation.request.ReservationRequest;
import org.apache.ibatis.session.SqlSession;
import servlets.Reservation;
import types.exceptions.BadRequestException;
import utilities.reservation.request.TemporaryReservationRequest;

import java.time.LocalTime;
import java.util.Date;

/**
 * Created by marco on 21/07/15.
 */
public class TemporaryReservationManager {
    ReservationCleanerThread reservationCleanerThread;
    private final long TEMPORARY_RESERVATION_DURATION = 1000*60*10;

    public TemporaryReservationManager()
    {
        reservationCleanerThread = ReservationCleanerThreadFactory.getReservationCleanerThread();
    }

    public String addReservationRequest(ReservationRequest reservationRequest, SqlSession session) throws BadRequestException {
        Date date = new Date();
        return reservationCleanerThread.add(new TemporaryReservationRequest(reservationRequest,date.getTime()+TEMPORARY_RESERVATION_DURATION),session);
    }

    public ReservationRequest confirmReservationRequest(String registrationCode,SqlSession session) throws BadRequestException {
        return reservationCleanerThread.confirmReservationRequest(registrationCode,session);
    }

    public ReservationRequest getReservation(String reservationCode) throws BadRequestException {
        return reservationCleanerThread.getReservation(reservationCode);
    }
}
