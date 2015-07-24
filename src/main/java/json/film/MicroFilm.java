package json.film;

import com.google.gson.annotations.Expose;
import database.datatypes.film.FilmData;

/**
 * Created by etrunon on 24/07/15.
 */
public class MicroFilm extends AbsFilm {


    public MicroFilm() {
    }

    public MicroFilm(int id_film, String film_title, int year) {
        this.id_film = id_film;
        this.film_title = film_title;
        this.year = year;
    }

    public MicroFilm(FilmData filmData) {
        this.id_film = filmData.getId_film();
        this.film_title = filmData.getFilm_title();
        this.year = filmData.getYear();
    }
}
