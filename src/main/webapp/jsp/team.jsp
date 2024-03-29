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

        <div id="rowContainer" class="callout radious panel wow fadeIn">

            <div id="first" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Alberigo.jpg">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Luca Alberigo</strong>

                        <p>Front End Developer<br>
                            Global Tactics Designer<br>
                            Chief Usability Officer
                        </p>

                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Bacchiega.png">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Alessandro Bacchiega</strong>

                        <p>Front End Developer<br>
                            Chief Creative Director<br>
                            Dynamic Brand Strategist
                        </p>
                    </div>
                </div>
            </div>
            <div id="second" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Bosotti.jpeg">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Luca Bosotti</strong>

                        <p>Back End Developer<br>
                            Future Research Developer<br>
                            Central Configuration Architect
                        </p>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Federici.png">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Marco Federici</strong>

                        <p>Back End Developer<br>
                            Central Interactions Manager<br>
                            3D Graphic Designer</p>
                    </div>
                </div>
            </div>
            <div id="third" class="row">
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Mion.png">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Carlo Mion</strong>

                        <p>Front End Developer<br>
                            Legacy Functionality Representative<br>
                            Central Quality Orchestrator
                        </p>
                    </div>
                </div>
                <div class="medium-6 centered columns">
                    <div class="medium-4 columns wow swing">
                        <img class="img-circle" src="/img/team_photo/Simonetto.png">
                    </div>
                    <div class="medium-8 columns wow flipInY">
                        <strong>Luca Simonetto</strong>

                        <p>Back End Developer<br>
                            Chief Data Representative<br>
                            Direct Integration Producer
                        </p>
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
