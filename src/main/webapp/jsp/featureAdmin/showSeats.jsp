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
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="medium-4 columns">
                    <h4>Selezionare il giorno</h4>
                </div>
                <div class="medium-4 columns end">
                    <input type="text" id="datepicker">
                </div>
            </div>
            <div class="row">
                <div class="medium-4 columns">
                    <h4>Selezionare lo spettacolo</h4>
                </div>
                <div class="medium-4 columns end">
                    <select>
                        <option><span id="Film"></span></option>
                    </select>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r71/three.min.js"></script>
<script src="http://threejs.org/examples/js/controls/OrbitControls.js"></script>
<script src="http://threejs.org/examples/js/loaders/OBJLoader.js"></script>
<script src="<c:url value="/javascript/3DCinemaView.js"/>"></script>
<script src="/lib/js/pikaday.js"></script>
<script src="/lib/js/pikaday.jquery.js"></script>
<script src="/javascript/showSeats.js"></script>
</body>
</html>
