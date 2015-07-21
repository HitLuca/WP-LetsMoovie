package json.userPersonalData;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by etrunon on 21/07/15.
 */
public class UserCreditResponse implements OperationResult {

    @Expose
    float credit;

    public UserCreditResponse(float credit) {
        this.credit = credit;
    }
}
