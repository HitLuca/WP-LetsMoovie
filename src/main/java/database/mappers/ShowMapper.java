package database.mappers;

import database.datatypes.Show;
import database.datatypes.ShowIdTime;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Time;
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

    @Select("SELECT show_time, id_show FROM shows WHERE shows.show_date=#{show_date} AND shows.id_film=#{id_film}")
    List<ShowIdTime> getShowTimeAndId(@Param("show_date") Date show_date, @Param("id_film") int id_film);

    @Select("SELECT DISTINCT id_film FROM shows WHERE shows.show_date=#{show_date}")
    List<Integer> getDayFilms(Date show_date);

    @Select("SELECT * FROM shows WHERE shows.show_date=#{show_date}")
    List<Show> getDayShows(Date show_date);

    @Insert("INSERT INTO shows (room_number, id_film, show_date, show_time) VALUES (#{room_number}, #{id_film}, #{show_date}, #{show_time})")
    void insertShow(Show show);

    @Select("SELECT id_show FROM shows WHERE room_number=#{room_number} AND id_film=#{id_film} AND show_date=#{show_date} AND show_time=#{show_time}")
    int getShowId(Show show);

    @Delete("DELETE FROM shows WHERE id_show=#{id_show}")
    void deleteShow(int id_show);

    //TODO:Test
    @Update("UPDATE shows SET show_time=#{show_time} WHERE id_show=#{id_show}")
    void updateShowDuration(@Param("id_show") int id_show, @Param("show_time") Time show_time);
}
