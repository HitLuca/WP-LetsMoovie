<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Login"/>
</c:url>
<c:import url="${url}"/>
<body>
<c:import url="/jsp/header.jsp"/>
<div class="row content">
    <div class="small-11 small-centered large-6 medium-8 columns">
        <div class="login-box">
            <form action="doLoginTest" id="loginForm">
                <input type="hidden" value="${param.sourcePage}" name="sourcePage">
                <label>
                    Username
                    <input type="text" name="username" placeholder="Inserisci il tuo username"/>
                </label>
                <label>
                    Password
                    <input type="password" name="password" placeholder="Inserisci la tua password"/>
                </label>

                <div class="row">
                    <div class="medium-6 columns">
                        <input type="submit" class="button expand" value="Log In"/>
                    </div>
                    <div class="medium-6 columns">
                        <input type="submit" class="button expand" value="Password dimenticata?"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/jsp/footer.jsp"/>
</body>

<%--TODO:IMPLEMENTARE LIBRERIA NOTIFICHE--%>
<script src="//cdn.jsdelivr.net/alertifyjs/1.4.1/alertify.min.js"></script>
<link rel="stylesheet" href="//cdn.jsdelivr.net/alertifyjs/1.4.1/css/alertify.min.css"/>
<link rel="stylesheet" href="//cdn.jsdelivr.net/alertifyjs/1.4.1/css/themes/default.min.css"/>

<script>
    $("#loginForm").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var $form = $(this),
                username = $form.find("input[name='username']").val(),
                password = $form.find("input[name='password']").val(),
                url = $form.attr("action");

        // Send the data using post
        var posting = $.post(url,
                {
                    username: username,
                    password: password
                }
        );

        // Put the results in a div
        posting.done(function (data) {
            var success = data.success;
            if (success)
                alertify.notify("Benvenuto, " + data.username + "!", "success", 2);
            else
                alertify.notify("Username o password errata!", "error", 2);

        });
    });
</script>
</html>
