package types;

import java.util.Date;

/**
 * Created by marco on 25/06/15.
 */
public class UserRegistrationRequest {
    private String email;
    private long expireDate;

    public UserRegistrationRequest(String email, long expireDate) {
        this.email=email;
        this.expireDate = expireDate;
    }

    public String getEmail() {
        return email;
    }

    public long getExpireDate() {
        return expireDate;
    }

}
