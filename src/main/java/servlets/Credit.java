package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.userPersonalData.UserCreditResponse;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by etrunon on 21/07/15.
 */
@WebServlet(name = "Credit", urlPatterns = "/api/credit/*")
public class Credit extends HttpServlet {
    private Gson gsonWriter;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        response.setContentType("application/json");

        OperationResult opRes;

        try {
            //Check se l'utente è loggato
            BadReqExeceptionThrower.checkUserLogged(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            //Check se il parametro inviato era vuoto
            String nick = rs.getParameter();
            BadReqExeceptionThrower.checkNullInput(nick);

            //Check: Se l'utente è un admin E il ricercato non esiste allora lo comunico
            BadReqExeceptionThrower.checkAdminUserNotFound(request, nick);
            //Check: se l'utente è USER e NON sta cercando sè stesso lo blocco
            BadReqExeceptionThrower.checkUserAuthorization(request, nick);

            opRes = new UserCreditResponse(userMapper.getUserData(nick).getResidual_credit());

        } catch (BadRequestException e) {
            opRes = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            opRes = new BadRequestException();
            response.setStatus(400);
        }


        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }
}
