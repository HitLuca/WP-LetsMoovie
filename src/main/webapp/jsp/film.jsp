<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Scheda Film"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class=" collapse row">
        <div class="medium-3 text-center columns">
            <img id="poster" src="http://placehold.it/214x317">
        </div>
        <div class="medium-9 columns">
            <div class="panel radius">
                <div class="collapse row">
                    <div id="film_title" class="medium-7 columns">
                        <h3>Titolo</h3>
                    </div>
                    <div id="year" class="medium-5 columns">
                        <p>Anno:</p>
                    </div>
                </div>
                <div class="row">
                    <strong id="director">Regista: </strong>
                </div>
                <div class="hide">
                    <strong id="id_film"></strong>
                </div>
                <div class="collapse row">
                    <div class="medium-12 columns">
                        <strong id="plot">Trama: </strong>

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
                <div class="collapse row">
                    <div class="medium-7 columns">
                        <strong id="trailer">Trailer:</strong>
                    </div>
                    <div class="medium-5 columns">
                        <strong id="duration">Durata:</strong>
                    </div>
                </div>
                <div class="collapse row">
                    <div class="medium-7 rating columns">
                        <strong id="rating">Rating:</strong>
                    </div>
                    <div class="medium-5 columns">
                        <strong id="metascore">Metascore:</strong>
                    </div>
                </div>
                <div class="collapse row">
                    <strong id="vm">Vietato ai minori:</strong>
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