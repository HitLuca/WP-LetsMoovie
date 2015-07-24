package json.film.response;

import com.google.gson.annotations.Expose;
import json.OperationResult;
import json.film.AbsFilm;
import json.film.Film;
import json.film.MicroFilm;

import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class FilmListSuccess implements OperationResult {

    @Expose
    List<AbsFilm> filmList;

    public FilmListSuccess(List<AbsFilm> filmList) {
        this.filmList = filmList;
    }

}
