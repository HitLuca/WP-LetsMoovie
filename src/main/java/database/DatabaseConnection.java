package database;

import database.mappers.FilmMapper;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import database.mappers.UserMapper;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by etrunon on 24/06/15.
 */
public class DatabaseConnection {

    private static SqlSessionFactory factory;

    public static SqlSessionFactory getFactory() {
        if(factory == null)
        {
            URI dbUri = null;
            SqlSessionFactory newFactory = null;

            try {

                Class.forName("org.postgresql.Driver");
                dbUri = new URI(System.getenv("DATABASE_URL"));
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];

                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                DataSource dataSource = new UnpooledDataSource("org.postgresql.Driver", dbUrl, username, password);

                Environment environment = new Environment("development", transactionFactory, dataSource);
                Configuration configuration = new Configuration(environment);
                configuration.addMapper(UserMapper.class);
                configuration.addMapper(FilmMapper.class);
                configuration.addMapper(SeatMapper.class);
                configuration.addMapper(ShowMapper.class);
                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                newFactory = builder.build(configuration);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            factory = newFactory;
        }
        return factory;
    }

}
