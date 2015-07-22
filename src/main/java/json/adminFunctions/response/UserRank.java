package json.adminFunctions.response;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPaid;

/**
 * Created by hitluca on 22/07/15.
 */
public class UserRank {
    @Expose
    private String username;
    @Expose
    private float totalPayments;

    public UserRank(UserPaid u) {
        this.username = u.getUsername();
        this.totalPayments = u.getPaid();
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
}
