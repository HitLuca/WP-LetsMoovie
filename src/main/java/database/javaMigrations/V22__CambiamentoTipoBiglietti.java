package database.javaMigrations;

import database.DatabaseConnection;
import database.mappers.SeatMapper;
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
import java.util.Set;

/**
 * Created by hitluca on 25/07/15.
 */
public class V22__CambiamentoTipoBiglietti implements JdbcMigration {

    private SeatMapper seatMapper;
    private UserMapper userMapper;
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

        //Cambio tutti i tipi date e time in text
        sql = "UPDATE payments SET ticket_type='Intero' WHERE ticket_type='Intero 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "UPDATE payments SET ticket_type='Ridotto' WHERE ticket_type='Ridotto 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "UPDATE payments SET ticket_type='Ridotto universitari' WHERE ticket_type='Ridotto universitari 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM prices WHERE ticket_type='Intero 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM prices WHERE ticket_type='Ridotto 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "DELETE FROM prices WHERE ticket_type='Ridotto universitari 3D'";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
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
