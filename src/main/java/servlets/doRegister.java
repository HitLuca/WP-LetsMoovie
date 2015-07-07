package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import json.OperationError;
import json.OperationResult;
import json.register.request.RegistrationRequest;
import json.register.response.InvalidRegistration;
import json.register.response.SuccessfullRegistration;
import org.apache.ibatis.session.SqlSession;
import utilities.InputValidator.ModelValidator;
import types.exceptions.InvalidRegistrationException;
import database.mappers.UserMapper;
import utilities.mail.verification.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OperationResult registrationStatus;
        try {
            //Provo a parsare il Json nell'oggetto RegistrationRequest. Se exception esce dalla sevlet
            RegistrationRequest registrationRequest = gson.fromJson(request.getReader(), RegistrationRequest.class);
            //Il validatore valida tutte le stringhe di RegistrationRequest e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            List<String> invalidParameters = ModelValidator.validate(registrationRequest);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty()) {
                throw new InvalidRegistrationException(invalidParameters);
            }

            //TODO Controllo che i dati inseriti siano unici

            //Invio la mail di verifica TODO ritornare codici diversi in caso di mail già inviata
            if (!verificationMailSender.sendEmail(registrationRequest, request.getRequestURL().toString())) {
                throw new InvalidRegistrationException("email");
            }

            registrationStatus = new SuccessfullRegistration(registrationRequest.getEmail());

        } catch (InvalidRegistrationException e) {
            registrationStatus = new InvalidRegistration(e.getInvalidParameters());
            response.setStatus(400);
        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e){
            registrationStatus = new OperationError("Bad Request");
            response.setStatus(400);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gson.toJson(registrationStatus));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RegistrationRequest registrationRequest = verificationMailSender.verify(request.getParameter("verificationCode"));
        response.getWriter().print("Bella sei dentro! " + registrationRequest.getUsername());
        //TODO implementare e testare le funzionalità mancanti
        /*userMapper.insertUser(registrationRequest.getEmail(),
                registrationRequest.getName(),
                registrationRequest.getSurname(),
                registrationRequest.getUsername(),
                registrationRequest.getPassword(),
                registrationRequest.getPhone(),
                registrationRequest.getBirthday());*/
    }


    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        verificationMailSender = new VerificationMailSender();
        gson = new Gson();
    }
}


