package database.datatypes.user;

/**
 * Created by hitluca on 16/07/15.
 */
public class UserPaid {
    private String username;
    private float paid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }
}
