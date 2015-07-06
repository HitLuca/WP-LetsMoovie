package utilities.mail.verification;

import json.register.request.User;

/**
 * Created by marco on 25/06/15.
 */
public class UserRegistrationRequest {
    private User user;
    private long expireDate;

    public UserRegistrationRequest(User user, long expireDate) {
        this.user=user;
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public long getExpireDate() {
        return expireDate;
    }

}
