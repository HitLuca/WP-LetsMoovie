<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Risorsa non disponibile!"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/index.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="column small-centered text-center">
            <h1>La risorsa richiesta non è disponibile</h1>
            <a href="<c:url value="/"/>" class="button radius">Torna alla Home Page</a>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/index.js"/>"></script>
</body>
</html>