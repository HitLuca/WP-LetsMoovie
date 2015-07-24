<%--posti più venduti--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Mostra posti riservati"/>
    <c:param name="css" value="/css/reservedSeats.css"></c:param>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona una sala dalla lista per visualizzarne i posti più venduti</h4>

                    <form id="selezionePercentuale" action="/api/admin/getRankedReservations">
                        <div class="row collapse">
                            <div class="medium-6 small-centered columns">
                                <label>Sale cinema
                                    <select id="listaSale" name="room_number" data-bind="roomList">
                                        <option id="room_number"></option>
                                    </select>
                                </label>
                            </div>
                        </div>

                        <div class="row collapse">
                            <div class="medium-6 small-centered columns">
                                <div class="row collapse">
                                    <div class="small-8 columns">
                                        <input type="number" name="top" min="1" max="100" value="50">
                                    </div>
                                    <div class="small-1 columns">
                                        <span class="postfix">%</span>
                                    </div>
                                    <div class="small-3 columns">
                                        <button class="button ladda-button postfix" data-style="zoom-out">
                                            <span class="ladda-label">
                                                Vai
                                            </span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <h4>Mappa dei posti più venduti nella sala selezionata</h4>
                </div>
            </div>
            <div class="row collapse">
                <div class="small-12 columns text-center">
                    <div id="roomMap">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r71/three.min.js"></script>
<script src="http://threejs.org/examples/js/controls/OrbitControls.js"></script>
<script src="http://threejs.org/examples/js/loaders/OBJLoader.js"></script>
<script src="<c:url value="/javascript/3DCinemaView.js"/>"></script>
<script src="/javascript/featureAdmin/reservedSeats.js"></script>
</body>
</html