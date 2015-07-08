package types.exceptions;

import com.google.gson.annotations.Expose;
import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/07/15.
 */
public class BadParametersException extends BadRequestException {
    @Expose private List<String> invalidParameters;

    public BadParametersException(ErrorCode code,List<String> invalidParameters) {
        super(code);
        this.invalidParameters = invalidParameters;
    }

    public BadParametersException(ErrorCode code,String invalidParameter)
    {
        super(code);
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }

    public List<String> getInvalidParameters()
    {
        return invalidParameters;
    }
}
