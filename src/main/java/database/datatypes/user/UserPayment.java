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
    private int row;
    @Expose
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

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
