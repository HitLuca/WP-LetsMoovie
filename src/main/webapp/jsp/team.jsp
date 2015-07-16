<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Team"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <h3>I componenti del team di sviluppo</h3>

        <div id="first" class="row">
            <div class="medium-6 centered columns">

            </div>
            <div class="medium-6 centered columns">

            </div>
        </div>
        <div id="second" class="row">
            <div class="medium-6 centered columns">

            </div>
            <div class="medium-6 centered columns">

            </div>
        </div>
        <div id="third" class="row">
            <div class="medium-6 centered columns">

            </div>
            <div class="medium-6 centered columns">

            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>
