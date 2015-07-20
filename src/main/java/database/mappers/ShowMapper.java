package database.mappers;

import database.datatypes.show.Show;
import database.datatypes.show.ShowIdTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface ShowMapper {

    /**
     * @param id_show id dello show
     * @return incassi totali di id_show
     */
    @Select("SELECT SUM(price) " +
            "FROM payments NATURAL JOIN prices " +
            "WHERE id_show=#{id_show}")
    float getShowIncome(int id_show);

    /**
     * @param id_film id del film
     * @return lista di tutti gli show che proiettano id_film
     */
    @Select("SELECT * " +
            "FROM shows " +
            "WHERE id_film=#{id_film}")
    List<Show> getProjectingShows(int id_film);

    /**
     * @param id_show id dello show
     * @return dati di id_show
     */
    @Select("SELECT * " +
            "FROM shows " +
            "WHERE id_show=#{id_show}")
    Show getShowData(int id_show);

    /**
     * @param show_date data di proiezione
     * @param id_film   id del film
     * @return lista degli orari e id_show di tutti gli show che proiettano id_sfilm in data show_date
     */
    @Select("SELECT show_time, id_show, room_number " +
            "FROM shows " +
            "WHERE shows.show_date=#{show_date}::DATE AND shows.id_film=#{id_film}")
    List<ShowIdTime> getShowTimeAndId(@Param("show_date") String show_date, @Param("id_film") int id_film);

    /**
     * @param show_date data di proiezione
     * @return lista di tutti gli id_film che proiettano in data show_date
     */
    @Select("SELECT DISTINCT id_film " +
            "FROM shows " +
            "WHERE shows.show_date=#{show_date}::DATE")
    List<Integer> getDayFilms(String show_date);

    /**
     * @param show_date data di proiezione
     * @return lista di tutti gli show che proiettano in data show_date
     */
    @Select("SELECT * " +
            "FROM shows " +
            "WHERE show_date=#{show_date}::DATE " +
            "ORDER BY show_time")
    List<Show> getDayShows(String show_date);

    /**
     * @param show oggetto Show
     */
    @Insert("INSERT INTO shows (room_number, id_film, show_date, show_time) " +
            "VALUES (#{room_number}, #{id_film}, #{show_date}::DATE, #{show_time}::TIME)")
    void insertShow(Show show);

    /**
     * @param show oggetto Show
     * @return id_show dello show show
     */
    @Select("SELECT id_show " +
            "FROM shows " +
            "WHERE room_number=#{room_number} AND id_film=#{id_film} AND show_date=#{show_date}::DATE AND show_time=#{show_time}::TIME")
    int getShowId(Show show);

    /**
     * @param id_show id dello show
     */
    @Delete("DELETE FROM shows " +
            "WHERE id_show=#{id_show}")
    void deleteShow(int id_show);

    /**
     * @param id_show   id dello show
     * @param show_time ora di proiezione
     */
    @Update("UPDATE shows " +
            "SET show_time=#{show_time}::TIME " +
            "WHERE id_show=#{id_show}")
    void updateShowDuration(@Param("id_show") int id_show, @Param("show_time") String show_time);

    /**
     * @param id_show id dello show
     * @return numero della stanza associata a id_show
     */
    //TODO:Test
    @Select("SELECT room_number FROM shows WHERE id_show=#{id_Show}")
    int getRoomNumber(int id_show);

}

