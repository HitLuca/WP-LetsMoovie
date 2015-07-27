package servlets;

import database.ShowsCompression;
import database.datatypes.show.Show;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by etrunon on 22/07/15.
 */
@WebServlet(name = "Test", urlPatterns = "/api/test")
public class Test extends HttpServlet {
    private ShowsCompression showsCompression;
    private Map<Show, String> mappedShows;

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
        showsCompression = new ShowsCompression();
    }
}
