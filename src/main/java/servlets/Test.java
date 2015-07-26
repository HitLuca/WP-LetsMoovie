package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.ShowsCompression;
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
    private final ShowsCompression showsCompression = new ShowsCompression();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showsCompression.decompressShows();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            showsCompression.compressShows(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {


    }
}
