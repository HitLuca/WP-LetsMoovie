package types;

import types.exceptions.*;

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




    private String email;

    private String username;
    private String password;
    private String name;
    private String surname;
    private Date birthday;
    private String phone;
    public User(Map<String,String[]> values) throws InvalidRegistrationException {
/*
        String bday;
        boolean valid = true;
        InvalidRegistrationException exception = new InvalidRegistrationException();

        if(values.get(emailParameter)[0]!=null) {
            email = values.get(emailParameter)[0];
        } else {
            valid = false;
            exception.addInvalidParameter(emailParameter);
        }

        if(values.get(usernameParameter)[0]!=null) {
            username = values.get(usernameParameter)[0];
        } else {
            valid = false;
            exception.addInvalidParameter(usernameParameter);
        }
        username = values.get(usernameParameter)[0];
        password = values.get(passwordParameter)[0];
        name = values.get(nameParameter)[0];
        surname = values.get(surnameParameter)[0];
        bday = values.get(birthdayParameter)[0];
        phone = values.get(phoneParameter)[0];




        if(!emailPattern.matcher(email).matches())
        {
            exception.addInvalidParameter(emailParameter);
            valid=false;
        }
        if(!usernamePattern.matcher(username).matches())
        {
            exception.addInvalidParameter(usernameParameter);
            valid=false;
        }
        if(!passwordPattern.matcher(password).matches())
        {
            exception.addInvalidParameter(passwordParameter);
            valid=false;
        }
        if(!namePattern.matcher(name).matches())
        {
            exception.addInvalidParameter(nameParameter);
            valid=false;
        }
        if(!surnamePattern.matcher(surname).matches())
        {
            exception.addInvalidParameter(surnameParameter);
            valid=false;
        }
        if(!phonePattern.matcher(phone).matches())
        {
            exception.addInvalidParameter(phoneParameter);
            valid=false;
        }
        try{
            birthday = format.parse(bday);
        } catch (ParseException e) {
            exception.addInvalidParameter(birthdayParameter);
            valid=false;
        }

        if(!valid)
        {
            throw exception;
        }*/
    }

    @toSanitize (name = "email")
    public String getEmail() {
        return email;
    }

    @toSanitize (name = "username")
    public String getUsername() {
        return username;
    }

    @toSanitize (name = "password")
    public String getPassword() {
        return password;
    }

    @toSanitize (name = "name")
    public String getName() {
        return name;
    }

    @toSanitize (name = "surname")
    public String getSurname() {
        return surname;
    }

    @toSanitize (name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    @toSanitize (name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
