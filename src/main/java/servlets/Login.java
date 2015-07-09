package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.UserLoginCredential;
import database.mappers.UserMapper;
import json.OperationResult;
import json.login.request.LoginRequest;
import json.login.response.SuccessfullLogin;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
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

/** Servlet che gestisce il login degli utenti. Risponde a richieste POST in cui viene inviato un Json contenente l'username e
 *  la password dell'utente da loggare.
 *  Se l'utente è già loggato gli viene impedito l'accesso alla servlet, a quel punto vengono fatti controlli sulla leggitimità
 *  dell'input e infine viene creata una sessione per l'utente.
 *
 *  Questa servlet lancia i seguenti errori con questo formato:
 *
 *  - (0)   BAD_REQUEST         con payload vuoto, lanciato quando succedono errori gravi all'interno della servlet
 *  - (1)    EMPTY_REQ          Con payload {}, nel caso in cui arrivi alla servlet una richiesta vuota
 *  - (2)    EMPTY_WRONG_FIELD  Con payload variabile nelle seguenti possibilità, quando uno o più campi non sono ritenuti
 *                              validi dal validatore o non sono presenti nel DB.
 *                              {Lista dei parametri in input non validabili}
 *                              {2} nel caso l'username o la pass non sono validi (Non specifichiamo per scelta)
 *   - (7)    ALREADY_LOGGED     con payload {}, quando l'utente non dispone di una sessione valida da cui sloggare
 *
 * Created by etrunon on 24/06/15.
 */



/**
 * @api {post} /api/login
 * @apiName Login
 * @apiGroup Login
 *
 * @apiParam {String} username Username.
 * @apiParam {String} password password.
 *
 * @apiSuccess {String} username Username.
 *
 *
 * @apiError (0) {int} errorCode lanciato quando succedono errori gravi all'interno della servlet
 *
 * @apiError (1) {int} errorCode richiesta vuota
 *
 * @apiError (2) {int} errorCode quando uno o più campi non sono ritenuti validi dal validatore o non sono presenti nel DB
 * @apiError (2) {String[]} invalidParameters parametri invalidi
 *
 * @apiError (7) {int} errorCode l'utente non dispone di una sessione valida da cui sloggare
 *
 */
@WebServlet(name = "Login", urlPatterns = "/api/login")
public class Login extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;
    private SqlSession session;
    private UserMapper userMapper;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        OperationResult loginStatus;
        try {
            //Check sulla sessione già presente e l'utente è già loggato con un username
            HttpSession session = request.getSession();
            if (session.getAttribute("username") != null) {

                throw new BadRequestException(ErrorCode.ALREADY_LOGGED);
            }

            //Check sulla richiesta vuota
            LoginRequest loginRequest = gsonReader.fromJson(request.getReader(), LoginRequest.class);
            if (loginRequest == null) {
                throw new BadRequestException(ErrorCode.EMPTY_REQ);
            }

            //Parso e valido la request
            //Check sulla parametri non parsabili
            List<String> invalidParameters = ModelValidator.validate(loginRequest);
            if (!invalidParameters.isEmpty()) {
                throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
            }

            //Controllo sui dati nel DB
            UserLoginCredential userCredential = userMapper.getUserCredential(loginRequest.getUsername());
            if (userCredential == null) {   //username non nel db
                throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
            } else if (!loginRequest.getPassword().equals(userCredential.getPassword())) { //password errata
                throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
            }

            //L'utente era nel db e la password era corretta
            session = request.getSession(true);
            session.setAttribute("role", userCredential.getRole());
            session.setAttribute("username", loginRequest.getUsername());
            loginStatus = new SuccessfullLogin(loginRequest.getUsername());

        } catch (BadRequestException e) {
            loginStatus = e;
            response.setStatus(400);

        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            loginStatus = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(loginStatus));
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }
}
