package servlets;

import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.User;
import types.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User(request);
        String sourcePage = request.getParameter("sourcePage");

        //Controllo che i campi inseriti non siano NULL
        if (user.checkNulls() == true)
            errorHandler(response, ErrorType.NULL_VALUE);
        if (sourcePage == null)
            errorHandler(response, ErrorType.NULL_VALUE);


        /*
        //Procedo all'inserimento nel DB
        try {
            userMapper.insertUser(username, password, email, date, name, surname, phone);
        } catch (Exception e) {
            System.out.println("Inserimento non effettuato");
        }
*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void errorHandler(HttpServletResponse response, ErrorType e) {
        switch (e) {
            case NULL_VALUE: {

                try {
                    response.sendRedirect("register");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);

    }
}


