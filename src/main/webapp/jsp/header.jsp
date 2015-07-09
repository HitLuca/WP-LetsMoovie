<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="top-bar" data-topbar role="navigation" data-options="is_hover: false">
    <ul class="title-area">
        <li class="name">
            <h1><a href="<c:url value="/"/>"><img id="logo" src="http://placehold.it/100x50"></a></h1>
        </li>

        <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
        <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
    </ul>

    <section class="top-bar-section">
        <!-- Right Nav Section -->
        <ul class="right">
            <li><a href="#">Listino prezzi</a></li>
            <li><a href="#">Info cinema</a></li>
            <c:choose>
                <c:when test="${sessionScope.username != null}">
                    <li class="has-dropdown"><a href="#">${sessionScope.username}</a>
                        <ul class="dropdown">
                            <li><a href="<c:url value="/user/${sessionScope.username}"/>">Area Utente</a></li>
                            <li><a href="<c:url value="/doLogout"/>" id="logout">Logout</a></li>
                        </ul>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="<c:url value="/login"/>">Login</a></li>
                    <li><a href="<c:url value="/registration"/>">Registrati</a></li>
                </c:otherwise>
            </c:choose>

        </ul>
    </section>
</nav>
