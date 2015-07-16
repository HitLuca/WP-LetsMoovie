package database;

import database.datatypes.show.Show;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import org.apache.ibatis.session.SqlSession;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hitluca on 14/07/15.
 */

public class ShowsCompression {
    private Connection conn;
    private Statement statement;
    private static ShowMapper showMapper;
    private static FilmMapper filmMapper;
    private static SqlSession session;
    private static DateTimeFormatter dateFormat;
    private static DateTimeFormatter timeFormat;

    public ShowsCompression() {
        try {
            conn = getConnection();
            statement = conn.createStatement();
            dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            session = DatabaseConnection.getFactory().openSession(true);
            showMapper = session.getMapper(ShowMapper.class);
            filmMapper = session.getMapper(FilmMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public void compressShows(int duration) {
        LocalTime startingTime = LocalTime.now();
        LocalTime localTime = startingTime;
        LocalDate localDate = LocalDate.now();
        List<Show> shows = showMapper.getDayShows(localDate.format(dateFormat));
        ArrayList<ArrayList<Show>> array = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            array.add(i, new ArrayList<Show>());
        }

        for (Show s : shows) {
            array.get(s.getRoom_number() - 1).add(s);
        }

        for (int i = 0; i < 6; i++) {
            for (Show s : array.get(i)) {
                showMapper.updateShowDuration(s.getId_show(), localTime.format(timeFormat));
                localTime = localTime.plusMinutes(duration);
            }
            localTime = startingTime;
        }
    }

    public void decompressShows() {
        LocalTime startingTime = LocalTime.of(15, 00, 00);
        LocalTime localTime = startingTime;
        LocalDate localDate = LocalDate.now();
        List<Show> shows = showMapper.getDayShows(localDate.format(dateFormat));
        ArrayList<ArrayList<Show>> array = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            array.add(i, new ArrayList<Show>());
        }

        for (Show s : shows) {
            array.get(s.getRoom_number() - 1).add(s);
        }

        for (int i = 0; i < 6; i++) {
            for (Show s : array.get(i)) {
                showMapper.updateShowDuration(s.getId_show(), localTime.format(timeFormat));
                localTime = localTime.plusMinutes(filmMapper.getFilmData(s.getId_film()).getDuration() + 30);
            }
            localTime = startingTime;
        }
    }
}