package json.payments;

import com.google.gson.annotations.Expose;
import database.datatypes.user.UserPayment;

/**
 * Created by etrunon on 24/07/15.
 */
public class SingleSeatPaid {

    @Expose
    private String ticket_type;
    @Expose
    private float price;
    @Expose
    private int s_row;
    @Expose
    private int s_column;

    public SingleSeatPaid(UserPayment u) {
        this.ticket_type = u.getTicket_type();
        this.price = u.getPrice();
        this.s_row = u.getRow();
        this.s_column = u.getColumn();
    }
}
