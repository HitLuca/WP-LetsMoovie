<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Scheda Film"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="/css/film.css">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="medium-3 text-center columns">

            <img id="copertina" class="hide" data-wow-duration="0.25s" data-bind="poster" src="about:blank">

        </div>
        <div class="medium-9 columns wow fadeIn">
            <div class="panel radius">
                <div class="row">
                    <div class="medium-12 columns">
                        <h2 data-bind="film_title">Titolo</h2>
                    </div>
                </div>
                <div class="row">
                    <div class="medium-12 columns">
                        <strong>Anno: <span data-bind="year"></span></strong>
                    </div>
                </div>
                <div class="row">
                    <div class="medium-12 columns">
                        <strong>Regista: <span data-bind="director"></span></strong>
                    </div>
                </div>
                <div class="row">
                    <div class="medium-12 columns">
                        <strong>Trama:</strong>

                        <p align="justify" data-bind="plot">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                            nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                            reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                            pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                            culpa qui officia deserunt mollit anim id est laborum.
                        </p>
                    </div>
                </div>

                <div class="row">
                    <div class="medium-12 columns">
                        <strong>Orari proiezioni: </strong>
                        <ul id="proiezioni" class="accordion" data-accordion role="tablist">
                            <li class="accordion-navigation">
                                <a href="#panel1d" role="tab" id="panel1d-heading" aria-controls="panel1d">Lunedì</a>

                                <div id="panel1d" class="content wow fadeIn" role="tabpanel"
                                     aria-labelledby="panel1d-heading">
                                    <ul class="small-block-grid-3 medium-block-grid-4">
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                        <li><a href="#" class="small radius button expand"
                                               data-bind="show_time">19:30</a></li>
                                    </ul>
                                </div>
                            </li>

                        </ul>
                    </div>
                </div>

                <div class="row collapse wow zoomIn">
                    <div class="medium-12 text-center columns">
                        <strong>Guarda il trailer:</strong>

                        <div id="trailerContainer" class="text-center row">
                            <div class="video-container">
                                <iframe data-bind="trailer" src="about:blank">
                                </iframe>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row wow fadeInUp">
                    <div class="medium-7 columns">
                        <strong>Rating:</strong>
                        <span data-bind="rating"></span>
                    </div>
                    <div class="medium-5 columns">
                        <strong>Metascore:</strong>
                        <span data-bind="metascore"></span>
                    </div>
                </div>
                <div class="row wow fadeInUp">
                    <div class="medium-7 columns">
                        <strong>Durata:</strong>
                        <span data-bind="duration"></span> minuti
                    </div>
                    <div class="medium-5 columns">
                        <strong>Vietato ai minori:</strong>
                        <span data-bind="vm"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/film.js"/>"></script>
</body>
</html>