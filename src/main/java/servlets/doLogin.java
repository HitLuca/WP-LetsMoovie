package servlets;

import com.google.gson.Gson;
import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.UserLoginCredential;
import types.exceptions.MyException;
import types.json.LoginStatus;
import types.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doLogin", urlPatterns = "/doLogin")
public class doLogin extends HttpServlet {
    Gson gson;
    private SqlSession session;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (username == null || password == null)   //campi nulli
            {   //Lancia l'eccezione campi nulli
                throw new MyException();
            }

            UserLoginCredential userCredential = userMapper.getUserCredential(username);
            if (userCredential == null) {   //username non nel db
                throw new MyException();
            } else if (!password.equals(userCredential.getPassword())) { //password errata
                throw new MyException();
            }

            //Si crea la sessione e la si inizializza coi dati utente. Il sessionId viene inviato automaticamente
            HttpSession session = request.getSession(true);
            session.setAttribute("role", "USER");
            session.setAttribute("username", username);
            //Si setta il fatto che si stanno inviando Json
            response.setContentType("application/json");

            // Creo il Jsno di risposta
            LoginStatus loginStatus = new LoginStatus();
            loginStatus.setSuccess(true);
            loginStatus.setUsername(username);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print(gson.toJson(loginStatus));

        } catch (MyException e) {
            errorHandler(request, response);
        }

    }

    private void errorHandler(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setSuccess(false);
        loginStatus.setUsername(null);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print(gson.toJson(loginStatus));
        } catch (IOException e) {
            // Decidere che fare con problemi grossi (IOException)
        }
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        gson = new Gson();

    }
}
