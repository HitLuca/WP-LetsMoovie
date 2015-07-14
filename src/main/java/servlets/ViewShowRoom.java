package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Servlet che mostra la lista di posti con il relativo stato di una specifica proiezione
 * Created by etrunon on 14/07/15.
 */
@WebServlet(name = "ViewShowRoom", urlPatterns = "/api/viewShowRoom/*")
public class ViewShowRoom extends HttpServlet {

    private Gson gsonWriter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OperationResult viewResult;
        //Inizializzo la connessione al db con il mapper
        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
/*
        //todo inizializza le cose

        try{
            //todo fai le cose

        } catch (BadRequestException e) {
            viewResult = e;
            response.setStatus(400);

        } catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            viewResult = new BadRequestException();
            response.setStatus(400);
        }
*/
        //todo invia le cose
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
