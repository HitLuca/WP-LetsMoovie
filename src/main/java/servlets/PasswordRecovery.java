package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.password_recovery.request.PasswordRecoveryRequest;
import json.password_recovery.response.SuccessfullPasswordRecovery;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.BadReqExeceptionThrower;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.PasswordRecoveryMailSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @api {post} /api/passwordRecovery
 * @apiName PasswordRecovery
 * @apiGroup PasswordRecovery
 *
 * @apiParam {String} email l'email a cui inviare il recupero password
 *
 * @apiSuccess {String} email l'indirizzo email a cui è stata inviata la mail
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {String[]} errorCode EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione
 * @apiError (7) {int} errorCode ALREADY_LOGGED: è già presente una sessione valida
 * @apiError (9) {String[]} errorCode INVALID_MAIL: parameters la mail non valida
 */
@WebServlet(name = "PasswordRecovery",  urlPatterns = "/api/passwordRecovery")
public class PasswordRecovery extends HttpServlet {
    private final String url = "/api/passwordRecovery";
    Gson gsonWriter;
    Gson gsonReader;
    PasswordRecoveryMailSender passwordRecoveryMailSender;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);


        OperationResult recoveryStatus;
        response.setContentType("application/json");
        try
        {
            //Check sulla sessione già presente e l'utente è già loggato con un username
            BadReqExeceptionThrower.checkNotUserLogged(request);

            PasswordRecoveryRequest passwordRecoveryRequest = gsonReader.fromJson(request.getReader(), PasswordRecoveryRequest.class);
            BadReqExeceptionThrower.checkRegex(passwordRecoveryRequest);

            String username = userMapper.getUsernameByEmail(passwordRecoveryRequest.getEmail());

            BadReqExeceptionThrower.checkNullInput(username);

            String recoveryMailUrl = request.getRequestURL().toString().replace(url,"");
            recoveryMailUrl+="/passwordRecovery?verificationCode=";
            if(!passwordRecoveryMailSender.sendEmail(passwordRecoveryRequest.getEmail(),username,recoveryMailUrl))
            {
                throw new BadRequestExceptionWithParameters(ErrorCode.INVALID_MAIL,"email");
            }
            recoveryStatus = new SuccessfullPasswordRecovery(passwordRecoveryRequest.getEmail());

        } catch (BadRequestException e) {
            recoveryStatus = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            recoveryStatus = new BadRequestException();
            response.setStatus(400);
        }
        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(recoveryStatus));

        sessionSql.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void init() throws ServletException {

        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        passwordRecoveryMailSender = new PasswordRecoveryMailSender(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }
}
