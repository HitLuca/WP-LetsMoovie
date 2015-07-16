package database.mappers;

import database.datatypes.film.Actor;
import database.datatypes.film.FilmData;
import database.datatypes.film.FilmIncome;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface FilmMapper {

    @Select("SELECT id_film FROM films WHERE film_title= #{film_title} AND year= #{year}")
    Integer getFilmId(@Param("film_title") String film_title, @Param("year") int year);

    @Select("SELECT * FROM films WHERE id_film=#{id_film}")
    FilmData getFilmData(int id_film);

    @Select("SELECT genre FROM film_genres WHERE id_film=#{id_film}")
    List<String> getFilmGenres(int id_film);

    @Select("SELECT * FROM film_actors NATURAL JOIN actors WHERE id_film=#{id_film}")
    List<Actor> getFilmActors(int id_film);

    @Select("SELECT url_photo FROM actors WHERE actor_name=#{actor_name}")
    String getActorPhoto(String actor_name);

    @Insert("INSERT INTO films (film_title, poster, duration, trailer, metascore, rating, year, plot, director, vm) " +
            "VALUES (" +
            "#{film_title}, " +
            "#{poster}, " +
            "#{duration}, " +
            "#{trailer}, " +
            "#{metascore}, " +
            "#{rating}, " +
            "#{year}, " +
            "#{plot}, " +
            "#{director}, " +
            "#{vm})")
    void insertFilm(FilmData film);

    @Insert("INSERT INTO actors (actor_name, url_photo) VALUES (#{actor_name}, #{url_photo})")
    void insertActor(Actor actor);

    @Insert("INSERT INTO film_actors (id_film, actor_name, role)  VALUES (#{id_film}, #{actor_name}, #{role})")
    void insertFilmActor(Actor actor);

    @Insert("INSERT INTO film_genres (id_film, genre) VALUES (#{id_film}, #{genre})")
    void insertGenre(@Param("id_film") int id_film, @Param("genre") String genre);

    @Delete("DELETE FROM films WHERE id_film=#{id_film}")
    void deleteFilm(int id_film);

    @Delete("DELETE FROM actors WHERE actor_name=#{actor_name}")
    void deleteActor(String actor_name);

    @Delete("DELETE FROM film_actors WHERE id_film=#{id_film} AND actor_name=#{actor_name} AND role=#{role}")
    void deleteFilmActor(Actor actor);

    @Delete("DELETE FROM film_genres WHERE id_film=#{id_film} AND genre=#{genre}")
    void deleteFilmGenre(@Param("id_film") int id_film, @Param("genre") String genre);

    @Select("SELECT * FROM films")
    List<FilmData> getAllFilms();

    @Update("UPDATE films SET poster=#{poster}, plot=#{plot} WHERE id_film=#{id_film}")
    void updateFilm(FilmData f);

    @Select("SELECT * FROM actors")
    List<Actor> getAllActors();

    @Update("UPDATE actors SET url_photo=#{url_photo} WHERE actor_name=#{actor_name}")
    void updateActor(Actor a);

    //TODO:Test
    @Select("SELECT SUM(price) FROM shows NATURAL JOIN prices NATURAL JOIN payments WHERE id_film=#{id_film}")
    float getFilmIncome(int id_film);

    //TODO:Test
    @Select("SELECT id_film, SUM(price) FROM shows NATURAL JOIN prices NATURAL JOIN payments GROUP BY id_film")
    List<FilmIncome> getAllFilmsIncome();
}
