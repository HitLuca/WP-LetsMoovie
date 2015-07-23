package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.datatypes.show.ShowIdTime;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.film.FilmAndShows;
import json.film.response.FilmAndShowListSuccess;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet che spedisce la lista di tutti i film proiettati durante la data ricevuta in input. Per ogni film, oltre ai
 * dati del film allega inoltre la lista di tutti gli spettacoli della giornata.
 * <p/>
 * Created by etrunon on 09/07/15.
 */

/**
 * @api {get} /api/filmDay/*
 * @apiName FilmDay
 * @apiGroup FilmAndShows
 *
 * @apiParam {String} Stringa con la data su cui interrogare (in formato "yyyy-mm-dd")
 * @apiSuccess {String} Lista dei film proiettati in quella giornata. Contenente tutti i dati del film e la lista
 *                          degli spettacoli relativi a quel film in quella giornata con data, orario e codice spettacolo.
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {int} errorCode EMPTY_WRONG_FIELD: Lanciato quanto i parametri passati tramite la url non matchano
 */
@WebServlet(name = "FilmDay", urlPatterns = "/api/filmDay/*")
public class FilmDay extends HttpServlet {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession();
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        //response.setContentType("application/json");
        OperationResult getFilmOfDay = null;

        try {
            // Creo un UrlMatcher con la mia url. Se non matcha la url lancia l'eccezione 2
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());

            //Converto la data a SqlDate per il Db e cerco tutti gli spettacoli della giornata
            LocalDate date = LocalDate.parse(rs.getParameter());
            List<Integer> idList = showMapper.getDayFilms(date.format(dateFormat));
            //Inizializzo la lista della risposta vuota
            List<FilmAndShows> timetable = new ArrayList<>();

            for (Integer i : idList) {

                //Prendo le info del filmAndShows con id I proiettato in quella data
                FilmData filmData = filmMapper.getFilmData(i);
                //Prendo i differenti
                List<ShowIdTime> hours = showMapper.getShowTimeAndId(date.format(dateFormat), i);

                hours.forEach(database.datatypes.show.ShowIdTime::convertTime);

                //Creo l'oggetto FilmAndShows e lo riempio
                //TODO, errore? hours nel costruttore non serve
                FilmAndShows filmAndShows = new FilmAndShows(filmData, hours, filmMapper);
                filmAndShows.addHours(date.format(dateFormat), hours);
                //Aggiungo il FilmAndShows alla lista
                timetable.add(filmAndShows);
            }

            //Creo l'oggetto da trascrivere come Json di risposta
            getFilmOfDay = new FilmAndShowListSuccess(timetable);

        } catch (BadRequestException e) {
            getFilmOfDay = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            getFilmOfDay = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(getFilmOfDay));

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

