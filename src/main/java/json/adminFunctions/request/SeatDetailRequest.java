package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 23/07/15.
 */
public class SeatDetailRequest {
    private int row;
    private int column;
    private String ticket_type;
    private float price;

    @toSanitize(name = "row", reg = Regex.ID)
    public int getRow() {
        return row;
    }

    @toSanitize(name = "row", reg = Regex.ID)
    public int getColumn() {
        return column;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    @toSanitize(name = "row", reg = Regex.ID)
    public float getPrice() {
        return price;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
