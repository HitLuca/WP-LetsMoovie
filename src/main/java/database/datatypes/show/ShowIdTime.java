package database.datatypes.show;

import com.google.gson.annotations.Expose;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by etrunon on 14/07/15.
 */
public class ShowIdTime {

    @Expose
    private int id_show;
    @Expose
    private String show_time;
    @Expose
    private int room_number;

    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");


    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public int getId_show() {
        return id_show;
    }

    public void setId_show(int id_show) {
        this.id_show = id_show;
    }

    public String getShow_time() { return show_time;    }

    public void convertTime(){

        LocalTime localTime = LocalTime.parse(show_time);
        show_time = localTime.format(timeFormat);
    }

}
