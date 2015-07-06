package types.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class RegisterStatus {
    private List<String> invalidParameters;
    boolean success;

    public RegisterStatus(List<String> invalidParameters)
    {
        success = false;
        this.invalidParameters=invalidParameters;
    }

    public RegisterStatus() {
        success = false;
        invalidParameters = new ArrayList<String>();
        invalidParameters.add("email");
    }

    public void  setSuccess(boolean success)
    {
        this.success=success;
    }

    public void addInvalidParameter(String parameter)
    {
        invalidParameters.add(parameter);
    }
}
