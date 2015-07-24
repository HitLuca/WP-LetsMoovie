<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Modifica stato posto"/>
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

                    <form id="selezioneSale" action="/api/admin/updateSeatStatus">
                        <div class="row collapse">
                            <div class="medium-6 small-centered columns">
                                <label>Sale cinema
                                    <select id="listaSale" name="room_number" data-bind="roomList">
                                        <option id="room_number"></option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona uno o più posti</h4>

                    <div class="row collapse">
                        <div class="small-12 columns text-center">
                            <div id="roomMap">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona il nuovo stato</h4>

                    <div class="row collapse">
                        <div class="medium-6 small-centered columns">
                            <label>Stato
                                <select>
                                    <option>Agibile</option>
                                    <option>Inagibile</option>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row collapse">
                <div class="medium-3 small-centered columns">
                    <button class="button radius ladda-button" data-style="zoom-out">Conferma cambiamento</button>
                </div>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="/javascript/featureAdmin/updateStatus.js"></script>
</body>
</html>
