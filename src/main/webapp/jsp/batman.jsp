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
        <div class="row">
            <div class="column small-12 small-centered medium-5" id="mobile">
            </div>
        </div>
        <div class="row">
            <div class="column small-12 small-centered medium-5">
                <audio id="audio" src="/audio/Batman.mp3" autoplay loop></audio>
                <img src="/img/batman.png" id="batman" class="" style="opacity: 0.0;"/>
            </div>
        </div>

    </div>

    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script type='text/javascript' src="//wurfl.io/wurfl.js"></script>
<script>
    console.log(WURFL);
    if (WURFL.is_mobile) {
        var content = document.getElementById("mobile");
        var clicky = document.createElement("a");
        clicky.setAttribute("onClick", "Play()");
        var h = document.createElement("h1");
        var span = document.createElement('span');
        span.setAttribute("style", "text-decoration: underline;");
        var text = document.createTextNode("NANANANANA");
        span.appendChild(text);
        h.appendChild(span);
        clicky.appendChild(h);
        content.appendChild(clicky);
    }
    else {
        var batman = document.getElementById("batman");
        batman.removeAttribute("style")
        $('#batman').addClass("animated bounceInUp");
        setTimeout(continueExecution, 1200);
    }
    function Play() {
        var audio = document.getElementById("audio");
        audio.play();
        var batman = document.getElementById("batman");
        batman.removeAttribute("style")
        $('#batman').addClass("animated bounceInUp");
        setTimeout(continueExecution, 1200);
    }
    function continueExecution() {
        $('#batman').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', $('#batman').removeClass("bounceInUp").addClass("infinite shake"));
    }
</script>
</body>
</html>
