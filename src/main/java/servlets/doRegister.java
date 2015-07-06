package servlets;

import com.google.gson.Gson;
import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.ModelValidator;
import types.User;
import types.exceptions.InvalidRegistrationException;
import types.json.RegisterStatus;
import types.mappers.UserMapper;
import types.toSanitize;
import utilities.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

enum ErrorType {
    NULL_VALUE
}

/** Servlet che gestisce la registrazione di un nuovo utente.
 * Controlla
 *      che i campi non siano nulli,
 *      che l'username non sia già presente nel db,
 *      che la password sia della lunghezza giusta
 *
 * Fatto questo, invia una mail all'utente. Quando viene clickato il link viene fatto il controllo sulla scadenza
 * inserisce nel db il nuovo utente con tutti i dati associati e reindirizza l'utente sulla pagina dove si trovava
 * prima di fare la registrazione.
 *
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doRegister", urlPatterns = "/doRegister")
public class doRegister extends HttpServlet {


    private SqlSession session;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;
    Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {
            String line = null;
            StringBuffer jsonString = new StringBuffer();
            BufferedReader reader = request.getReader();
            while( (line = reader.readLine() )!= null) {
                jsonString.append(line);
            }

            User user = gson.fromJson(jsonString.toString(),User.class); //TODO catch JSONException
            if(user==null)
            {
                //TODO send success:false
            }

        try {
            List<String> invalidParameters = ModelValidator.validate(user);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //Invio la mail di verifica
        //TODO togliere if false
        if(false) {
            boolean success = true;
            if (verificationMailSender.sendEmail(user)) {
                response.getOutputStream().print(success);
            } else {
                response.getOutputStream().print(gson.toJson(new RegisterStatus()));
            }
        }

//        }
//        catch (InvalidRegistrationException e) {
//            RegisterStatus registerStatus = new RegisterStatus(e.getInvalidParameters());
//            response.getOutputStream().print(gson.toJson(registerStatus));
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //verificationMailSender.verify("fdisufiusifusi");
    }


    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        verificationMailSender = new VerificationMailSender();
        gson = new Gson();
    }
}


