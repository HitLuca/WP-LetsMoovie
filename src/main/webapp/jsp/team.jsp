<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Team"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="/css/team.css">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <h3>I componenti del team di sviluppo</h3>

        <div id="rowContainer" class="callout radious panel">

            <div id="first" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Alberigo.jpg">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Luca Alberigo</strong>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Bacchiega.png">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Alessandro Bacchiega</strong>
                    </div>
                </div>
            </div>
            <div id="second" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Bosotti.jpeg">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Luca Bosotti</strong>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Federici.png">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Marco Federici</strong>
                    </div>
                </div>
            </div>
            <div id="third" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Mion.png">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Carlo Mion</strong>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" height="100" width="100" src="/img/team_photo/Simonetto.png">
                    </div>
                    <div class="medium-8 columns">
                        <strong>Luca Simonetto</strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>
