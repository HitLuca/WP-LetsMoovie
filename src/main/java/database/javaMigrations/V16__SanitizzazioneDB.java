package database.javaMigrations;

import database.DatabaseConnection;
import database.datatypes.film.Actor;
import database.datatypes.film.FilmData;
import database.mappers.FilmMapper;
import org.apache.ibatis.session.SqlSession;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.util.List;

/**
 * migrazione
 * Created by etrunon on 14/07/15.
 */
public class V16__SanitizzazioneDB implements JdbcMigration {

    private FilmMapper filmMapper;


    @Override
    public void migrate(Connection connection) throws Exception {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession(true);
        filmMapper = sessionSql.getMapper(FilmMapper.class);

        List<FilmData> filmDatas = filmMapper.getAllFilms();

        for (FilmData f : filmDatas) {

            String tmp = f.getPlot().replaceAll("\t+", "");
            tmp = tmp.replaceAll(" {2,}", " ");
            tmp = tmp.replaceAll("\n", "");

            f.setPlot(tmp);

            String tmp2 = f.getPoster().replaceAll("\t+", "");
            tmp2 = tmp2.replaceAll(" {2,}", "");
            tmp2 = tmp2.replaceAll("\n", "");
            f.setPoster(tmp2);

            filmMapper.updateFilm(f);
        }

        List<Actor> actors = filmMapper.getAllActors();

        for (Actor a : actors) {

            String tmp = a.getUrl_photo().replaceAll("\t+", "");
            tmp = tmp.replaceAll(" {2,}", "");
            tmp = tmp.replaceAll("\n", "");
            a.setUrl_photo(tmp);

            filmMapper.updateActor(a);
        }

    }
}
