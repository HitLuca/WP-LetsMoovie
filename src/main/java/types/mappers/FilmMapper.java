package types.mappers;

import org.apache.ibatis.annotations.Select;
import types.Actor;
import types.FilmData;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface FilmMapper {

    @Select("SELECT id_film FROM films WHERE film_title=#{film_title} AND year=#{year}")
    int getFilmId(String film_title, int year);

    @Select("SELECT * FROM films WHERE id_film=#{id_film}")
    FilmData getFilmData(int id_film);

    @Select("SELECT genre FROM film_genres WHERE id_film=#{id_film}")
    List<String> getFilmGenres(int id_film);

    @Select("SELECT actor_name, role, url_photo FROM film_actors NATURAL JOIN actors WHERE id_film=#{id_film}")
    List<Actor> getFilmActors(int id_film);
}
