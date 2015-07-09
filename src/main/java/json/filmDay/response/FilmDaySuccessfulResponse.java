package json.filmDay.response;

import com.google.gson.annotations.Expose;
import database.datatypes.FilmData;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmDaySuccessfulResponse implements OperationResult {

    @Expose
    List<FilmDayShows> filmList;

    public FilmDaySuccessfulResponse(List<FilmDayShows> filmList) {
        this.filmList = filmList;
    }

    public FilmDaySuccessfulResponse() {
        filmList = new ArrayList<FilmDayShows>();
    }

    public void addFilm(FilmDayShows film) {
        filmList.add(film);
    }
}
