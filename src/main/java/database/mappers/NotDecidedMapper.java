package database.mappers;

import database.datatypes.other.ConfigParameter;
import database.datatypes.other.Ticket;
import database.datatypes.seat.RoomData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hitluca on 06/07/15.
 */
public interface NotDecidedMapper {

    /**
     * @param ticket_type tipo di biglietto
     * @return prezzo di ticket_type
     */
    @Select("SELECT price " +
            "FROM prices " +
            "WHERE ticket_type=#{ticket_type}")
    float getTicketPrice(String ticket_type);

    /**
     * @return lista dei dati di tutti i biglietti
     */
    @Select("SELECT * " +
            "FROM prices")
    List<Ticket> getAllTickets();
    /**
     *
     * @return lista dei dati di tutte le configurazioni
     */
    @Select("SELECT * " +
            "FROM config")
    List<ConfigParameter> getAllConfigParameters();

    /**
     * @param parameter parametro
     * @return valore associato a parameter
     */
    @Select("SELECT * " +
            "FROM config " +
            "WHERE parameter=#{parameter}")
    ConfigParameter getConfigParameter(String parameter);

    /**
     * @return lista di dati riguardanti ogni sala
     */
    @Select("SELECT * " +
            "FROM cinema_rooms ")
    List<RoomData> getRoomList();

    /**
     *
     * @param ticket_type tipo di biglietto
     * @return prezzo associato al biglietto
     */
    @Select("SELECT price FROM prices WHERE ticket_type=#{ticket_type}")
    float getPrice(String ticket_type);

    /**
     *
     * @param code codice di prenotazione
     * @return ritorna il codice stesso se è presente o "" altrimenti
     */
    @Select("SELECT DISTINCT code " +
            "FROM payments " +
            "WHERE code=#{code}")
    String checkDoubleCode(String code);
}
