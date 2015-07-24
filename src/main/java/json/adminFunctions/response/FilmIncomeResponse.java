package json.adminFunctions.response;

import com.google.gson.annotations.Expose;
import database.datatypes.film.FilmData;
import json.OperationResult;

/**
 * Created by hitluca on 22/07/15.
 */
public class FilmIncomeResponse implements OperationResult {
    @Expose
    private String film_title;
    @Expose
    private int year;
    @Expose
    private float income;

    public FilmIncomeResponse(String film_title, int year, float income) {
        this.film_title = film_title;
        this.year = year;
        this.income = income;
    }

    public FilmIncomeResponse(FilmData f, float i) {
        this.film_title = f.getFilm_title();
        this.year = f.getYear();
        this.income = i;
    }
}
