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
        <form action="doLogin" id="loginForm" data-abide="ajax">
            <%--<input type="hidden" value="${param.sourcePage}" name="sourcePage">--%>
            <div>
                <label>
                    Username
                    <input type="text" name="username" required pattern="alpha"
                           placeholder="Inserisci il tuo username"/>
                </label>
                <small class="error">Username richiesto!</small>

            </div>
            <div>
                <label>
                    Password
                    <input type="password" name="password" required pattern="alpha"
                           placeholder="Inserisci la tua password"/>
                </label>
                <small class="error">Password richiesta!</small>
            </div>

            <div class="row">
                <div class="medium-6 columns">
                    <button class="button expand">Login</button>
                </div>
                <div class="medium-6 columns">
                    <button class="button expand" href="">Password dimenticata?</button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:import url="/jsp/footer.jsp"/>

<script>

    function successNotifier(data) {
        alertify.success("Benvenuto, " + data.username + "!");
        window.location = "/";
    }
    function errorNotifier(data) {
        alertify.error("Username o password errata!");
        $("input[type='password']").val('');
    }

    $(document).ready(function () {
        PostForm("loginForm", successNotifier, errorNotifier)
    });
</script>

</body>
</html>
