package json.register.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidRegistration extends RegistrationStatus {
    private List<String> invalidParameters;

    public InvalidRegistration(List<String> invalidParameters) {
        success = false;
        this.invalidParameters = invalidParameters;
    }

    public InvalidRegistration(String invalidParameter) {
        success = false;
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }

    public InvalidRegistration()
    {
        success = false;
    }
}
