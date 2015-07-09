package json.userPersonalData.response;

import com.google.gson.annotations.Expose;
import database.datatypes.UserData;
import json.OperationResult;

/**
 * Created by etrunon on 08/07/15.
 */
public class PersonalRespose implements OperationResult {

    @Expose
    private String email;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String birthday;
    @Expose
    private String phone;

    public PersonalRespose(UserData userData) {
        email = userData.getEmail();
        username = userData.getUsername();
        password = userData.getPassword();
        name = userData.getName();
        surname = userData.getSurname();
        birthday = userData.getBirthday().toString();
        phone = userData.getPhone_number();
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }


}
