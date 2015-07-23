<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row panel callout radius hide biglietto" id="posto" data-position="">

    <div class="medium-8 columns" id="posizione">
        <span class="radius label">Posizione</span>
        <strong>Fila <span id="rowNumber"></span> Posto <span id="columnNumber"></span></strong>
    </div>
    <div class="medium-4 columns ticketChoice">
        <label id="ticketsList">Tipologia biglietto
            <select name="seats[][type]" data-bind="tickets" disabled>
                <option value="" data-bind="ticket_type"></option>
            </select>
        </label>
        <input name="seats[][column]" type="hidden" data-seat="column" disabled/>
        <input name="seats[][row]" type="hidden" data-seat="row" disabled/>
        <span class="info radius label price-tag">Prezzo</span><h4 class="right" id="price"></h4>
    </div>
</div>
