package json.film.response;

import com.google.gson.annotations.Expose;
import database.datatypes.film.FilmData;
import json.OperationResult;
import json.film.Film;

/**
 * Created by etrunon on 14/07/15.
 */
public class FilmSuccess implements OperationResult {

    @Expose
    Film filmData;

    public FilmSuccess(FilmData filmData) {
        this.filmData = new Film(filmData);
    }
}
