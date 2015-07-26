<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div data-bind="shows" class="text-center">
    <a href="#" class="button radius" data-reveal-id="modal" id="openModal">Orari proiezioni</a>

    <div id="modal" class="reveal-modal tiny" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
        <h4>Orari delle proiezioni:</h4>

        <div data-bind="orari" aria-hidden="true">
            <div class="row">
                <div class="small-centered large-6 medium-12 columns">
                    <a href="#" class="small radius button expand" data-bind="show_time"></a>
                </div>
            </div>
        </div>
        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
    </div>
</div>
