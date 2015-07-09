package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.userPersonalData.request.PersonalRequest;
import json.userPersonalData.response.PersonalRespose;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Questa servlet lancia i seguenti errori con questo formato:
 * - (0)   BAD_REQUEST         con payload vuoto, lanciato quando succedono errori gravi all'interno della servlet
 * - (1)   EMPTY_REQ           Se il Json in input non ha contenuto. Non ha payload.
 * - (7)   NOT_LOGGED_IN       Se l'utente non ha nessun login effettuato. Non ha payload.
 * - (8)   NOT_AUTHORIZED      Se l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 * <p/>
 * Created by etrunon on 08/07/15.
 */
@WebServlet(name = "getUser", urlPatterns = "/api/getUser")
public class getUser extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OperationResult getUserStatus = null;

        try {

            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            HttpSession session = request.getSession(false);
            if (session.getAttribute("username") == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }
            String usernameSession = session.getAttribute("username").toString();

            //Check sulla richiesta vuota
            PersonalRequest personalRequest = gsonReader.fromJson(request.getReader(), PersonalRequest.class);
            if (personalRequest == null) {
                throw new BadRequestException(ErrorCode.EMPTY_REQ);
            }
            String usernameSearched = personalRequest.getUsername();

            //Se sei un utente normale puoi vedere solo i tuoi dati
            if (session.getAttribute("role") == Role.USER && !(userMapper.getUserData(personalRequest.getUsername())).equals(usernameSession)) {
                throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
            }
            getUserStatus = new PersonalRespose(userMapper.getUserData(usernameSearched));

        } catch (BadRequestException e) {
            getUserStatus = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
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
