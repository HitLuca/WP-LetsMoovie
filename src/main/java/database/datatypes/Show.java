package database.datatypes;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by hitluca on 06/07/15.
 */
public class Show {

    private int id_show;
    private int room_number;
    private int id_film;
    private Date show_date;
    private Time show_time;

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public Date getShow_date() {
        return show_date;
    }

    public void setShow_date(Date show_date) {
        this.show_date = show_date;
    }

    public Time getShow_time() {
        return show_time;
    }

    public void setShow_time(Time show_time) {
        this.show_time = show_time;
    }

    public int getId_show() {
        return id_show;
    }

    public void setId_show(int id_show) {
        this.id_show = id_show;
    }
}
