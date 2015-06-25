<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Let's Moovie"/>
</c:url>
<c:import url="${url}"/>
<body>
<c:import url="header.jsp"/>
<h2>Hello World!</h2>
<c:import url="footer.jsp"/>
</body>
</html>
