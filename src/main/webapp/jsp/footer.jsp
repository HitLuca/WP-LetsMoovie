<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- footer da usare in tutte le pagine --%>
<footer>
    <div class="row">
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/contattaci"/>" class="button small expand radius">Contattaci</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/privacy"/>" class="button small expand radius">Privacy</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/info"/>" class="button small expand radius">Chi siamo</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/team"/>" class="button small expand radius">Il team</a>
        </div>
    </div>
</footer>

<%-- contattaci apre pagina per inviare la mail, about us descrizione azienda, T&C: Tn'C fittizzi, sito crato da: uan nostra mini bio --%>