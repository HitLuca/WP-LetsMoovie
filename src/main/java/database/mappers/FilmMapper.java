package database.mappers;

import database.datatypes.Actor;
import database.datatypes.FilmData;
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

    @Select("SELECT actor_name, role, url_photo FROM film_actors NATURAL JOIN actors WHERE id_film=#{id_film}")
    List<Actor> getFilmActors(int id_film);

    @Select("SELECT url_photo FROM actors WHERE actor_name=#{actor_name}")
    String getActorPhoto(String actor_name);
}
