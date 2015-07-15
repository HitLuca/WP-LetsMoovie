package database.datatypes;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by hitluca on 14/07/15.
 */
public class Payment {
    Date payment_date;
    Time payment_time;
    String ticket_type;
    int id_seat;
    int id_show;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Time getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Time payment_time) {
        this.payment_time = payment_time;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public int getId_seat() {
        return id_seat;
    }

    public void setId_seat(int id_seat) {
        this.id_seat = id_seat;
    }

    public int getId_show() {
        return id_show;
    }

    public void setId_show(int id_show) {
        this.id_show = id_show;
    }

    public void setShow_date(String payment_date) {
        this.payment_date = java.sql.Date.valueOf(payment_date);
    }

    public void setShow_time(String payment_time) {
        this.payment_time = java.sql.Time.valueOf(payment_time);
    }
}
