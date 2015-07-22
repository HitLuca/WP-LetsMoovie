package utilities.reservation;

/**
 * Created by marco on 21/07/15.
 */
public class ReservationCleanerThreadFactory {
    private static ReservationCleanerThread reservationCleanerThread;
    public static ReservationCleanerThread getReservationCleanerThread()
    {
        if(reservationCleanerThread==null)
        {
            reservationCleanerThread = new ReservationCleanerThread();
            reservationCleanerThread.start();
        }
        return reservationCleanerThread;
    }
}
