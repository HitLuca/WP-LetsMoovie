package json.payments;

import com.google.gson.annotations.Expose;
import database.datatypes.user.CompletePayments;
import database.mappers.UserMapper;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 14/07/15.
 */
public class ListPastPayment implements OperationResult {

    @Expose
    private List<PastPayment> pastPaymentList;


    public ListPastPayment(List<CompletePayments> cp, UserMapper u, String username) {

        pastPaymentList = new ArrayList<>();

        for (CompletePayments c : cp) {
            pastPaymentList.add(new PastPayment(c, u, username, c.getCode()));
        }
    }
}
