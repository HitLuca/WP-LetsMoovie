package json.film;

import com.google.gson.annotations.Expose;

/**
 * Created by etrunon on 24/07/15.
 */
public class AbsFilm {
    @Expose
    protected int id_film;
    @Expose
    protected String film_title;
    @Expose
    protected int year;
}
