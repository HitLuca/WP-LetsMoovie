<%--
  Created by IntelliJ IDEA.
  User: alessandro
  Date: 25/06/15
  Time: 12.04
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="top-bar" data-topbar role="navigation">
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
            <li><a href="#">Login</a></li>
            <li><a href="#">Registrati</a></li>
        </ul>
    </section>
</nav>
