package types.exceptions;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import types.enums.ErrorCode;

/**
 * Created by etrunon on 06/07/15.
 */
public class BadRequestException extends Exception implements OperationResult{
    @Expose private ErrorCode code;

    public ErrorCode getCode() {
        return code;
    }

    public BadRequestException(ErrorCode code) {
        this.code = code;
    }

}
