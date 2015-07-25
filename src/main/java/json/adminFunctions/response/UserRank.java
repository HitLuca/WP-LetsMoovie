package json.adminFunctions.response;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPaid;

/**
 * Created by hitluca on 22/07/15.
 */
public class UserRank {
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String username;
    @Expose
    private float totalPayments;
    @Expose
    private int rank;

    public UserRank(UserPaid userPaid, int rank) {
        this.name = userPaid.getName();
        this.surname = userPaid.getSurname();
        this.username = userPaid.getUsername();
        this.totalPayments = userPaid.getPaid();
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(float totalPayments) {
        this.totalPayments = totalPayments;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
