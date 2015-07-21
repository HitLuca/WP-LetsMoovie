<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row biglietto" id="posto" data-position="">

    <div class="medium-5 columns" id="posizione">
        <span class="radius label">Posizione</span>
        <strong>Fila <span id="rowNumber"></span> Posto <span id="columnNumber"></span></strong>
    </div>
    <div class="medium-5 columns" id="tipologia">
        <span class="radius label">Tipo</span>
        <strong>Tipo <span id="tipo"></span></strong>
    </div>
    <div class="medium-2 columns">
        <span class="info radius label price-tag">Prezzo</span><h4 class="right" id="price"></h4>
    </div>
</div>