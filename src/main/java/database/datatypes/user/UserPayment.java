package database.datatypes.user;

import com.google.gson.annotations.Expose;
import json.OperationResult;

/**
 * Created by hitluca on 21/07/15.
 */
public class UserPayment implements OperationResult {

    private String ticket_type;
    private float price;
    private int row;
    private int column;

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

    public int getRow() {
        return row;
    }

    public void sets_row(int s_row) {
        this.row = s_row;
    }

    public int getColumn() {
        return column;
    }

    public void sets_column(int s_column) {
        this.column = s_column;
    }
}
