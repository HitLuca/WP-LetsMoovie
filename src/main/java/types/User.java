package types;

import java.util.Date;

/**
 * Created by etrunon on 25/06/15.
 */
public class User {

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

    //====== Setter per il Mapping

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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
