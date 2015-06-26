<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Let's Moovie"/>
</c:url>
<c:import url="${url}"/>
<body>
<c:import url="header.jsp"/>

<div class="content">
    <h2>Hello World!</h2>
</div>

<c:import url="footer.jsp"/>
</body>
</html>
