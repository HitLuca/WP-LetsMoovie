package database.datatypes.film;

/**
 * Created by hitluca on 16/07/15.
 */
public class FilmIncome {
    private int id_film;
    private float income;

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
}
