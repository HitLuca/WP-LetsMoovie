package servlets;

import beans.Error;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import types.ErrorType;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sourcePage = request.getParameter("sourcePage");

        RequestDispatcher sourceDispatcher = request.getRequestDispatcher(sourcePage);

        //RequestDispatcher backDispatcher = request.getRequestDispatcher(request.getHeader("referer"));

        if(username==null || password==null )
        {
           errorHandler(request,response,sourcePage);
        }

        String userCredential = userMapper.getUserCredential(username);

        if (userCredential == null)
        {
            errorHandler(request,response,sourcePage);
        } else if (!password.equals(userCredential))
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

        URI dbUri = null;

        try {
            Class.forName("org.postgresql.Driver");
            dbUri = new URI(System.getenv("DATABASE_URL"));
            System.out.println(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];

            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            DataSource dataSource = new UnpooledDataSource("org.postgresql.Driver",dbUrl,username,password);

            Environment environment = new Environment("development", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(UserMapper.class);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(configuration);
            session = factory.openSession();
            userMapper = session.getMapper(UserMapper.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
