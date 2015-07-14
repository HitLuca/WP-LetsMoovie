package database.datatypes;

/**
 * Created by hitluca on 06/07/15.
 */
public class FilmData {

    private int id_film;
    private String film_title;
    private String poster;
    private int duration;
    private String trailer;
    private int metascore;
    private float rating;
    private int year;
    private String plot;
    private String director;
    private int vm;

    public int getVm() {
        return vm;
    }

    public void setVm(int vm) {
        this.vm = vm;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getFilm_title() {
        return film_title;
    }

    public void setFilm_title(String film_title) {
        this.film_title = film_title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "FilmData{" +
                "id_film=" + id_film +
                ", film_title='" + film_title + '\'' +
                ", poster='" + poster + '\'' +
                ", duration=" + duration +
                ", trailer='" + trailer + '\'' +
                ", metascore=" + metascore +
                ", rating=" + rating +
                ", year=" + year +
                ", plot='" + plot + '\'' +
                ", director='" + director + '\'' +
                ", vm=" + vm +
                '}';
    }
}
