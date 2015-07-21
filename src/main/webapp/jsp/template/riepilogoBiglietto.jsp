<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">

    <div class="medium-5 columns">
        <p>Posizione: <span data-bind="postoSelezionato"></span></p>
    </div>
    <div class="medium-5 columns">
        <p>Tipo: <span data-bind="tipoBiglietto"></span></p>
    </div>
    <div class="medium-2 columns">
        <p>Prezzo: <span id="price"></span></p>
    </div>

</div>
