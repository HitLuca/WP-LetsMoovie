package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.FilmData;
import database.mappers.FilmMapper;
import json.OperationResult;
import json.film.response.FilmSuccess;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che se interrogata con sintassi REST restituisce un film singolo
 * Created by etrunon on 14/07/15
 */

/**
 * @api {post} /api/film
 * @apiName FilmSingolo
 * @apiGroup FilmSingolo
 * @apiError (0) {int} errorCode lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode Lanciato quanto i parametri passati tramite la url non matchano
 * @apiError (4) {int} errorCode lanciato quando il film cercato non esiste nel DB
 */
@WebServlet(name = "FilmSingolo", urlPatterns = "/api/film/*")
public class FilmSingolo extends HttpServlet {

    private Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        OperationResult getFilm;

        try {
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String id = rs.getParameter();

            FilmData fd = filmMapper.getFilmData(Integer.parseInt(id));

            if (fd == null) {
                throw new BadRequestException(ErrorCode.FILM_NOT_FOUND);
            }

            getFilm = new FilmSuccess(fd);

        } catch (BadRequestException e) {
            getFilm = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            getFilm = new BadRequestException();
            response.setStatus(400);
        }


        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getFilm));
        sessionSql.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }
}

