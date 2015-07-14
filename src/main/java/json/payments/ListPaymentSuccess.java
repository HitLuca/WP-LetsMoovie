package json.payments;

import com.google.gson.annotations.Expose;
import json.OperationResult;

import java.util.List;

/**
 * Created by etrunon on 14/07/15.
 */
public class ListPaymentSuccess implements OperationResult {

    @Expose
    List<Payments> paymentsList;

    public ListPaymentSuccess(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }
}
