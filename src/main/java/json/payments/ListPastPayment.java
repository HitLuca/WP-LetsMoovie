package json.payments;

import com.google.gson.annotations.Expose;
import json.OperationResult;

import java.util.List;

/**
 * Created by etrunon on 14/07/15.
 */
public class ListPastPayment implements OperationResult {

    @Expose
    List<PastPayment> pastPaymentList;

    public ListPastPayment(List<PastPayment> pastPaymentList) {
        this.pastPaymentList = pastPaymentList;
    }
}
