package types;

/**
 * Created by hitluca on 06/07/15.
 */
public class DetailedPayment {

    private String payment_date;
    private String payment_time;
    private String ticket_type;
    private float price;
    private int row;
    private int column;
    private int room_number;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom__number(int room_number) {
        this.room_number = room_number;
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
