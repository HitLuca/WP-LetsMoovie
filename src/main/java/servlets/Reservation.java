package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.reservation.request.ReservationRequest;
import json.reservation.response.SuccessfullReservation;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;
import utilities.reservation.TemporaryReservationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * INVALID_RESERVATION
 * Created by marco on 17/07/15.
 */
@WebServlet(name = "Reservation", urlPatterns = "/api/reservation/*")
public class Reservation extends HttpServlet {

    private Gson gsonWriter;
    private Gson gsonReader;
    private TemporaryReservationManager temporaryResManager;

    /**
     * Ritorniamo un codice di prenotazione data una lista di posti. Nel caso in cui questi siano prenotabili
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        response.setContentType("application/json");

        OperationResult result;

        /*riceviamo un oggetto di tipo ReservationRequest da parsare da JSON
        chiami la TemporaryReservationManager.add(reservationRequest) che tira eventualmente eccezioni e restituisce il codice della
         registrazione temporanea
        */

        try {
            BadReqExeceptionThrower.checkUserLogged(request);

            ReservationRequest rr = gsonReader.fromJson(request.getReader(), ReservationRequest.class);

            BadReqExeceptionThrower.checkNullInput(rr);

            //Lancia ErrorCode:INVALID_RESERVATION
            result = new SuccessfullReservation(temporaryResManager.addReservationRequest(rr, sessionSql));

        } catch (BadRequestException e) {
            result = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            result = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(result));
        sessionSql.close();

        //in caso affermativo rispondo con un oggetto di tipo SuccessfullReservation dove reservationCode è la stringa di
        // ritorno della funzione reserveSeats(reservationRequest,session);
    }

    /**
     * Ritorniamo la lista dei posti prenotati dato un codice di prenotazione
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        response.setContentType("application/json");

        OperationResult result = null;

        /*
        chiamo TemporaryReservationManager tramite la funzione getReservation(string reservationCode) che ritorna un oggetto
        RegistrationRequest da spedire direttamente al client e tira un eccezione se il codice non è valido
        */
        try {

            BadReqExeceptionThrower.checkUserLogged(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String code = rs.getParameter();
            BadReqExeceptionThrower.checkNullInput(code);

            TemporaryReservationManager t = new TemporaryReservationManager();
            result = t.getReservation(code);

        } catch (BadRequestException e) {
            result = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            result = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(result));
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
        temporaryResManager = new TemporaryReservationManager();
    }
}
