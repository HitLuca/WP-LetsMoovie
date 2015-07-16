package utilities.mail.request;

import json.register.request.RegistrationRequest;

/**
 * Created by marco on 25/06/15.
 */
public class UserRegistrationRequest extends UserEmailRequest {
    private RegistrationRequest registrationRequest;


    public UserRegistrationRequest(RegistrationRequest registrationRequest, long expireDate) {
        super(expireDate,registrationRequest.getUsername(),registrationRequest.getEmail());
        this.registrationRequest = registrationRequest;
    }

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }
}
