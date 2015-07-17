<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Pagamento"/>
</c:url>
<c:import url="${url}"/>

<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <%--Content goes here--%>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>