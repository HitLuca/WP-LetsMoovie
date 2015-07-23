package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import database.datatypes.other.Ticket;
import database.mappers.NotDecidedMapper;
import json.OperationResult;
import json.tickets.TicketResponse;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
@WebServlet(name = "Tickets", urlPatterns = "/api/tickets")
public class Tickets extends HttpServlet {

    private Gson gsonWriter;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        NotDecidedMapper notDecidedMapper = sessionSql.getMapper(NotDecidedMapper.class);
        response.setContentType("application/json");

        OperationResult opRes;

        List<Ticket> ticketses = notDecidedMapper.getAllTickets();

        opRes = new TicketResponse(ticketses);

        PrintWriter outputStream = response.getWriter();
        outputStream.print(gsonWriter.toJson(opRes));
        sessionSql.close();
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.disableHtmlEscaping().create();
    }
}
