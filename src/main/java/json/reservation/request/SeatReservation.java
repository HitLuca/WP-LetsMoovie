package json.reservation.request;

import com.google.gson.annotations.Expose;
import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by marco on 17/07/15.
 */
public class SeatReservation {
    @Expose
    String row;
    @Expose
    String column;
    @Expose
    String type;

    public SeatReservation(String row, String col, String type) {
        this.row = row;
        this.column = col;
        this.type = type;
    }

    @toSanitize(name = "row", reg = Regex.INTEGER)
    public String getRow() {
        return row;
    }

    @toSanitize(name = "column", reg = Regex.INTEGER)
    public String getColumn() {
        return column;
    }

    public int getIntRow() {
        return Integer.valueOf(row);
    }

    public int getIntColumn() {
        return Integer.valueOf(column);
    }

    public void setRow(int row) {
        this.row = String.valueOf(row);
    }

    public void setColumn(int column) {
        this.column = String.valueOf(column);
    }

    public String getTicket_type() {
        return type;
    }

    public void setTicket_type(String type) {
        this.type = type;
    }
}
