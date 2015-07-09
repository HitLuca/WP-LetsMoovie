package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.UserData;
import database.mappers.UserMapper;
import json.OperationResult;
import json.filmDay.request.FilmDayRequest;
import json.userPersonalData.response.PersonalRespose;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Servlet che spedisce la lista di tutti i film proiettati durante la data ricevuta in input. Per ogni film, oltre ai
 * dati del film allega inoltre la lista di tutti gli spettacoli della giornata.
 * <p/>
 * Created by etrunon on 09/07/15.
 */
@WebServlet(name = "FilmDay", urlPatterns = "/api/filmDay")
public class FilmDay extends HttpServlet {

    private Gson gsonWriter;
    private Gson gsonReader;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        OperationResult getUserStatus = null;

        try {

            //Check sulla richiesta vuota
            FilmDayRequest inputDate = gsonReader.fromJson(request.getReader(), FilmDayRequest.class);
            if (inputDate == null) {
                throw new BadRequestException(ErrorCode.EMPTY_REQ);
            }
            List<String> invalidParameters = ModelValidator.validate(inputDate);
            //Se ho stringhe invalide lancio l'eccezione di registrazione
            if (!invalidParameters.isEmpty()) {
                throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, invalidParameters);
            }

            String dateSearched = inputDate.getdate();
            //todo


        } catch (BadRequestException e) {
            getUserStatus = e;
            response.setStatus(400);
        } catch (JsonIOException | JsonSyntaxException | NullPointerException | IllegalAccessException | InvocationTargetException e) {
            getUserStatus = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(getUserStatus));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        userMapper = sessionSql.getMapper(UserMapper.class);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }
}
