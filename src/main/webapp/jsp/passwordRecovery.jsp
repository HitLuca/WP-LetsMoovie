<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Reimposta Password"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div class="row" id="content">
        <div class="small-11 small-centered large-6 medium-8 columns">
            <h3>Reimposta la nuova password:</h3>

            <div class="row">
                <div class="medium-12 columns">
                    <p>
                        Completando i campi sottostanti con una nuova password avrai la possibilità
                        di impostarne una nuova.
                    </p>
                </div>
            </div>
            <form action="<c:url value="/api/setNewPassword"/>" id="setNewPassoword" data-abide="ajax">
                <input type="hidden" name="code" value="${param.verificationCode}">

                <div class="row">
                    <div class="medium-12 columns">
                        <label>Password:
                            <input type="password" id="password" name="password" placeholder="Password" required/>
                        </label>
                        <small class="error">La password deve contenere almeno 4 caratteri di cui uno non alfabetico
                        </small>
                    </div>
                    <div class="medium-12 columns">
                        <label>Conferma password:
                            <input type="password" name="passwordconfirm" placeholder="Password" required
                                   data-equalto="password"/>
                        </label>
                        <small class="error">Le password devono corrispondere!</small>
                    </div>
                </div>

                <div class="row">
                    <div class="medium-12 text-center columns">
                        <button class="button radius ladda-button" data-style="zoom-out">
                            <span class="ladda-label">
                                Conferma
                            </span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>

<script src="<c:url value="/javascript/passwordRecovery.js"/>"></script>
</body>
</html>