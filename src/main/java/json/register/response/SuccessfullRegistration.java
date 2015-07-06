package json.register.response;

import json.logout.response.SuccessfullLogout;

/**
 * Created by marco on 06/07/15.
 */
public class SuccessfullRegistration extends RegistrationStatus {
    public SuccessfullRegistration()
    {
        success = true;
    }
}
