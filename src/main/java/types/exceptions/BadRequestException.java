package types.exceptions;

import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 06/07/15.
 */
public class BadRequestException extends Exception {
    private ErrorCode code;

    public ErrorCode getCode() {
        return code;
    }

    public BadRequestException(ErrorCode code) {
        this.code = code;
    }

}
