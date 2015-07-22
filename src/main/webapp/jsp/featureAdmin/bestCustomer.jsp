<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Clienti migliori"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/bestCustomer.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <div class="small-12 columns">

            <h4>Inserisci una percentuale per visualizzare la lista dei clienti che
                hanno comprato più biglietti.</h4>

            <form action="" method="post">
                <div class="row">
                    <div class="small-10 medium-7 large-5 small-centered columns">
                        <div class="row collapse">
                            <div class="small-8 columns">
                                <input type="number" min="1" max="100">
                            </div>
                            <div class="small-1 columns">
                                <span class="postfix">%</span>
                            </div>
                            <div class="small-3 columns">
                                <a href="#" class="button postfix">Go</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div id="customerList" class="row">

                <h4>
                    I clienti che hanno comprato più biglietti
                </h4>

                <div class="medium-12 medium-centered columns">
                    <div class="row">
                        <div class="medium-3 columns">
                            <strong>Nome</strong>
                        </div>
                        <div class="medium-3 columns">
                            <strong>Cognome</strong>
                        </div>
                        <div class="medium-3 columns">
                            <strong>Username</strong>
                        </div>
                        <div class="medium-3 columns">
                            <strong>Totale</strong>
                        </div>
                    </div>
                    <p></p>
                    <c:import url="/jsp/template/customer.jsp"></c:import>
                    <c:import url="/jsp/template/customer.jsp"></c:import>
                    <c:import url="/jsp/template/customer.jsp"></c:import>
                    <c:import url="/jsp/template/customer.jsp"></c:import>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>

