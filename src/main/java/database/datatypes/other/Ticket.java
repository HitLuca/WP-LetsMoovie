package database.datatypes.other;

import com.google.gson.annotations.Expose;

/**
 * Created by root on 07/07/15.
 */
public class Ticket {

    @Expose
    String ticket_type;
    @Expose
    float price;
    @Expose
    String descripiton;

    public String getDescripiton() {
        return descripiton;
    }

    public void setDescripiton(String descripiton) {
        this.descripiton = descripiton;
    }

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
