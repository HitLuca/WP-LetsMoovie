package servlets;

import beans.Error;
import com.google.gson.Gson;
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
import types.json.LoginStatus;
import types.mappers.UserMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
    Gson gson;
    private SqlSession session;
    private UserMapper userMapper;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if(username==null || password==null )
        {
            errorHandler(request, response);
        }
        else {
            UserLoginCredential userCredential = userMapper.getUserCredential(username);
            if (userCredential == null) {
                errorHandler(request, response);
            } else if (!password.equals(userCredential.getPassword())) {
                errorHandler(request, response);
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("role", "USER");
                session.setAttribute("username", username);
                String sessionId = session.getId();
                response.setContentType("application/json");
                LoginStatus loginStatus = new LoginStatus();
                loginStatus.setSuccess(true);
                loginStatus.setUsername(username);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.print(gson.toJson(loginStatus));
            }
        }
    }

    private void errorHandler(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setSuccess(false);
        loginStatus.setUsername(null);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gson.toJson(loginStatus));
    }

    @Override
    public void init() throws ServletException {

        session = DatabaseConnection.getFactory().openSession();
        userMapper = session.getMapper(UserMapper.class);
        gson = new Gson();

    }
}
