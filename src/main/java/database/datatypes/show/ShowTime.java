package database.datatypes.show;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by etrunon on 24/07/15.
 */
public class ShowTime {

    private String show_time;
    private String show_date;

    public void setShow_time(String show_time) {
        this.show_time = show_time;
    }

    public String getShow_date() {
        return show_date;
    }

    public String getShow_time() {
        return show_time;
    }

    public void setShow_date(String show_date) {
        this.show_date = show_date;
    }

/*
    public ShowTime(String show_time, String show_date) {
        this.show_time = show_time;
        this.show_date = show_date;
    }
*/

    public LocalTime getLocalTime() {
        return LocalTime.parse(show_time);
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(show_date);
    }
}
