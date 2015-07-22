package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.show.Show;
import database.mappers.FilmMapper;
import database.mappers.ShowMapper;
import database.mappers.UserMapper;
import json.OperationResult;
import json.adminData.DateRequest;
import json.adminData.ShowData;
import json.register.request.RegistrationRequest;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by etrunon on 22/07/15.
 */
@WebServlet(name = "AdminData", urlPatterns = "/api/adminData/*")
public class AdminData extends HttpServlet {

    private Gson gsonWriter;
    private Gson gsonReader;
    private SqlSession sessionSql;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        sessionSql = DatabaseConnection.getFactory().openSession();

        response.setContentType("application/json");

        OperationResult opRes = null;

        try {
            //Check se l'utente è loggato ed è un admin o superadmin
            BadReqExeceptionThrower.checkUserLogged(request);
            BadReqExeceptionThrower.checkAdminSuperAdmin(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String modality = rs.getParameter();
            //Check stringa vuota
            BadReqExeceptionThrower.checkEmptyString(modality);

            switch (modality) {
                case ("getShows"):
                    opRes = getShows(request);
                    break;
                case ("getRooms"):
                    opRes = getRooms();
                    break;
                case ("getReservationDetails"):
                    opRes = getReservationDetails();
                    break;
            }

        } catch (BadRequestException e) {
            opRes = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException | IOException e) {
            opRes = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = gsonBuilder.create();
    }

    private OperationResult getShows(HttpServletRequest request) throws IOException {

        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        DateRequest dateRequest = gsonReader.fromJson(request.getReader(), DateRequest.class);

        List<Show> showList = showMapper.getDayShows(dateRequest.getDateString());
        List<ShowData> showDataList = showList.stream().map(s -> new ShowData(s, filmMapper)).collect(Collectors.toList());
        //Todo vedere se sta roba va bene
        //todo finire servlet adminData. Finire conversione show->showData e implementare altre funzionalità
        return null;
    }

    private OperationResult getRooms() {
        return null;
    }

    private OperationResult getReservationDetails() {
        return null;
    }
}
