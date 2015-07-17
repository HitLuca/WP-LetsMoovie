<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Scelta posto"/>
</c:url>
<c:import url="${url}"/>

<body>
<link rel="stylesheet" href="<c:url value="/css/choiceSeats.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">

        <div data-magellan-expedition="fixed">
            <dl class="sub-nav">
                <dd data-magellan-arrival="sceltaPosti"><a href="#sceltaPosti">Scegli i posti</a></dd>
                <dd data-magellan-arrival="riepilogo"><a href="#riepilogo">Riepilogo</a></dd>
            </dl>
        </div>

        <div class="row">
            <div class="row collapse" data-magellan-destination="sceltaPosti">
                <a name="sceltaPosti"></a>

                <div class="medium-6 columns text-center">
                    Blocca visuale
                    <div class="switch">
                        <input id="exampleCheckboxSwitch" type="checkbox">
                        <label for="exampleCheckboxSwitch"></label>
                    </div>
                </div>
                <div class="medium-6 columns text-center">
                    <a href="" class="button radius">Resetta visuale</a>
                </div>
            </div>
        </div>

        <div class="row collapse" data-magellan-destination="sceltaPosti">
            <a name="sceltaPosti"></a>

            <div id="roomMap" class="small-12 columns text-center">
                <%--Qui va inserita la mappa della sala--%>
                <img src="http://placehold.it/600x500">
            </div>
        </div>
        <div class="row collapse">
            <h5>Seleziona per ciascun posto la tipologia del biglietto:</h5>

            <div class="small-12 columns" data-magellan-destination="riepilogo">
                <a name="riepilogo"></a>
                <c:import url="/jsp/template/postoItemList.jsp"></c:import>
            </div>
        </div>
        <div class="row collapse">
            <div class="small-6 columns text-center">
                <h4>Totale:<span id="total"></span></h4>
            </div>
            <div class="small-6 columns text-center">
                <a href="/payment/" class="button radius">Procedi al pagamento</a>
            </div>
        </div>

    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/seats.js"/>"></script>
</body>
</html>
