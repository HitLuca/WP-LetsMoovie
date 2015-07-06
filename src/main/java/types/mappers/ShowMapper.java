package types.mappers;

import org.apache.ibatis.annotations.Select;
import types.Show;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface ShowMapper {
    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowInfo(int id_show);

    @Select("SELECT SUM(price) FROM payments NATURAL JOIN prices WHERE id_show=#{id_show}")
    float getShowIncome(int id_show);

    @Select("SELECT id_show FROM shows WHERE id_film=#{id_film}")
    List<Integer> getProjectingShows(String id_film);

    @Select("SELECT * FROM shows WHERE id_show=#{id_show}")
    Show getShowData(int id_show);
}
