<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- footer da usare in tutte le pagine --%>
<footer>
    <div class="row">
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/jsp/contattaci.jsp"/>" class="button expand radius">Contattaci</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/jsp/privacy.jsp"/>" class="button expand radius">Privacy</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/jsp/info.jsp"/>" class="button expand radius">Chi siamo</a>
        </div>
        <div id="fooBut" class="small-6 medium-3 columns">
            <a href="<c:url value="/jsp/team.jsp"/>" class="button expand radius">Il team</a>
        </div>
    </div>
</footer>

<%-- contattaci apre pagina per inviare la mail, about us descrizione azienda, T&C: Tn'C fittizzi, sito crato da: uan nostra mini bio --%>