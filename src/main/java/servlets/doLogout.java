package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

/** Servlet che gestisce il logout dalla sessione di un utente. Non riceve input, nel momento in cui un utente fa una richiesta
 *  GET o POST se si trova in una sessione valida questa viene automaticamente invalidata e cancellata.
 *
 *  Questa servlet lancia i seguenti errori con questo formato:
 *  - (0)   BAD_REQUEST         con payload vuoto, lanciato quando succedono errori gravi all'interno della servlet
 *  - (10)    NOT_LOGGED_IN                 con payload {}, quando l'utente non dispone di una sessione valida da cui sloggare
 *
 * Created by marco on 26/06/15.
 */
@WebServlet(name = "doLogout", urlPatterns = "/doLogout")
public class doLogout extends HttpServlet {
    Gson gson;


    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        OperationResult logoutStatus = null;
        try {
            //Prendo la sessione dell'utente
            HttpSession session = request.getSession(false);
            //Se è nulla allora vuol dire che è già sloggato o non autenticato
            if (session == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }

            session.invalidate();

        } catch (BadRequestException e) {
            logoutStatus = e;
            response.setStatus(400);

        } catch (IllegalStateException e) {
            response.setStatus(400);
        }
        response.getOutputStream().print(gson.toJson(logoutStatus));
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request,response);
    }

    @Override
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gson = gsonBuilder.create();
    }
}
