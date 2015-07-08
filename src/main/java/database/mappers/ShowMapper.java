package database.mappers;

import database.datatypes.Show;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface ShowMapper {

    //TODO:Test
    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowInfo(int id_show);

    //TODO:Test
    @Select("SELECT SUM(price) FROM payments NATURAL JOIN prices WHERE id_show=#{id_show}")
    float getShowIncome(int id_show);

    //TODO:Test
    @Select("SELECT id_show FROM shows WHERE id_film=#{id_film}")
    List<Integer> getProjectingShows(String id_film);

    //TODO:Test
    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowData(int id_show);
}
