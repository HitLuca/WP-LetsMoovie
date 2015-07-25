package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 23/07/15.
 */
public class SeatDetailRequest {
    private int s_row;
    private int s_column;
    private String ticket_type;
    private float price;
    private boolean checked;

    @toSanitize(name = "row", reg = Regex.ID)
    public int getS_row() {
        return s_row;
    }

    @toSanitize(name = "row", reg = Regex.ID)
    public int getS_column() {
        return s_column;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    @toSanitize(name = "row", reg = Regex.ID)
    public float getPrice() {
        return price;
    }

    public void setS_row(int row) {
        this.s_row = row;
    }

    public void setS_column(int column) {
        this.s_column = column;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public SeatDetailRequest(int s_row, int s_column, String ticket_type, float price) {
        this.s_row = s_row;
        this.s_column = s_column;
        this.ticket_type = ticket_type;
        this.price = price;
    }
}
