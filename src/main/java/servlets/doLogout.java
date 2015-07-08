package servlets;

import com.google.gson.Gson;
import json.OperationError;
import json.OperationResult;
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
 * Created by marco on 26/06/15.
 */
@WebServlet(name = "doLogout", urlPatterns = "/doLogout")
public class doLogout extends HttpServlet {
    Gson gson;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        OperationResult logoutStatus;

        try {
            //Prendo la sessione dell'utente
            HttpSession session = request.getSession(false);
            //Se è nulla allora vuol dire che è già sloggato o non autenticato
            if (session == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }

            session.invalidate();
            //todo controllare se invalidate cancella il cookie al client
            logoutStatus = new OperationResult();

        } catch (BadRequestException e) {
            logoutStatus = new OperationError(e.getCode());
            response.setStatus(400);

        } catch (IllegalStateException e) {
            logoutStatus = new OperationError();
            response.setStatus(400);
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
