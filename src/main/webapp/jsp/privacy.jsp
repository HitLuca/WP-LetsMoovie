<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Privacy"/>
</c:url>
<c:import url="${url}"/>

<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>

    <div id="content" class="row">
        <h3><strong>Privacy</strong></h3>

        <p align="justify">
            Ai sensi e per gli effetti dell'art. 13 del decreto legislativo 196/2003 "Codice in materia di protezione
            dei dati personali", La informiamo che i dati personali da Lei trasmessi saranno trattati nella banca dati
            cartacea
            ed informatica del Titolare del trattamento Let's Moovie Srl. Al fine di fornire le informazioni da Lei
            richieste sui nostri servizi, offerte, tariffe, ecc. compilando il modulo atto a contattarci Lei
            acconsentirà all'invio
            dei dati che successivamente verranno utilizzati per rispondere alle Sue richieste. Il conferimento dei dati
            è necessario
            per poter inoltarLe le informazioni che ci richiede. Non verranno diffusi o comunicati a terzi per nessuna
            ragione,
            potremmo invece utilizzarli, previo consenso, per mandarLe delle periodiche newsletter. La informiamo infine
            che in
            relazione al trattamento potrà esercitare i diritti di cui all'articolo 7 della legge citata ed in
            particolare
            chiedere in qualsiasi momento al titolare, la cancellazione, l'aggiornamento, la modifica dei dati in nostri
            possesso.
        </p>

        <h3><strong>Cookies</strong></h3>

        <p align="justify">Su questo sito vengono utilizzati solamente cookie tecnici necessari al funzionamento stesso
            del sito.</p>
    </div>

    <div class="push"></div>
</div>
<c:import url="/jsp/footer.jsp"/>
</body>
</html>
