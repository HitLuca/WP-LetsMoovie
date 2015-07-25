package json.tickets;

/**
 * Created by etrunon on 23/07/15.
 */
public class TicketData {

    private String film_title;
    private String date;
    private String time;
    private String s_row;
    private String s_column;
    private String username;

    public String getCode() {
        return code;
    }

    private String code;

    public String getUsername() {
        return username;
    }

    public TicketData(String film_title, String date, String time, String s_row, String s_column, String username, String code) {
        this.username = username;
        this.film_title = film_title;
        this.date = date;
        this.time = time;
        this.s_row = s_row;
        this.s_column = s_column;
        this.username = username;
        this.code = code;

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
