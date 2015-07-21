package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import database.datatypes.user.UserPayment;
import database.mappers.UserMapper;
import json.OperationResult;
import json.reservation.response.ReservationDetail;
import json.reservation.response.SuccessfullReservation;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by marco on 17/07/15.
 */
@WebServlet(name = "Reservation", urlPatterns = "/api/reservation/*")
public class Reservation extends HttpServlet {

    //Accettiamo solo richieste in post
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //riceviamo un oggetto di tipo TemporaryReservationRequest da parsare da JSON
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
        response.setContentType("application/json");

        Gson gsonWriter;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        OperationResult opRes;

        List<UserPayment> seats = userMapper.getUserPayments("2015-06-19", "14:52:00", "M4rcOSX");
        OperationResult result = new ReservationDetail(seats, 213456);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(result));
    }

    //nella init bisogna chiamare come per le mail Temporary reservation
}
