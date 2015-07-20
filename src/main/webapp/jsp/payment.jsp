<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Pagamento"/>
</c:url>
<c:import url="${url}"/>

<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <h3>Hai selezionato questi posti:</h3>

        <div class="panel callout radius" id="listaPostiScelti">
            <c:import url="/jsp/template/riepilogoBiglietto.jsp"/>
        </div>
        <div class="row">
            <div class="medium-12 text-center columns">
                <a href="#" class="button radius" data-reveal-id="myModal">Modalità di pagamento</a>

                <div id="myModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
                     role="dialog">
                    <h3>Seleziona una carta con cui effettuare il pagamento:</h3>

                    <p">
                    <li>Carta 1</li>
                    <li>Carta 1</li>
                    <li>Carta 1</li>
                    <li>Carta 1</li>
                    </p>
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