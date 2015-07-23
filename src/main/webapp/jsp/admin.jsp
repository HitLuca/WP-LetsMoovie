<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Area Utente"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <h3>Funzionalità amministratore</h3>

        <ul class="accordion" data-accordion>
            <li class="accordion-navigation">
                <a href="#panel1a">Gestione sale</a>

                <div id="panel1a" class="content active">
                    <div class="panel radius">

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/showSeats.jsp" class="button expand radius">Posti venduti</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di vedere la lista dei posti venduti
                                    per ciascuna programmazione.</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/reservedSeats.jsp" class="button expand radius">Posti più
                                    prenotati</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di vedere la mappa dei posti più prenotati.</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/deleteReservation.jsp" class="button expand radius">
                                    Cancella prenotazione</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di cancellare una prenotazione
                                    per una proiezione non ancora iniziata.</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/updateStatus.jsp" class="button expand radius">
                                    Modifica posto</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di modificare lo stato di un posto.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="accordion-navigation">
                <a href="#panel2a">Statistiche</a>

                <div id="panel2a" class="content">
                    <div class="panel radius">

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/filmCashing.jsp" class="button expand radius">
                                    Incassi per film</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di vedere la lista degli incassi
                                    dei film.</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="medium-4 columns">
                                <a href="/jsp/featureAdmin/bestCustomer.jsp" class="button expand radius">
                                    Clienti top</a>
                            </div>
                            <div class="medium-8 columns">
                                <p>Consente di vedere la lista dei clienti
                                    che hanno comprato più biglietti.</p>
                            </div>
                        </div>


                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>

<script src="<c:url value="/javascript/user.js"/>"></script>

</body>
</html>