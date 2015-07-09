<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Reimposta Password"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>
    <div class="row content">
        <div class="small-11 small-centered large-6 medium-8 columns">
            <h3>Reimposta la nuova password:</h3>

            <div class="row">
                <div class="medium-12 columns">
                    <p>
                        Completando i campi sottostanti con una nuova password avrai la possibilità
                        di impostarne una nuova.<br>
                    </p>
                </div>
            </div>
            <form action="/api/setNeWPassword" id="setNewPassoword" data-abide="ajax">
                <div class="row">
                    <div class="medium-12 columns">
                        <label>Password:
                            <input type="password" id="password" name="password" placeholder="Password" required/>
                        </label>
                        <small class="error">Nuova password richesta!
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
                        <button class="button radius ">Conferma</button>
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