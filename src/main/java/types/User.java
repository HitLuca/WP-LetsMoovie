package types;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by etrunon on 25/06/15.
 */
public class User {

    private final transient Pattern emailPattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient Pattern usernamePattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient Pattern passwordPattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient Pattern namePattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient Pattern surnamePattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient Pattern phonePattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
    private final transient DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);

    String email;
    String username;
    String password;
    String name;
    String surname;
    Date birthday;
    String phone;

    public User(Map<String,String[]> values) throws ParseException {

        String bday;

        email = values.get("email")[0];
        username = values.get("username")[0];
        password = values.get("password")[0];
        name = values.get("name")[0];
        surname = values.get("surname")[0];
        bday = values.get("birthday")[0];
        phone = values.get("phone")[0];

        if(!emailPattern.matcher(email).matches())
        {

        }

        birthday = format.parse(bday);


    }
}
