package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.seat.RoomData;
import database.mappers.SeatMapper;
import database.mappers.UserMapper;
import json.OperationResult;
import json.adminFunctions.request.SeatRequest;
import json.adminFunctions.request.ShowRequest;
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
import java.util.Arrays;

/**
 * Created by hitluca on 20/07/15.
 */
@WebServlet(name = "AdminFunctions", urlPatterns = "/api/admin/*")
public class AdminFunctions extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        SeatMapper seatMapper = sessionSql.getMapper(SeatMapper.class);

        response.setContentType("application/json");
        OperationResult operationResult = null;

        ServletOutputStream outputStream = response.getOutputStream();

        try {
            BadReqExeceptionThrower.checkUserLogged(request);

            //Controllo che abbia i permessi adatti
            BadReqExeceptionThrower.checkAdminSuperAdmin(request);

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String function = rs.getParameter();

            switch (function) {
                case "updateSeatStatus": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);
                    //Setto il campo che non uso a 1 così passa la regex
                    seatRequest.setId_show("1");

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());

                    //Lo status deve essere tra quelli accettati
                    BadReqExeceptionThrower.checkStatusString(new ArrayList<String>(Arrays.asList("ok", "broken")), seatRequest.getStatus());

                    seatMapper.updateSeatStatus(seatRequest.getStatus(), id_seat);

                    break;
                }
                case "removeSeatReservation": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    seatMapper.removeSeatReservation(id_show, id_seat);
                    break;
                }
                case "updateShowSeatStatus": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    //Lo status deve essere tra quelli accettati
                    BadReqExeceptionThrower.checkStatusString(new ArrayList<String>(Arrays.asList("reserved", "broken")), seatRequest.getStatus());

                    seatMapper.updateShowSeatStatus(id_show, id_seat, seatRequest.getStatus());
                    break;
                }
                //posti venduti cancella prenotazione incassi per film client top
                case "getShowSeats": {
                    ShowRequest showRequest = gsonReader.fromJson(request.getReader(), ShowRequest.class);
                    RoomData roomData = seatMapper.getRoomData(showRequest.getRoom_number());
                    //SeatList seatList = new SeatList(seatMapper.get);

                    break;
                }
            }
        } catch (BadRequestException e) {
            operationResult = e;
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            operationResult = new BadRequestException();
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        }
        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
