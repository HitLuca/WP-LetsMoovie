package utilities.reservation;

import database.datatypes.seat.Seat;
import database.mappers.NotDecidedMapper;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import utilities.reservation.request.TemporaryReservationRequest;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by marco on 17/07/15.
 */
public class ReservationCleanerThread extends Thread{
    private final long CLEANROUTINETIME = 60 * 5;
    private final int RESERVATION_CODE_SIZE = 17;
    int reservationIndex;
    private Map<String,TemporaryReservationRequest> pendingReservations;
    private Semaphore mutex;
    private Semaphore noRequest;


    public ReservationCleanerThread()
    {
        pendingReservations = new HashMap<>();
        mutex = new Semaphore(1,true);
        noRequest = new Semaphore(0,true);
        reservationIndex = 0;
        System.out.println("Start");
    }

    public String add(TemporaryReservationRequest reservation, SqlSession session) throws BadRequestException {
        String reservationCode="";
        try {
            mutex.acquire();
            SeatMapper seatMapper = session.getMapper(SeatMapper.class);

            int validSeats = 0;

            for (Seat reservedSeat : seatMapper.getShowFreeSeat(reservation.getReservationRequest().getIntIdShow()))
            {
                for (SeatReservation requestedSeat : reservation.getReservationRequest().getReservation())
                {
                    if (reservedSeat.getColumn() == requestedSeat.getIntColumn() && reservedSeat.getRow() == requestedSeat.getIntRow())
                    {
                        validSeats++;
                    }
                }
            }

            if(validSeats!=reservation.getReservationRequest().getReservation().size())
            {
                mutex.release();
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
                                mutex.release();
                                throw new BadRequestException(ErrorCode.INVALID_RESERVATION);
                            }
                        }
                    }
                }
            }

            NotDecidedMapper notDecidedMapper = session.getMapper(NotDecidedMapper.class);
            reservationCode = RandomStringUtils.randomAlphanumeric(RESERVATION_CODE_SIZE);

            while(notDecidedMapper.checkDoubleCode(reservationCode)!=null)
            {
                reservationCode = RandomStringUtils.randomAlphanumeric(RESERVATION_CODE_SIZE);
            }

            pendingReservations.put(reservationCode,reservation);
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
                mutex.release();
                throw new BadRequestException(ErrorCode.WRONG_RESERVATION_CODE);
            }

            SeatMapper seatMapper = session.getMapper(SeatMapper.class);
            ShowMapper showMapper = session.getMapper(ShowMapper.class);

            int roomNumber = showMapper.getShowData(temporaryReservationRequest.getReservationRequest().getIntIdShow()).getRoom_number();

            for(SeatReservation reservedSeat : temporaryReservationRequest.getReservationRequest().getReservation())
            {
                int seatId = seatMapper.getIdSeat(roomNumber, reservedSeat.getIntRow(), reservedSeat.getIntColumn());
                seatMapper.insertSeatReservation(temporaryReservationRequest.getReservationRequest().getIntIdShow(), seatId, "reserved");
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
        System.out.print("Entro");
        try {
            while (true) {
                System.out.println("Mi metto in attesa che qualcuno risponda");
                noRequest.acquire();
                System.out.println("Entro per cancellare i posti");
                mutex.acquire();
                while (pendingReservations.size() > 0) {
                    removeExpired();
                    System.out.println("Posti Cancellati");
                    mutex.release();
                    System.out.println("Mi metto in attesa");
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
                mutex.release();
                throw new BadRequestException(ErrorCode.WRONG_RESERVATION_CODE);
            }
            reservationRequest = temporaryReservationRequest.getReservationRequest();
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reservationRequest;
    }

    public List<SeatReservation> getTemporaryReservedSeats(int showId) {
        List<SeatReservation> temporaryReservedSeats = new ArrayList<>();
        try{
            mutex.acquire();

            for(TemporaryReservationRequest temporaryReservationRequest : pendingReservations.values())
            {
                if(temporaryReservationRequest.getReservationRequest().getId_show().equals(""+showId))
                {
                    for(SeatReservation seatReservation:temporaryReservationRequest.getReservationRequest().getReservation())
                    {
                        temporaryReservedSeats.add(seatReservation);
                    }
                }
            }

            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return temporaryReservedSeats;
    }
}
