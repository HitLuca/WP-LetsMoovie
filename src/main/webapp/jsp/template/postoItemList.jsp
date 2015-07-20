<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius hide" id="posto" data-position="">

    <div class="medium-8 columns">
        <strong></strong>Colonna <span id="columnNumber"></span> Riga <span id="rowNumber"></span>
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

        <p>Prezzo: <span id="price"></span></p>
    </div>
</div>
