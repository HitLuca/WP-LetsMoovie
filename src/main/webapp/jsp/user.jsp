<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Area Utente"/>
    <c:param name="css" value="/css/user.css"></c:param>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <h3>Informazioni utente</h3>

        <ul class="accordion" data-accordion>
            <li class="accordion-navigation">
                <a href="#panel1a">Generalità</a>

                <div id="panel1a" class="content active">
                    <div class="panel radius">
                        <h3>Generalità utente</h3>

                        <p></p>

                        <div class="row">
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Nome:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="name"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Cognome:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="surname"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Data di nascita:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="birthday"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Indirizzo email:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="email"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Telefono:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="phone"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="medium-12 columns">
                                <div class="row">
                                    <div class="medium-4 columns">
                                        <strong>Username:</strong>
                                    </div>
                                    <div class="medium-8 small-only-text-center columns">
                                        <span id="username"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="accordion-navigation">
                <a href="#panel2a">Storico acquisti</a>

                <div id="panel2a" class="content">
                    <div class="row">
                        <div class="medium-12 columns">
                            <table id="storico" class="display" role="grid" width="100%">
                                <thead>
                                <tr>
                                    <th>ID Prenotazione</th>
                                    <th>Film</th>
                                    <th>Data</th>
                                    <th>Totale pagamento</th>
                                    <%--<tr>--%>
                                    <%--<th>1</th>--%>
                                    <%--<th>2</th>--%>
                                    <%--</tr>--%>
                                </tr>

                                </thead>

                            </table>
                        </div>
                    </div>
                </div>
            </li>
            <li class="accordion-navigation" id="carte">
                <a href="#panel3a">Portafoglio</a>

                <div id="panel3a" class="content">
                    <div class="panel radius">

                        <div class="row" id="carteRegistrate">
                            <div class="medium-12 columns">
                                <h3>Carte registrate</h3>

                                <div class="row" id="contenitoreCarte">
                                    <div class="medium-12 columns">
                                        <div class="row">
                                            <div class="medium-4 columns">
                                                <strong>Carta:</strong>
                                            </div>
                                            <div class="medium-8 small-only-text-center columns">
                                                <span data-bind="number"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="creditoResiduo">
                            <div class="medium-12 columns">
                                <h3>Credito</h3>

                                <div class="row" id="contenitoreCredito">
                                    <div class="medium-12 columns">
                                        <div class="row">
                                            <div class="medium-4 columns">
                                                <strong>Residuo:</strong>
                                            </div>
                                            <div class="medium-8 small-only-text-center columns">
                                                <span id="credit" data-bind="number"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/lib/js/numeral.min.js"/>"></script>
<script src="<c:url value="/lib/js/it.min.js"/>"></script>
<%--DATATABLES--%>
<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<script src="//cdn.datatables.net/plug-ins/1.10.7/integration/foundation/dataTables.foundation.js"></script>
<script src="<c:url value="/javascript/user.js"/>"></script>

</body>
</html>
