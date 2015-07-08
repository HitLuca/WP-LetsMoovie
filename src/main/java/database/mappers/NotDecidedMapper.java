package database.mappers;

import database.datatypes.Ticket;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface NotDecidedMapper {

    //TODO:Test
    @Select("SELECT price FROM prices WHERE ticket_type=#{ticket_type}")
    float getTicketPrice(String ticket_type);

    //TODO:Test
    @Select("SELECT * FROM prices")
    List<Ticket> getAllTickets();
}
