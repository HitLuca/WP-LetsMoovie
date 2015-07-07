package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import json.OperationError;
import json.OperationResult;
import json.login.response.*;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.AlreadyLoggedInException;
import utilities.InputValidator.ModelValidator;
import database.datatypes.UserLoginCredential;
import types.exceptions.InvalidLoginException;
import json.login.request.LoginRequest;
import database.mappers.UserMapper;

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

/**
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doLogin", urlPatterns = "/doLogin")
public class doLogin extends HttpServlet {
    Gson gson;
    private SqlSession session;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OperationResult loginStatus;
        try {
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            List<String> invalidParameters = ModelValidator.validate(loginRequest);

            if (!invalidParameters.isEmpty()) {
                throw new InvalidLoginException();
            }

            UserLoginCredential userCredential = userMapper.getUserCredential(loginRequest.getUsername());
            if (userCredential == null) {   //username non nel db
                throw new InvalidLoginException();
            } else if (!loginRequest.getPassword().equals(userCredential.getPassword())) { //password errata
                throw new InvalidLoginException();
            } else {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    if (session.getAttribute("username") != null && session.getAttribute("role") != null) {
                        if (session.getAttribute("username").equals(loginRequest.getUsername())) {
                            throw new AlreadyLoggedInException();
                        }
                    }
                }
                session = request.getSession(true);
                session.setAttribute("role", userCredential.getRole());
                session.setAttribute("username", loginRequest.getUsername());
                response.setContentType("application/json");
                loginStatus = new SuccessfullLogin(loginRequest.getUsername());
            }
        } catch (InvalidLoginException e) {
            loginStatus = new OperationError(2); //TODO usare il codice specifico
            response.setStatus(400);
        } catch (AlreadyLoggedInException e) {
            loginStatus = new OperationError(2); //TODO usare il codice specifico
            response.setStatus(400);
        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            loginStatus = new OperationError(2); //TODO usare il codice specifico
            response.setStatus(400);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gson.toJson(loginStatus));
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        gson = new Gson();

    }
}
