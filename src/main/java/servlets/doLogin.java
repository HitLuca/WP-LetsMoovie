package servlets;

import beans.Error;
import dbConnection.DatabaseConnection;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import types.ErrorType;
import types.UserLoginCredential;
import types.mappers.UserMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by etrunon on 24/06/15.
 */
@WebServlet(name = "doLogin", urlPatterns = "/doLogin")
public class doLogin extends HttpServlet {
    private SqlSession session;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sourcePage = request.getParameter("sourcePage");

        RequestDispatcher sourceDispatcher = request.getRequestDispatcher(sourcePage);

        if(username==null || password==null )
        {
            errorHandler(request, response, sourcePage);
        }

        UserLoginCredential userCredential = userMapper.getUserCredential(username);

        if (userCredential == null)
        {
            errorHandler(request,response,sourcePage);
        } else if (!password.equals(userCredential.getPassword()))
        {
            errorHandler(request,response,sourcePage);
        }
        else
        {
            HttpSession session = request.getSession();
            session.setAttribute("role","USER");
            session.setAttribute("username",username);
            sourceDispatcher.forward(request, response);
        }
    }

    private void errorHandler(HttpServletRequest request,HttpServletResponse response,String sourcePage) throws ServletException, IOException {

        RequestDispatcher errorDispatcher = request.getRequestDispatcher("jsp/login.jsp");
        Error error = new Error();
        error.setErrorType(ErrorType.INVALID_LOGIN);
        request.setAttribute("error", error);
        request.setAttribute("sourcePage",sourcePage);
        errorDispatcher.forward(request,response);
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);

    }
}
