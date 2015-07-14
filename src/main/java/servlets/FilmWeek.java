package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import database.datatypes.FilmData;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.film.response.FilmListSuccess;
import org.apache.ibatis.session.SqlSession;
import types.Film;
import types.FilmList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * @api {post} /api/filmWeek
 * @apiName FilmWeek
 * @apiGroup eek
 * @apiError (0) {int} errorCode lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode Lanciato quanto i parametri passati tramite la url non matchano
 */
@WebServlet(name = "FilmWeek", urlPatterns = "/api/filmWeek")
public class FilmWeek extends HttpServlet {

    private Gson gsonWriter;
    private ShowMapper showMapper;
    private FilmMapper filmMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OperationResult getFilmOfWeek = null;

        LocalDate today = LocalDate.now();
        FilmList filmList = new FilmList();

        for (int i = 0; i < 7; i++) {
            List<Integer> idList = showMapper.getDayFilms(Date.valueOf(today));

            for (Integer j : idList) {

                List<String> hours = showMapper.getDayShowsId(Date.valueOf(today), j);
                filmList.addId(today, j, hours);
            }

            today = today.plusDays(1);
        }

        for (Film f : filmList.getFilmList()) {
            FilmData filmData = filmMapper.getFilmData(f.getId_film());
            f.setData(filmData);
        }

        getFilmOfWeek = new FilmListSuccess(filmList);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getFilmOfWeek));

        //todo chiudere sessioni sql

    }

    @Override
    public void init() throws ServletException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        showMapper = sessionSql.getMapper(ShowMapper.class);
        filmMapper = sessionSql.getMapper(FilmMapper.class);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }

}
