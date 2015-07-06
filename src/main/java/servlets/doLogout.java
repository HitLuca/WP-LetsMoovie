package servlets;

import com.google.gson.Gson;
import json.GenericOperationError;
import json.GenericSuccessfullOperation;
import json.OperationStatus;
import json.logout.response.InvalidLogout;
import types.exceptions.InvalidLogoutException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by marco on 26/06/15.
 */
@WebServlet(name = "doLogout", urlPatterns = "/doLogout")
public class doLogout extends HttpServlet {
    Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/JSON");
        String sourcePage = request.getParameter("sourcePage");
        OperationStatus logoutStatus;
        try {
            if(session==null) {
                throw new InvalidLogoutException();
            }
            session.invalidate();
            logoutStatus = new GenericSuccessfullOperation();
        } catch (InvalidLogoutException e) {
            logoutStatus = new InvalidLogout();
        } catch (IllegalStateException e){
            logoutStatus = new GenericOperationError(e.getMessage());
        }
        response.getOutputStream().print(gson.toJson(logoutStatus));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() {
        gson = new Gson();
    }
}
