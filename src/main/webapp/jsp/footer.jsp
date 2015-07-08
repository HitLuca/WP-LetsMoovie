<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- footer da usare in tutte le pagine --%>
<footer>
    <div class="row">
        <div class="small-6 medium-3 columns">
            <a href="<c:url value="/contattaci"/>" class="button small expand radius">Contattaci</a>
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
</footer>

<%--JQUERY--%>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fastclick/1.0.6/fastclick.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/foundation/5.5.2/js/foundation.min.js"></script>

<%--JQUERY PLUGINS--%>
<script src="<c:url value="/lib/jquery.serialize-object.min.js"/>"></script>
<script src="<c:url value="/lib/jquery.storageapi.min.js"/>"></script>

<%--JAVASCRIPT PERSONALE--%>
<script src="<c:url value="/javascript/main.js"/>"></script>

<script>
    $(document).foundation();
</script>

<%-- contattaci apre pagina per inviare la mail, about us descrizione azienda, T&C: Tn'C fittizzi, sito crato da: uan nostra mini bio --%>