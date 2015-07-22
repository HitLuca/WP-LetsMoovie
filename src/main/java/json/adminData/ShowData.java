package json.adminData;

import com.google.gson.annotations.Expose;
import database.datatypes.show.Show;
import database.mappers.FilmMapper;
import json.OperationResult;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by etrunon on 22/07/15.
 */
public class ShowData implements OperationResult {

    @Expose
    private int id_show;
    @Expose
    private int room_number;
    @Expose
    private String film_Name;
    @Expose
    private String show_date;
    @Expose
    private String show_time;

    public ShowData(Show show, FilmMapper filmMapper) {
        this.id_show = show.getId_show();
        this.room_number = show.getRoom_number();
        this.show_date = show.getShow_date();
        this.show_time = show.getShow_time();

        this.film_Name = filmMapper.getFilmName(show.getId_film());
    }
}
