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

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by hitluca on 22/07/15.
 */

@WebServlet(name = "PaymentManagement", urlPatterns = "/api/payment/*")

public class PaymentManagement extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;

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

        ServletOutputStream outputStream = response.getOutputStream();

        try {
            //BadReqExeceptionThrower.checkUserLogged(request);
            // TODO:togliere commento

            PaymentRequest paymentRequest = gsonReader.fromJson(request.getReader(), PaymentRequest.class);
            boolean usesCard = paymentRequest.getCredit_card_number().equals("");

            ReservationRequest reservationRequest = temporaryReservationManager.confirmReservationRequest(paymentRequest.getCode(), sqlSession);

            int totalPaid = 0;
            int room_number = showMapper.getRoomNumber(reservationRequest.getIntIdShow());
            String payment_date = LocalDate.now().format(dateFormatter);
            String payment_time = LocalTime.now().format(timeFormatter);
            String username = request.getSession().getAttribute("username").toString();
            int id_show = reservationRequest.getIntIdShow();

            for (SeatReservation sr : reservationRequest.getReservation()) {
                int id_seat = seatMapper.getIdSeat(room_number, sr.getIntRow(), sr.getIntColumn());
                Payment payment = new Payment(payment_date, payment_time, sr.getTicket_type(), id_seat, id_show, username);
                userMapper.insertPayment(payment);
                float price = notDecidedMapper.getTicketPrice(sr.getTicket_type());
                totalPaid += price;
            }
            float residualCredit = userMapper.getResidualCredit(username);

            if (!usesCard) {
                BadReqExeceptionThrower.checkPaymentAmount(residualCredit, totalPaid);
                userMapper.removeCredit(username, totalPaid);
            } else {
                if (residualCredit < totalPaid) {
                    userMapper.removeCredit(username, totalPaid);
                } else {
                    userMapper.removeCredit(username, userMapper.getResidualCredit(username));
                }
            }
        } catch (JsonIOException | JsonSyntaxException | NullPointerException e) {
            operationResult = new BadRequestException();
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        } catch (BadRequestException e) {
            operationResult = new BadRequestException();
            operationResult = e;
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        }
        sqlSession.commit();
        sqlSession.close();
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
