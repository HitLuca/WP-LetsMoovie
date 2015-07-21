<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Listino prezzi"/>
</c:url>
<c:import url="${url}"/>
<link rel="stylesheet" href="<c:url value="/css/listinoPrezzi.css"/>">
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>

    <div id="content" class="row collapse">
        <div class="row">
            <h2 class="small-12 columns">
                LISTINO PREZZI
            </h2>
        </div>

        <div id="tableHeader" class="row collapse">
            <div class="medium-3 columns">
                <h5>TIPO BIGLIETTO</h5>
            </div>
            <div class="medium-2 columns">
                <h5>PREZZO</h5>
            </div>
            <div class="medium-7 columns">
                <h5>DESCRIZIONE</h5>
            </div>
        </div>

        <c:import url="/jsp/template/prezzi.jsp"/>
        <c:import url="/jsp/template/prezzi.jsp"/>
        <c:import url="/jsp/template/prezzi.jsp"/>
        <c:import url="/jsp/template/prezzi.jsp"/>
    </div>


    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>