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
    @Select("SELECT * " +
            "FROM cinema_rooms")
    List<RoomData> getAllRooms();

    @Select("SELECT * " +
            "FROM cinema_rooms " +
            "WHERE room_number=#{room_number}")
    RoomData getRoomData(int room_number);

    @Select("SELECT * " +
            "FROM cinema_rooms c JOIN shows s ON c.room_number = s.room_number " +
            "WHERE s.id_show=#{id_show}")
    RoomData getShowRoomData(int id_show);

    @Select("SELECT * " +
            "FROM seats " +
            "WHERE room_number=#{room_number} AND status='broken'")
    List<Seat> getBrokenSeats(int room_number);

    @Select("SELECT * " +
            "FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat " +
            "WHERE sr.id_show=#{id_show} AND sr.status='reserved'")
    List<Seat> getSeatReservations(int id_show);

    @Select("SELECT * " +
            "FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat " +
            "WHERE sr.id_show=#{id_show} AND sr.status='broken'")
    List<Seat> getShowBrokenSeats(int id_show);

    @Select("SELECT * " +
            "FROM shows sh NATURAL JOIN seats se " +
            "WHERE id_show=#{id_show} " +
            "AND id_seat NOT IN " +
            "(SELECT id_seat " +
            "FROM seat_reservations " +
            "WHERE id_show=#{id_show})")
    List<Seat> getShowFreeSeat(int id_show);

    @Select("SELECT * " +
            "FROM shows sh NATURAL JOIN seats se " +
            "WHERE id_show=#{id_show} ")
    List<Seat> getShowSeats(int id_show);

    @Select("SELECT * " +
            "FROM seats " +
            "WHERE room_number=#{room_number}")
    List<Seat> getRoomSeats(int room_number);

    @Select("SELECT id_seat " +
            "FROM seats " +
            "WHERE room_number=#{room_number} AND row=#{row} AND \"column\"=#{column}")
    int getIdSeat(@Param("room_number") int room_number, @Param("row") int row, @Param("column") int column);

    @Update("UPDATE seats " +
            "SET status=#{status} " +
            "WHERE id_seat=#{id_seat}")
    void updateSeatStatus(@Param("status") String status, @Param("id_seat") int id_seat);

    @Delete("DELETE FROM seat_reservations " +
            "WHERE id_show=#{id_show} AND id_seat=#{id_seat}")
    void removeSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat);

    @Insert("INSERT INTO seat_reservations (id_show, id_seat, status) " +
            "VALUES (#{id_show}, #{id_seat}, #{status})")
    void insertSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat, @Param("status") String status);

    @Update("UPDATE seat_reservations " +
            "SET status=#{status} " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    void updateShowSeatStatus(@Param("id_show") int id_show, @Param("id_seat") int id_seat, @Param("status") String status);

    @Select("SELECT status " +
            "FROM seat_reservations " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    String getReservationStatus(@Param("id_seat") int id_seat, @Param("id_show") int id_show);

    @Select("SELECT price " +
            "FROM payments NATURAL JOIN prices " +
            "WHERE id_seat=#{id_seat} AND id_show=#{id_show}")
    float getSeatPayment(@Param("id_seat") int id_seat, @Param("id_show") int id_show);

    @Select("SELECT sr.id_seat, COUNT(*) " +
            "FROM seat_reservations sr JOIN seats s ON sr.id_seat=s.id_seat " +
            "WHERE room_number=#{room_number} AND sr.status='reserved' " +
            "GROUP BY sr.id_seat " +
            "ORDER BY COUNT(*) " +
            "DESC " +
            "TOP #{top}")
    List<SeatCount> getRankedSeatReservations(@Param("room_number") int room_number, @Param("top") int top);
}
