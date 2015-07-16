package database.mappers;

import database.datatypes.show.Show;
import database.datatypes.show.ShowIdTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface ShowMapper {
    @Select("SELECT SUM(price) FROM payments NATURAL JOIN prices WHERE id_show=#{id_show}")
    float getShowIncome(int id_show);

    @Select("SELECT * FROM shows WHERE id_film=#{id_film}")
    List<Show> getProjectingShows(int id_film);

    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowData(int id_show);

    @Select("SELECT show_time, id_show FROM shows WHERE shows.show_date=#{show_date}::DATE AND shows.id_film=#{id_film}")
    List<ShowIdTime> getShowTimeAndId(@Param("show_date") String show_date, @Param("id_film") int id_film);

    @Select("SELECT DISTINCT id_film FROM shows WHERE shows.show_date=#{show_date}::DATE")
    List<Integer> getDayFilms(String show_date);

    @Select("SELECT * FROM shows WHERE show_date=#{show_date}::DATE ORDER BY show_time")
    List<Show> getDayShows(String show_date);

    @Insert("INSERT INTO shows (room_number, id_film, show_date, show_time) VALUES (#{room_number}, #{id_film}, #{show_date}::DATE, #{show_time}::TIME)")
    void insertShow(Show show);

    @Select("SELECT id_show FROM shows WHERE room_number=#{room_number} AND id_film=#{id_film} AND show_date=#{show_date}::DATE AND show_time=#{show_time}::TIME")
    int getShowId(Show show);

    @Delete("DELETE FROM shows WHERE id_show=#{id_show}")
    void deleteShow(int id_show);

    @Update("UPDATE shows SET show_time=#{show_time}::TIME WHERE id_show=#{id_show}")
    void updateShowDuration(@Param("id_show") int id_show, @Param("show_time") String show_time);
}
