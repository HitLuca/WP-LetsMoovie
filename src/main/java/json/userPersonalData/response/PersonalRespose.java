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
    private String name;
    @Expose
    private String surname;
    @Expose
    private String birthday;
    @Expose
    private String phone;
    @Expose
    private float residual_credit;
    @Expose
    private int role;

    public PersonalRespose(UserData userData) {
        email = userData.getEmail();
        username = userData.getUsername();
        name = userData.getName();
        surname = userData.getSurname();
        birthday = userData.getBirthday().toString();
        phone = userData.getPhone_number();
        residual_credit = userData.getResidual_credit();
        role = userData.getRole();
    }

    public float getResidual_credit() {
        return residual_credit;
    }

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
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
