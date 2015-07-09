package types.exceptions;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import types.enums.ErrorCode;

/**
 * Created by etrunon on 06/07/15.
 */
public class BadRequestException extends Exception implements OperationResult{

    private ErrorCode code;
    @Expose
    private int errorCode;

    public BadRequestException(ErrorCode code) {

        this.code = code;
        this.errorCode = code.getValue();
    }

    public BadRequestException()
    {
        code = ErrorCode.BAD_REQUEST;
    }

    public ErrorCode getCode() {
        return code;
    }

}
