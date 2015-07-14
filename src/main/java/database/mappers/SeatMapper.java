package database.mappers;

import database.datatypes.RoomData;
import database.datatypes.Seat;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by hitluca on 29/06/15.
 */
public interface SeatMapper {
    @Select("SELECT * FROM cinema_rooms")
    List<RoomData> getAllRooms();

    @Select("SELECT * FROM cinema_rooms WHERE room_number=#{room_number}")
    RoomData getRoomData(int room_number);

    @Select("SELECT * FROM cinema_rooms c JOIN shows s ON c.room_number = s.room_number WHERE s.id_show=#{id_show}")
    RoomData getShowRoomData(int id_show);

    @Select("SELECT * FROM seats WHERE room_number=#{room_number} AND status='broken'")
    List<Seat> getBrokenSeats(int room_number);

    @Select("SELECT * FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat WHERE sr.id_show=#{id_show} AND sr.status='reserved'")
    List<Seat> getSeatReservations(int id_show);

    @Select("SELECT * FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat WHERE sr.id_show=#{id_show} AND sr.status='broken'")
    List<Seat> getShowBrokenSeats(int id_show);

    //@Select("SELECT * FROM seats s JOIN seat_reservation sr ON s..id_seat=sr.id_seat WHERE sr.id_show=#{id_show} AND sr.status<>'reserved' AND s.status<>'broken'")
    @Select("SELECT * FROM seats s JOIN seat_reservation sr ON s..id_seat=sr.id_seat WHERE NOT IN (SELECT * FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat WHERE sr.id_show=#{id_show} AND sr.status='reserved') AND NOT IN (SELECT * FROM seats s JOIN seat_reservations sr ON s.id_seat=sr.id_seat WHERE sr.id_show=#{id_show} AND sr.status='broken') ")
    List<Seat> getFreeSeat(int id_show);

    @Select("SELECT * FROM seats WHERE room_number=#{room_number}")
    List<Seat> getRoomSeats(int room_number);

    //TODO:Test
    @Update("UPDATE seats SET status=#{status} WHERE id_seat=#{id_seat}")
    void updateSeatStatus(@Param("status") String status, @Param("id_seat") int id_seat);

    //TODO:Test
    @Delete("DELETE FROM seat_reservations WHERE id_show=#{id_show} AND id_seat=#{id_seat}")
    void removeSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat);

    //TODO:Test
    @Insert("INSERT INTO seat_reservations (id_show, id_seat, status) VALUES (#{id_show}, #{id_seat}, \'reserved\')")
    void insertSeatReservation(@Param("id_show") int id_show, @Param("id_seat") int id_seat);
}
