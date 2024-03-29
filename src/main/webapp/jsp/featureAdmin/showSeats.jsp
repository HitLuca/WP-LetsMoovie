<%--Posti venduti--%>
<%--seleziona il giorno, poi seleziona lo spettacolo (film, sala,ora in un' unica stringa), poi mostro la mappa--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Mostra posti venduti"/>
    <c:param name="css" value="/lib/css/pikaday.css"></c:param>
</c:url>
<c:import url="${url}"/>
<body>
<link rel="stylesheet" href="<c:url value="/css/showSeats.css"/>">
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="medium-4 columns">
                    <h4>Seleziona il giorno</h4>
                </div>
                <div class="medium-4 columns end">
                    <form id="sceltaData" action="/api/adminData/getShows">
                        <input name="date" type="text" id="datepicker" placeholder="Clicca per scegliere la data">
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="medium-4 columns">
                    <h4>Seleziona lo spettacolo</h4>
                </div>
                <div class="medium-4 columns end">
                    <form id="sceltaSpettacolo" action="/api/admin/getShowSeats">
                        <select id="Film" name="id_show" data-bind="showDataList">
                            <option></option>
                            <option value="" data-bind="film_Name"></option>
                        </select>
                    </form>
                </div>
            </div>
            <%--mostro la mappa 3d con i pulsanti reset, blocca--%>
            <div class="row">
                <div class="small-12 columns text-center">
                    <h4>Posti prenotati:</h4>

                    <div id="roomMap">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<%--THREE JS--%>
<script src="<c:url value="/lib/js/threejs/three.r71.min.js"/>"></script>
<script src="<c:url value="/lib/js/threejs/OrbitControls.min.js"/>"></script>
<script src="<c:url value="/lib/js/threejs/OBJLoader.min.js"/>"></script>
<script src="<c:url value="/javascript/3DCinemaView.js"/>"></script>

<script src="/lib/js/pikaday.js"></script>
<script src="/lib/js/pikaday.jquery.js"></script>
<script src="/javascript/featureAdmin/showSeats.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.css" rel="stylesheet"/>
</body>
</html>
