package types.mappers;

import org.apache.ibatis.annotations.Select;

/**
 * Created by marco on 24/06/15.
 */
public interface UserMapper {
    @Select("SELECT password FROM users WHERE username = #{user}")
    String getUserCredential(String user);
}
