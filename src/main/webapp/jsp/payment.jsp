<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Pagamento"/>
</c:url>
<c:import url="${url}"/>

<body>
<link rel="stylesheet" href="<c:url value="/css/payment.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <h3>Hai selezionato questi posti:</h3>

        <div class="panel callout radius" id="listaPostiScelti">
            <c:import url="/jsp/template/riepilogoBiglietto.jsp"></c:import>
        </div>

        <h3>Procedi al pagamento:</h3>

        <div class="row" id="choice">
            <div class="medium-12 text-center columns">

                <a href="#" class="button radius" data-reveal-id="cardsList">Scegli una carta</a>

                <div id="cardsList" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
                     role="dialog">
                    <div class="row">
                        <div class="medium-6 columns">
                            <a href="#" class="button radius">Carta 1</a>
                        </div>
                        <div class="medium-6 columns">
                            <a href="#" class="button radius">Carta 2</a>
                        </div>
                    </div>

                    <a href="#" class="button alert radius">Aggiungi nuova carta</a>

                    <form class="panel callout radius">
                        <div class="row">
                            <div class="medium-6 small-centered columns">
                                <label>Nome titolare
                                    <input type="text" placeholder="Mario"/>
                                </label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="medium-6 small-centered columns">
                                <label>Cognome titolare
                                    <input type="text" placeholder="Rossi"/>
                                </label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="medium-6 small-centered columns">
                                <label>Numero carta
                                    <input type="text" placeholder="XXXX-XXXX-XXXX-XXXX"/>
                                </label>
                            </div>
                        </div>
                        <div class="row">
                            <a href="#" class="button radius">Conferma</a>
                        </div>
                    </form>
                    <a class="close-reveal-modal" aria-label="Close">&#215;</a>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>