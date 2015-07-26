package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.datatypes.film.FilmTitle;
import database.datatypes.show.Show;
import database.datatypes.user.Payment;
import database.datatypes.user.UserData;
import database.mappers.*;
import json.OperationResult;
import json.payment_management.request.PaymentRequest;
import json.payments.PdfTicketCreator;
import json.reservation.request.ReservationRequest;
import json.reservation.request.SeatReservation;
import json.tickets.TicketData;
import json.userPersonalData.UserCreditResponse;
import org.apache.ibatis.session.SqlSession;
import types.exceptions.BadRequestException;
import utilities.BadReqExeceptionThrower;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.TicketMail;
import utilities.reservation.TemporaryReservationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @api {post} /api/payment/*
 * @apiName PaymentManagement
 * @apiGroup PaymentManagement
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
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Inizializzazione degli oggetti necessari
        SqlSession sqlSession = DatabaseConnection.getFactory().openSession(false);
        TemporaryReservationManager temporaryReservationManager = new TemporaryReservationManager();
        //Mapper database
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        SeatMapper seatMapper = sqlSession.getMapper(SeatMapper.class);
        ShowMapper showMapper = sqlSession.getMapper(ShowMapper.class);
        FilmMapper filmMapper = sqlSession.getMapper(FilmMapper.class);
        NotDecidedMapper notDecidedMapper = sqlSession.getMapper(NotDecidedMapper.class);

        response.setContentType("application/json");
        OperationResult operationResult = null;

        PrintWriter outputStream = response.getWriter();

        try {
            //Check se l'utente è loggato
            BadReqExeceptionThrower.checkUserLogged(request);

            //Leggo la richiesta di pagamento
            PaymentRequest paymentRequest = gsonReader.fromJson(request.getReader(), PaymentRequest.class);
            //Check sull input nullo
            BadReqExeceptionThrower.checkNullInput(paymentRequest);

            //Variabile che indica se l'utente usa una carta oppure no
            boolean usesCard = paymentRequest.getCredit_card_number() != null;

            //Prendo la prenotazione dalla tabella temporanea
            ReservationRequest reservationRequest = temporaryReservationManager.getReservation(paymentRequest.getCode());

            int totalPaid = 0;
            int room_number = showMapper.getRoomNumber(reservationRequest.getIntIdShow());
            String payment_date = LocalDate.now().format(dateFormatter);
            String payment_time = LocalTime.now().format(timeFormatter);
            String username = request.getSession().getAttribute("username").toString();
            int id_show = reservationRequest.getIntIdShow();

            //Calcolo il costo totale della prenotazione
            for (SeatReservation sr : reservationRequest.getReservation()) {
                float price = notDecidedMapper.getTicketPrice(sr.getTicket_type());
                totalPaid += price;
            }

            //Controllo quanto credito ha l'utente
            float residualCredit = userMapper.getResidualCredit(username);


            if (!usesCard) {
                //Controllo il credito e lancio eccezione se credito minore di costo
                BadReqExeceptionThrower.checkPaymentAmount(residualCredit, totalPaid);
                userMapper.removeCredit(username, totalPaid);
            } else {
                if (residualCredit > totalPaid) {
                    //Tolgo solo credito e non uso la carta di credito
                    userMapper.removeCredit(username, totalPaid);
                } else {
                    //Tolgo il credito che c'è e poi faccio opeazioni sulla carta di credito
                    userMapper.removeCredit(username, userMapper.getResidualCredit(username));
                }
            }

            //Per ogni sedile prenotato creo una riga di pagamento nel database e creo un oggetto Ticket data per inviare il
            // biglietto
            List<TicketData> ticketDatas = new ArrayList<>();
            FilmTitle film_name = filmMapper.getFilmTitle(showMapper.getShowData(id_show).getId_film());

            for (SeatReservation sr : reservationRequest.getReservation()) {
                int id_seat = seatMapper.getIdSeat(room_number, sr.getIntRow(), sr.getIntColumn());
                Payment payment = new Payment();
                payment.setPayment_date(payment_date);
                payment.setPayment_time(payment_time);
                payment.setTicket_type(sr.getTicket_type());
                payment.setId_seat(id_seat);
                payment.setId_show(id_show);
                payment.setUsername(username);
                payment.setCode(paymentRequest.getCode());
                userMapper.insertPayment(payment);

                TicketData td = new TicketData(payment, sr, film_name.getFilm_title(), notDecidedMapper, showMapper);
                ticketDatas.add(td);

            }
            temporaryReservationManager.confirmReservationRequest(paymentRequest.getCode(), sqlSession);

            //Mando la mail


            PdfTicketCreator pdfcr = new PdfTicketCreator();
            ByteArrayOutputStream os = null;
            try {
                os = pdfcr.createPdf(ticketDatas, getServletContext());
            } catch (Exception e) {
                //new badReq errore nel pdf
                e.printStackTrace();
            }


            ByteArrayInputStream is = null;
            try {
                byte ar[] = os.toByteArray();
                is = new ByteArrayInputStream(ar);
            } catch (NullPointerException e) {
                //eccezione sul pdf di nuovo
                e.printStackTrace();
            }

            TicketMail tk = new TicketMail();
            UserData userData = userMapper.getUserData(username);

            tk.sendEmail(userData.getEmail(), username, film_name.getFilm_title(), is);



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
