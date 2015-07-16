<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="shows" class="text-center">
    <a href=# class="button radius" data-reveal-id="myModal">Orari proiezioni</a>

    <div id="myModal" class="reveal-modal tiny" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
         role="dialog">
        <h4>Orari delle proiezioni:</h4>

        <p></p>

        <div id="drop1" data-bind="orari" aria-hidden="true">
            <div class="row">
                <div class="small-11 small-centered large-6 medium-10 columns">
                    <a href="#" class="small radius button expand" data-bind="show_time"></a>
                </div>
            </div>
        </div>
        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
    </div>
</div>
