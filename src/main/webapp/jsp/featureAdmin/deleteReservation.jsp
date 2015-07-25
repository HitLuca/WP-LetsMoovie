<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Cancella prenotazione"/>
    <c:param name="css" value="/css/payment.css"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/deleteReservation.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="large-12 columns">
                    <h4>Inserisci il codice di una prenotazione per procedere alla sua cancellazione.</h4>

                    <form action="/api/admin/deleteReservation" id="codicePrenotazione">
                        <div class="row">
                            <div class="small-10 medium-7 large-5 small-centered columns">
                                <div class="row collapse">
                                    <div class="small-8 columns">
                                        <input type="text" name="code">
                                    </div>
                                    <div class="small-4 columns">
                                        <button class="button ladda-button postfix" data-style="zoom-out">
                                            <span class="ladda-label">
                                                Vai
                                            </span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <form action="/api/admin/deleteReservation" id="cancellaPrenotazione">
                <div class="row">
                    <div id="listaTickets" class="large-12 columns">
                        <h4>Posti relativi alla prenotazione selezionata</h4>

                        <div class="row collapse" id="postiPrenotazione" data-bind="seatList">
                            <c:import url="/jsp/template/riepilogoBiglietto.jsp"></c:import>
                        </div>
                    </div>
                </div>
                <div id="confirm" class="row">
                    <div class="medium-3 small-centered columns">
                        <button class="button ladda-button radius" data-style="zoom-out">
                            <span class="ladda-label">Cancella prenotazione</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>

<script src="/javascript/featureAdmin/deleteReservation.js"></script>
</body>
</html>