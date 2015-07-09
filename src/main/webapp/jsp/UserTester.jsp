<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mion00
  Date: 09/07/15
  Time: 10.53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:import url="head.jsp"/>
<body>
<c:import url="header.jsp"/>
<form id="tester" action="/api/getUser" data-abide="ajax">
    <label>
        <input type="text" name="username"/>
    </label>
    <button>Submit</button>
</form>
<div id="response"></div>

<c:import url="footer.jsp"/>
<script>
    function Succes(data) {
        $("#response").append(JSON.stringify(data) + "<br>");
    }

    function error(data) {
        $("#response").append(JSON.stringify(data.responseJSON) + "<br>");
    }
    PostForm("tester", Succes, error);
</script>
</body>
</html>
