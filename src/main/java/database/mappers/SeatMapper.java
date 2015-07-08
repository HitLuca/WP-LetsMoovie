package database.mappers;

import database.datatypes.Seat;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hitluca on 29/06/15.
 */
public interface SeatMapper {

    //TODO:Test
    @Select("SELECT id_seat, row, 'column' FROM seats WHERE room_number=#{room_number} AND status='broken'")
    List<Seat> getBrokenSeats(String room_number);

    //TODO:Test
    @Select("SELECT")
    List<Seat> getSeatReservations(String show);
}
