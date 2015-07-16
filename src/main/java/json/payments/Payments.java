package json.payments;

import com.google.gson.annotations.Expose;
import database.datatypes.user.DetailedPayment;

/**
 * f
 * Created by etrunon on 14/07/15.
 */
public class Payments {

    @Expose
    private String payment_date;
    @Expose
    private String payment_time;
    @Expose
    private String ticket_type;
    @Expose
    private float price;
    @Expose
    private int row;
    @Expose
    private int column;
    @Expose
    private int room_number;
    @Expose
    private String filmTitle;
    @Expose
    private String username;

    public Payments(DetailedPayment p, String filmTitle) {
        this.payment_date = p.getPayment_date();
        this.payment_time = p.getPayment_time();
        this.ticket_type = p.getTicket_type();
        this.price = p.getPrice();
        this.row = p.getRow();
        this.column = p.getColumn();
        this.room_number = p.getRoom_number();
        this.filmTitle = filmTitle;
        this.username = p.getUsername();
    }
}
