package json.reservation.request;

import com.google.gson.annotations.Expose;
import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by marco on 17/07/15.
 */
public class SeatReservation {
    @Expose
    String s_row;
    @Expose
    String s_column;
    @Expose
    String tycket_type;

    public SeatReservation(String s_row, String col, String tycket_type) {
        this.s_row = s_row;
        this.s_column = col;
        this.tycket_type = tycket_type;
    }

    @toSanitize(name = "s_row", reg = Regex.INTEGER)
    public String getRow() {
        return s_row;
    }

    @toSanitize(name = "s_column", reg = Regex.INTEGER)
    public String getColumn() {
        return s_column;
    }

    public int getIntRow() {
        return Integer.valueOf(s_row);
    }

    public int getIntColumn() {
        return Integer.valueOf(s_column);
    }

    public void setRow(int s_row) {
        this.s_row = String.valueOf(s_row);
    }

    public void setColumn(int s_column) {
        this.s_column = String.valueOf(s_column);
    }

    public String getTicket_type() {
        return tycket_type;
    }

    public void setTicket_type(String tycket_type) {
        this.tycket_type = tycket_type;
    }
}
