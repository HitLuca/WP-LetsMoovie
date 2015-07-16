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
    <div id="content" class="collapse row">
        <div class="medium-3 text-center columns">
            <img data-bind="poster" src="http://placehold.it/214x317">
        </div>
        <div class="medium-9 columns">
            <div class="panel radius">
                <div class="collapse row">
                    <div data-bind="film_title" class="medium-7 columns">
                        <h3>Titolo</h3>
                    </div>
                    <div data-bind="year" class="medium-5 columns">
                        <p>Anno:</p>
                    </div>
                </div>
                <div class="row">
                    <strong data-bind="director">Regista: </strong>
                </div>
                <div class="hide">
                    <strong data-bind="id_film"></strong>
                </div>
                <div class="collapse row">
                    <div class="medium-12 columns">
                        <strong data-bind="plot">Trama: </strong>

                        <p align="justify">
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
                <strong>Guarda il trailer:</strong>

                <div id="trailerContainer" class="collapse text-center row">
                    <div class="video-container">
                        <iframe data-bind="trailer" width="420" height="315"
                                src="https://www.youtube.com/embed/6bh4mvJ5jUg">
                        </iframe>
                    </div>
                </div>
                <div class="collapse row">
                    <div class="medium-7 columns">
                        <strong>Rating:</strong>
                        <span data-bind="rating"></span>
                    </div>
                    <div class="medium-5 columns">
                        <strong>Metascore:</strong>
                        <span data-bind="metascore"></span>
                    </div>
                </div>
                <div class="collapse row">
                    <div class="medium-7 columns">
                        <strong>Durata:</strong>
                        <span data-bind="vm"></span>
                    </div>
                    <div class="medium-5 columns">
                        <strong>Vietato ai minori:</strong>
                        <span data-bind="duration"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>