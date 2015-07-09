package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import database.DatabaseConnection;
import database.mappers.UserMapper;
import json.OperationResult;
import json.set_new_password.SetNewPasswordRequest;
import org.apache.ibatis.session.SqlSession;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;
import types.exceptions.BadRequestExceptionWithParameters;
import utilities.InputValidator.ModelValidator;
import utilities.mail.MailCleanerThread;
import utilities.mail.MailCleanerThreadFactory;
import utilities.mail.PasswordRecoveryCodeCheck;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by marco on 09/07/15.
 */
@WebServlet(name = "SetNewPassword", urlPatterns = "/api/setNewPassword")
public class SetNewPassword extends HttpServlet {
    Gson gsonWriter;
    Gson gsonReader;
    UserMapper userMapper;
    PasswordRecoveryCodeCheck passwordRecoveryCodeCheck;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OperationResult setPasswordOperation;
        try
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("username") != null)
            {
                //TODO aggiungere cambio password
            }
            else {
                SetNewPasswordRequest setNewPasswordRequest = gsonReader.fromJson(request.getReader(), SetNewPasswordRequest.class);
                List<String> invalidParameters = ModelValidator.validate(setNewPasswordRequest);
                if (!invalidParameters.isEmpty()) {
                    throw new BadRequestExceptionWithParameters(ErrorCode.EMPTY_WRONG_FIELD, "password");
                }

                String username = passwordRecoveryCodeCheck.verify(setNewPasswordRequest.getCode());
                if(username == null)
                {
                    throw new BadRequestException(ErrorCode.WRONG_CONFIRMATION_CODE);
                }

                userMapper.updatePassword(username,setNewPasswordRequest.getPassword());
            }
            setPasswordOperation = null;

        }catch (BadRequestExceptionWithParameters e) {
            setPasswordOperation = e;
            response.setStatus(400);

        } catch (BadRequestException e) {
            setPasswordOperation = e;
            response.setStatus(400);
        }catch (IllegalAccessException | InvocationTargetException | JsonIOException | JsonSyntaxException | NullPointerException e) {
            setPasswordOperation = new BadRequestException();
            response.setStatus(400);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(gsonWriter.toJson(setPasswordOperation));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void init() throws ServletException {
        SqlSession session = DatabaseConnection.getFactory().openSession(true);
        userMapper = session.getMapper(UserMapper.class);

        MailCleanerThread mailCleanerThread = MailCleanerThreadFactory.getMailCleanerThread();
        passwordRecoveryCodeCheck = new PasswordRecoveryCodeCheck(mailCleanerThread);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWriter = gsonBuilder.create();
        gsonReader = new Gson();
    }

}
