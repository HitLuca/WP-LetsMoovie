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
public class FilmAndShowListSuccess implements OperationResult {

    @Expose
    List<FilmAndShows> filmAndShowsList;

    public FilmAndShowListSuccess(List<FilmAndShows> filmAndShowsList) {
        this.filmAndShowsList = filmAndShowsList;
    }

    public FilmAndShowListSuccess(FilmList filmList) {
        this.filmAndShowsList = filmList.getFilmAndShowsList();
    }

    public FilmAndShowListSuccess() {
        filmAndShowsList = new ArrayList<FilmAndShows>();
    }

    public void addFilm(FilmAndShows filmAndShows) {
        filmAndShowsList.add(filmAndShows);
    }
}
