<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%--JQUERY--%>
<script src="<c:url value="/lib/js/jquery-2.1.4.min.js"/>"></script>
<%--FASTCLICK--%>
<script src="<c:url value="/lib/js/fastclick.min.js"/>"></script>
<%--FOUNDATION--%>
<script src="<c:url value="/lib/js/foundation.min.js"/>"></script>

<%--JQUERY PLUGINS--%>
<script src="<c:url value="/lib/js/jquery.serialize-object.min.js"/>"></script>
<script src="<c:url value="/lib/js/jquery.storageapi.min.js"/>"></script>

<%--NOTIFICHE JS--%>
<script src="<c:url value="/lib/js/alertify.min.js"/>"></script>


<%--JAVASCRIPT PERSONALE--%>
<script src="<c:url value="/javascript/main.js"/>"></script>
<script src="<c:url value="/javascript/header.js"/>"></script>

<script>
    $(document).foundation();
</script>