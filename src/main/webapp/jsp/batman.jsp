<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Batman"/>
</c:url>
<c:import url="${url}"/>

<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>

    <div id="content" class="row">
        <audio autoplay loop>
            <source src="/audio/Batman.mp3"/>
        </audio>
        <br>

        <div class="row">
            <div class="column small-12 small-centered medium-5">
                <img src="/img/batman.png" id="batman" class="animated bounceInUp"/>
            </div>
        </div>

    </div>

    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script>
    $("#batman").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $("#batman").removeClass("bounceInUp").addClass("infinite shake");
    });
</script>
</body>
</html>
