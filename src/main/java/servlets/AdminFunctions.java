package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.SeatMapper;
import database.mappers.UserMapper;
import json.OperationResult;
import json.adminFunctions.request.SeatRequest;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;
import utilities.RestUrlMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

        response.setContentType("application/json");
        OperationResult operationResult = null;

        ServletOutputStream outputStream = response.getOutputStream();

        try {
            //Check se l'utente NON è loggato (da sloggato non vedi dati di nessuno
            /*HttpSession session = request.getSession();

            if (session.getAttribute("username") == null) {
                throw new BadRequestException(ErrorCode.NOT_LOGGED_IN);
            }

            if ((int) session.getAttribute("role") != Role.ADMIN.getValue() && (int) session.getAttribute("role") != Role.SUPER_ADMIN.getValue()) {
                throw new BadRequestException(ErrorCode.NOT_AUTHORIZED);
            }
*/
            RestUrlMatcher rs = new RestUrlMatcher(request.getPathInfo());
            String function = rs.getParameter();

            switch (function) {
                case "updateSeatStatus": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);
                    seatRequest.setId_show("1");

                    List<String> invalidParameters = ModelValidator.validate(seatRequest);
                    if (!invalidParameters.isEmpty()) {
                        throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, invalidParameters);
                    }

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());

                    if (!seatRequest.getStatus().equals("ok") && !seatRequest.getStatus().equals("broken")) {
                        System.out.println(seatRequest.getStatus());
                        throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
                    }

                    seatMapper.updateSeatStatus(seatRequest.getStatus(), id_seat);

                    break;
                }
                case "removeSeatReservation": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    List<String> invalidParameters = ModelValidator.validate(seatRequest);
                    if (!invalidParameters.isEmpty()) {
                        throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, invalidParameters);
                    }

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());
                    int id_show = Integer.parseInt(seatRequest.getId_show());

                    seatMapper.removeSeatReservation(id_show, id_seat);
                    break;
                }
                case "updateShowSeatStatus": {
                    SeatRequest seatRequest = gsonReader.fromJson(request.getReader(), SeatRequest.class);

                    List<String> invalidParameters = ModelValidator.validate(seatRequest);
                    if (!invalidParameters.isEmpty()) {
                        throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, invalidParameters);
                    }

                    int id_seat = Integer.parseInt(seatRequest.getId_seat());
                    int id_show = Integer.parseInt(seatRequest.getId_show());


                    if (!seatRequest.getStatus().equals("reserved") && !seatRequest.getStatus().equals("broken")) {
                        throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
                    }

                    seatMapper.updateShowSeatStatus(id_show, id_seat, seatRequest.getStatus());
                    break;
                }
            }


        } catch (BadRequestException e) {
            operationResult = e;
            response.setStatus(400);
            outputStream.print(gsonWriter.toJson(operationResult));
        } catch (JsonIOException | JsonSyntaxException | NullPointerException | IllegalAccessException | InvocationTargetException e) {
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
