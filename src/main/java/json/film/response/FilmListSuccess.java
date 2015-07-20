package json.film.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import json.film.FilmAndShows;
import json.film.FilmList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmListSuccess implements OperationResult {

    @Expose
    List<FilmAndShows> filmAndShowsList;

    public FilmListSuccess(List<FilmAndShows> filmAndShowsList) {
        this.filmAndShowsList = filmAndShowsList;
    }

    public FilmListSuccess(FilmList filmList) {
        this.filmAndShowsList = filmList.getFilmAndShowsList();
    }

    public FilmListSuccess() {
        filmAndShowsList = new ArrayList<FilmAndShows>();
    }

    public void addFilm(FilmAndShows filmAndShows) {
        filmAndShowsList.add(filmAndShows);
    }
}
