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
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="row">
            <div class="large-12 columns">
                <h4>Inserisci il codice di una prenotazione per procedere alla sua cancellazione.</h4>

                <form action="" method="post">
                    <div class="row">
                        <div class="small-10 medium-7 large-5 small-centered columns">
                            <div class="row collapse">
                                <div class="small-8 columns">
                                    <input type="text">
                                </div>
                                <div class="small-4 columns">
                                    <a href="#" class="button postfix">Go</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="row">
            <div id="listaTickets" class="large-12 columns">
                <h4>Posti relativi alla prenotazione selezionata</h4>
                <c:import url="/jsp/template/riepilogoBiglietto.jsp"></c:import>
            </div>
        </div>

        <div class="row">
            <div class="large-12 columns">
                <a href="#" class="button radius">Conferma cancellazione</a>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/deleteReservation.js"/>"></script>
</body>
</html>
