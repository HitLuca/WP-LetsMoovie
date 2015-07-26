package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 23/07/15.
 */
public class SeatDetailRequest {
    private String s_row;
    private String s_column;
    private String ticket_type;
    private float price;
    private String checked;

    @toSanitize(name = "s_row", reg = Regex.ID)
    public String getS_row() {
        return s_row;
    }

    @toSanitize(name = "s_column", reg = Regex.ID)
    public String getS_column() {
        return s_column;
    }

    public void setS_row(String row) {
        this.s_row = row;
    }

    public void setS_column(String column) {
        this.s_column = column;
    }


    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public SeatDetailRequest(String s_row, String s_column) {
        this.s_row = s_row;
        this.s_column = s_column;
    }

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
}
