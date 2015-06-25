<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="login"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="row">
    <div class="small-11 small-centered large-6 medium-8 columns">
        <div class="login-box">
            <form action="doLogin" method="POST">
                <input type="hidden" value="${param.sourcePage}" name="sourcePage">
                <label>
                    Username
                    <input type="text" name="username" placeholder="Username"/>
                </label>
                <label>
                    Password
                    <input type="password" name="password" placeholder="Password"/>
                </label>

                <div class="row">
                    <div class="medium-6 columns">
                        <input type="submit" class="button expand" value="Log In"/>
                    </div>
                    <div class="medium-6 columns">
                        <input type="submit" class="button expand" value="Forgot your password?"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
