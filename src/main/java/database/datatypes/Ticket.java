package database.datatypes;

/**
 * Created by root on 07/07/15.
 */
public class Ticket {
    String ticket_type;
    float price;

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
