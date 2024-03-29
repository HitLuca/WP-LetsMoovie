<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Modifica stato posto"/>
    <c:param name="css" value="/css/updateStatus.css"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona una sala dalla lista</h4>

                    <div class="row collapse">
                        <div class="medium-6 small-centered columns">
                            <form id="salaCinema" action="/api/admin/getRoomSeats">
                            <label>Sale cinema
                                <select id="listaSale" name="room_number" data-bind="roomList">
                                    <option data-bind="room_number"></option>
                                </select>
                            </label>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona uno o più posti dalla mappa</h4>

                    <div class="row collapse">
                        <div class="small-12 columns text-center">
                            <div id="roomMap">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <form id="conferma" action="<c:url value="/api/admin/updateRoomSeatStatus"/>">
                <input type="hidden" id="roomId" name="room_number">

                <div id="posti">
                    <div id="posto" data-coordinate="">
                        <input type="hidden" id="s_row" name="seats[][row]" disabled>
                        <input type="hidden" id="s_column" name="seats[][column]" disabled>
                    </div>
                </div>
                <div class="row collapse">
                    <h4>Clicca su un posto per modificarne lo stato</h4>

                    <div class="medium-3 small-centered columns">
                        <button class="button radius ladda-button" data-style="zoom-out">Conferma cambiamento</button>
                    </div>
                </div>
            </form>
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
<script src="<c:url value="/javascript/featureAdmin/updateStatus.js"/>"></script>
</body>
</html>
