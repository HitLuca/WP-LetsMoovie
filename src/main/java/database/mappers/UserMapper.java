package database.mappers;

import database.datatypes.user.*;
import json.adminFunctions.request.SeatDetailRequest;
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
            "SELECT  #{email}, #{name}, #{surname}, #{username}, #{password}, #{phone}, #{birthday}, 0, 0")
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
    @Select("SELECT DISTINCT payment_date, payment_time, room_number, film_title " +
            "FROM payments NATURAL JOIN seats NATURAL JOIN films NATURAL JOIN shows " +
            "WHERE username=#{username} " +
            "ORDER BY payment_date, payment_time")
    List<CompletePayments> getUserUniquePayments(String username);

    @Select("SELECT ticket_type, price, row, \"column\" " +
            "FROM payments NATURAL JOIN seats NATURAL JOIN prices " +
            "WHERE payment_date=#{payment_date} AND payment_time=#{payment_time} AND username=#{username}")
    List<UserPayment> getUserPayments(@Param("payment_date") String payment_date, @Param("payment_time") String payment_time, @Param("username") String username);
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
            "SELECT #{payment_date}, #{payment_time}, #{ticket_type}, #{id_seat}, #{id_show}, #{username}")
    void insertPayment(Payment payment);

    /**
     *
     * @param payment oggetto Payment
     */
    @Delete("DELETE FROM payments " +
            "WHERE payment_date=#{payment_date} AND payment_time=#{payment_time} AND ticket_type=#{ticket_type} AND id_seat=#{id_seat} AND id_show=#{id_show} AND username=#{username}")
    void deletePayment(Payment payment);

    /**
     * @param code codice associato alla prenotazione
     */
    @Delete("DELETE FROM payments " +
            "WHERE code=#{code} AND id_seat=#{id_seat}")
    void deletePaymentFromCode(@Param("code") String code, @Param("id_seat") int id_seat);

    /**
     * @param id id l'id temporaneo del pagamento
     * @return dati del pagamento con id id
     */
    @Select("SELECT * " +
            "FROM payments " +
            "WHERE id=#{id}")
    PaymentWithIdCode getPaymentDataFromId(int id);

    /**
     * @param payment_date
     * @param payment_time
     * @param username
     * @return
     */
    @Select("SELECT id " +
            "FROM payments " +
            "WHERE payment_date=#{payment_date} AND payment_time=#{payment_time} AND username=#{username}")
    List<Integer> getIdList(@Param("payment_date") String payment_date, @Param("payment_time") String payment_time, @Param("username") String username);

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
     * @return lista dei (top)% utenti e totali che hanno pagato di piu'
     */
    @Select("SELECT name, surname, username, SUM(price) as paid " +
            "FROM payments NATURAL JOIN prices NATURAL JOIN users " +
            "GROUP BY name, surname, username " +
            "ORDER BY SUM(price) " +
            "DESC " +
            "LIMIT #{top}")
    List<UserPaid> getRankedUserTotalPayments(int top);

    /**
     * @param credit_card_number numero di carta di credito da inserire
     * @param username username dell'utente
     */
    @Insert("INSERT INTO user_credit_cards (credit_card_number, username) " +
            "VALUES (#{credit_card_number}, #{username})")
    void insertCreditCard(@Param("credit_card_number") String credit_card_number, @Param("username") String username);

    /**
     * @return numero totale di utenti
     */
    @Select("SELECT COUNT(*) " +
            "FROM users")
    int getUserCountForRank();

    /**
     * @param username username dell'utente
     * @return credito rimanente
     */
    @Select("SELECT residual_credit " +
            "FROM users " +
            "WHERE username=#{username}")
    float getResidualCredit(String username);

    /**
     *
     * @return numero di entry nella tabella payments
     */
    @Select("SELECT COUNT(*) " +
            "FROM payments")
    int getPaymentCount();

    /**
     * @return tutti i pagamenti
     */
    @Select("SELECT * " +
            "FROM payments " +
            "ORDER BY payment_date, payment_time, username, id_seat, id_show, ticket_type, username")
    List<PaymentWithIdCode> getAllPayments();

    /**
     * @param code codice associato alla prenotazione
     * @param id   id temporaneo creato per questa query
     */
    @Update("UPDATE payments " +
            "SET code=#{code} " +
            "WHERE id=#{id}")
    void insertCode(@Param("code") String code, @Param("id") int id);

    //TODO:Test
    @Select("SELECT * " +
            "FROM payments " +
            "WHERE code=#{code}")
    Payment getPaymentFromCode(String code);

    /**
     * @param show_date data di proiezione
     * @param show_time ora di proiezione
     * @param id_show   id dello show
     * @param username  username dell'utente
     * @return lista di posti con relativo prezzo e tipo di biglietto
     */
    @Select("SELECT row as s_row, \"column\" as s_column, ticket_type, price " +
            "FROM payments NATURAL JOIN prices NATURAL JOIN seats " +
            "WHERE payment_date=#{show_date} AND payment_time=#{show_time} AND id_show=#{id_show} AND username=#{username}")
    List<SeatDetailRequest> getReservationBlock(@Param("show_date") String show_date, @Param("show_time") String show_time, @Param("id_show") int id_show, @Param("username") String username);
}