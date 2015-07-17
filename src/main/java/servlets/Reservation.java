package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by marco on 17/07/15.
 */
@WebServlet(name = "Reservation")
public class Reservation extends HttpServlet {

    //Accettiamo solo richieste in post
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //riceviamo un oggetto di tipo ReservationRequest da parsare da JSON

        //in caso affermativo rispondo con un oggetto di tipo SuccessfullReservation dove reservationCode è la stringa di ritorno della funzione reserveSeats(reservationRequest,username);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //nella init bisogna chiamare come per le mail Temporary reservation
}
