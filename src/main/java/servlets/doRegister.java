package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.ModelValidator;
import types.User;
import types.exceptions.InvalidRegistrationException;
import types.json.RegisterStatus;
import types.mappers.UserMapper;
import utilities.VerificationMailSender;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
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

    //TODO campi "" vengono accettati!!!

    private SqlSession session;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;
    private Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String line = null;
            StringBuffer jsonString = new StringBuffer();
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            if (jsonString.length() == 0) {
                throw new InvalidRegistrationException();
            }

            //Provo a parsare il Json nell'oggetto User. Se exception esce dalla sevlet
            User user = gson.fromJson(jsonString.toString(), User.class);
            //Il validatore valida tutte le stringhe di User e nel caso non siano sanitizzate allora
            //le aggiunge alla lista di stringhe invalide
            List<String> invalidParameters = ModelValidator.validate(user);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty()) {
                throw new InvalidRegistrationException(invalidParameters);
            }

            //Invio la mail di verifica
            boolean success = true;
            if (verificationMailSender.sendEmail(user, request.getRequestURL().toString())) {
                //TODO is always true... why?
                response.getOutputStream().print(success);
            } else {
                List<String> invalidMail = new ArrayList<String>();
                invalidMail.add("email");
                throw new InvalidRegistrationException(invalidMail);
            }

        } catch (InvalidRegistrationException e) {

            if (e.getInvalidParameters().isEmpty())
                errorHandler(response, null);
            else
                errorHandler(response, e.getInvalidParameters());

        } catch (JsonSyntaxException e) { //JSOn Malformato
            errorHandler(response, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione che gestisce i casi in cui vengano inviati pacchetti Json nulli o malformati.
     * Risponde con la lista dei parametri invalidi
     */
    private void errorHandler(HttpServletResponse response, List<String> invalidParameters) {

        response.setContentType("application/json");
        RegisterStatus registerStatus = new RegisterStatus(invalidParameters);

        //Converto l'ogg in Json e lo spedisco al Client
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print(gson.toJson(registerStatus));
        } catch (IOException e) {
            // Decidere che fare con problemi grossi (IOException)
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = verificationMailSender.verify(request.getParameter("verificationCode"));
        response.getWriter().print("Bella sei dentro! " + user.getUsername());
        userMapper.insertUser(user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getPassword(),
                user.getPhone(),
                user.getBirthday());
    }


    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        verificationMailSender = new VerificationMailSender();
        gson = new Gson();
    }
}


