package types;

import java.util.Date;

/**
 * Created by marco on 25/06/15.
 */
public class UserData {
    private String name;
    private String surname;
    private String username;
    private String email;
    private Date birthday;
    private String telephone;
    private int role;

    public UserData(String name, String surname, String username, String email, Date birthday, String telephone, int role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.telephone = telephone;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getRole() {
        return role;
    }
}
