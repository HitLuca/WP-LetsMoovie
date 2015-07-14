package types;

import com.google.gson.annotations.Expose;
import database.datatypes.FilmData;
import database.datatypes.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto da sparare fuori al frontEnd come Lista di Film (con i dati del film) e un array di
 * Shows che contengono la data degli spettacoli e le ore.
 * <p/>
 * Created by etrunon on 13/07/15.
 */
public class Film {

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
    @Expose
    private List<Shows> shows;


    public Film(FilmData filmData, List<String> hours) {

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

        shows = new ArrayList<Shows>();
    }

    public void addHours(String date, List<String> hours) {

        Shows shows1 = new Shows(date, hours);
        shows.add(shows1);
    }
}

class Shows {

    @Expose
    private String date;
    @Expose
    private List<String> orari;

    public Shows(String date, List<String> orari) {
        this.date = date;
        this.orari = orari;
    }


}