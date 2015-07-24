package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.datatypes.film.FilmIncome;
import database.datatypes.film.FilmTitle;
import database.datatypes.seat.RoomData;
import database.datatypes.seat.Seat;
import database.datatypes.seat.SeatCount;
import database.datatypes.user.UserPaid;
import database.mappers.FilmMapper;
import database.mappers.SeatMapper;
import database.mappers.ShowMapper;
import database.mappers.UserMapper;
import json.OperationResult;
import json.adminFunctions.request.*;
import json.adminFunctions.response.*;
import json.showRoom.SeatList;
import json.showRoom.ShowSeat;
import org.apache.ibatis.session.SqlSession;
import types.enums.SeatStatus;
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
import java.util.Arrays;
import java.util.List;

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
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);

        response.setContentType("application/json");
        OperationResult operationResult = null;

        PrintWriter outputStream = response.getWriter();

        try {
            //BadReqExeceptionThrower.checkUserLogged(request); TODO:Togliere commento

            //Controllo che abbia i permessi adatti
            //BadReqExeceptionThrower.checkAdminSuperAdmin(request); TODO:Togliere commento

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String function = rs.getParameter();

            switch (function) {
                case "updateSeatStatus": {
                    //TODO:Mettere apposto
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
                case "removeSeatReservation": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    seatMapper.removeSeatReservation(id_show, id_seat);
                    break;
                }
                case "getShowSeats": {
                    ShowRequest showRequest = gsonReader.fromJson(request.getReader(), ShowRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(showRequest);

                    int id_show = Integer.parseInt(showRequest.getId_show());
                    int room_number = showMapper.getRoomNumber(id_show);
                    RoomData roomData = seatMapper.getRoomData(room_number);
                    SeatList seatList = new SeatList(roomData.getLength(), roomData.getWidth());

                    List<Seat> totalSeats = seatMapper.getShowFreeSeat(id_show);
                    totalSeats.addAll(seatMapper.getShowReservedSeats(id_show));
                    totalSeats.addAll(seatMapper.getShowBrokenSeats(id_show));

                    for (Seat s : totalSeats) {
                        String status = s.getStatus();
                        switch (s.getStatus()) {
                            case "reserved": {
                                seatList.addSeat(new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.RESERVED));
                                break;
                            }
                            case "broken": {
                                seatList.addSeat(new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.BROKEN));
                                break;
                            }
                            default: { //"free"
                                seatList.addSeat(new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.FREE));
                                break;
                            }
                        }
                    }
                    outputStream.print(gsonWriter.toJson(seatList));
                    sessionSql.close();
                    break;
                }
                case "getRankedReservations": {
                    RankedRoomRequest rankedRoomRequest = gsonReader.fromJson(request.getReader(), RankedRoomRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(rankedRoomRequest);

                    int room_number = Integer.parseInt(rankedRoomRequest.getRoom_number());
                    int top = Integer.parseInt(rankedRoomRequest.getTop());
                    int seat_count = seatMapper.getSeatCountForRank(room_number);
                    RoomData roomData = seatMapper.getRoomData(room_number);
                    RoomSeatList roomSeatList = new RoomSeatList(roomData.getLength(), roomData.getWidth());
                    int percentage = Math.round(seat_count * top / 100f);
                    List<SeatCount> seatCounts = seatMapper.getRankedSeatReservations(room_number, percentage);
                    for (SeatCount sc : seatCounts) {
                        roomSeatList.addSeat(new RoomSeatCount(sc.getRow(), sc.getColumn(), sc.getCount()));
                    }
                    outputStream.print(gsonWriter.toJson(roomSeatList));
                    sessionSql.close();
                    break;
                }
                case "getRankedUsers": {
                    UserRankRequest userRankRequest = gsonReader.fromJson(request.getReader(), UserRankRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(userRankRequest);

                    int top = Integer.parseInt(userRankRequest.getTop());
                    int user_count = userMapper.getUserCountForRank();
                    int percentage = Math.round(1.0f * user_count * top / 100);
                    List<UserPaid> userPaids = userMapper.getRankedUserTotalPayments(percentage);
                    List<UserRank> userRankRequests = new ArrayList<UserRank>();
                    for (UserPaid u : userPaids) {
                        userRankRequests.add(new UserRank(u));
                    }

                    outputStream.print(gsonWriter.toJson(userRankRequests));
                    sessionSql.close();
                    break;
                }
                case "getFilmIncome": {
                    FilmIncomeRequest filmIncomeRequest = gsonReader.fromJson(request.getReader(), FilmIncomeRequest.class);
                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(filmIncomeRequest);

                    int id_film = Integer.valueOf(filmIncomeRequest.getId_film());

                    FilmData fd = filmMapper.getFilmData(id_film);
                    float income = filmMapper.getFilmIncome(id_film);
                    FilmIncomeResponse res = new FilmIncomeResponse(fd, income);

                    outputStream.print(gsonWriter.toJson(res));
                    sessionSql.close();
                    break;
                }
                case "getAllFilmsIncome": {
                    List<FilmIncome> filmIncomes = filmMapper.getAllFilmsIncome();
                    List<FilmIncomeResponse> filmIncomeResponses = new ArrayList<FilmIncomeResponse>();
                    for (FilmIncome f : filmIncomes) {
                        FilmTitle filmTitle = filmMapper.getFilmTitle(f.getId_film());
                        filmIncomeResponses.add(new FilmIncomeResponse(filmTitle.getFilm_title(), filmTitle.getYear(), f.getIncome()));
                    }
                    outputStream.print(gsonWriter.toJson(filmIncomeResponses));
                    sessionSql.close();
                    break;
                }
                case "deleteReservation": {
                    DeleteReservationRequest deleteReservationRequest = gsonReader.fromJson(request.getReader(), DeleteReservationRequest.class);
                    String code = deleteReservationRequest.getCode();
                    List<SeatDetailRequest> seatDetailRequests = deleteReservationRequest.getSeatList();

                    BadReqExeceptionThrower.checkDeleteReservation(deleteReservationRequest);

                    for (SeatDetailRequest sdr : seatDetailRequests) {
                        BadReqExeceptionThrower.checkRegex(sdr);
                    }

                    if (seatDetailRequests != null && !code.equals("")) {
                        float totalRefund = 0f;
                        float refoundPercentage = 0.8f;
                        for (SeatDetailRequest sdr : seatDetailRequests) {
                            float ticket_price = sdr.getPrice();
                            int room_number = showMapper.getRoomNumberFromCode(code);
                            int id_seat = seatMapper.getIdSeat(room_number, sdr.getS_row(), sdr.getS_column());
                            userMapper.deletePaymentFromCode(code, id_seat);
                            totalRefund += ticket_price;
                        }
                        float computedRefound = totalRefund * refoundPercentage;
                        userMapper.addCredit("username", computedRefound);
                    } else if (seatDetailRequests == null && !code.equals("")) {
                        //OggettoConLeCose oggetto = ClasseMagica.dammiLeCose(code); username, data, ora, id_show
                        seatDetailRequests = userMapper.getReservationBlock("show_date", "show_time", 1, "username");
                        List<SeatDetailResponse> seatDetailResponses = new ArrayList<SeatDetailResponse>();
                        for (SeatDetailRequest sdr : seatDetailRequests) {
                            seatDetailResponses.add(new SeatDetailResponse(sdr));
                        }
                        outputStream.print(gsonWriter.toJson(seatDetailResponses));
                        sessionSql.close();
                    }
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
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
        gsonReader = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
