package database.mappers;

import database.datatypes.show.Show;
import database.datatypes.show.ShowIdTime;
import database.datatypes.show.ShowTime;
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
     * @param code codice della prenotazione
     * @return il room_number associato al codice
     */
    @Select("SELECT DISTINCT room_number " +
            "FROM payments NATURAL JOIN shows " +
            "WHERE code=#{code}")
    int getRoomNumberFromCode(String code);

    /**
     * @param show_date data di proiezione
     * @param id_film   id del film
     * @return lista degli orari e id_show di tutti gli show che proiettano id_sfilm in data show_date
     */
    @Select("SELECT show_time, id_show, room_number " +
            "FROM shows " +
            "WHERE shows.show_date=#{show_date} AND shows.id_film=#{id_film} " +
            "ORDER BY show_time ")
    List<ShowIdTime> getShowTimeAndId(@Param("show_date") String show_date, @Param("id_film") int id_film);

    /**
     * @param show_date data di proiezione
     * @return lista di tutti gli id_film che proiettano in data show_date
     */
    @Select("SELECT DISTINCT id_film " +
            "FROM shows " +
            "WHERE shows.show_date=#{show_date}")
    List<Integer> getDayFilms(String show_date);

    /**
     * @param show_date data di proiezione
     * @return lista di tutti gli show che proiettano in data show_date
     */
    @Select("SELECT * " +
            "FROM shows " +
            "WHERE show_date=#{show_date} " +
            "ORDER BY show_time")
    List<Show> getDayShows(String show_date);

    /**
     * @param show oggetto Show
     */
    @Insert("INSERT INTO shows (room_number, id_film, show_date, show_time) " +
            "VALUES (#{room_number}, #{id_film}, #{show_date}, #{show_time})")
    void insertShow(Show show);

    /**
     *
     * @param show_date data dello show
     * @param show_time ora dello show
     * @param room_number stanza in cui viene proiettato lo show
     * @return id dello show
     */
    @Select("SELECT id_show " +
            "FROM shows " +
            "WHERE room_number=#{room_number} AND show_date=#{show_date} AND show_time=#{show_time}")
    int getShowId(@Param("show_date") String show_date, @Param("show_time") String show_time, @Param("room_number") int room_number);

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
            "SET show_time=#{show_time} " +
            "WHERE id_show=#{id_show}")
    void updateShowDuration(@Param("id_show") int id_show, @Param("show_time") String show_time);

    /**
     * @param id_show id dello show
     * @return numero della stanza associata a id_show
     */
    @Select("SELECT room_number FROM shows WHERE id_show=#{id_Show}")
    int getRoomNumber(int id_show);


    /**
     * @param id_show id dello show
     * @return data e ora dello show
     */
    @Select("SELECT show_date, show_time " +
            "FROM shows " +
            "WHERE id_show=#{id_show} ")
    ShowTime getShowTime(int id_show);

}

