package json.register.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by etrunon on 25/06/15.
 */
public class RegistrationRequest {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String birthday;
    private String phone;

    // ======= Getter per la sanitizzazione

    @toSanitize(name = "email", reg = Regex.EMAIL)
    public String getEmail() {
        return email;
    }

    @toSanitize(name = "username", reg = Regex.USERNAME)
    public String getUsername() {
        return username;
    }

    @toSanitize(name = "password", reg = Regex.PASSWORD)
    public String getPassword() {
        return password;
    }

    @toSanitize(name = "name", reg = Regex.NAME)
    public String getName() {
        return name;
    }

    @toSanitize(name = "surname", reg = Regex.NAME)
    public String getSurname() {
        return surname;
    }

    @toSanitize(name = "birthday", reg = Regex.DATE)
    public String getBirthday() {
        return birthday;
    }

    @toSanitize(name = "phone", reg = Regex.PHONE)
    public String getPhone() {
        return phone;
    }

}
