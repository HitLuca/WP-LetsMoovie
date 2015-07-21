package servlets;

import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import org.apache.ibatis.session.SqlSession;
import utilities.BadReqExeceptionThrower;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by etrunon on 21/07/15.
 */
@WebServlet(name = "Tickets")
public class Tickets extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlSession sessionSql;
        sessionSql = DatabaseConnection.getFactory().openSession();
        UserMapper userMapper = sessionSql.getMapper(UserMapper.class);
        response.setContentType("application/json");

        OperationResult opRes;

    }
}
