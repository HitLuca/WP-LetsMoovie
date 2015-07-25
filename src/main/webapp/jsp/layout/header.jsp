<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="top-bar" data-topbar role="navigation" data-options="is_hover: false">
    <ul class="title-area">
        <li class="name">
            <h1><a href="<c:url value="/"/>">
                <img id="logo" src="<c:url value="/img/logoTotale2.png"/>"></a>
            </h1>
        </li>

        <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
        <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
    </ul>

    <section class="top-bar-section">
        <!-- Right Nav Section -->
        <ul class="right">
            <li><a href="<c:url value="/"/>">Home</a></li>
            <li><a href="<c:url value="/listinoPrezzi"/>">Listino prezzi</a></li>
            <li><a href="<c:url value="/info"/>">Info cinema</a></li>
            <c:choose>
                <c:when test="${sessionScope.username != null}">
                    <li class="has-dropdown"><a href="#">${sessionScope.username}</a>
                        <ul class="dropdown">
                            <li><a href="<c:url value="/user/${sessionScope.username}"/>">Area Utente</a></li>
                            <c:choose>
                                <c:when test="${ sessionScope.role == 1 || sessionScope.role == 2}">
                                    <li><a href="<c:url value="/admin"/>">Area Amministrazione</a></li>
                                </c:when>
                            </c:choose>
                            <li><a href="<c:url value="/api/logout"/>" id="logout">Logout</a></li>
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

<div id="oldBrowser" class="hide">
    <%--TODO: MIGLIORARE MESSAGGIO DI AVVISO--%>
    <p class="title">Browser non supportato!</p>

    <p class="content">
        Siamo spiacenti, ma il tuo browser non supporta alcune tecnologie WEB moderne usate dal nostro sito.<br>
        Non siamo quindi in grado di garantirti una degna esperienza di navigazione.
        Se desideri utilizzare il nostro sito, per favore aggiorna il tuo browser!<br>
        Puoi utilizzare questo ottimo browser: <a href="http://www.netscape.ca/ns/browsers/7/download/">http://www.netscape.ca/ns/browsers/7/download/</a>
    </p>
</div>