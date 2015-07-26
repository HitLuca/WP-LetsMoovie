package utilities;

import database.datatypes.film.FilmData;
import database.datatypes.show.ShowTime;
import database.datatypes.user.Payment;
import json.adminFunctions.request.DeleteReservationRequest;
import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class BadReqExeceptionThrower {

    /**
     * Lancia eccezione se l'utente non è loggato
     *
     * @param request
     * @throws BadRequestException
     */
    public static void checkUserLogged(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null)
            throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
    }

    public static void checkNotUserLogged(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null)
            throw new BadRequestException(ErrorCode.ALREADY_LOGGED);
    }

    /**
     * Lancia eccezione se l'utente è USER e sta cercando di accedere a un nome utente diverso dal suo
     *
     * @param request usato per vedere l'username loggato
     * @param nick    username ricercato
     * @throws BadRequestException
     */
    public static void checkUserAuthorization(HttpServletRequest request, String nick) throws BadRequestException {
        //Se sei un utente e stai cercando un utente diverso da te ti blocco
        HttpSession session = request.getSession();
        String userSession = session.getAttribute("username").toString();
        if ((int) session.getAttribute("role") == Role.USER.getValue() && !nick.equals(userSession))
            throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
    }

    /**
     * Lancia eccezione se le due stringhe in input sono uguali
     *
     * @param s
     * @param ss
     * @throws BadRequestException
     */
    public static void checkDuplicateString(String s, String ss) throws BadRequestException {
        if (s.equals(ss))
            throw new BadRequestException(ErrorCode.DUPLICATE_FIELD);
    }

    /**
     * Lancia eccezione se l'utente è ADMIN o SUPER_ADMIN e l'oggetto mapped, ottenuto dal db, è nullo.
     * Ciò vuol dire che nel database non si trova l'oggetto cercato
     *
     * @param request
     * @param mapped
     * @throws BadRequestException
     */
    public static void checkAdminUserNotFound(HttpServletRequest request, Object mapped) throws BadRequestException {
        //Se sei un admin e stai cercando un utente che non esiste te lo dico
        HttpSession session = request.getSession();

        if (mapped instanceof List) {
            if (((List) mapped).size() == 0 && (int) session.getAttribute("role") != Role.USER.getValue())
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
        } else if (mapped instanceof String) {
            if (mapped.equals("") && (int) session.getAttribute("role") != Role.USER.getValue())
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * Se l'utente non è nè ADMIN nè SUPER_ADMIN si lancia eccezione
     *
     * @param request
     * @throws BadRequestException
     */
    public static void checkAdminSuperAdmin(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("role") != Role.ADMIN.getValue() && (int) session.getAttribute("role") != Role.SUPER_ADMIN.getValue()) {
            throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    /**
     * Funzione che valida un oggetto e se vi sono campi vuoti o errati lancia eccezione
     *
     * @param object
     * @throws BadRequestExceptionWithParameters
     */
    public static void checkRegex(Object object) throws BadRequestExceptionWithParameters {
        try {
            List<String> invalidParameters = ModelValidator.validate(object);
            if (!invalidParameters.isEmpty()) {
                throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, invalidParameters);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione che lancia un eccezione nel caso la stringa status non sia contenuta nella collection accepted
     *
     * @param accepted
     * @param status
     * @throws BadRequestException
     */
    public static void checkStatusString(List<String> accepted, String status) throws BadRequestException {
        if (!accepted.contains(status)) {
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        }
    }

    /**
     * Lancia eccezione se l'oggetto in input oppure se è una stringa
     * Lancia eccezione se la stringa in input è "", vuota
     *
     * @param o
     * @throws BadRequestException
     */
    public static void checkNullInput(Object o) throws BadRequestException {

        if (o instanceof String)
            if (o.equals(""))
                throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        if (o == null)
            throw new BadRequestException(ErrorCode.EMPTY_REQ);
    }

    /**
     * Funzione che lancia un'eccezione in caso il credito non sia sufficiente a pagare il totale
     *
     * @param residualCredit
     * @param totalPaid
     * @throws BadRequestException
     */
    public static void checkPaymentAmount(float residualCredit, float totalPaid) throws BadRequestException {
        if (residualCredit < totalPaid) {
            throw new BadRequestException(ErrorCode.NOT_ENOUGH_CREDIT);
        }
    }

    public static void checkDeleteReservation(DeleteReservationRequest drr) throws BadRequestException {
        if (drr.getSeatList() == null && drr.getCode().equals("")) {
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        }
    }

    public static void checkUserAlreadyLogged(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null)
            throw new BadRequestException(ErrorCode.ALREADY_LOGGED);
    }

    public static void checkDuplicateFields(List<String> sl, String s) throws BadRequestException {
        if (sl.contains(s))
            throw new BadRequestException(ErrorCode.DUPLICATE_FIELD);
    }

    public static void checkFilmNotFound(FilmData o) throws BadRequestException {
        if (o == null) {
            throw new BadRequestException(ErrorCode.FILM_NOT_FOUND);
        }
    }

    public static void checkShowIsPassed(ShowTime time) throws BadRequestException {

        //Se lo spettacolo era ieri
        if (time.getLocalDate().isBefore(LocalDate.now()))
            throw new BadRequestException(ErrorCode.END_OF_TIME);

        //Se lo spettacolo era un ora fa
        if (time.getLocalDate().isEqual(LocalDate.now()))
            if (time.getLocalTime().isBefore(LocalTime.now()))
                throw new BadRequestException(ErrorCode.END_OF_TIME);
    }

    public static void checkTime(String showDate, String showTime) throws BadRequestException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String todayDate = LocalDate.now().format(dateFormatter);
        String todayTime = LocalTime.now().format(timeFormatter);

        if (Date.valueOf(showDate).before(Date.valueOf(todayDate))) {
            throw new BadRequestException(ErrorCode.BACK_IN_TIME);
        } else if (Date.valueOf(showDate).equals(Date.valueOf(todayDate))) {
            if (Time.valueOf(todayTime).before(Time.valueOf(todayTime))) {
                throw new BadRequestException(ErrorCode.BACK_IN_TIME);
            }
        }
    }

    public static void checkPayments(List<Payment> payments) throws BadRequestException {
        if (payments == null) {
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        } else if (payments.size() == 0) {
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        }
    }
}
