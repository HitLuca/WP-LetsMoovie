package servlets;

import com.google.gson.Gson;
import database.DatabaseConnection;
import json.register.response.InvalidRegistration;
import json.register.response.SuccessfullRegistration;
import org.apache.ibatis.session.SqlSession;
import utilities.InputValidator.ModelValidator;
import json.register.request.User;
import types.exceptions.InvalidRegistrationException;
import json.register.response.RegistrationStatus;
import database.mappers.UserMapper;
import utilities.mail.verification.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    //TODO campi "" vengono accettati!!!

    private SqlSession session;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;
    private Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RegistrationStatus registrationStatus = new InvalidRegistration();
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            //Provo a parsare il Json nell'oggetto User. Se exception esce dalla sevlet
            User user = gson.fromJson(request.getReader(), User.class);
            //Il validatore valida tutte le stringhe di User e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            List<String> invalidParameters = ModelValidator.validate(user);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty()) {
                registrationStatus = new InvalidRegistration(invalidParameters);
                throw new InvalidRegistrationException();
            }

            //TODO Controllo che i dati inseriti siano unici

            //Invio la mail di verifica TODO ritornare codici diversi in caso di mail già inviata
            if (verificationMailSender.sendEmail(user, request.getRequestURL().toString())) {
               registrationStatus = new SuccessfullRegistration();
            } else {
                registrationStatus = new InvalidRegistration("email");
                throw new InvalidRegistrationException();
            }

        } catch (InvalidRegistrationException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.print(gson.toJson(registrationStatus));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = verificationMailSender.verify(request.getParameter("verificationCode"));
        response.getWriter().print("Bella sei dentro! " + user.getUsername());
        //TODO implementare e testare le funzionalità mancanti
        /*userMapper.insertUser(user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getPassword(),
                user.getPhone(),
                user.getBirthday());*/
    }


    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        verificationMailSender = new VerificationMailSender();
        gson = new Gson();
    }
}


