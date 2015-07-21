package json.film;

import com.google.gson.annotations.Expose;
import database.datatypes.film.FilmData;
import database.datatypes.show.ShowIdTime;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto da sparare fuori al frontEnd come Lista di FilmAndShows (con i dati del film) e un array di
 * Shows che contengono la data degli spettacoli e le ore.
 * <p/>
 * Created by etrunon on 13/07/15.
 */
public class FilmAndShows extends Film {

    private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Expose
    private List<Shows> shows;


    public FilmAndShows(FilmData filmData, List<ShowIdTime> hours, FilmMapper filmMapper) {
        super(filmData, filmMapper);
        shows = new ArrayList<>();
    }

    public FilmAndShows(FilmData filmData, FilmMapper filmMapper) {
        super(filmData, filmMapper);
        shows = new ArrayList<>();
    }


    public FilmAndShows(String date, int id_film, List<ShowIdTime> shows) {

        this.id_film = id_film;
        this.shows = new ArrayList<>();
        addHours(date, shows);
    }

    public void addHours(String date, List<ShowIdTime> hours) {

        Shows shows1 = new Shows(date, hours);
        shows.add(shows1);
    }

    public void queryShowsWeek(LocalDate day, ShowMapper showMapper) {
        for (int i = 0; i < 7; i++) {
            List<ShowIdTime> temp = showMapper.getShowTimeAndId(day.format(dayFormat), this.id_film);
            if (temp.size() != 0)
                this.addHours(day.format(dayFormat), temp);
            day = day.plusDays(1);
        }
    }
}

class Shows {

    @Expose
    private String date;
    @Expose
    private List<ShowIdTime> orari;

    public Shows(String date, List<ShowIdTime> orari) {
        this.date = date;
        this.orari = orari;
    }
}