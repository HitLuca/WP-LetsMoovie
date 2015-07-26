package database.javaMigrations;

import database.DatabaseConnection;
import database.datatypes.seat.Seat;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import database.mappers.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by hitluca on 26/07/15.
 */
public class V23__AggiuntaPostiFuturiRotti implements JdbcMigration {

    private SeatMapper seatMapper;
    private UserMapper userMapper;
    private ShowMapper showMapper;
    private PreparedStatement preparedStatement;
    private Connection conn;
    private URI dbUri;
    private String sql;
    private SqlSession sqlSession;
    private Set<String> codeSet;
    private SecureRandom random;


    @Override
    public void migrate(Connection connection) throws Exception {

        conn = getConnection();
        sqlSession = DatabaseConnection.getFactory().openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
        showMapper = sqlSession.getMapper(ShowMapper.class);
        seatMapper = sqlSession.getMapper(SeatMapper.class);

        Map<Integer, List<Integer>> brokenSeatsMap = new HashMap<>();

        for (int i = 1; i <= 6; i++) {
            List<Seat> seats = seatMapper.getBrokenSeats(i);
            List<Integer> id_seats = new ArrayList<>();
            for (Seat s : seats) {
                id_seats.add(s.getId_seat());
            }
            brokenSeatsMap.put(i, id_seats);
        }

        for (int i = 1104; i <= 1606; i++) {
            int room_number = showMapper.getRoomNumber(i);
            for (int id_seat : brokenSeatsMap.get(room_number)) {
                seatMapper.insertSeatReservation(i, id_seat, "broken");
            }
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
