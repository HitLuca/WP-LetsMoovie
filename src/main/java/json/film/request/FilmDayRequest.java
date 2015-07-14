package json.film.request;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmDayRequest {

    private String date;

    @toSanitize(name = "date", reg = Regex.DATE)
    public String getdate() {
        return date;
    }

}
