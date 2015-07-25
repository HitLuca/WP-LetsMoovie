<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Clienti migliori"/>
    <c:param name="css" value="/css/bestCustomer.css"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <div class="small-12 columns">

            <h4>Inserisci una percentuale per visualizzare la lista dei clienti che
                hanno comprato più biglietti</h4>

            <form action="/api/admin/getRankedUsers/" method="post" id="percentageSelect">
                <div class="row">
                    <div class="small-10 medium-7 large-5 small-centered columns">
                        <div class="row collapse">
                            <div class="small-8 columns">
                                <input name="top" type="number" min="1" max="100" value="10">
                            </div>
                            <div class="small-1 columns">
                                <span class="postfix">%</span>
                            </div>
                            <div class="small-3 columns">
                                <button class="button postfix">Vai</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div id="customerList" class="row hide">

                <h4>
                    Ecco i clienti che hanno comprato più biglietti:
                </h4>

                <div class="medium-12 medium-centered text-center columns">
                    <table id="customers" class="display" role="grid" width="100%">
                        <thead>
                        <tr>
                            <th>Posizione</th>
                            <th>Nome</th>
                            <th>Cognome</th>
                            <th>Username</th>
                            <th>Spesa</th>
                        </tr>
                        </thead>

                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>

<%--DATATABLES--%>
<link rel="stylesheet" href="<c:url value="/lib/css/dataTables.foundation.min.css"/>"/>
<script src="<c:url value="/lib/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/lib/js/dataTables.foundation.min.js"/>"></script>

<%--JS della pagina--%>
<script src="<c:url value="/javascript/featureAdmin/bestCustomer.js"/>"></script>
</body>
</html>

