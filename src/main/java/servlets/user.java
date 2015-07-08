package servlets;

import types.enums.ErrorCode;
import types.exceptions.BadRequestException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by etrunon on 08/07/15.
 */
@WebServlet(name = "user", urlPatterns = "/api/user")
public class user extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //Check sulla sessione già presente e l'utente è già loggato con un username
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }

        } catch (BadRequestException e) {

        }
    }
}
