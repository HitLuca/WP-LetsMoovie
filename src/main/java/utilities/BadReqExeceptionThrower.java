package utilities;

import types.enums.ErrorCode;
import types.enums.Role;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class BadReqExeceptionThrower {

    public static void checkUserLogged(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null)
            throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
    }

    public static void checkUserAuthorization(HttpServletRequest request, String nick) throws BadRequestException {
        //Se sei un utente e stai cercando un utente diverso da te ti blocco
        HttpSession session = request.getSession();
        String userSession = session.getAttribute("username").toString();
        if ((int) session.getAttribute("role") == Role.USER.getValue() && !nick.equals(userSession))
            throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
    }

    public static void checkEmptyString(String string) throws BadRequestException {
        if (string.equals(""))
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
    }

    public static void checkDuplicateString(String s, String ss) throws BadRequestException {
        if (s.equals(ss))
            throw new BadRequestException(ErrorCode.DUPLICATE_FIELD);
    }

    public static void checkAdminUserNotFound(HttpServletRequest request, Object mapped) throws BadRequestException {
        //Se sei un admin e stai cercando un utente che non esiste te lo dico
        HttpSession session = request.getSession();

        if (mapped instanceof List) {
            if (((List) mapped).size() == 0 && (int) session.getAttribute("role") != Role.USER.getValue())
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
        } else if (mapped instanceof String) {
            if (((String) mapped).equals("") && (int) session.getAttribute("role") != Role.USER.getValue())
                throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public static void checkAdminSuperAdmin(HttpServletRequest request) throws BadRequestException {
        HttpSession session = request.getSession();
        if ((int) session.getAttribute("role") != Role.ADMIN.getValue() && (int) session.getAttribute("role") != Role.SUPER_ADMIN.getValue()) {
            throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
        }
    }

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

    public static void checkStatusString(List<String> accepted, String status) throws BadRequestException {
        if (!accepted.contains(status)) {
            throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
        }
    }
}
