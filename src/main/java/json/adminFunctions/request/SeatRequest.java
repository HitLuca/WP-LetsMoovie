package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 20/07/15.
 */
public class SeatRequest {
    private String id_seat;
    private String id_show;
    private String status;

    @toSanitize(name = "id_seat", reg = Regex.ID)
    public String getId_seat() {
        return id_seat;
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

    public void setId_seat(String id_seat) {
        this.id_seat = id_seat;
    }

    public void setId_show(String id_show) {
        this.id_show = id_show;
    }
}
