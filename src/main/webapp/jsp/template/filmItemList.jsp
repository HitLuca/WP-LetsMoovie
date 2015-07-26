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

        <div class="row">
            <div class="medium-8 columns">
                <a href="#" class="filmLink small-only-text-center">
                    <h3 id="film_title"></h3>
                </a>
            </div>
            <div class="medium-4 small-only-text-center medium-text-right columns">
                <a href="#" class="filmLink radius small button">Info film</a>
            </div>
        </div>

        <p align="justify" id="plot" class="hidden-for-small">
        </p>

        <c:if test="${requestScope.orario == true}">
            <c:import url="/jsp/template/orarioFilm.jsp"/>
        </c:if>

    </div>
</div>