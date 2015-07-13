package json.filmDay.response;

import com.google.gson.annotations.Expose;
import database.datatypes.FilmData;

/**
 * Created by etrunon on 09/07/15.
 */
public class FilmDayShows {
    @Expose
    private int id_film;
    @Expose
    private String film_title;
    @Expose
    private String poster;
    @Expose
    private int duration;
    @Expose
    private String trailer;
    @Expose
    private int metascore;
    @Expose
    private float rating;
    @Expose
    private int year;
    @Expose
    private String plot;
    @Expose
    private String director;
    @Expose
    private int vm;


    public FilmDayShows(FilmData film) {

        this.id_film = film.getId_film();
        this.film_title = film.getFilm_title();
        this.poster = film.getPoster();
        this.duration = film.getDuration();
        this.trailer = film.getTrailer();
        this.metascore = film.getMetascore();
        this.rating = film.getRating();
        this.year = film.getYear();
        this.plot = film.getPlot();
        this.director = film.getDirector();
        this.vm = film.getVm();
    }
}
