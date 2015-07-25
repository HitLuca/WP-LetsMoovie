package database.javaMigrations;

import database.DatabaseConnection;
import database.datatypes.user.PaymentWithIdCode;
import database.mappers.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hitluca on 24/07/15.
 */
public class V21__CodicePrenotazioniERimozioneDateTime implements JdbcMigration {

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
        sql = "ALTER TABLE payments ALTER payment_date TYPE TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "ALTER TABLE payments ALTER payment_time TYPE TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "ALTER TABLE shows ALTER show_date TYPE TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "ALTER TABLE shows ALTER show_time TYPE TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        sql = "ALTER TABLE users ALTER birthday TYPE TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();

        //Aggiungo la colonna id
        sql = "ALTER TABLE payments ADD COLUMN id SERIAL";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();

        //Aggiungo la colonna code
        sql = "ALTER TABLE payments ADD COLUMN code TEXT";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();

        //Setto i codici
        sql = "UPDATE payments SET code=? WHERE id=?";
        preparedStatement = conn.prepareStatement(sql);

        random = new SecureRandom();
        random.setSeed(123456789);
        String code = null;
        codeSet = new HashSet<>();
        List<PaymentWithIdCode> paymentList = new ArrayList<>();
        String preceding_date = "";
        String preceding_time = "";
        String preceding_username = "";
        paymentList = userMapper.getAllPayments();
        int id;
        for (PaymentWithIdCode pwc : paymentList) {
            if (!pwc.getUsername().equals(preceding_username)) {
                preceding_username = pwc.getUsername();
                preceding_date = pwc.getPayment_date();
                preceding_time = pwc.getPayment_time();
                do {
                    code = nextCode();
                } while (codeSet.contains(code));
                preparedStatement.setString(1, code);
                preparedStatement.setInt(2, pwc.getId());
                preparedStatement.executeUpdate();
            } else if (!pwc.getPayment_date().equals(preceding_date)) {
                preceding_date = pwc.getPayment_date();
                preceding_time = pwc.getPayment_time();
                do {
                    code = nextCode();
                } while (codeSet.contains(code));
                preparedStatement.setString(1, code);
                preparedStatement.setInt(2, pwc.getId());
                preparedStatement.executeUpdate();
            } else if (!pwc.getPayment_time().equals(preceding_time)) {
                preceding_time = pwc.getPayment_time();
                do {
                    code = nextCode();
                } while (codeSet.contains(code));
                preparedStatement.setString(1, code);
                preparedStatement.setInt(2, pwc.getId());
                preparedStatement.executeUpdate();
            } else {
                preparedStatement.setString(1, code);
                preparedStatement.setInt(2, pwc.getId());
                preparedStatement.executeUpdate();
            }
        }

        //Cancello la riga id
        sql = "ALTER TABLE payments DROP COLUMN id";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    private String nextCode() {
        return RandomStringUtils.randomAlphanumeric(10);
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