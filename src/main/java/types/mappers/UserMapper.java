package types.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import types.DetailedPayment;
import types.UserData;
import types.UserLoginCredential;

import java.util.List;

/**
 * Created by marco on 24/06/15.
 */
public interface UserMapper {

    @Select("SELECT username, password, role FROM users WHERE username = #{username}")
    UserLoginCredential getUserCredential(String username);

    @Select("SELECT * FROM users WHERE username=#{username}")
    UserData getUserData(String username);

    @Insert("INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) SELECT " +
            "#{email}," +
            "#{name}," +
            "#{surname}," +
            "#{username}," +
            "#{password}," +
            "#{phone_number}," +
            "#{birthday}::DATE" +
            "#{residual_credit}," +
            "#{role},")
    void insertUser(String email,String name, String surname, String username,String password, String phone_number, String birthday);

    @Delete("DELETE FROM user_credit_cards WHERE username=#{username}")
    void deleteUser(String username);

    @Update("UPDATE users SET residual_credit=#{credit} + (SELECT residual_credit FROM users WHERE username=#{username}) WHERE username=#{username}")
    void addCredit(String username, float credit);

    @Update("UPDATE users SET residual_credit=(SELECT residual_credit FROM users WHERE username=#{username})-#{credit} WHERE username=#{username}")
    void removeCredit(String username, float credit);

    @Select("SELECT payment_date, payment_time, id_show, username, ticket_type, price, row, 'column', room_number" +
            "FROM payments NATURAL JOIN prices NATURAL JOIN seats" +
            "WHERE username=#{username}")
    List<DetailedPayment> getUserPayments(String username);

    @Select("SELECT sum(price) FROM payments NATURAL JOIN prices WHERE username=#{username}")
    float getUserTotalPayments(String username);
}
