<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Login"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>
    <div class="row content">
        <div class="small-11 small-centered large-6 medium-8 columns">
            <h3>Accedi al tuo account:</h3>

            <form action="doLogin" id="loginForm" data-abide="ajax">
                <%--<input type="hidden" value="${param.sourcePage}" name="sourcePage">--%>
                <div>
                    <label>
                        Username
                        <input type="text" name="username" required
                               placeholder="Inserisci il tuo username"/>
                    </label>
                    <small class="error">Username richiesto!</small>

                </div>
                <div>
                    <label>
                        Password
                        <input type="password" name="password" required
                               placeholder="Inserisci la tua password"/>
                        <%--TODO:CHECK PASSWORD RICHIESTA--%>
                    </label>
                    <small class="error">Password richiesta!</small>
                </div>

                <div class="row">
                    <div class="medium-6 columns">
                        <button class="button radius expand">Login</button>
                    </div>
                    <div class="medium-6 columns">
                        <button class="button radius expand">Password dimenticata?</button>
                        <%--TODO: inserire link a password dimenticata--%>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/footer.jsp"/>

<script src="<c:url value="/javascript/login.js"/>"></script>

</body>
</html>
