package types;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by etrunon on 25/06/15.
 */
public class User {
    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date birthday;
    private String phone;

    public User(HttpServletRequest request) {

        String bday;

        email = request.getParameter("email");
        username = request.getParameter("username");
        password = request.getParameter("password");
        name = request.getParameter("name");
        surname = request.getParameter("surname");
        bday = request.getParameter("birthday");
        phone = request.getParameter("phone");

        //Converto birthday a formato date
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try {
            birthday = format.parse(bday);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Boolean checkNulls() {

        if (email == null)
            return true;
        else if (username == null)
            return true;
        else if (password == null)
            return true;
        else if (name == null)
            return true;
        else if (surname == null)
            return true;
        else if (birthday == null)
            return true;
        else if (phone == null)
            return true;
        else
            return false;
    }
}
