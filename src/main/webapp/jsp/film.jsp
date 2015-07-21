<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Scheda Film"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/film.css"/>">
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
                <div id="anno" class="row">
                    <div class="medium-12 columns">
                        <div class="row">
                            <div class="medium-2 columns">
                                <strong>Anno</strong>
                            </div>
                            <div class="medium-10 columns">
                                <span data-bind="year"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="regista" class="row">
                    <div class="medium-12 columns">
                        <div class="row">
                            <div class="medium-2 columns">
                                <strong>Regista</strong>
                            </div>
                            <div class="medium-10 columns">
                                <span data-bind="director"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row collapse">
                    <div class="medium-12 columns text-center">
                        <div class="label radius info">Trama</div>

                        <p id="tramaFilm" align="justify" data-bind="plot">
                        </p>
                    </div>
                </div>

                <div class="row">
                    <div id="tabella" class="medium-12 columns text-center">
                        <strong class="label radius info">Orari proiezioni</strong>
                        <ul id="shows" class="accordion" data-accordion role="tablist">
                            <li class="accordion-navigation">
                                <a href="#dayPanel" id="dayLabel">Lunedì</a>

                                <div id="dayPanel" class="content">
                                    <ul class="small-block-grid-3 medium-block-grid-4" data-bind="orari">
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
                        <strong class="label info radius">Trailer</strong>

                        <div id="trailerContainer" class="text-center row">
                            <div class="video-container">
                                <iframe data-bind="trailer" src="about:blank">
                                </iframe>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row wow fadeInUp">
                    <div class="medium-6 columns">
                        <div class="row collapse">
                            <div class="medium-6 label info radius columns">
                                <strong>Rating</strong>
                            </div>
                            <div class="medium-6 text-center columns">
                                <span data-bind="rating"></span>
                            </div>
                        </div>
                    </div>
                    <div class="medium-6 columns">
                        <div class="row collapse">
                            <div class="medium-6 label info radius columns">
                                <strong>Metascore</strong>
                            </div>
                            <div class="medium-6 text-center columns">
                                <span data-bind="metascore"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row wow fadeInUp">
                    <div class="medium-6 columns">
                        <div class="row collapse">
                            <div class="medium-6 label info radius columns">
                                <strong>Durata</strong>
                            </div>
                            <div class="medium-6 text-center columns">
                                <span data-bind="duration"> minuti</span>
                            </div>
                        </div>
                    </div>
                    <div class="medium-6 columns">
                        <div class="row collapse">
                            <div class="medium-6 label info radius columns">
                                <strong>VM</strong>
                            </div>
                            <div class="medium-6 text-center columns">
                                <span data-bind="vm"></span>
                            </div>
                        </div>
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