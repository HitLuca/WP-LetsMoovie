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
import utilities.InputValidator.ModelValidator;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
 *
 * Questa servlet lancia i seguenti errori con questo formato:
 *  - (0)   BAD_REQUEST                     con payload vuoto, lanciato quando succedono errori gravi all'interno della servlet
 *  - (2)   EMPTY_WRONG_FIELD               con payload avente la seguente possibilità. Viene lanciato quando uno o più campi sono vuoti
 *                                          oppure errati (non validabili)
 *                                              {tutti i parametri di input che non passano la validazione}
 *  - (3)    DUPLICATE_FIELD                con payload {"username"} per esempio quando l'username e/o la mail in input è già
 *                                          presente nel DB
 *  - (7)    ALREADY_LOGGED                 con payload vuoto. Se è già presente una sessione valida con quel client
 *  - (9)    INVALID_MAIL                   con payload {"mail"} quando la mail in input non è valida e non può ricevere
 *                                          la mail di registrazione
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "Register", urlPatterns = "/api/register")
public class Register extends HttpServlet {

    Gson gsonWriter;
    Gson gsonReader;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        OperationResult registrationStatus;
        try {

            //Check sulla sessione già presente e l'utente è già loggato con un username e lo si spara fuori
            HttpSession session = request.getSession();
            if (session.getAttribute("username") != null) {
                throw new BadRequestExceptionWithParameters(ErrorCode.ALREADY_LOGGED, (String)session.getAttribute("username"));
            }

            //Provo a parsare il Json nell'oggetto RegistrationRequest. Se exception esce dalla sevlet
            RegistrationRequest registrationRequest = gsonReader.fromJson(request.getReader(), RegistrationRequest.class);
            //Il validatore valida tutte le stringhe di RegistrationRequest e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            List<String> invalidParameters = ModelValidator.validate(registrationRequest);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty())
                throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD,invalidParameters);

            //Controllo se l'username e la password da registrare non sono già presenti nel db
            String username = userMapper.getDuplicateUsername(registrationRequest.getUsername());
            String mail = userMapper.getDuplicateEmail(registrationRequest.getEmail());
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
            if (!verificationMailSender.sendEmail(registrationRequest, request.getRequestURL().toString())) {
                throw new BadRequestExceptionWithParameters(ErrorCode.INVALID_MAIL, "email");
            }

            registrationStatus = new SuccessfullRegistration(registrationRequest.getEmail());

        } catch (BadRequestException e) { //
            registrationStatus = e;
            response.setStatus(400);

        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            registrationStatus = new BadRequestException();
            response.setStatus(400);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(registrationStatus));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    @Override
    public void init() throws ServletException {
        SqlSession session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        verificationMailSender = new VerificationMailSender(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }
}


