package types.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class RegisterStatus {
    private List<String> invalidParameters;
    boolean success;

    public RegisterStatus(List<String> invalidParameters) {
        this.success = false;
        this.invalidParameters = invalidParameters;
    }

    public void  setSuccess(boolean success)
    {
        this.success=success;
    }

    public void addInvalidParameter(String parameter)
    {
        invalidParameters.add(parameter);
    }

    public void addInvalidParametersList(List<String> parameter) {
        invalidParameters.addAll(parameter);
    }

}
