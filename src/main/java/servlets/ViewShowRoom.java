package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.RoomData;
import database.datatypes.Seat;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import json.OperationResult;
import json.film.response.FilmListSuccess;
import json.showRoom.SeatList;
import json.showRoom.ShowSeat;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.enums.SeatStatus;
import types.exceptions.BadRequestException;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        SeatMapper seatMapper = sessionSql.getMapper(SeatMapper.class);
        response.setContentType("application/json");

        try{
            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            HttpSession session = request.getSession();

            if (session.getAttribute("username") == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }

            //String matcher che preleva lo showId da cercare dall'url e lancia Err.2 in caso sia nullo o mal formattato
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            int showId = Integer.valueOf(rs.getParameter());

            RoomData roomData = seatMapper.getShowRoomData(showId);
            SeatList showSeats = new SeatList(roomData.getLength(), roomData.getWidth());

            List<Seat> showBrokenSeats = seatMapper.getShowBrokenSeats(showId);
            for (Seat s : showBrokenSeats) {
                ShowSeat showSeat = new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.BROKEN);
                showSeats.addSeat(showSeat);
            }

            List<Seat> showReservedSeat = seatMapper.getSeatReservations(showId);
            for (Seat s : showReservedSeat) {
                ShowSeat showSeat = new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.RESERVED);
                showSeats.addSeat(showSeat);
            }

            List<Seat> showFreeSeat = seatMapper.getFreeSeat(showId);
            for (Seat s : showFreeSeat) {
                ShowSeat showSeat = new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.FREE);
                showSeats.addSeat(showSeat);
            }

            viewResult = showSeats;

        } catch (BadRequestException e) {
            viewResult = e;
            response.setStatus(400);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            viewResult = new BadRequestException();
            response.setStatus(400);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(viewResult));
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
