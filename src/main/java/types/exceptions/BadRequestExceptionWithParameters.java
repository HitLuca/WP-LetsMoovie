package types.exceptions;

import com.google.gson.annotations.Expose;
import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class BadRequestExceptionWithParameters extends BadRequestException {
    @Expose private List<String> parameters;

    public BadRequestExceptionWithParameters(ErrorCode code, List<String> invalidParameters) {
        super(code);
        this.parameters = invalidParameters;
    }

    public BadRequestExceptionWithParameters(ErrorCode code, String invalidParameter)
    {
        super(code);
        parameters = new ArrayList<String>();
        parameters.add(invalidParameter);
    }

    public List<String> getInvalidParameters()
    {
        return parameters;
    }
}
