package database.mappers;

import database.datatypes.seat.RoomData;
import database.datatypes.seat.Seat;
import database.datatypes.seat.SeatCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 29/06/15.
 */
public interface SeatMapper {

    /**
     * @return lista dei dati di tutte le stanze
     */
    @Select("SELECT * " +
            "FROM cinema_rooms")
    List<RoomData> getAllRooms();

    /**
     * @param room_number numero della stanza
     * @return dati di room_number
     */
    @Select("SELECT * " +
            "FROM cinema_rooms " +
            "WHERE room_number=#{room_number}")
    RoomData getRoomData(int room_number);

    /**
     *
     * @param id_show id dello show
     * @return dati della sala associata a id_show
     */
    @Select("SELECT * " +
            "FROM cinema_rooms c JOIN shows s ON c.room_number = s.room_number " +
            "WHERE s.id_show=#{id_show}")
    RoomData getShowRoomData(int id_show);

    /**
     *
     * @param room_number numero della stanza
     * @return lista dei posti rotti in room_number
     */
    @Select("SELECT * " +
            "FROM seats " +
            "WHERE room_number=#{room_number} AND status='broken'")
    List<Seat> getBrokenSeats(int room_number);

    /**
     *
     * @param id_show id dello show
     * @return lista dei posti prenotati associati a id_show
     */
    @Select("SELECT * " +
            "FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat " +
            "WHERE sr.id_show=#{id_show} AND sr.status='reserved'")
    List<Seat> getShowReservedSeats(int id_show);

    /**
     *
     * @param id_show id dello show
     * @return lista dei posti rotti associati a id_show
     */
    @Select("SELECT * " +
            "FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat " +
            "WHERE sr.id_show=#{id_show} AND sr.status='broken'")
    List<Seat> getShowBrokenSeats(int id_show);

    /**
     *
     * @param id_show id dello show
     * @return lista dei posti liberi associati a id_show
     */
    @Select("SELECT * " +
            "FROM shows sh NATURAL JOIN seats se " +
            "WHERE id_show=#{id_show} " +
            "AND id_seat NOT IN " +
            "(SELECT id_seat " +
            "FROM seat_reservations " +
            "WHERE id_show=#{id_show})")
    List<Seat> getShowFreeSeat(int id_show);

    /**
     *
     * @param id_show id dello show
     * @return lista di tutti i posti associati a id_show
     */
    @Select("SELECT * " +
            "FROM shows sh NATURAL JOIN seats se " +
            "WHERE id_show=#{id_show} ")
    List<Seat> getShowSeats(int id_show);

    /**
     *
     * @param room_number numero della stanza
     * @return lista di posti in room_number
     */
    @Select("SELECT * " +
            "FROM seats " +
            "WHERE room_number=#{room_number}")
    List<Seat> getRoomSeats(int room_number);

    /**
     *
     * @param room_number numero della stanza
     * @param row riga del posto
     * @param column colonna del posto
     * @return id_seat associato al posto
     */
    @Select("SELECT id_seat " +
            "FROM seats " +
            "WHERE room_number=#{room_number} AND row=#{row} AND \"column\"=#{column}")
    int getIdSeat(@Param("room_number") int room_number, @Param("row") int row, @Param("column") int column);

    /**
     *
     * @param status stato del posto
     * @param id_seat id del posto
     */
    @Update("UPDATE seats " +
            "SET status=#{status} " +
            "WHERE id_seat=#{id_seat}")
    void updateSeatStatus(@Param("status") String status, @Param("id_seat") int id_seat);

    /**
     *
     * @param id_show id dello show
     * @param id_seat id del posto
     */
    @Delete("DELETE FROM seat_reservations " +
            "WHERE id_show=#{id_show} AND id_seat=#{id_seat}")
    void removeSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat);

    /**
     *
     * @param id_show id dello show
     * @param id_seat id del posto
     * @param status stato del posto
     */
    @Insert("INSERT INTO seat_reservations (id_show, id_seat, status) " +
            "VALUES (#{id_show}, #{id_seat}, #{status})")
    void insertSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat, @Param("status") String status);

    /**
     *
     * @param id_show id dello show
     * @param id_seat id del posto
     * @param status stato del posto
     */
    @Update("UPDATE seat_reservations " +
            "SET status=#{status} " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    void updateShowSeatStatus(@Param("id_show") int id_show, @Param("id_seat") int id_seat, @Param("status") String status);

    /**
     *
     * @param id_seat id del posto
     * @param id_show id dello show
     * @return stato del posto associato a id_show
     */
    @Select("SELECT status " +
            "FROM seat_reservations " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    String getSeatReservationStatus(@Param("id_seat") int id_seat, @Param("id_show") int id_show);

    /**
     *
     * @param id_seat id del posto
     * @return stato del posto id_seat
     */
    //TODO:Test
    @Select("SELECT status " +
            "FROM seats " +
            "WHERE id_seat=#{id_seat}")
    String getSeatStatus(int id_seat);

    /**
     *
     * @param id_seat id del posto
     * @param id_show id dello show
     * @return costo del tipo di biglietto associato a id_seat e a id_show
     */
    @Select("SELECT price " +
            "FROM payments NATURAL JOIN prices " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    float getSeatPayment(@Param("id_seat") int id_seat, @Param("id_show") int id_show);

    /**
     *
     * @param room_number numero della stanza
     * @param top percentuale da usare per filtrare i risultati
     * @return lista dei (top)% posti piu' prenotati
     */
    @Select("SELECT sr.id_seat, COUNT(*) " +
            "FROM seat_reservations sr JOIN seats s ON sr.id_seat=s.id_seat " +
            "WHERE room_number=#{room_number} AND sr.status='reserved' " +
            "GROUP BY sr.id_seat " +
            "ORDER BY COUNT(*) " +
            "DESC " +
            "TOP #{top}")
    List<SeatCount> getRankedSeatReservations(@Param("room_number") int room_number, @Param("top") int top);
}
