package json.tickets;

import database.datatypes.user.Payment;
import json.reservation.request.SeatReservation;

/**
 * Created by etrunon on 23/07/15.
 */
public class TicketData {

    private String film_title;
    private String date;
    private String time;
    private String s_row;
    private String s_column;
    private String username;

    public String getCode() {
        return code;
    }

    private String code;

    public String getUsername() {
        return username;
    }

    public TicketData(String film_title, String date, String time, String s_row, String s_column, String username, String code) {
        this.username = username;
        this.film_title = film_title;
        this.date = date;
        this.time = time;
        this.s_row = s_row;
        this.s_column = s_column;
        this.username = username;
        this.code = code;

    }

    public TicketData(Payment p, SeatReservation sr, String filmtitle) {
        this.username = p.getUsername();
        this.film_title = filmtitle;
        this.date = p.getPayment_date();
        this.time = p.getPayment_time();
        this.s_row = sr.getRow();
        this.s_column = sr.getColumn();
        this.username = p.getUsername();
        this.code = p.getCode();

    }

    public String getFilm_title() {
        return film_title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getS_row() {
        return s_row;
    }

    public String getS_column() {
        return s_column;
    }
}
