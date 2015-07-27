<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Modifica spettacoli"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <h3>Modifica la programmazione del cinema</h3>
        </div>
        <div class="row text-center">
            <div class="column small-6">
                <input type="text" name="minuti" id="minuti" value="2" placeholder="Inserisci i minuti">
                <button id="edit" class="radius warning ladda-button" data-style="zoom-out">
                    <label class="ladda-label">
                        Comprimi la durata degli spettacoli
                    </label>
                </button>
            </div>
            <div class="column small-6">
                <button id="reset" class="radius success ladda-button" data-style="zoom-out">
                    <label class="ladda-label">
                        Reimposta la durata degli spettacoli originali
                    </label>
                </button>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/layout/footer.jsp"/>
<script src="<c:url value="/javascript/featureAdmin/editShowDuration.js"/>"></script>
</body>
</html>