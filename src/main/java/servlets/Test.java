package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.payments.PdfTicketCreator;
import json.tickets.TicketData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 22/07/15.
 */
@WebServlet(name = "Test", urlPatterns = "/api/test")
public class Test extends HttpServlet {
    private Gson gsonWriter;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OutputStream out = response.getOutputStream();
        List<TicketData> ticketDatas = new ArrayList<>();

        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));
        ticketDatas.add(new TicketData("titolo film", "2015-07-26", "18:48", "5", "8", "Etrunon", "21:54", "2015-07-26", "intero", 8f, "ultracodice", 2));


        response.setContentType("application/pdf");
        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"yourFile.pdf\"");

        String pathToWeb = getServletContext().getRealPath(File.separator);

        PdfTicketCreator pd = new PdfTicketCreator();

        try {
            response.getOutputStream().write(pd.createPdf(ticketDatas, getServletContext()).toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
    }
}
