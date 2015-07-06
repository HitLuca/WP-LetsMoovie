package utilities.mail.verification;

import json.register.request.RegistrationRequest;

/**
 * Created by marco on 25/06/15.
 */
public class UserRegistrationRequest {
    private RegistrationRequest registrationRequest;
    private long expireDate;

    public UserRegistrationRequest(RegistrationRequest registrationRequest, long expireDate) {
        this.registrationRequest = registrationRequest;
        this.expireDate = expireDate;
    }

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public long getExpireDate() {
        return expireDate;
    }

}
