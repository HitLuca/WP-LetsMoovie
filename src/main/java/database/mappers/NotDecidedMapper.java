package database.mappers;

import org.apache.ibatis.annotations.Select;

/**
 * Created by hitluca on 06/07/15.
 */
public interface NotDecidedMapper {
    @Select("SELECT url_photo FROM actors WHERE actor_name=#{actor_name}")
    String getActorPhoto(String actor_name);
}
