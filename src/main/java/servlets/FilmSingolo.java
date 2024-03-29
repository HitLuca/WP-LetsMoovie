package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.film.FilmAndShows;
import json.film.response.FilmSuccess;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 * Servlet che se interrogata con sintassi REST restituisce un film singolo
 * Created by etrunon on 14/07/15
 */

/**
 * @api {get} /api/film/*
 * @apiName FilmSingolo
 * @apiGroup FilmAndShows
 *
 * @apiParam {Int} Numero intero codice del film da restituire
 * @apiSuccess {FilmAndShows}   Oggetto contenente tutti i dati del film degli spettacoli relativi a quel film in quella giornata
 *                      con data, orario e codice spettacolo.
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode EMPTY_WRONG_FIELD: Lanciato quanto i parametri passati tramite la url non matchano
 * @apiError (4) {int} errorCode FILM_NOT_FOUND: lanciato quando il film cercato non esiste nel DB
 */
@WebServlet(name = "FilmSingolo", urlPatterns = "/api/film/*")
public class FilmSingolo extends HttpServlet {

    private Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        response.setContentType("application/json");


        OperationResult getFilm;

        try {
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String id = rs.getParameter();

            FilmData fd = filmMapper.getFilmData(Integer.parseInt(id));
            BadReqExeceptionThrower.checkFilmNotFound(fd);

            FilmAndShows filmShows = new FilmAndShows(fd, filmMapper);

            filmShows.setData(fd, filmMapper);
            LocalDate today = LocalDate.now();

            filmShows.queryShowsWeek(today, showMapper);

            getFilm = new FilmSuccess(filmShows);

        } catch (BadRequestException e) {
            getFilm = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            getFilm = new BadRequestException();
            response.setStatus(400);
        }


        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(getFilm));
        sessionSql.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
    }
}

