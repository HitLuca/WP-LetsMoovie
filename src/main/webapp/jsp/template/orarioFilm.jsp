<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="shows" class="text-center">
    <a href=# class="button radius" data-reveal-id="myModal">Orari proiezioni</a>

    <div id="myModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
        <ul id="drop1" data-bind="orari" aria-hidden="true">
            <li><a href="#" data-bind="show_time"></a></li>
        </ul>
        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
    </div>
</div>
