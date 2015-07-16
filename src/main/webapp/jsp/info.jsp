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
    <div id="content" class="row">
        <%--Insert content here--%>
            <%--maps--%>
            <div class="medium-4 columns">
                <p><h4>
                Orari:</h4> Lun-Dom 14:00-24:00 </p>
                <p>
                <h4>Telefono:</h4> +39 0461 3823758 </p>

                <p>
                <h4>Indirizzo:</h4> Via Prato Bovino, Castelnovo di Sotto RE</p>
            </div>
            <div class="google-maps medium-8 columns">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d8004.2459016107805!2d10.56920970825469!3d44.823025775551095!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x478022a3d1c127cb%3A0x136bc78d4f5bdcde!2sVia+Prato+Bovino%2C+42024+Castelnovo+di+Sotto+RE!5e0!3m2!1sit!2sit!4v1437055975498"
                        width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
            </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>