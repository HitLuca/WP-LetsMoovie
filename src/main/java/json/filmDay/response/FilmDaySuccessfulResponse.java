package json.filmDay.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import types.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmDaySuccessfulResponse implements OperationResult {

    @Expose
    List<Film> filmList;

    public FilmDaySuccessfulResponse(List<Film> filmList) {
        this.filmList = filmList;
    }

    public FilmDaySuccessfulResponse() {
        filmList = new ArrayList<Film>();
    }

    public void addFilm(Film film) {
        filmList.add(film);
    }
}
