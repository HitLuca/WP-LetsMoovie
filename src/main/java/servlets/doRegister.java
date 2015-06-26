package servlets;

import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.mappers.UserMapper;
import utilities.VerificationMailSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

enum ErrorType {
    NULL_VALUE
}

/** Servlet che gestisce la registrazione di un nuovo utente.
 * Controlla
 *      che i campi non siano nulli,
 *      che l'username non sia già presente nel db,
 *      che la password sia della lunghezza giusta
 *
 * Fatto questo, inserisce nel db il nuovo utente con tutti i dati associati e reindirizza l'utente sulla pagina dove si trovava
 * prima di fare la registrazione.
 *
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doRegister", urlPatterns = "/doRegister")
public class doRegister extends HttpServlet {

    private SqlSession session;
    private UserMapper userMapper;
    private VerificationMailSender verificationMailSender;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("boh");

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String sourcePage = request.getParameter("sourcePage");

        System.out.println("ho letto");

        //Controllo che i campi inseriti non siano NULL
        if (email == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (username == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (password == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (name == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (surname == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (birthday == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (phone == null)
            errorHandler(ErrorType.NULL_VALUE);
        if (sourcePage == null)
            errorHandler(ErrorType.NULL_VALUE);

        System.out.println("non erano null");

        //Converto birthday a formato date
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Procedo all'inserimento nel DB
        try {
            userMapper.insertUser(username, password, email, date, name, surname, phone);
        } catch (Exception e) {
            System.out.println("Inserimento non effettuato");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("mandaMail")!=null)
        {
            if(verificationMailSender.sendEmail(request.getParameter("mandaMail")))
            {
                System.out.println("EMAIL INSERITA");
            }
            else
            {
                System.out.println("ERRORE INVIO");
            }
        }
        if(request.getParameter("confermaMail")!=null)
        {
            System.out.println(verificationMailSender.verify(request.getParameter("confermaMail")));
        }
    }

    private void errorHandler(ErrorType e) {
        switch (e) {
            case NULL_VALUE: {

            }
        }
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        verificationMailSender = new VerificationMailSender();

    }
}


