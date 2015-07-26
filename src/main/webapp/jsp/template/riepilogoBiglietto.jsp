<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel callout radius row biglietto text-center" id="posto">
    <div class="medium-1 columns hide checkbox-biglietto">
        <input name="seatList[[checked]]:string" type="checkbox" value="true">
        <input type="hidden" name="seatList[[s_row]]" data-bind="s_row_input">
        <input type="hidden" name="seatList[[s_column]]" data-bind="s_column_input">
    </div>
    <div class="medium-3 columns" id="posizione">
        <div class="row collapse">
            <div class="column small-4">
                <span class="radius label">Posizione</span>
            </div>
            <div class="column small-8">
                <strong>Fila <span data-bind="s_row"></span> Posto <span data-bind="s_column"></span></strong>
            </div>
        </div>
    </div>
    <div class="medium-4 columns" id="tipologia">
        <div class="row collapse">
            <div class="columns small-4">
                <span class="radius label">Tipo</span>
            </div>
            <div class="columns small-8">
                <strong><span id="ticket_type"></span></strong>
            </div>
        </div>
    </div>
    <div class="medium-4 columns">
        <div class="row collapse">
            <div class="column small-4">
                <span class="info radius label price-tag">Prezzo</span>
            </div>
            <div class="column small-8">
                <h4 id="price"></h4>
            </div>
        </div>
    </div>
</div>