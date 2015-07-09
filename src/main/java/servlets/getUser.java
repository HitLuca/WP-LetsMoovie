package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.UserData;
import database.mappers.UserMapper;
import json.OperationResult;
import json.userPersonalData.request.PersonalRequest;
import json.userPersonalData.response.PersonalRespose;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Questa servlet lancia i seguenti errori con questo formato:
 * - (0)   BAD_REQUEST         con payload vuoto, lanciato quando succedono errori gravi all'interno della servlet
 * - (1)   EMPTY_REQ           Se il Json in input non ha contenuto. Non ha payload.
 * - (7)   NOT_LOGGED_IN       Se l'utente non ha nessun login effettuato. Non ha payload.
 * - (8)   NOT_AUTHORIZED      Se l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 * <p/>
 * Created by etrunon on 08/07/15.
 */

/**
 * @api {get} /api/getUser
 * @apiName GetUser
 * @apiGroup GetUser
 *
 * @apiParam {String} username l'username di cui si vogliono ottenere i dati
 *
 * @apiSuccess {String} username l'username dell'utente richiesto
 * @apiSuccess {String} name il nome dell'utente
 * @apiSuccess {String} surname il cognome dell'utente
 * @apiSuccess {String} email l'email dell'utente
 * @apiSuccess {String} phone_number il numero di telefono dell'utente
 * @apiSuccess {String} bithday la data di nascita dell'utente
 * @apiSuccess {float} residual_credit il credito residuo dell'utente
 * @apiSuccess {int} role i privilegi dell'utente
 *
 * @apiError (0) {int} errorCode lanciato quando succedono errori gravi all'interno della servlet
 *
 * @apiError (1) {int} errorCode Json in input non ha contenuto.
 *
 * @apiError (7) {int} errorCode l'utente non ha nessun login effettuato.
 *
 * @apiError (8) {int} errorCode l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 *
 * @apiError (10) {int} errorCode l'utente non è loggato
 */


@WebServlet(name = "getUser", urlPatterns = "/api/getUser")
public class getUser extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;
    private UserMapper userMapper;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        OperationResult getUserStatus = null;

        try {

            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            HttpSession session = request.getSession();

            if (session.getAttribute("username") == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }
            String usernameSession = session.getAttribute("username").toString();

            //Check sulla richiesta vuota
            PersonalRequest personalRequest = gsonReader.fromJson(request.getReader(), PersonalRequest.class);
            if (personalRequest == null) {
                throw new BadRequestException(ErrorCode.EMPTY_REQ);
            }

            List<String> invalidParameters = ModelValidator.validate(personalRequest);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty())
                throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD,invalidParameters);

            String usernameSearched = personalRequest.getUsername();

            //Se sei un utente normale puoi vedere solo i tuoi dati
            UserData user = userMapper.getUserData(personalRequest.getUsername());

            if(user == null)
            {
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
            }

            if ((int) session.getAttribute("role") == Role.USER.getValue() && !user.getUsername().equals(usernameSession)) {
                throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
            }
            getUserStatus = new PersonalRespose(userMapper.getUserData(usernameSearched));

        } catch (BadRequestException e) {
            getUserStatus = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException | IllegalAccessException | InvocationTargetException e) {
            getUserStatus = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getUserStatus));
    }

    @Override
    public void init() throws ServletException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        userMapper = sessionSql.getMapper(UserMapper.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }

}