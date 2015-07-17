<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius large-uncollapse film wow zoomIn" data-wow-duration="0.30s">
    <div class="medium-3 text-center columns hide">
        <a class="th filmLink" href="#">
            <img id="poster" src="about:blank">
        </a>

        <p></p>
    </div>
    <div class="medium-9 columns">
        <a href="#" class="filmLink">
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