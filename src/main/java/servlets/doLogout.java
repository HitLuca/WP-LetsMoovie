package servlets;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sourcePage = request.getParameter("sourcePage");
        if(session.getAttribute("username")!=null)
        {
            session.removeAttribute("username");
        }
        if(session.getAttribute("role")!=null)
        {
            session.removeAttribute("role");
        }
        response.sendRedirect(sourcePage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
