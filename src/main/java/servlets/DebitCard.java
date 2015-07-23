package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.debitCards.DebitCardResponse;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @api {get} /api/user/*
 * @apiName GetUser
 * @apiGroup GetUser
 * @apiSuccess {String[]} Array delle stringhe delle carte di credito associate all'utente
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode EMPTY_WRONG_FIELD: Nella url in get non è specificato un utente da cercare
 * @apiError (6) {int} errorCode USER_NOT_FOUND: L'utente è un admin o più e ha cercato dati di un utente non esistente.
 * @apiError (8) {int} errorCode NOT_AUTHORIZED: l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 * @apiError (10) {int} errorCode NOT_LOGGED_IN: l'utente non è loggato
 */
@WebServlet(name = "DebitCard", urlPatterns = "/api/debitCards/*")
public class DebitCard extends HttpServlet {

    private Gson gsonWriter;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        response.setContentType("application/json");

        OperationResult opRes = null;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gsonReader = gsonBuilder.create();

        try {
            //Check se l'utente NON è loggato (da sloggato non inserisci dati nel db)
            BadReqExeceptionThrower.checkUserLogged(request);

            json.debitCards.DebitCard debitCard = gsonReader.fromJson(request.getReader(), json.debitCards.DebitCard.class);

            String newCardId = debitCard.getNumber();
            BadReqExeceptionThrower.checkNullInput(newCardId);

            HttpSession session = request.getSession();
            List<String> presentCards = userMapper.getCreditCards(session.getAttribute("username").toString());
            if (presentCards.contains(newCardId))
                throw new BadRequestException(ErrorCode.DUPLICATE_FIELD);

            userMapper.insertCreditCard(newCardId, session.getAttribute("username").toString());

        } catch (BadRequestException e) {
            opRes = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            opRes = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        response.setContentType("application/json");

        OperationResult opRes;

        try {

            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            BadReqExeceptionThrower.checkUserLogged(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String nick = rs.getParameter();

            //Controllo che la stringa nell'url non sia vuota
            BadReqExeceptionThrower.checkNullInput(nick);
            //Check se l'utente è USER e non sta cercando sè stesso. (Prob sicurezza)
            BadReqExeceptionThrower.checkUserAuthorization(request, nick);

            List<String> numbers = userMapper.getCreditCards(nick);

            //Se sei un admin e stai cercando un utente che non esiste te lo dico
            BadReqExeceptionThrower.checkAdminUserNotFound(request, numbers);

            //Se sei un utente che cerca sè stesso (devi andare in un tempio buddhista) e se non esisti allora ci sono
            // problemi gravi nel db del server
            if (numbers.size() == 0)
                throw new BadRequestException();

            opRes = new DebitCardResponse(numbers);

        } catch (BadRequestException e) {
            opRes = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            opRes = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();

    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
    }
}

