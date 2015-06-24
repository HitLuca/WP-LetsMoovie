package types.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by marco on 24/06/15.
 */
public interface UserMapper {
    @Select("SELECT password FROM users WHERE username = #{user}")
    String getUserCredential(String user);

    @Insert("INSERT INTO users (username, password, email, birthday, name, surname, phone) SELECT " +
            "#{username}," +
            "#{password}," +
            "#{email}," +
            "#{birthday}," +
            "#{name}," +
            "#{surname}," +
            "#{phone}")
    void insertUser(String username,String password, String email, Date birthday, String name,String surname, String phone);
}
