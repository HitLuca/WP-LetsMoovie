package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import database.datatypes.user.CompletePayments;
import database.mappers.UserMapper;
import json.OperationResult;
import json.payments.ListPastPayment;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @api {get} /api/pagamenti
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
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode EMPTY_WRONG_FIELD: l'Url di richiesta in input non ha contenuto o è imparsabile.
 * @apiError (6) {int} errorCode USER_NOT_FOUND: L'utente è un admin che ha cercato informazioni su un utente inesistente
 * @apiError (8) {int} errorCode NOT_AUTHORIZED: l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.
 * @apiError (10) {int} errorCode NOT_LOGGED_IN: l'utente non è loggato
 */
@WebServlet(name = "historyPayments", urlPatterns = "/api/historyPayments/*")
public class historyPayments extends HttpServlet {

    Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        OperationResult opRes;

        try {
            BadReqExeceptionThrower.checkUserLogged(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());

            BadReqExeceptionThrower.checkAdminUserNotFound(request, rs.getParameter());
            BadReqExeceptionThrower.checkNullInput(rs.getParameter());
            BadReqExeceptionThrower.checkUserAuthorization(request, rs.getParameter());

            List<CompletePayments> cp = userMapper.getUserUniquePayments(rs.getParameter());
            ListPastPayment lpp = new ListPastPayment(cp, userMapper, rs.getParameter());

            opRes = lpp;

        } catch (BadRequestException e) {
            opRes = e;
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
        gsonWriter = gsonBuilder.create();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
