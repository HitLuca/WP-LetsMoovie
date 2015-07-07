<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%--FOUNDATION--%>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/foundation/5.5.2/css/normalize.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/foundation/5.5.2/css/foundation.min.css">

    <%--MODERNIZR--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/foundation/5.5.2/js/vendor/modernizr.js"></script>

    <%--ALERTIFY (NOTIFICHE E POPUP)--%>
    <script src="http://cdn.jsdelivr.net/alertifyjs/1.4.1/alertify.min.js"></script>
    <link rel="stylesheet" href="http://cdn.jsdelivr.net/alertifyjs/1.4.1/css/alertify.min.css"/>
    <link rel="stylesheet" href="http://cdn.jsdelivr.net/alertifyjs/1.4.1/css/themes/default.min.css"/>

    <%--CSS PERSONALE--%>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">

    <title>
        <c:out value="${param.title}"/>
    </title>
</head>