package json.film.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import json.film.Film;
import json.film.FilmList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmListSuccess implements OperationResult {

    @Expose
    List<Film> filmList;

    public FilmListSuccess(List<Film> filmList) {
        this.filmList = filmList;
    }

    public FilmListSuccess(FilmList filmList) {
        this.filmList = filmList.getFilmList();
    }

    public FilmListSuccess() {
        filmList = new ArrayList<Film>();
    }

    public void addFilm(Film film) {
        filmList.add(film);
    }
}
