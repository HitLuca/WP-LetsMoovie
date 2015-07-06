package servlets;

import com.google.gson.Gson;

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
        boolean success = false;
        String sourcePage = request.getParameter("sourcePage");
        try {
            session.invalidate();
            success = true;
            response.getOutputStream().print(gson.toJson(success));
        } catch (IllegalStateException ex) {
            session.invalidate();
            response.getOutputStream().print(gson.toJson(success));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() {
        gson = new Gson();
    }
}
