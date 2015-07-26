<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- footer da usare in tutte le pagine --%>
<div class="row footer">
    <div class="small-6 medium-3 columns">
        <a href="#" class="button small expand radius" data-reveal-id="contactUs">Contattaci</a>

        <div id="contactUs" class="reveal-modal medium" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
             role="dialog">
            <h3>Hai bisogno di aiuto?</h3>

            <div class="row">
                <div class="medium-9 small-centered columns">
                    <p align="justify">
                        Se per qualsiasi motivo riscontrassi difficoltà nell'utilizzo del sito,
                        del sistema di prenotazione dei posti, durante le operazioni di pagamento o,
                        più semplicemente, avessi qualche domanda particolare, sentiti
                        libero di contattarci con una email oppure una telefonata.<br><br>
                        Tutte le informazioni utili puoi trovarle su
                        <a href="/info">questa</a>
                        pagina.
                    </p>
                </div>
            </div>
            <a class="close-reveal-modal" aria-label="Close">&#215;</a>
        </div>
    </div>
    <div class="small-6 medium-3 columns">
        <a href="<c:url value="/privacy"/>" class="button small expand radius">Privacy</a>
    </div>
    <div class="small-6 medium-3 columns">
        <a href="<c:url value="/info"/>" class="button small expand radius">Chi siamo</a>
    </div>
    <div class="small-6 medium-3 columns">
        <a href="<c:url value="/team"/>" class="button small expand radius">Il team</a>
    </div>
</div>

<c:import url="/jsp/layout/scripts.jsp"/>

<%-- contattaci apre pagina per inviare la mail, about us descrizione azienda, T&C: Tn'C fittizzi, sito crato da: una nostra mini bio --%>