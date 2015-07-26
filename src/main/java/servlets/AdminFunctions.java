package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import database.DatabaseConnection;
import database.datatypes.film.FilmData;
import database.datatypes.film.FilmIncome;
import database.datatypes.film.FilmTitle;
import database.datatypes.seat.RoomData;
import database.datatypes.seat.Seat;
import database.datatypes.seat.SeatCount;
import database.datatypes.show.Show;
import database.datatypes.user.Payment;
import database.datatypes.user.UserData;
import database.datatypes.user.UserPaid;
import database.mappers.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hitluca on 20/07/15.
 */
@WebServlet(name = "AdminFunctions", urlPatterns = "/api/admin/*")
public class AdminFunctions extends HttpServlet {
    private Gson gsonWriter;
    private Gson gsonReader;
    private SendGrid sendGrid;
    private boolean sendEmail;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql = DatabaseConnection.getFactory().openSession(true);
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        SeatMapper seatMapper = sessionSql.getMapper(SeatMapper.class);
        ShowMapper showMapper = sessionSql.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sessionSql.getMapper(FilmMapper.class);
        NotDecidedMapper notDecidedMapper = sessionSql.getMapper(NotDecidedMapper.class);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        response.setContentType("application/json");
        OperationResult operationResult = null;

        PrintWriter outputStream = response.getWriter();

        try {
            //Controllo che l'utente sia loggato
            //BadReqExeceptionThrower.checkUserLogged(request); //TODO:mettere apposto

            //Controllo che abbia i permessi adatti
            //BadReqExeceptionThrower.checkAdminSuperAdmin(request);//TODO:mettere apposto

            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String function = rs.getParameter();

            switch (function) {
                //FATTO
                case "updateRoomSeatStatus": {
                    RoomSeatRequest roomSeatRequest = gsonReader.fromJson(request.getReader(), RoomSeatRequest.class);

                    int room_number = Integer.parseInt(roomSeatRequest.getRoom_number());

                    int id_seat;
                    for (SeatRequest seatRequest : roomSeatRequest.getSeats()) {
                        seatRequest.setId_show("1");
                        seatRequest.setStatus("ok");

                        //Controllo di non avere parametri invalidi
                        BadReqExeceptionThrower.checkRegex(roomSeatRequest);

                        //Lo status deve essere tra quelli accettati
                        BadReqExeceptionThrower.checkStatusString(new ArrayList<>(Arrays.asList("ok", "broken")), seatRequest.getStatus());

                        int row = Integer.parseInt(seatRequest.getRow());
                        int column = Integer.parseInt(seatRequest.getColumn());

                        id_seat = seatMapper.getIdSeat(room_number, row, column);

                        String pastStatus = seatMapper.getSeatStatus(id_seat);

                        LocalDateTime localDate = LocalDateTime.now();


                        String todayDate = localDate.format(dateFormatter);
                        String todayTime = localDate.format(timeFormatter);

                        switch (pastStatus) {
                            case "ok": //Il posto deve essere rotto
                            {
                                String newStatus = "broken";
                                seatMapper.updateSeatStatus(newStatus, id_seat);
                                List<Payment> payments = userMapper.getDayPaymentsAfterTime(localDate.format(dateFormatter), localDate.format(timeFormatter));
                                payments.addAll(userMapper.getPaymentsAfterDate(localDate.format(dateFormatter)));

                                for (Payment p : payments) {
                                    if (p.getId_seat() == id_seat) {
                                        String ticket_type = p.getTicket_type();
                                        float price = notDecidedMapper.getTicketPrice(ticket_type);
                                        String username = p.getUsername();
                                        UserData userData = userMapper.getUserData(username);
                                        userMapper.addCredit(username, price);
                                        userMapper.deletePayment(p);
                                        String film_title = filmMapper.getFilmTitle((showMapper.getShowData(payments.get(0).getId_show())).getId_film()).getFilm_title();

                                        Seat s = seatMapper.getSeatFromId(p.getId_seat());
                                        SeatDetailRequest seatDetailRequest = new SeatDetailRequest(String.valueOf(s.getRow()), String.valueOf(s.getColumn()));
                                        seatDetailRequest.setTicket_type(p.getTicket_type());
                                        seatDetailRequest.setPrice(notDecidedMapper.getTicketPrice(p.getTicket_type()));
                                        sendEmail(userData.getEmail(), userData.getName(), userData.getSurname(), p, new ArrayList<SeatDetailRequest>(Arrays.asList(seatDetailRequest)), userMapper, film_title, 100f);

                                        seatMapper.insertSeatReservation(p.getId_show(), p.getId_seat(), newStatus);
                                    }
                                }
                                break;
                            }
                            case "broken": //Il posto va inserito negli spettacoli
                            {
                                String newStatus = "ok";

                                List<Show> shows = showMapper.getDayShowsAfterTime(localDate.format(dateFormatter), localDate.format(timeFormatter));
                                shows.addAll(showMapper.getShowsAfterDate(localDate.format(dateFormatter)));

                                for (Show s : shows) {
                                    if (s.getRoom_number() == room_number) {
                                        int id_show = s.getId_show();
                                        seatMapper.removeSeatReservation(id_show, id_seat);
                                    }
                                }
                                seatMapper.updateSeatStatus(newStatus, id_seat);
                                break;
                            }
                        }
                    }
                    outputStream.print("{}");
                    sessionSql.close();
                    break;
                }
                //NON UTILIZZATO
                case "updateShowSeatStatus": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int room_number = showMapper.getRoomNumber(Integer.parseInt(seatRequest.getId_show()));
                    int id_seat = seatMapper.getIdSeat(room_number, Integer.parseInt(seatRequest.getRow()), Integer.parseInt(seatRequest.getColumn()));
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    //Lo status deve essere tra quelli accettati
                    BadReqExeceptionThrower.checkStatusString(new ArrayList<>(Arrays.asList("reserved", "broken")), seatRequest.getStatus());

                    seatMapper.updateShowSeatStatus(id_show, id_seat, seatRequest.getStatus());
                    break;
                }
                case "removeSeatReservation": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);
                    seatRequest.setStatus("");
                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(seatRequest);

                    int room_number = showMapper.getRoomNumber(Integer.parseInt(seatRequest.getId_show()));
                    int id_seat = seatMapper.getIdSeat(room_number, Integer.parseInt(seatRequest.getRow()), Integer.parseInt(seatRequest.getColumn()));
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    seatMapper.removeSeatReservation(id_show, id_seat);
                    break;
                }
                //FATTO
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
                //FATTO
                case "getRoomSeats": {
                    RoomRequest roomRequest = gsonReader.fromJson(request.getReader(), RoomRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(roomRequest);

                    int room_number = Integer.parseInt(roomRequest.getRoom_number());
                    RoomData roomData = seatMapper.getRoomData(room_number);
                    SeatList seatList = new SeatList(roomData.getLength(), roomData.getWidth());

                    List<Seat> totalSeats = seatMapper.getRoomSeats(room_number);

                    for (Seat s : totalSeats) {
                        String status = s.getStatus();
                        switch (s.getStatus()) {
                            case "ok": {
                                seatList.addSeat(new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.FREE));
                                break;
                            }
                            case "broken": {
                                seatList.addSeat(new ShowSeat(s.getRow(), s.getColumn(), SeatStatus.BROKEN));
                                break;
                            }
                        }
                    }

                    outputStream.print(gsonWriter.toJson(seatList));
                    sessionSql.close();
                    break;
                }
                //FATTO
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
                //FATTO
                case "getRankedUsers": {
                    UserRankRequest userRankRequest = gsonReader.fromJson(request.getReader(), UserRankRequest.class);

                    //Controllo di non avere parametri invalidi
                    BadReqExeceptionThrower.checkRegex(userRankRequest);

                    int top = Integer.parseInt(userRankRequest.getTop());
                    int user_count = userMapper.getUserCountForRank();
                    int percentage = Math.round(1.0f * user_count * top / 100);
                    List<UserPaid> userPaids = userMapper.getRankedUserTotalPayments(percentage);
                    List<UserRank> userRankRequests = new ArrayList<>();
                    int i = 1;
                    for (UserPaid u : userPaids) {
                        userRankRequests.add(new UserRank(u, i));
                        i++;
                    }

                    outputStream.print(gsonWriter.toJson(userRankRequests));
                    sessionSql.close();
                    break;
                }
                //FATTO
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
                //NON UTILIZZATO
                case "getAllFilmsIncome": {
                    List<FilmIncome> filmIncomes = filmMapper.getAllFilmsIncome();
                    List<FilmIncomeResponse> filmIncomeResponses = new ArrayList<>();
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

                    Show show = showMapper.getShowData(userMapper.getPaymentFromCode(code).get(0).getId_show());

                    String showDate = show.getShow_date();
                    String showTime = show.getShow_time();

                    List<Payment> payments = userMapper.getPaymentFromCode(code);

                    BadReqExeceptionThrower.checkTime(showDate, showTime);

                    if (seatDetailRequests != null) {
                        for (SeatDetailRequest sdr : seatDetailRequests) {
                            BadReqExeceptionThrower.checkRegex(sdr);
                        }
                    }

                    if (seatDetailRequests != null && !code.equals("")) {
                        float totalRefund = 0f;
                        float refoundPercentage = 0.8f;
                        int room_number = showMapper.getRoomNumberFromCode(code);
                        int id_show = payments.get(0).getId_show();
                        String username = userMapper.getPaymentFromCode(code).get(0).getUsername();
                        for (SeatDetailRequest sdr : seatDetailRequests) {
                            if (sdr.getChecked() != null) {
                                if (sdr.getChecked().equals("true")) {
                                    int id_seat = seatMapper.getIdSeat(room_number, Integer.parseInt(sdr.getS_row()), Integer.parseInt(sdr.getS_column()));
                                    float ticket_price = seatMapper.getSeatTicketPrice(id_seat, id_show);
                                    String ticket_type = userMapper.getPaymentData(id_show, id_seat).getTicket_type();

                                    sdr.setTicket_type(ticket_type);
                                    sdr.setPrice(ticket_price);

                                    userMapper.deletePaymentFromCode(code, id_seat);
                                    totalRefund += ticket_price;
                                }
                            }
                        }
                        float computedRefound = totalRefund * refoundPercentage;
                        userMapper.addCredit(username, computedRefound);
                        String film_title = filmMapper.getFilmTitle((showMapper.getShowData(payments.get(0).getId_show())).getId_film()).getFilm_title();
                        UserData userData = userMapper.getUserData(username);
                        String name = userData.getName();
                        String surname = userData.getSurname();
                        String email = userData.getEmail();
                        sendEmail(email, name, surname, payments.get(0), seatDetailRequests, userMapper, film_title, 80f);
                    } else if (seatDetailRequests == null && !code.equals("")) {
                        String username = payments.get(0).getUsername();
                        int id_show = payments.get(0).getId_show();

                        List<SeatDetailResponse> seatDetailResponses = new ArrayList<>();
                        Seat seat;

                        for (Payment p : payments) {
                            seat = seatMapper.getSeatFromId(p.getId_seat());
                            seatDetailResponses.add(new SeatDetailResponse(seat.getRow(), seat.getColumn(), p.getTicket_type(), notDecidedMapper.getTicketPrice(p.getTicket_type())));
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

        String api_user = System.getenv("API_USER");
        String api_key = System.getenv("API_KEY");
        sendGrid = new SendGrid(api_user, api_key);
        try {
            sendEmail = System.getenv("SEND_EMAIL").equals("TRUE");
        } catch (Exception e) {
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void sendEmail(String userEmail, String name, String surname, Payment payment, List<SeatDetailRequest> seats, UserMapper userMapper, String film_title, float refoundPercentage) {

        String message = "Salve " + name + " " + surname + ",<br>La informiamo che le prenotazioni dei seguenti posti, per la proiezione "
                + film_title + " in data " + payment.getPayment_date() + ", alle ore " + payment.getPayment_time() + ", sono state disdette:<br><br>";
        for (SeatDetailRequest sdr : seats) {
            if (sdr.getChecked() != null) {
                if (sdr.getChecked().equals("true")) {
                    message += "Fila: " + (Integer.parseInt(sdr.getS_row()) + 1) + "<br>Posto: " + (Integer.parseInt(sdr.getS_column()) + 1) + "<br>Tipo biglietto: " + sdr.getTicket_type() + "<br>Prezzo: Euro " + sdr.getPrice() + "<br><br>";
                }
            }
        }
        message += "La informiamo inoltre che il " + refoundPercentage + "% dell'intero importo pagato è stato accreditato nel suo account.";

        System.out.println(message);

        SendGrid.Email email = new SendGrid.Email();
        email.addTo(/*userEmail*/ "luca.simonetto.94@gmail.com"); //TODO:mettere apposto
        email.setFrom("info@letsmoovie.com");
        email.setSubject("Prenotazione disdetta");
        email.setTemplateId("fa28abaf-6b95-44a2-b4a2-d4f21fe730c8");
        email.setHtml(message);

        if (sendEmail) {
            try {
                sendGrid.send(email).getMessage();
            } catch (SendGridException e) {
            }
        }
    }
}
