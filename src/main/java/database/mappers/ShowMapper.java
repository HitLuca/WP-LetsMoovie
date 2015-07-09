package database.mappers;

import database.datatypes.Show;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface ShowMapper {
    //TODO:Test
    @Select("SELECT SUM(price) FROM payments NATURAL JOIN prices WHERE id_show=#{id_show}")
    float getShowIncome(int id_show);

    @Select("SELECT * FROM shows WHERE id_film=#{id_film}")
    List<Show> getProjectingShows(int id_film);

    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowData(int id_show);

    @Select("SELECT * FROM shows WHERE shows.show_date=#{show_date}")
    List<Show> getDayShows(Date show_date);

    @Insert("INSERT INTO shows (room_number, id_film, show_date, show_time) VALUES (#{room_number}, #{id_film}, #{show_date}, #{show_time})")
    void insertShow(Show show);

    @Select("SELECT id_show FROM shows WHERE room_number=#{room_number} AND id_film=#{id_film} AND show_date=#{show_date} AND show_time=#{show_time}")
    int getShowId(Show show);

    @Delete("DELETE FROM shows WHERE id_show=#{id_show}")
    void deleteShow(int id_show);
}
