package json.film;

import com.google.gson.annotations.Expose;
import database.datatypes.film.Actor;
import database.datatypes.film.FilmData;
import database.mappers.FilmMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 17/07/15.
 */
public class Film {

    @Expose
    protected int id_film;
    @Expose
    protected String film_title;
    @Expose
    protected String poster;
    @Expose
    protected int duration;
    @Expose
    protected String trailer;
    @Expose
    protected int metascore;
    @Expose
    protected float rating;
    @Expose
    protected int year;
    @Expose
    protected String plot;
    @Expose
    protected String director;
    @Expose
    protected int vm;
    @Expose
    protected List<Genre> genres;
    @Expose
    protected List<Actor> actors;


    public void setData(FilmData filmData, FilmMapper filmMapper) {
        this.id_film = filmData.getId_film();
        this.film_title = filmData.getFilm_title();
        this.poster = filmData.getPoster();
        this.duration = filmData.getDuration();
        this.trailer = filmData.getTrailer();
        this.metascore = filmData.getMetascore();
        this.rating = filmData.getRating();
        this.year = filmData.getYear();
        this.plot = filmData.getPlot();
        this.director = filmData.getDirector();
        this.vm = filmData.getVm();

        genres = new ArrayList<>();
        for (String s : filmMapper.getFilmGenres(id_film))
            genres.add(new Genre(s));

        actors = filmMapper.getFilmActors(id_film);

    }

    public Film(FilmData filmData, FilmMapper filmMapper) {
        setData(filmData, filmMapper);
    }

    public Film(int id_film) {

        this.id_film = id_film;
    }

    public Film() {

    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_film() {
        return id_film;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Film)
            return id_film == ((Film) o).getId_film();
        else
            return false;

    }

    @Override
    public int hashCode() {
        return id_film;
    }
}
