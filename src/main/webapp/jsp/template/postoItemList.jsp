<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius hide biglietto" id="posto" data-position="">

    <div class="medium-8 columns" id="posizione">
        <span class="radius label">Posizione</span>
        <strong>Fila <span id="rowNumber"></span> Posto <span id="columnNumber"></span></strong>
    </div>
    <div class="medium-4 columns">
        <select name="tickets[][type]" data-bind="tickets">
            <option value="ridotto">Ridotto</option>
            <option value="ridotto3d">Ridotto 3D</option>
            <option value="ridottoUni">Ridotto universitario</option>
            <option value="ridottoUni3d">Ridotto universitario 3D</option>
            <option selected value="intero">Intero</option>
            <option value="intero3d">Intero 3D</option>
        </select>
        <input name="tickets[][column]" type="hidden" value="1" data-seat="column"/>
        <input name="tickets[][row]" type="hidden" value="1" data-seat="row"/>
        <span class="info radius label price-tag">Prezzo</span><h4 class="right" id="price"></h4>
    </div>
</div>
