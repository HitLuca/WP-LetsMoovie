package database.datatypes.user;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by hitluca on 21/07/15.
 */
public class UserPayment implements OperationResult {

    @Expose
    private String ticket_type;
    @Expose
    private float price;
    @Expose
    private int s_row;
    @Expose
    private int s_column;

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int gets_row() {
        return s_row;
    }

    public void sets_row(int s_row) {
        this.s_row = s_row;
    }

    public int gets_column() {
        return s_column;
    }

    public void sets_column(int s_column) {
        this.s_column = s_column;
    }
}
