package json.film;

import com.google.gson.annotations.Expose;
import database.datatypes.show.ShowIdTime;
import json.OperationResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Wrapper per una lista di FilmAndShows
 * Created by etrunon on 14/07/15.
 */
public class FilmList implements OperationResult {

    public List<FilmAndShows> getFilmAndShowsList() {
        return filmAndShowsList;
    }

    @Expose
    List<FilmAndShows> filmAndShowsList;

    public FilmList() {
        this.filmAndShowsList = new ArrayList<>();
    }

    public void addId(LocalDate date, int id, List<ShowIdTime> shows) {

        //cerco nella lista se ho un filmAndShows con id = id e
        for (FilmAndShows f : filmAndShowsList) {

            //Se trovo già un filmAndShows con quell'ID, aggiungo la lista di show del giorno in questione
            if (f.getId_film() == id) {
                f.addHours(date.toString(), shows);
                return;
            }
        }

        //Altrimenti non l'ho trovato e devo crearne uno nuovo
        FilmAndShows filmAndShows = new FilmAndShows(date.toString(), id, shows);
        filmAndShowsList.add(filmAndShows);

    }

    @Override
    public String toString() {
        return "FilmList{" +
                "filmAndShowsList=" + filmAndShowsList +
                '}';
    }

}
