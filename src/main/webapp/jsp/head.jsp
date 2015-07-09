<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%--FOUNDATION--%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/lib/css/normalize.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/lib/css/foundation.min.css"/>">

    <%--MODERNIZR--%>
    <script src="<c:url value="/lib/js/modernizr.js"/>"></script>

    <%--ALERTIFY (NOTIFICHE E POPUP)--%>
    <link rel="stylesheet" href="<c:url value="/lib/css/alertify.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/lib/css/default.min.css"/>"/>

    <%--CSS PERSONALE--%>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">

    <title>
        <c:out value="${param.title}"/>
    </title>
</head>