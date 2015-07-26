package json.tickets;

import database.datatypes.user.Payment;
import database.mappers.NotDecidedMapper;
import database.mappers.ShowMapper;
import json.reservation.request.SeatReservation;

/** Oggetto che rappresenta un biglietto acquistato
 * Created by etrunon on 23/07/15.
 */
public class TicketData {

    private String film_title;
    private String date;
    private String time;
    private String s_row;
    private String s_column;
    private String username;
    private String showTime;
    private String showDate;
    private String ticketType;
    private int showRoom;
    private float price;

    public TicketData(String film_title, String date, String time, String s_row, String s_column, String username, String showTime, String showDate, String ticketType, float price, String code, int showRoom) {
        this.film_title = film_title;
        this.date = date;
        this.time = time;
        this.s_row = s_row;
        this.s_column = s_column;
        this.username = username;
        this.showTime = showTime;
        this.showDate = showDate;
        this.ticketType = ticketType;
        this.price = price;
        this.code = code;
        this.showRoom = showRoom;
    }

    public TicketData(Payment p, SeatReservation sr, String filmTitle, NotDecidedMapper notDecidedMapper, ShowMapper showMapper) {
        this.username = p.getUsername();
        this.film_title = filmTitle;
        this.date = p.getPayment_date();
        this.time = p.getPayment_time();

        this.s_row = String.valueOf(Integer.valueOf(sr.getRow()) + 1);
        this.s_column = String.valueOf(Integer.valueOf(sr.getColumn()) + 1);

        this.username = p.getUsername();
        this.code = p.getCode();
        this.showTime = showMapper.getShowTime(p.getId_show()).getShow_time();
        this.showDate = showMapper.getShowTime(p.getId_show()).getShow_date();
        this.ticketType = sr.getTicket_type();
        this.price = notDecidedMapper.getPrice(sr.getTicket_type());
        this.showRoom = showMapper.getRoomNumber(p.getId_show());

    }

    public int getShowRoom() {
        return showRoom;
    }

    public String getCode() {
        return code;
    }

    private String code;

    public String getUsername() {
        return username;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getShowDate() {
        return showDate;
    }

    public String getTicketType() {
        return ticketType;
    }

    public float getPrice() {
        return price;
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
