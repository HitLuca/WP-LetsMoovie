<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Contattaci"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <h3>Hai bisogno di aiuto?</h3>

        <p align="justify">
            Se per qualsiasi motivo riscontrassi difficoltà nell'utilizzo del sito,
            del sistema di prenotazione dei posti, durante le operazioni di pagamento o,
            più semplicemente, avessi qualche domanda particolare da porci sentiti
            libero di contattarci scrivendoci una email oppure telefonando.
        </p>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>