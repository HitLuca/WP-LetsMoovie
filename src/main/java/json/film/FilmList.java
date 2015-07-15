package json.film;

import com.google.gson.annotations.Expose;
import database.datatypes.ShowIdTime;
import json.OperationResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 14/07/15.
 */
public class FilmList implements OperationResult {

    public List<Film> getFilmList() {
        return filmList;
    }

    @Expose
    List<Film> filmList;

    public FilmList() {
        this.filmList = new ArrayList<>();
    }

    public void addId(LocalDate date, int id, List<ShowIdTime> shows) {

        //cerco nella lista se ho un film con id = id e
        for (Film f : filmList) {

            //Se trovo già un film con quell'ID, aggiungo la lista di show del giorno in questione
            if (f.getId_film() == id) {
                f.addHours(date.toString(), shows);
            }
        }

        //Altrimenti non l'ho trovato e devo crearne uno nuovo
        Film film = new Film(date.toString(), id, shows);
        filmList.add(film);

    }

    @Override
    public String toString() {
        return "FilmList{" +
                "filmList=" + filmList +
                '}';
    }

}
