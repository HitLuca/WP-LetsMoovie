package database.datatypes;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by etrunon on 14/07/15.
 */
public class ShowIdTime {

    @Expose
    private int id_show;
    @Expose
    private Time show_time;

    public int getId_show() {
        return id_show;
    }

    public void setId_show(int id_show) {
        this.id_show = id_show;
    }

    public Time getShow_time() {
        return show_time;
    }

    public void setShow_time(Time show_time) {
        this.show_time = show_time;
    }
}
