package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 20/07/15.
 */
public class SeatRequest {
    private String row;
    private String column;
    private String id_show;
    private String status;

    @toSanitize(name = "row", reg = Regex.ID)
    public String getRow() {
        return row;
    }

    @toSanitize(name = "column", reg = Regex.ID)
    public String getColumn() {
        return column;
    }

    @toSanitize(name = "id_show", reg = Regex.ID)
    public String getId_show() {
        return id_show;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_show(String id_show) {
        this.id_show = id_show;
    }
}
