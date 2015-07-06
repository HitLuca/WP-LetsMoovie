package servlets;

import com.google.gson.Gson;
import json.logout.response.InvalidLogout;
import json.logout.response.LogoutStatus;
import json.logout.response.SuccessfullLogout;

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
        HttpSession session = request.getSession();
        response.setContentType("application/JSON");
        String sourcePage = request.getParameter("sourcePage");
        LogoutStatus logoutStatus;
        try {
            session.invalidate();
            logoutStatus = new SuccessfullLogout();
        } catch (IllegalStateException ex) {
            logoutStatus = new InvalidLogout();
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
