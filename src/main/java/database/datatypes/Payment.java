package database.datatypes;

/**
 * Created by hitluca on 14/07/15.
 */
public class Payment {
    private String payment_date;
    private String payment_time;
    private String ticket_type;
    private int id_seat;
    private int id_show;
    private String username;

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
