<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius large-uncollapse film animated zoomIn wow" data-wow-duration="0.25s" data-equalizer>
    <div class="medium-3 text-center columns" data-equalizer-watch>
        <a class="th" href="#">
            <img id="poster" src="http://placehold.it/214x317">
        </a>

        <p></p>
    </div>
    <div class="medium-9 columns" data-equalizer-watch>
        <a href="#">
            <h3 id="film_title"></h3>
        </a>

        <p></p>

        <p align="justify" id="plot">
        </p>

        <c:if test="${requestScope.orario == true}">
            <c:import url="/jsp/template/orarioFilm.jsp"/>
        </c:if>

    </div>
</div>