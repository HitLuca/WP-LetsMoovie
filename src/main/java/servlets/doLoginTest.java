package servlets;

import beans.*;
import beans.Error;
import dbConnection.DatabaseConnection;
import org.apache.ibatis.session.SqlSession;
import types.*;
import types.mappers.UserMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by marco on 02/07/15.
 */
@WebServlet(name = "doLoginTest", urlPatterns = "/doLoginTest")
public class doLoginTest extends HttpServlet {
    private SqlSession session;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if(username==null || password==null )
        {
            errorHandler(request, response);
        }

        UserLoginCredential userCredential = userMapper.getUserCredential(username);

        if (userCredential == null)
        {
            errorHandler(request,response);
        } else if (!password.equals(userCredential.getPassword()))
        {
            errorHandler(request,response);
        }
        else
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("role","USER");
            session.setAttribute("username", username);
            String sessionId = session.getId();
            response.setContentType("application/json");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print("{\"status\":\"OK\"}");

        }
    }

    private void errorHandler(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print("{\"sessionId\":\"ERROR\"}");
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);

    }
}
