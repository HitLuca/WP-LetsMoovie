package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.set_new_password.SetNewPasswordRequest;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.PasswordRecoveryCodeCheck;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @api {post} /api/setNewPassword
 * @apiName SetNewPassword
 * @apiGroup PasswordRecovery
 * @apiParam {String} password password.
 * @apiParam {String} verificationCode il codice di verifica relativo
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {String[]} errorCode EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione
 * @apiError (7) {int} errorCode ALREADY_LOGGED: L'utente è già loggato e fino all'implementazione del cambio password non può fare niente
 * @apiError (11) {int} errorCode WRONG_CONFIRMATION_CODE: Codice di conferma non valido
 */
@WebServlet(name = "SetNewPassword", urlPatterns = "/api/setNewPassword")
public class SetNewPassword extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;

    PasswordRecoveryCodeCheck passwordRecoveryCodeCheck;

    //TODO RITORNARE L'USERNAME

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Inizializzo la sessione Sql
        SqlSession sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        OperationResult setPasswordOperation;
        response.setContentType("application/json");
        try {

            BadReqExeceptionThrower.checkUserAlreadyLogged(request);

            SetNewPasswordRequest setNewPasswordRequest = gsonReader.fromJson(request.getReader(), SetNewPasswordRequest.class);
            BadReqExeceptionThrower.checkRegex(setNewPasswordRequest);

            String username = passwordRecoveryCodeCheck.verify(setNewPasswordRequest.getCode());
            if (username == null) {
                throw new BadRequestException(ErrorCode.WRONG_CONFIRMATION_CODE);
            }

            userMapper.updatePassword(username, setNewPasswordRequest.getPassword());

            setPasswordOperation = null;

        } catch (BadRequestException e) {
            setPasswordOperation = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            setPasswordOperation = new BadRequestException();
            response.setStatus(400);
        }
        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(setPasswordOperation));
        sessionSql.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void init() throws ServletException {

        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        passwordRecoveryCodeCheck = new PasswordRecoveryCodeCheck(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }

}
