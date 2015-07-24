package json.adminFunctions.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by hitluca on 22/07/15.
 */
public class FilmIncomeRequest {
    private String id_film;

    @toSanitize(name = "id_film", reg = Regex.INTEGER)
    public String getId_film() {
        return id_film;
    }
}
