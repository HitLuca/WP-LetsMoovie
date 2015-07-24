package servlets.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.OperationResult;
import types.enums.ErrorCode;
import types.exceptions.BadRequestException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mion00 on 24/07/15.
 */
@WebFilter(filterName = "AdminAuthentication", urlPatterns = {"/api/admin/*"})
public class AdminAuthentication implements Filter {
    Gson writer;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object username = session.getAttribute("username");
            Integer role = (Integer) session.getAttribute("role");
            if (username != null) {
                if (role > 1) {
//                    È un admin
                    chain.doFilter(req, resp);
                }
            }
        } else {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            OperationResult status = new BadRequestException(ErrorCode.NOT_AUTHORIZED);
            writer.toJson(status);
            response.getWriter().print(writer.toJson(status));
        }
    }

    public void init(FilterConfig config) throws ServletException {
        writer = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
    }

}
