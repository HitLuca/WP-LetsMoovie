<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%--JQUERY--%>
<script src="<c:url value="/lib/js/jquery-2.1.4.min.js"/>"></script>
<%--FASTCLICK--%>
<script src="<c:url value="/lib/js/fastclick.min.js"/>"></script>
<%--FOUNDATION--%>
<script src="<c:url value="/lib/js/foundation.min.js"/>"></script>

<%--JQUERY PLUGINS--%>
<script src="<c:url value="/lib/js/jquery.serializejson.min.js"/>"></script>
<script src="<c:url value="/lib/js/jquery.storageapi.min.js"/>"></script>

<%--NOTIFICHE JS--%>
<script src="<c:url value="/lib/js/alertify.min.js"/>"></script>

<%--LADDA BUTTONS--%>
<script src="<c:url value="/lib/js/spin.min.js"/>"></script>
<script src="<c:url value="/lib/js/ladda.min.js"/>"></script>

<%--TRANSPARENCY (MOSTRARE JSON NELLA PAGINA--%>
<script src="<c:url value="/lib/js/transparency.min.js"/>"></script>

<%--MOMENT.JS GESTIONE DATE--%>
<script src="<c:url value="/lib/js/moment-with-locales.min.js"/>"></script>

<%--WOW.JS--%>
<script src="<c:url value="/lib/js/wow.min.js"/>"></script>

<%--NUMERAL JS--%>
<script src="<c:url value="/lib/js/numeral.min.js"/>"></script>
<script src="<c:url value="/lib/js/it.min.js"/>"></script>

<%--JAVASCRIPT PERSONALE--%>
<script src="<c:url value="/javascript/main.js"/>"></script>
<script src="<c:url value="/javascript/header.js"/>"></script>

<script>
    $(document).foundation();
    var wow = new WOW();
    wow.init();
</script>