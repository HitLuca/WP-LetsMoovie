package database.mappers;

import database.datatypes.DetailedPayment;
import database.datatypes.UserData;
import database.datatypes.UserLoginCredential;
import json.register.request.RegistrationRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by marco on 24/06/15.
 */
public interface UserMapper {

    @Select("SELECT username, password, role FROM users WHERE username = #{username}")
    UserLoginCredential getUserCredential(String username);

    @Select("SELECT * FROM users WHERE username=#{username}")
    UserData getUserData(String username);

    @Select("SELECT username FROM users WHERE username = #{username}")
    String checkUsername(String username);

    @Select("SELECT email FROM users WHERE email = #{email}")
    String checkEmail(String email);

    @Select("SELECT username FROM users WHERE email = #{email}")
    String getUsernameByEmail(String email);
    
    @Insert("INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) SELECT " +
            "#{email}," +
            "#{name}," +
            "#{surname}," +
            "#{username}," +
            "#{password}," +
            "#{phone}," +
            "#{birthday}::DATE," +
            "0," +
            "0")
    void insertUser(RegistrationRequest user);

    @Update("UPDATE users SET password=#{password} WHERE username=#{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

    @Delete("DELETE FROM users WHERE username=#{username}")
    void deleteUser(String username);

    @Update("UPDATE users SET residual_credit=#{credit} + (SELECT residual_credit FROM users WHERE username=#{username}) WHERE username=#{username}")
    void addCredit(@Param("username") String username, @Param("credit") float credit);

    @Update("UPDATE users SET residual_credit=(SELECT residual_credit FROM users WHERE username=#{username})-#{credit} WHERE username=#{username}")
    void removeCredit(@Param("username") String username, @Param("credit") float credit);

    //TODO:Test
    @Select("SELECT payment_date, payment_time, id_show, username, ticket_type, price, row, 'column', room_number" +
            "FROM payments NATURAL JOIN prices NATURAL JOIN seats" +
            "WHERE username=#{username}")
    List<DetailedPayment> getUserPayments(String username);

    //TODO:Test
    @Select("SELECT sum(price) FROM payments NATURAL JOIN prices WHERE username=#{username}")
    float getUserTotalPayments(String username);

    @Select("SELECT credit_card_number FROM user_credit_cards WHERE username=#{username}")
    List<String> getCreditCards(String username);
}
