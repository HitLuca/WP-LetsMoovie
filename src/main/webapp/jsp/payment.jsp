<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Pagamento"/>
    <c:param name="css" value="/css/payment.css"/>
</c:url>
<c:import url="${url}"/>

<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <div class="columns small-12">

            <h3>Riepilogo posti selezionati</h3>

            <div class="row collapse" id="listaPostiScelti">
                <div class="columns small-12">
                    <div class="" data-bind="seatList">
                        <c:import url="/jsp/template/riepilogoBiglietto.jsp"/>
                    </div>
                </div>
                <div class="columns small-12">
                    <div class="row panel small-collapse">
                        <div class="columns small-5">
                            <h3>Totale</h3>
                        </div>
                        <div class="columns small-7">
                            <h3 class="right" data-bind="totalPrice"></h3>
                        </div>
                    </div>
                    <div class="row" id="userCredit">
                        <div class="columns small-7">
                            <h4>Credito disponibile: </h4>
                        </div>
                        <div class="columns small-4">
                            <h3 class="right " id="credit"></h3>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row" id="choice">
                <div id="advise" class="medium-12 text-center columns">
                    <h5 align="justify">Per effettuare il pagamento verrà utilizzato dapprima il credito associato al
                        tuo
                        account.
                        Se il tuo credito non è sufficiente, è necessario anche selezionare una carta fra quelle già
                        registrate
                        oppure inserirne una al momento.</h5>
                </div>

                <div class="medium-12 text-center columns">
                    <a href="#" class="button radius info" data-reveal-id="cardsList">Scegli una carta</a>

                    <div id="cardsList" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
                         role="dialog">
                        <div class="row">
                            <h4>Carte associate al tuo account:</h4>

                            <div class="medium-12 columns" data-bind="cards">
                                <button class="creditCard secondary" data-bind="number"></button>
                            </div>
                        </div>

                        <a href="#" class="button alert radius" id="addCard">Aggiungi nuova carta</a>

                        <form class="panel callout hide radius" id="addForm" action="<c:url value="/api/debitCards/"/>">
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
                                        <input type="text" name="number" placeholder="XXXX-XXXX-XXXX-XXXX"/>
                                    </label>
                                </div>
                            </div>
                            <div class="row">
                                <button class="radius">Conferma</button>
                            </div>
                        </form>
                        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
                    </div>
                </div>

            </div>
            <div id="selectedCard" class="row hide panel callout radius">
                <h4>Carta selezionata</h4>

                <div class="small-6 text-center columns">
                    <span id="paymentCard"></span>
                </div>
                <div class="small-6 columns">
                    <button class="tiny secondary" id="rimuoviCarta">Non usare questa carta</button>
                </div>
            </div>

            <div class="row">
                <div class="medium-12 text-center columns">
                    <%--TODO: LADDA BUTTON--%>
                        <button id="confermaPagamento" class="radius success ladda-button" data-style="zoom-out">
                        <span class="ladda-label">
                        Conferma pagamento
                        </span>
                        </button>
                </div>
            </div>
        </div>

        <div id="paymentOk" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
             role="dialog">
            <h2 id="modalTitle">Pagamento effettuato!</h2>

            <p>Il pagamento è andato a buon fine, ti abbiamo appena inviato via email i biglietti
                della prenotazione.<br>
                Ricordati di stamparli per poter accedere alla sala al momento della proiezione.
            </p>
            <a class="close-reveal-modal" aria-label="Close">&#215;</a>
        </div>

    </div>
</div>
<div class="push"></div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/payment.js"/>"></script>
</body>
</html>