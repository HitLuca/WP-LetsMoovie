package database.mappers;

import database.datatypes.Actor;
import database.datatypes.FilmData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
