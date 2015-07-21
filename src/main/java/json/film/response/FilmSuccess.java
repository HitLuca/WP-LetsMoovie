package json.film.response;

import com.google.gson.annotations.Expose;
import database.datatypes.film.FilmData;
import json.OperationResult;
import json.film.FilmAndShows;

/**
 * Created by etrunon on 14/07/15.
 */
public class FilmSuccess implements OperationResult {

    @Expose
    FilmAndShows filmAndShowsData;

    public FilmSuccess(FilmAndShows filmAndShowsData) {
        this.filmAndShowsData = filmAndShowsData;
    }
}
