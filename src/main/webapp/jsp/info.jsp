<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Info"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/info.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="text-center row">
        <div class="row">
            <h3>Informazioni generali</h3>
        </div>
        <div class="medium-4 columns">
            <div class="panel callout radious">
                <p>
                    <strong>Orari:</strong>
                    Lunedì - Domenica 15:00 - 02:00</p>

                <p>
                    <strong>Telefono:</strong>
                    +39 0461 382375 </p>

                <p>
                    <strong>Indirizzo:</strong>
                    Via Prato Bovino 2, Castelnovo di Sotto <a href="/batman">(BAT)</a>
                </p>

                <p>
                    <strong>Email:</strong>
                    info@lets-moovie.tk
                </p>
            </div>
        </div>
        <div class="embed-container medium-8 columns">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2483.999980930391!2d10.567629199999999!3d44.812736699999995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x478022a10b7ce1b7%3A0xfb752316bd7086bb!2sVia+Prato+Bovino%2C+2%2C+42024+Castelnovo+di+Sotto+RE!5e1!3m2!1sit!2sit!4v1437118738937"
                    frameborder="0" style="border:0" allowfullscreen></iframe>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>