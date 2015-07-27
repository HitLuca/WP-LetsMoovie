package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.datatypes.show.Show;
import database.mappers.FilmMapper;
import database.mappers.NotDecidedMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.adminData.DateRequest;
import json.adminData.RoomList;
import json.adminData.ShowDataList;
import json.film.AbsFilm;
import json.film.MicroFilm;
import json.film.response.FilmListSuccess;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @api {get} /api/adminData
 * @apiName AdminData
 * @apiGroup AdminData
 *
 *  @apiParam getShows
 *  @apiParam getRooms
 *  @apiParam getReservationDetails
 *  @apiParam getFilmList
 *
 * @apiSuccess {Result} result il risultato della richiesta specifica
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (1) {int} errorCode EMPTY_REQ: richiesta vuota
 * @apiError (2) {String[]} errorCode EMPTY_WRONG_FIELD: invalidParameters parametri invalidi
 * @apiError (8) {String[]} errorCode NOT_AUTHORIZED: L'utente non è autorizzato a visualizzare i dati
 *
 */
@WebServlet(name = "AdminData", urlPatterns = "/api/adminData/*")
public class AdminData extends HttpServlet {

    private Gson gsonWriter;
    private Gson gsonReader;
    private SqlSession sessionSql;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
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
            BadReqExeceptionThrower.checkNullInput(modality);

            switch (modality) {
                case "getShows":
                    opRes = getShows(request);
                    break;
                case "getRooms":
                    opRes = getRooms();
                    break;
                case "getReservationDetails":
                    opRes = getReservationDetails();
                    break;
                case "getFilmList":
                    opRes = getFilmList();
                    break;
                default:
                    throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
            }

        } catch (BadRequestException e) {
            opRes = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException | IOException e) {
            opRes = new BadRequestException();
            response.setStatus(400);
        }

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }

    private OperationResult getShows(HttpServletRequest request) throws IOException {

        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        DateRequest dateRequest = gsonReader.fromJson(request.getReader(), DateRequest.class);

        List<Show> showList = showMapper.getDayShows(dateRequest.getDate());
        ShowDataList showDataList = new ShowDataList(filmMapper);

        for (Show s : showList) {
            showDataList.addShow(s);
        }

        return showDataList;
    }

    private OperationResult getRooms() {

        NotDecidedMapper notDecidedMapper = sessionSql.getMapper(NotDecidedMapper.class);

        RoomList roomList = new RoomList();
        roomList.addRooms(notDecidedMapper.getRoomList());

        return roomList;
    }

    private OperationResult getReservationDetails() {
        return null;
    }

    private OperationResult getFilmList() {

        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        List<FilmData> fd = filmMapper.getAllFilms();
        List<AbsFilm> mf = new ArrayList<>();

        for (FilmData f : fd) {
            mf.add(new MicroFilm(f));
        }

        FilmListSuccess fls = new FilmListSuccess(mf);

        return fls;
    }

}
