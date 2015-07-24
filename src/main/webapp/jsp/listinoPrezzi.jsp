<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Listino prezzi"/>
</c:url>
<c:import url="${url}"/>
<link rel="stylesheet" href="<c:url value="/css/listinoPrezzi.css"/>">
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>

    <div id="content" class="row collapse">
        <div class="row">
            <h2 class="small-12 columns">
                LISTINO PREZZI
            </h2>
        </div>
        <div class="row collapse" id="allTickets" data-bind="tickets">
            <div id="singleTicket" class="row panel callout radius wow zoomIn">

                <div class="medium-4 columns">
                    <p>

                    <div class="label radius">tipo biglietto</div>
                    <span data-bind="ticket_type"></span></p>
                </div>
                <div class="medium-2 columns">
                    <p>

                    <div class="label radius">prezzo</div>
                    <span data-bind="price"></span></p>
                </div>
                <div class="medium-6 columns">
                    <p>

                    <div class="label radius">dettagli</div>
                    <span data-bind="description"></span></p>
                </div>

            </div>
        </div>
    </div>

    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>

<script src="<c:url value="/lib/js/numeral.min.js"/>"></script>
<script src="<c:url value="/lib/js/it.min.js"/>"></script>
<script src="<c:url value="/javascript/listinoPrezzi.js"/>"></script>
</body>
</html>