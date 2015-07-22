package json.adminData;

import com.google.gson.annotations.Expose;
import database.datatypes.show.Show;
import database.mappers.FilmMapper;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 22/07/15.
 */
public class ShowDataList implements OperationResult {

    private FilmMapper filmMapper;
    @Expose
    List<ShowData> showDataList;

    public ShowDataList(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
        this.showDataList = new ArrayList<>();
    }

    public void addShow(Show s) {
        showDataList.add(new ShowData(s, filmMapper));
    }
}

class ShowData {

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

        this.film_Name = (filmMapper.getFilmTitle(show.getId_film())).getFilm_title();
    }
}
