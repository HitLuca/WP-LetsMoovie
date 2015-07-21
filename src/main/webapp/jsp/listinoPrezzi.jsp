<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Let's Moovie"/>
</c:url>
<c:import url="${url}"/>
<link rel="stylesheet" href="<c:url value="/css/listinoPrezzi.css"/>">
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <%--inserire qui la roba da mettere--%>
    <div class="row">
        <h2 class="small-12 column">
            LISTINO PREZZI
        </h2>
    </div>

    <div class="row">
        <h5 class="riga intestazione1 small-3 column">TIPO BIGLIETTO</h5>
        <h5 class="riga intestazione1 small-2 column">PREZZO</h5>
        <h5 class="riga intestazione1 small-7 column">DESCRIZIONE</h5>
    </div>
    <c:import url="/jsp/template/Prezzi.jsp"/>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/index.js"/>"></script>
</body>
</html>
