package database.mappers;

import database.datatypes.film.Actor;
import database.datatypes.film.FilmData;
import database.datatypes.film.FilmIncome;
import database.datatypes.film.FilmTitle;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface FilmMapper {

    /**
     * @param film_title titolo del film
     * @param year       anno del film
     * @return id_film associato
     */
    @Select("SELECT id_film " +
            "FROM films " +
            "WHERE film_title= #{film_title} AND year= #{year}")
    Integer getFilmId(@Param("film_title") String film_title, @Param("year") int year);

    /**
     * @param id_film id del film
     * @return dati riguardanti id_film come trama, durata, trailer ecc.
     */
    @Select("SELECT * " +
            "FROM films " +
            "WHERE id_film=#{id_film}")
    FilmData getFilmData(int id_film);

    /**
     * @param id_film id del film
     * @return tutti i generi di id_film
     */
    @Select("SELECT genre " +
            "FROM film_genres " +
            "WHERE id_film=#{id_film}")
    List<String> getFilmGenres(int id_film);

    /**
     *
     * @param id_film id del film
     * @return tutti gli attori di id_film
     */
    @Select("SELECT * " +
            "FROM film_actors NATURAL JOIN actors " +
            "WHERE id_film=#{id_film}")
    List<Actor> getFilmActors(int id_film);

    /**
     *
     * @param actor_name nome dell'attore
     * @return la foto dell'attore
     */
    @Select("SELECT url_photo " +
            "FROM actors " +
            "WHERE actor_name=#{actor_name}")
    String getActorPhoto(String actor_name);

    /**
     *
     * @param filmData oggetto FilmData
     */
    @Insert("INSERT INTO films (film_title, poster, duration, trailer, metascore, rating, year, plot, director, vm) " +
            "VALUES (#{film_title}, #{poster}, #{duration}, #{trailer}, #{metascore}, #{rating}, #{year}, #{plot}, #{director}, #{vm})")
    void insertFilm(FilmData filmData);

    /**
     *
     * @param actor oggetto Actor
     */
    @Insert("INSERT INTO actors (actor_name, url_photo) " +
            "VALUES (#{actor_name}, #{url_photo})")
    void insertActor(Actor actor);

    /**
     *
     * @param actor oggetto Actor
     */
    @Insert("INSERT INTO film_actors (id_film, actor_name, role)  " +
            "VALUES (#{id_film}, #{actor_name}, #{role})")
    void insertFilmActor(Actor actor);

    /**
     *
     * @param id_film id del film
     * @param genre genere del film
     */
    @Insert("INSERT INTO film_genres (id_film, genre) " +
            "VALUES (#{id_film}, #{genre})")
    void insertGenre(@Param("id_film") int id_film, @Param("genre") String genre);

    /**
     *
     * @param id_film id del film
     */
    @Delete("DELETE " +
            "FROM films " +
            "WHERE id_film=#{id_film}")
    void deleteFilm(int id_film);

    /**
     *
     * @param actor_name nome dell'attore
     */
    @Delete("DELETE " +
            "FROM actors " +
            "WHERE actor_name=#{actor_name}")
    void deleteActor(String actor_name);

    /**
     *
     * @param actor oggetto Actor
     */
    @Delete("DELETE FROM film_actors " +
            "WHERE id_film=#{id_film} AND actor_name=#{actor_name} AND role=#{role}")
    void deleteFilmActor(Actor actor);

    /**
     *
     * @param id_film id del film
     * @param genre genere del film
     */
    @Delete("DELETE FROM film_genres " +
            "WHERE id_film=#{id_film} AND genre=#{genre}")
    void deleteFilmGenre(@Param("id_film") int id_film, @Param("genre") String genre);

    /**
     *
     * @return lista dei dati di tutti i film
     */
    @Select("SELECT * " +
            "FROM films")
    List<FilmData> getAllFilms();

    /**
     *
     * @param filmData oggetto FilmData
     */
    @Update("UPDATE films " +
            "SET poster=#{poster}, plot=#{plot} " +
            "WHERE id_film=#{id_film}")
    void updateFilm(FilmData filmData);

    /**
     *
     * @return lista dei dati di tutti gli attori
     */
    @Select("SELECT * " +
            "FROM actors")
    List<Actor> getAllActors();

    /**
     *
     * @param actor oggetto Actor
     */
    @Update("UPDATE actors " +
            "SET url_photo=#{url_photo} " +
            "WHERE actor_name=#{actor_name}")
    void updateActor(Actor actor);

    /**
     *
     * @param id_film id del film
     * @return totale degli incassi di id_film
     */
    @Select("SELECT SUM(price) " +
            "FROM shows NATURAL JOIN prices NATURAL JOIN payments " +
            "WHERE id_film=#{id_film}")
    float getFilmIncome(int id_film);

    /**
     *
     * @return lista degli incassi totali di ogni film
     */
    @Select("SELECT id_film, SUM(price) as income " +
            "FROM shows NATURAL JOIN prices NATURAL JOIN payments " +
            "GROUP BY id_film")
    List<FilmIncome> getAllFilmsIncome();

    /**
     * @param id_film id del film
     * @return il titolo del film
     */
    @Select("SELECT film_title, year " +
            "FROM films " +
            "WHERE id_film=#{id_film}")
    FilmTitle getFilmTitle(int id_film);
}
