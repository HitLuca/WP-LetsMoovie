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
    private String price;
    private boolean checked;

    @toSanitize(name = "row", reg = Regex.ID)
    public String getS_row() {
        return s_row;
    }

    @toSanitize(name = "column", reg = Regex.ID)
    public String getS_column() {
        return s_column;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public String getPrice() {
        return price;
    }

    public void setS_row(String row) {
        this.s_row = row;
    }

    public void setS_column(String column) {
        this.s_column = column;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public SeatDetailRequest(String s_row, String s_column, String ticket_type, String price) {
        this.s_row = s_row;
        this.s_column = s_column;
        this.ticket_type = ticket_type;
        this.price = price;
    }


}
