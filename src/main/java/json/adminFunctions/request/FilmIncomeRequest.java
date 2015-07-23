package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 22/07/15.
 */
public class FilmIncomeRequest {
    private String film_title;
    private String year;

    @toSanitize(name = "year", reg = Regex.ID)
    public String getYear() {
        return year;
    }

    public String getFilm_title() {
        return film_title;
    }
}
