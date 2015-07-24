package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.register.request.RegistrationRequest;
import json.register.response.SuccessfullRegistration;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.BadReqExeceptionThrower;
import utilities.InputValidator.ModelValidator;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet che gestisce la registrazione di un nuovo utente.
 * Controlla
 * che i campi non siano nulli,
 * che l'username non sia già presente nel db,
 * che la password sia della lunghezza giusta
 * Fatto questo, invia una mail all'utente. Quando viene clickato il link viene fatto il controllo sulla scadenza
 * inserisce nel db il nuovo utente con tutti i dati associati e reindirizza l'utente sulla pagina dove si trovava
 * prima di fare la registrazione.
 */

/**
 * @api {post} /api/register
 * @apiName Register
 * @apiGroup Registration
 *
 * @apiParam {String} username Username.
 * @apiParam {String} password password.
 * @apiParam {String} birthday data di nascita
 * @apiParam {String} name nome dell'utente
 * @apiParam {String} surname cognome dell'utente
 * @apiParam {String} email email dell'utente
 * @apiParam {String} phone numero di telefono dell'utente
 *
 * @apiSuccess {String} email l'indirizzo email a cui è stata inviata la mail
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {String[]} errorCode EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione
 * @apiError (3) {String[]} errorCode DUPLICATE_FIELD: parameters parametri di input duplicati
 * @apiError (7) {int} errorCode ALREADY_LOGGED: è già presente una sessione valida
 * @apiError (9) {String[]} errorCode INVALID_MAIL: parameters la mail non valida
 */
@WebServlet(name = "Register", urlPatterns = "/api/register")
public class Register extends HttpServlet {

    private final String url = "/api/register";
    Gson gsonWriter;
    Gson gsonReader;
    private VerificationMailSender verificationMailSender;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Inizializzo la sessione Sql
        SqlSession sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);

        response.setContentType("application/json");
        OperationResult registrationStatus;
        try {

            //Check sulla sessione già presente e l'utente è già loggato con un username e lo si spara fuori
            BadReqExeceptionThrower.checkUserAlreadyLogged(request);

            //Provo a parsare il Json nell'oggetto RegistrationRequest. Se exception esce dalla sevlet
            RegistrationRequest registrationRequest = gsonReader.fromJson(request.getReader(), RegistrationRequest.class);
            //Il validatore valida tutte le stringhe di RegistrationRequest e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            BadReqExeceptionThrower.checkRegex(registrationRequest);

            //Controllo se l'username e la password da registrare non sono già presenti nel db
            String username = userMapper.checkUsername(registrationRequest.getUsername());
            String mail = userMapper.checkEmail(registrationRequest.getEmail());
            if (username != null && mail != null) {
                List<String> invList = new ArrayList<String>();
                invList.add("username");
                invList.add("email");
                throw new BadRequestExceptionWithParameters(ErrorCode.DUPLICATE_FIELD,invList);
            } else if (username != null)
                throw new BadRequestExceptionWithParameters(ErrorCode.DUPLICATE_FIELD, "username");
            else if (mail != null)
                throw new BadRequestExceptionWithParameters(ErrorCode.DUPLICATE_FIELD, "email");

            //verificationMailSender.checkDuplicates(RegistrationRequest registrationRequest) ritorna la lista di stringhe
            // che corrispondono ai campi duplicati, lista vuota se va bene

            //Invio la mail di verifica, se mail invalida tiro eccezione
            String registrationMailUrl = request.getRequestURL().toString().replace(url,"");
            registrationMailUrl+="/confirmRegistration?verificationCode=";
            if (!verificationMailSender.sendEmail(registrationRequest,registrationMailUrl)){
                throw new BadRequestExceptionWithParameters(ErrorCode.INVALID_MAIL, "email");
            }

            registrationStatus = new SuccessfullRegistration(registrationRequest.getEmail());

        } catch (BadRequestException e) { //
            registrationStatus = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            registrationStatus = new BadRequestException();
            response.setStatus(400);
        }
        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(registrationStatus));

        sessionSql.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    @Override
    public void init() throws ServletException {

        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        verificationMailSender = new VerificationMailSender(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }
}


