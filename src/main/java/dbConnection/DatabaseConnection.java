package dbConnection;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import types.mappers.UserMapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by etrunon on 24/06/15.
 */
public class DatabaseConnection {

    private static SqlSessionFactory factory = connect();


    private static SqlSessionFactory connect() {

        URI dbUri = null;
        SqlSessionFactory factory = null;

        try {

            Class.forName("org.postgresql.Driver");
            dbUri = new URI(System.getenv("DATABASE_URL"));
            System.out.println(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];

            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            DataSource dataSource = new UnpooledDataSource("org.postgresql.Driver", dbUrl, username, password);

            Environment environment = new Environment("development", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(UserMapper.class);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(configuration);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return factory;
    }

    public static SqlSessionFactory getFactory() {
        return factory;
    }

}
