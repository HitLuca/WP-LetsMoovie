package json.film.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import json.film.Film;

import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class FilmListSuccess implements OperationResult {

    @Expose
    List<Film> filmList;

    public FilmListSuccess(List<Film> filmList) {
        this.filmList = filmList;
    }
}
