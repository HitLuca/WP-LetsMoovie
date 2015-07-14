package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.DetailedPayment;
import database.mappers.FilmMapper;
import database.mappers.UserMapper;
import json.OperationResult;
import json.payments.ListPaymentSuccess;
import json.payments.Payments;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @api {get} /api/pagamenti/*
 * @apiName pagamenti
 * @apiGroup pagamenti
 * @apiSuccess {String} username l'username dell'utente richiesto
 * @apiSuccess {String} name il nome dell'utente
 * @apiSuccess {String} surname il cognome dell'utente
 * @apiSuccess {String} email l'email dell'utente
 * @apiSuccess {String} phone_number il numero di telefono dell'utente
 * @apiSuccess {String} bithday la data di nascita dell'utente
 * @apiSuccess {float} residual_credit il credito residuo dell'utente
 * @apiSuccess {int} role i privilegi dell'utente
 * @apiError (0) {int} errorCode lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode l'Url di richiesta in input non ha contenuto o è imparsabile.
 * @apiError (7) {int} errorCode l'utente non ha nessun login effettuato.
 * @apiError (8) {int} errorCode l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 * @apiError (10) {int} errorCode l'utente non è loggato
 */
@WebServlet(name = "Pagamenti", urlPatterns = "/api/pagamenti/*")
public class Pagamenti extends HttpServlet {

    Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        response.setContentType("application/json");
        OperationResult getUserStatus = null;

        try {

            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            HttpSession session = request.getSession();

            if (session.getAttribute("username") == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }
            String usernameSession = session.getAttribute("username").toString();

            //String matcher che preleva il nome utente da cercare dall'url e lancia Err.2 in caso sia nullo o mal formattato
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());

            String usernameSearched = rs.getParameter();

            //Se sei un utente normale puoi vedere solo i tuoi dati
            List<DetailedPayment> payments = userMapper.getUserPayments(usernameSearched);

            //Se sei un admin e stai cercando un utente che non esiste te lo dico
            if (payments == null && (int) session.getAttribute("role") != Role.USER.getValue()) {
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
            }

            //Se sei un utente e stai cercando un utente diverso da te ti blocco
            if ((int) session.getAttribute("role") == Role.USER.getValue() && !usernameSearched.equals(usernameSession)) {
                throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
            }

            //Se sei un utente che cerca sè stesso (devi andare in un tempio buddhista) e se non esisti allora ci sono
            // problemi gravi nel db del server
            if (payments == null) {
                throw new BadRequestException();
            }

            List<Payments> responses = new ArrayList<>();

            for (DetailedPayment d : payments) {
                String title = filmMapper.getFilmData(d.getId_show()).getFilm_title();
                Payments p = new Payments(d, title);
                responses.add(p);
            }

            getUserStatus = new ListPaymentSuccess(responses);

        } catch (BadRequestException e) {
            getUserStatus = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            getUserStatus = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getUserStatus));
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
