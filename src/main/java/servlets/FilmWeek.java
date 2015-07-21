package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.datatypes.show.ShowIdTime;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.film.FilmAndShows;
import json.film.FilmList;
import json.film.response.FilmListSuccess;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @api {get} /api/filmWeek
 * @apiName FilmWeek
 * @apiGroup FilmAndShows
 *
 * @apiSuccess {String} filmList Lista dei film proiettati in quella settimana. Contenente tutti i dati del film e la lista
 *                          degli spettacoli relativi a quel film in quella settimana con data, orario e codice spettacolo.
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (4) {int} errorCode FILM_NOT_FOUND: lanciato quando il film cercato non esiste nel DB
 */
@WebServlet(name = "FilmWeek", urlPatterns = "/api/filmWeek")
public class FilmWeek extends HttpServlet {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Gson gsonWriter;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession();
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        OperationResult getFilmOfWeek = null;
        response.setContentType("application/json");

        LocalDate today = LocalDate.now();
        FilmList filmList = new FilmList();

        for (int i = 0; i < 7; i++) {
            List<Integer> idList = showMapper.getDayFilms(dateFormat.format(today));

            for (Integer j : idList) {

                List<ShowIdTime> hours = showMapper.getShowTimeAndId(today.format(dateFormat), j);
                hours.forEach(database.datatypes.show.ShowIdTime::convertTime);
                filmList.addId(today, j, hours);
            }

            today = today.plusDays(1);
        }

        for (FilmAndShows f : filmList.getFilmAndShowsList()) {
            FilmData filmData = filmMapper.getFilmData(f.getId_film());

            f.setData(filmData, filmMapper);
        }

        getFilmOfWeek = new FilmListSuccess(filmList);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getFilmOfWeek));

        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }

}
