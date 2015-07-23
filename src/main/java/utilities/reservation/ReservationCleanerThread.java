package utilities.reservation;

import database.datatypes.seat.Seat;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import utilities.reservation.request.TemporaryReservationRequest;

import java.sql.SQLSyntaxErrorException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by marco on 17/07/15.
 */
public class ReservationCleanerThread extends Thread{
    private Map<String,TemporaryReservationRequest> pendingReservations;
    private Semaphore mutex;
    private Semaphore noRequest;
    private final long CLEANROUTINETIME = 60*10;
    int reservationIndex;
    private final int RESERVATION_CODE_SIZE = 20;

    @Override
    public void start()
    {
        pendingReservations = new HashMap<>();
        mutex = new Semaphore(1,true);
        noRequest = new Semaphore(0,true);
        reservationIndex = 0;
    }

    public String add(TemporaryReservationRequest reservation, SqlSession session) throws BadRequestException {
        String reservationCode="";
        try {
            mutex.acquire();
            SeatMapper seatMapper = session.getMapper(SeatMapper.class);

            int validSeats = 0;

            for (Seat reservedSeat : seatMapper.getShowFreeSeat(reservation.getReservationRequest().getId_show()))
            {
                for (SeatReservation requestedSeat : reservation.getReservationRequest().getReservation())
                {
                    if(reservedSeat.getColumn()==requestedSeat.getColumn() && reservedSeat.getRow()==requestedSeat.getRow())
                    {
                        validSeats++;
                    }
                }
            }

            if(validSeats!=reservation.getReservationRequest().getReservation().size())
            {
                throw new BadRequestException(ErrorCode.INVALID_RESERVATION);
            }

            removeExpired();

            for(TemporaryReservationRequest reservedSeats: pendingReservations.values())
            {
                if(reservation.getReservationRequest().getId_show()==reservedSeats.getReservationRequest().getId_show()) {
                    for (SeatReservation reservedSeat : reservedSeats.getReservationRequest().getReservation()) {
                        for(SeatReservation requestedSeat : reservation.getReservationRequest().getReservation())
                        {
                            if(reservedSeat.getColumn()==requestedSeat.getColumn() && reservedSeat.getRow()==requestedSeat.getRow())
                            {
                                throw new BadRequestException(ErrorCode.INVALID_RESERVATION);
                            }
                        }
                    }
                }
            }

            reservationCode = reservationIndex + RandomStringUtils.randomAlphanumeric(RESERVATION_CODE_SIZE);
            pendingReservations.put(reservationCode,reservation);
            reservationIndex++;
            mutex.release();
            noRequest.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reservationCode;
    }

    public ReservationRequest confirmReservationRequest(String reservationCode, SqlSession session) throws BadRequestException {
        ReservationRequest reservationRequest = null;
        try{
            mutex.acquire();

            TemporaryReservationRequest temporaryReservationRequest = pendingReservations.get(reservationCode);

            removeExpired();

            if(temporaryReservationRequest==null)
            {
                throw new BadRequestException(ErrorCode.WRONG_RESERVATION_CODE);
            }

            SeatMapper seatMapper = session.getMapper(SeatMapper.class);
            ShowMapper showMapper = session.getMapper(ShowMapper.class);

            int roomNumber = showMapper.getShowData(temporaryReservationRequest.getReservationRequest().getId_show()).getRoom_number();

            for(SeatReservation reservedSeat : temporaryReservationRequest.getReservationRequest().getReservation())
            {
                int seatId = seatMapper.getIdSeat(roomNumber,reservedSeat.getRow(),reservedSeat.getColumn());
                seatMapper.insertSeatReservation(temporaryReservationRequest.getReservationRequest().getId_show(),seatId,"reserved");
            }

            reservationRequest = temporaryReservationRequest.getReservationRequest();
            pendingReservations.remove(reservationCode);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return reservationRequest;
    }

    @Override
    public void run()
    {
        try {
            while (true) {
                noRequest.acquire();
                mutex.acquire();
                while (pendingReservations.size() > 0) {
                    removeExpired();
                    mutex.release();
                    sleep(CLEANROUTINETIME * 1000l);
                    mutex.acquire();
                }
                mutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void removeExpired() {
        Date currentDate = new Date();
        for (TemporaryReservationRequest reservationRequest : pendingReservations.values()) {
            if (currentDate.getTime() - reservationRequest.getExpireDate() > 0) {
                pendingReservations.remove(reservationRequest);
            }
        }

    }
/*
    public UserEmailRequest getUserEmailRequest(String verificationCode) {
        UserEmailRequest userEmailRequest = null;
        try {
            mutex.acquire();
            userEmailRequest = pendingRequests.get(verificationCode);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userEmailRequest;
    }

    public boolean checkUsername(String username) throws InterruptedException {
        mutex.acquire();
        for(UserEmailRequest userEmailRequest: pendingRequests.values())
        {
            if(userEmailRequest.getUsername().equals(username))
            {
                mutex.release();
                return false;
            }
        }
        mutex.release();
        return true;
    }

    public boolean checkEmail(String email) throws InterruptedException {
        mutex.acquire();
        for(UserEmailRequest userEmailRequest: pendingRequests.values())
        {
            if(userEmailRequest.getEmail().equals(email))
            {
                mutex.release();
                return false;
            }
        }
        mutex.release();
        return true;
    }
*/
    public void remove(String reservationCode) throws InterruptedException {
        mutex.acquire();
        pendingReservations.remove(reservationCode);
        mutex.release();
    }

    public ReservationRequest getReservation(String reservationCode) throws BadRequestException {
        ReservationRequest reservationRequest=null;
        try{
            mutex.acquire();
            removeExpired();
            TemporaryReservationRequest temporaryReservationRequest =  pendingReservations.get(reservationCode);
            if(temporaryReservationRequest==null){
                throw new BadRequestException(ErrorCode.WRONG_RESERVATION_CODE);
            }
            reservationRequest = temporaryReservationRequest.getReservationRequest();
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reservationRequest;
    }
}
