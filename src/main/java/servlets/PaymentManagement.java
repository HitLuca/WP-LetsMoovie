package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.user.Payment;
import database.mappers.*;
import json.OperationResult;
import json.payment_management.request.PaymentRequest;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.reservation.TemporaryReservationManager;
import utilities.reservation.request.TemporaryReservationRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @api {post} /api/payment/*
 * @apiName PaymentManagement
 * @apiGroup PaymentManagement
 *
 * @apiError (0) {int} errorCode BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet
 * @apiError (2) {String[]} errorCode EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione
 * @apiError (10) {int} errorCode NOT_LOGGED_IN: l'utente non è loggato
 */
@WebServlet(name = "PaymentManagement", urlPatterns = "/api/payment/*")
//TODO: mapping Servlet con asterisco in più
public class PaymentManagement extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;
    TemporaryReservationManager temporaryReservationManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sqlSession = DatabaseConnection.getFactory().openSession(false);
        TemporaryReservationManager temporaryReservationManager = new TemporaryReservationManager();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        SeatMapper seatMapper = sqlSession.getMapper(SeatMapper.class);
        ShowMapper showMapper = sqlSession.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sqlSession.getMapper(FilmMapper.class);
        NotDecidedMapper notDecidedMapper = sqlSession.getMapper(NotDecidedMapper.class);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        response.setContentType("application/json");
        OperationResult operationResult = null;

        PrintWriter outputStream = response.getWriter();

        try {
            BadReqExeceptionThrower.checkUserLogged(request);

            PaymentRequest paymentRequest = gsonReader.fromJson(request.getReader(), PaymentRequest.class);
            BadReqExeceptionThrower.checkNullInput(paymentRequest);
            boolean usesCard = paymentRequest.getCredit_card_number() != null;

            ReservationRequest reservationRequest = temporaryReservationManager.getReservation(paymentRequest.getCode());

            int totalPaid = 0;
            int room_number = showMapper.getRoomNumber(reservationRequest.getIntIdShow());
            String payment_date = LocalDate.now().format(dateFormatter);
            String payment_time = LocalTime.now().format(timeFormatter);
            String username = request.getSession().getAttribute("username").toString();
            int id_show = reservationRequest.getIntIdShow();

            for (SeatReservation sr : reservationRequest.getReservation()) {
                float price = notDecidedMapper.getTicketPrice(sr.getTicket_type());
                totalPaid += price;
            }

            float residualCredit = userMapper.getResidualCredit(username);

            if (!usesCard) {
                BadReqExeceptionThrower.checkPaymentAmount(residualCredit, totalPaid);
                userMapper.removeCredit(username, totalPaid);
            } else {
                if (residualCredit > totalPaid) {
                    userMapper.removeCredit(username, totalPaid);
                } else {
                    userMapper.removeCredit(username, userMapper.getResidualCredit(username));
                }
            }

            for (SeatReservation sr : reservationRequest.getReservation()) {
                int id_seat = seatMapper.getIdSeat(room_number, sr.getIntRow(), sr.getIntColumn());
                Payment payment = new Payment();
                payment.setPayment_date(payment_date);
                payment.setPayment_time(payment_time);
                payment.setTicket_type(sr.getTicket_type());
                payment.setId_seat(id_seat);
                payment.setId_show(id_show);
                payment.setUsername(username);
                userMapper.insertPayment(payment);
            }
            temporaryReservationManager.confirmReservationRequest(paymentRequest.getCode(),sqlSession);

        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            operationResult = new BadRequestException();
            response.setStatus(400);
        } catch (BadRequestException e) {
            operationResult = e;
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        }
        sqlSession.commit();
        sqlSession.close();
        outputStream.print(gsonWriter.toJson(operationResult));
    }

    @Override
    public void init() throws ServletException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
        temporaryReservationManager = new TemporaryReservationManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
