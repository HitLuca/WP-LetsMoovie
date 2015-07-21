package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.OperationResult;
import json.reservation.response.SuccessfullReservation;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by marco on 17/07/15.
 */
@WebServlet(name = "Reservation", urlPatterns = "/api/reservation")
public class Reservation extends HttpServlet {

    //Accettiamo solo richieste in post
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //riceviamo un oggetto di tipo ReservationRequest da parsare da JSON
        response.setContentType("application/json");

        Gson gsonWriter;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();

        OperationResult result = new SuccessfullReservation("CiaoCarlo");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(result));

        //in caso affermativo rispondo con un oggetto di tipo SuccessfullReservation dove reservationCode è la stringa di ritorno della funzione reserveSeats(reservationRequest,username);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //nella init bisogna chiamare come per le mail Temporary reservation
}
