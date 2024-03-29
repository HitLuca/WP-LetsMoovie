package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.confirm_registration.ConfirmRegistrationRequest;
import json.register.request.RegistrationRequest;
import json.register.response.SuccessfullRegistration;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.VerificationMailCodeChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @api {post} /api/confirmRegistration
 * @apiName ConfirmRegistration
 * @apiGroup Registration
 *
 * @apiParam {String} verificationCode il codice di verifica spedito via mail
 *
 * @apiSuccess {String} username l'username dell'utente appena registrato.
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (7) {int} errorCode ALREADY_LOGGED: è già presente una sessione valida con quel client
 * @apiError (11) {int} errorCode WRONG_CONFIRMATION_CODE: il codice di conferma della registrazione è errato
 */
@WebServlet(name = "ConfirmRegistration", urlPatterns = "/api/confirmRegistration")
public class ConfirmRegistration extends HttpServlet {

    Gson gsonWriter;
    Gson gsonReader;
    private VerificationMailCodeChecker verificationMailCodeChecker;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        response.setContentType("application/json");
        OperationResult registrationConfirmStatus;
        try {
            //Check sulla sessione già presente e l'utente è già loggato con un username e lo si spara fuori
            BadReqExeceptionThrower.checkUserAlreadyLogged(request);

            //Provo a parsare il Json nell'oggetto RegistrationRequest. Se exception esce dalla sevlet
            ConfirmRegistrationRequest confirmRegistrationRequest = gsonReader.fromJson(request.getReader(), ConfirmRegistrationRequest.class);

            RegistrationRequest registrationRequest = verificationMailCodeChecker.verify(confirmRegistrationRequest.getVerificationCode());
            BadReqExeceptionThrower.checkNullInput(registrationRequest);

            userMapper.insertUser(registrationRequest);
            HttpSession session = request.getSession(true);
            session.setAttribute("username",registrationRequest.getUsername());
            session.setAttribute("role",0);

            registrationConfirmStatus = new SuccessfullRegistration(registrationRequest.getUsername());

        } catch (BadRequestException e) { //
            registrationConfirmStatus = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            registrationConfirmStatus = new BadRequestException();
            response.setStatus(400);
        }
        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(registrationConfirmStatus));

        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        verificationMailCodeChecker = new VerificationMailCodeChecker(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }

}
