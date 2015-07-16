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
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Luca Alberigo</h5>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Alessandro Bacchiega</h5>
                    </div>
                </div>
            </div>
            <div id="second" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Luca Bosotti</h5>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Marco Federici</h5>
                    </div>
                </div>
            </div>
            <div id="third" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Carlo Mion</h5>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns">
                        <img class="img-circle" src="http://www.placehold.it/150x150">
                    </div>
                    <div class="medium-8 columns">
                        <h5>Luca Simonetto</h5>
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
