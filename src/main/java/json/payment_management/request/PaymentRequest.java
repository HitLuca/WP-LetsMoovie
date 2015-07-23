package json.payment_management.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 22/07/15.
 */
public class PaymentRequest {
    private String code;
    private String credit_card_number;

    public String getCode() {
        return code;
    }

    @toSanitize(name = "credit_card_number", reg = Regex.ID)
    public String getCredit_card_number() {
        return credit_card_number;
    }
}
