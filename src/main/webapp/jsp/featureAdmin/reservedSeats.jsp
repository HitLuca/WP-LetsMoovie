<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/layout/head.jsp">
    <c:param name="title" value="Mostra posti riservati"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/layout/header.jsp"/>
    <div id="content" class="row">
        <div class="small-12 columns">
            <div class="row">
                <div class="large-12 columns">
                    <h4>Seleziona una sala dalla lista per visualizzarne i posti venduti</h4>

                    <div class="row collapse">
                        <div class="medium-6 small-centered columns">
                            <label>Sale cinema
                                <select>
                                    <option><span id="roomNumber"></span></option>
                                </select>
                            </label>
                        </div>
                    </div>

                    <div class="row collapse">
                        <div class="medium-6 small-centered columns">
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
</body>
</html