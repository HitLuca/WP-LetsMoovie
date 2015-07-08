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
import types.exceptions.BadParametersException;
import utilities.InputValidator.ModelValidator;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.verification.VerificationMailSender;

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
 * <p/>
 * Fatto questo, invia una mail all'utente. Quando viene clickato il link viene fatto il controllo sulla scadenza
 * inserisce nel db il nuovo utente con tutti i dati associati e reindirizza l'utente sulla pagina dove si trovava
 * prima di fare la registrazione.
 * <p/>
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doRegister", urlPatterns = "/doRegister")
public class doRegister extends HttpServlet {

    private SqlSession session;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;
    Gson gsonWriter;
    Gson gsonReader;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        OperationResult registrationStatus = null;
        try {

            //Check sulla sessione già presente e l'utente è già loggato con un username e lo si spara fuori
            HttpSession session = request.getSession();
            if (session.getAttribute("username") != null)
                throw new BadRequestException(ErrorCode.ALREADY_LOGGED);

            //Provo a parsare il Json nell'oggetto RegistrationRequest. Se exception esce dalla sevlet
            RegistrationRequest registrationRequest = gsonReader.fromJson(request.getReader(), RegistrationRequest.class);
            //Il validatore valida tutte le stringhe di RegistrationRequest e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            List<String> invalidParameters = ModelValidator.validate(registrationRequest);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty())
                throw new BadParametersException(ErrorCode.EMPTY_WRONG_FIELD,invalidParameters);

            //Controllo se l'username e la password da registrare non sono già presenti nel db
            String username = userMapper.getDuplicateUsername(registrationRequest.getUsername());
            String mail = userMapper.getDuplicateEmail(registrationRequest.getEmail());
            if (username != null || mail != null) {
                List<String> invList = new ArrayList<String>();
                invList.add("username");
                invList.add("email");
                throw new BadParametersException(ErrorCode.DUPLICATE_USERNAME_AND_MAIL,invList);
            } else if (username != null)
                throw new BadParametersException(ErrorCode.DUPLICATE_USERNAME, "username");
            else if (mail != null)
                throw new BadParametersException(ErrorCode.DUPLICATE_MAIL, "email");

            //verificationMailSender.checkDuplicates(RegistrationRequest registrationRequest) ritorna la lista di stringhe
            // che corrispondono ai campi duplicati, lista vuota se va bene

            //Invio la mail di verifica, se mail invalida tiro eccezione
            if (!verificationMailSender.sendEmail(registrationRequest, request.getRequestURL().toString())) {
                throw new BadParametersException(ErrorCode.INVALID_MAIL, "email");
            }

            registrationStatus = new SuccessfullRegistration(registrationRequest.getEmail());

        } catch (BadParametersException e) {
            registrationStatus = e;
            response.setStatus(400);

        } catch (BadRequestException e) {
            registrationStatus = e;
            response.setStatus(400);

        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            response.setStatus(400);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(registrationStatus));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RegistrationRequest registrationRequest = verificationMailSender.verify(request.getParameter("verificationCode"));
        response.getWriter().print("Bella sei dentro! " + registrationRequest.getUsername());
        //TODO testare le funzionalità mancanti
        userMapper.insertUser(registrationRequest);
        session.commit();   //Per essere sicuri che vada nel DB

        HttpSession session = request.getSession(true);
        session.setAttribute("username",registrationRequest.getUsername());
        session.setAttribute("role",0);
        response.sendRedirect("/index.jsp"); //TODO SET USER PAGE
    }


    @Override
    public void init() throws ServletException {
        session = DatabaseConnection.getFactory().openSession(true);
        userMapper = session.getMapper(UserMapper.class);
        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        verificationMailSender = new VerificationMailSender(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }
}


