<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius large-uncollapse">
    <div class="medium-3 text-center columns">
        <a class="th" href="#">
            <img class="copertina" src="http://placehold.it/214x317">
        </a>

        <p></p>
    </div>
    <div class="medium-9 columns">
        <a href="#">
            <h3> Titolo Film</h3>
        </a>

        <p></p>

        <p align="justify">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit,
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
            nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
            reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
            pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
            culpa qui officia deserunt mollit anim id est laborum.
        </p>

        <c:if test="${requestScope.orario == true}">
            <button href="#" data-dropdown="drop1" aria-controls="drop1" aria-expanded="false" class="button dropdown">
                Orari
                proiezioni
            </button>
            <br>
            <ul id="drop1" data-dropdown-content class="f-dropdown" aria-hidden="true">
                <c:import url="/jsp/template/orarioFilm.jsp"/>
            </ul>
        </c:if>

    </div>
</div>