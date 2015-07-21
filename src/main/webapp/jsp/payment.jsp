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
        <h3>Hai selezionato questi posti</h3>

        <div class="panel callout radius" id="listaPostiScelti">
            <c:import url="/jsp/template/riepilogoBiglietto.jsp"/>
        </div>

        <h3>Procedi al pagamento</h3>

        <div class="row right">
            <h4>Credito disponibile: </h4><span id="credito"></span>
        </div>

        <div class="row" id="choice">
            <div id="advise" class="medium-12 text-center columns">
                <h5 align="justify">Per effettuare il pagamento verrà utilizzato dapprima il credito associato al tuo
                    account.
                    Se il tuo credito non è sufficiente, è necessario anche selezionare una carta fra quelle già
                    registrate
                    oppure inserirne una al momento.</h5>
            </div>

            <div class="medium-12 text-center columns">
                <a href="#" class="button radius" data-reveal-id="cardsList">Scegli una carta</a>

                <div id="cardsList" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
                     role="dialog">
                    <div class="row">
                        <h4>Carte associate al tuo account:</h4>

                        <div class="medium-12 columns">
                            <a href="#" class="button radius">Carta 1</a>
                        </div>
                    </div>

                    <a href="#" class="button alert radius">Aggiungi nuova carta</a>

                    <form class="panel callout hide radius">
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
        <div id="selectedCard" class="row">
            <h4>Carta selezionata</h4>

            <div class="medium-12 text-center panel callout radius columns">
                <span id="paymentCard"></span>
            </div>
        </div>

        <div class="row">
            <div class="medium-12 text-center columns">
                <a href="#" class="button radius">Conferma pagamento</a>
            </div>
        </div>
    </div>
</div>
<div class="push"></div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/payment.js"/>"></script>
</body>
</html>