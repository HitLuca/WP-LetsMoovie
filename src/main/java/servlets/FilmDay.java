package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.FilmData;
import database.datatypes.ShowIdTime;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import javafx.util.Pair;
import json.OperationResult;
import json.film.response.FilmListSuccess;
import org.apache.ibatis.session.SqlSession;
import json.film.Film;
import types.exceptions.BadRequestException;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
 * @apiGroup Film
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

    private Gson gsonWriter;
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession();
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        response.setContentType("application/json");
        OperationResult getFilmOfDay = null;

        try {
            // Creo un UrlMatcher con la mia url. Se non matcha la url lancia l'eccezione 2
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());

            //Converto la data a SqlDate per il Db e cerco tutti gli spettacoli della giornata
            String date = rs.getParameter();
            List<Integer> idList = showMapper.getDayFilms(date);
            //Inizializzo la lista della risposta vuota
            List<Film> timetable = new ArrayList<>();

            for (Integer i : idList) {

                //Prendo le info del film con id I proiettato in quella data
                FilmData filmData = filmMapper.getFilmData(i);
                //Prendo i differenti
                List<ShowIdTime> hours = showMapper.getShowTimeAndId(date, i);
                //Creo l'oggetto Film e lo riempio
                Film film = new Film(filmData, hours);
                film.addHours(date, hours);
                //Aggiungo il Film alla lista
                timetable.add(film);
            }

            //Creo l'oggetto da trascrivere come Json di risposta
            getFilmOfDay = new FilmListSuccess(timetable);

        } catch (BadRequestException e) {
            getFilmOfDay = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            getFilmOfDay = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getFilmOfDay));

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

