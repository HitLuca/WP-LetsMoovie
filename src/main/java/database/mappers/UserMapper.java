package database.mappers;

import database.datatypes.user.*;
import json.register.request.RegistrationRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by marco on 24/06/15.
 */
public interface UserMapper {

    /**
     * @param username username dell'utente
     * @return dati di login dell'utente username
     */
    @Select("SELECT username, password, role " +
            "FROM users " +
            "WHERE username = #{username}")
    UserLoginCredential getUserCredential(String username);

    /**
     * @param username username dell'utente
     * @return dati riguardanti username
     */
    @Select("SELECT * " +
            "FROM users " +
            "WHERE username=#{username}")
    UserData getUserData(String username);

    /**
     *
     * @param username username dell'utente
     * @return username, se presente, uguale a username. Usata per il controllo della sua presenza
     */
    @Select("SELECT username " +
            "FROM users " +
            "WHERE username = #{username}")
    String checkUsername(String username);

    /**
     *
     * @param email email dell'utente
     * @return email, se presente, uguale a email. Usata per il controllo della sua presenza
     */
    @Select("SELECT email " +
            "FROM users " +
            "WHERE email = #{email}")
    String checkEmail(String email);

    /**
     *
     * @param email email dell'utente
     * @return username associato all'email
     */
    @Select("SELECT username " +
            "FROM users " +
            "WHERE email = #{email}")
    String getUsernameByEmail(String email);

    /**
     *
     * @param registrationRequest oggetto RegistrationRequest
     */
    @Insert("INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) " +
            "SELECT  #{email}, #{name}, #{surname}, #{username}, #{password}, #{phone}, #{birthday}::DATE, 0, 0")
    void insertUser(RegistrationRequest registrationRequest);

    /**
     *
     * @param username username dell'utente
     * @param password password dell'utente
     */
    @Update("UPDATE users " +
            "SET password=#{password} " +
            "WHERE username=#{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

    /**
     *
     * @param username username dell'utente
     */
    @Delete("DELETE FROM users " +
            "WHERE username=#{username}")
    void deleteUser(String username);

    /**
     *
     * @param username username dell'utente
     * @param credit credito da aggiungere
     */
    @Update("UPDATE users " +
            "SET residual_credit=#{credit} + " +
            "(SELECT residual_credit " +
            "FROM users " +
            "WHERE username=#{username}) " +
            "WHERE username=#{username}")
    void addCredit(@Param("username") String username, @Param("credit") float credit);

    /**
     *
     * @param username username dell'utente
     * @param credit credito da togliere
     */
    @Update("UPDATE users " +
            "SET residual_credit=" +
            "(SELECT residual_credit " +
            "FROM users " +
            "WHERE username=#{username})-#{credit} " +
            "WHERE username=#{username}")
    void removeCredit(@Param("username") String username, @Param("credit") float credit);

    /**
     *
     * @param username username dell'utente
     * @return lista di dettagli dei pagamenti di username
     */
    @Select("SELECT payment_date, payment_time, id_show, ticket_type, price, row, \"column\", room_number " +
            "FROM payments NATURAL JOIN prices NATURAL JOIN seats " +
            "WHERE username=#{username} " +
            "ORDER BY payment_date, payment_time")
    List<DetailedPayment> getUserPayments(String username);

    /**
     *
     * @param username username dell'utente
     * @return totale dei pagamenti di username
     */
    @Select("SELECT sum(price) " +
            "FROM payments NATURAL JOIN prices " +
            "WHERE username=#{username}")
    float getUserTotalPayments(String username);

    /**
     *
     * @param username username dell'utente
     * @return lista dei credit_card_number associati a username
     */
    @Select("SELECT credit_card_number " +
            "FROM user_credit_cards " +
            "WHERE username=#{username}")
    List<String> getCreditCards(String username);

    /**
     *
     * @param payment oggetto Payment
     */
    @Insert("INSERT INTO payments (payment_date, payment_time, ticket_type, id_seat, id_show, username) " +
            "SELECT #{payment_date}::DATE, #{payment_time}::TIME, #{ticket_type}, #{id_seat}, #{id_show}, #{username}")
    void insertPayment(Payment payment);

    /**
     *
     * @param payment oggetto Payment
     */
    @Delete("DELETE FROM payments " +
            "WHERE payment_date=#{payment_date}::DATE AND payment_time=#{payment_time}::TIME AND ticket_type=#{ticket_type} AND id_seat=#{id_seat} AND id_show=#{id_show} AND username=#{username}")
    void deletePayment(Payment payment);

    /**
     *
     * @param id_show id dello show
     * @param id_seat id del posto
     * @return dati del pagamento con show id_show e posto id_seat
     */
    @Select("SELECT * " +
            "FROM payments " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    Payment getPaymentData(@Param("id_show") int id_show, @Param("id_seat") int id_seat);

    /**
     *
     * @param top percentuale da usare per filtrare i risultati
     * @return lista dei (top)% utenti e totali che hanno pagato di più
     */
    @Select("SELECT username, SUM(price) as paid " +
            "FROM payments NATURAL JOIN prices " +
            "GROUP BY username " +
            "ORDER BY SUM(price) " +
            "DESC " +
            "LIMIT #{top}")
    List<UserPaid> getRankedUserTotalPayments(int top);
}