package utilities.reservation;

import utilities.mail.request.UserEmailRequest;
import utilities.reservation.request.TemporaryReservationRequest;

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

    @Override
    public void start()
    {
        pendingReservations = new HashMap<>();
        mutex = new Semaphore(1,true);
        noRequest = new Semaphore(0,true);
    }

    public void add(String reservationCode,TemporaryReservationRequest reservation)
    {
        try {
            mutex.acquire();
            pendingReservations.put(reservationCode,reservation);
            mutex.release();
            noRequest.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try {
            while (true) {
                noRequest.acquire();
                mutex.acquire();
                while (pendingReservations.size() > 0) {
                    Date currentDate = new Date();
                    for (TemporaryReservationRequest reservationRequest : pendingReservations.values()) {
                        if (currentDate.getTime() - reservationRequest.getExpireDate() > 0) {
                            pendingReservations.remove(reservationRequest);
                        }
                    }
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
}
