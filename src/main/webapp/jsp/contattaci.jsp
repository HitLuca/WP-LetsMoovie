<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Contattaci"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>
    <div id="content" class="row">
        <%--Insert content here--%>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/footer.jsp"/>
</body>
</html>