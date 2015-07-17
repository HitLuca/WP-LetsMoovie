<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius">

    <div class="medium-8 columns">
        <p id="postoSelezionato">Posto 1</p>
    </div>
    <div class="medium-4 columns">
        <select>
            <option selected value="ridotto">Ridotto</option>
            <option value="ridotto3d">Ridotto 3D</option>
            <option value="ridottoUni">Ridotto universitario</option>
            <option value="ridottoUni3d">Ridotto universitario 3D</option>
            <option value="intero">Intero</option>
            <option value="intero3d">Intero 3D</option>
        </select>

        <p>Prezzo:<span id="price"></span></p>
    </div>
</div>
