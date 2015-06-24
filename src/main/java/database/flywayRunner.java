package database;

import org.flywaydb.core.Flyway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.URI;

/**
 * Created by hitluca on 24/06/15.
 */

public class flywayRunner implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
        flyway.setLocations("db_migrations");
        try {
            URI dbUri;
            dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            flyway.setDataSource(dbUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Point it to the database

        // Start the migration
        flyway.migrate();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
