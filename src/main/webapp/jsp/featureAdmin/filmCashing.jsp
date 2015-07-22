<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Mostra incassi"/>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/filmCashing.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row collapse">
        <div class="small-12 columns">
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona un film dalla lista per visualizzarne l'incasso</h4>

                    <div class="row">
                        <div class="medium-6 medium-centered columns">
                            <label>Lista film
                                <select>
                                    <option><span id="nomeFilm"></span></option>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div id="showCashing" class="row">
                <div class="large-12 columns">
                    <h4>Incasso per il film selezionato:</h4>

                    <div class="row>">
                        <div class="medium-6 medium-centered panel callout radius columns">
                            <h2 align="center"><span id="incasso">1000000€</span></h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
</body>
</html>

