package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.imageio.plugins.jpeg.JPEG;
import database.DatabaseConnection;
import database.datatypes.other.Ticket;
import database.mappers.NotDecidedMapper;
import json.OperationResult;
import json.payments.PdfTicketCreator;
import json.payments.QrCodeCreator;
import json.reservation.request.ReservationRequest;
import json.tickets.TicketResponse;
import org.apache.ibatis.session.SqlSession;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
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


/*
        QrCodeCreator codeCreator = new QrCodeCreator();
        response.setContentType("image/png");

        out.write(codeCreator.doSOmething("Un sacco di stronzate").toByteArray());
*/

        response.setContentType("application/pdf");
        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"yourFile.pdf\"");

        String pathToWeb = getServletContext().getRealPath(File.separator);

        PdfTicketCreator pd = new PdfTicketCreator();

        try {
            response.getOutputStream().write(pd.createPdf(null, pathToWeb).toByteArray());
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
