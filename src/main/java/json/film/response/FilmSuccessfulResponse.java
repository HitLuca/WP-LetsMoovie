package json.film.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import types.Film;
import types.FilmList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmSuccessfulResponse implements OperationResult {

    @Expose
    List<Film> filmList;

    public FilmSuccessfulResponse(List<Film> filmList) {
        this.filmList = filmList;
    }

    public FilmSuccessfulResponse(FilmList filmList) {
        this.filmList = filmList.getFilmList();
    }

    public FilmSuccessfulResponse() {
        filmList = new ArrayList<Film>();
    }

    public void addFilm(Film film) {
        filmList.add(film);
    }
}
